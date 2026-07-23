package PousadaAPI.service;
import PousadaAPI.domain.enums.StatusPagamento;
import PousadaAPI.domain.enums.StatusQuarto;
import PousadaAPI.domain.enums.StatusReserva;
import PousadaAPI.domain.exception.*;
import PousadaAPI.domain.mapper.ReservaMapper;
import PousadaAPI.domain.model.Hospede;
import PousadaAPI.domain.model.Pagamento;
import PousadaAPI.domain.model.Quarto;
import PousadaAPI.domain.model.Reserva;
import PousadaAPI.dto.request.CriarReservasRequestDto;
import PousadaAPI.dto.response.ReservasResumoDTO;
import PousadaAPI.repository.HospedeRepository;
import PousadaAPI.repository.PagamentoRepository;
import PousadaAPI.repository.QuartoRepository;
import PousadaAPI.repository.ReservaRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
@Data
public class ReservaService {
    private final ReservaRepository repositoryR;
    private final HospedeRepository repositoryH;
    private final ReservaMapper mapper;
    private final QuartoRepository repositoryQ;
    private final PagamentoRepository pagamentoRepository;

    public ReservasResumoDTO criarReserva(CriarReservasRequestDto dto) {
        Hospede hospede = repositoryH.findById(dto.hospedeId())
                .orElseThrow(() ->
                        new HospedeNaoEncontradoException("Hóspede não foi encontrado"));
        Quarto quarto = repositoryQ.findById(dto.quartoId())
                .orElseThrow(() ->
                        new QuartoNaoEncontradoException("O Quarto não foi encontrado"));
        StatusQuarto status = StatusQuarto.valueOf(dto.status().toString().toUpperCase());
        if (status == StatusQuarto.ocupado ||
                status == StatusQuarto.limpeza ||
                status == StatusQuarto.reservado) {
            throw new ReservaNaoDisponivelException("O quarto não está disponível no momento.");
        }
        if (LocalDate.now().isAfter(dto.checkin())) {
            throw new HorarioChegadaInvalidoException("A data de check-in não pode ser anterior a hoje.");
        }
        Reserva reserva = mapper.toEntity(dto, hospede, quarto);

        return mapper.toResponse(repositoryR.save(reserva));
    }

    public Object realizarCheckin(Reserva reserva) {
        Reserva reservaId = repositoryR.findById(reserva.getId())
                .orElseThrow(()
                        -> new ReservaNaoEncontradaException
                        ("A reserva com este respectivo identificador não foi encontrada"));

        if (reservaId.getStatusReserva() != StatusReserva.confirmada) {
            throw new ReservaJaRealizadaException("Check-in não permitido.");
        }
        if (LocalDate.now().isBefore(reserva.getCheckin())) {
            throw new CheckinAntecipadoException("Não é permitido check-in antecipado.");
        }
        reserva.setStatusReserva(StatusReserva.checkin_realizado);
        reserva.setStatus(StatusQuarto.ocupado);

        return mapper.toResponse(repositoryR.save(reservaId));
    }

    public ReservasResumoDTO realizarCheckOut(@Valid UUID id) {
        Reserva reserva = repositoryR.findById(id)
                .orElseThrow(()
                        -> new ReservaNaoEncontradaException("A reserva com este respectivio indentificador não foi encontrada."));
        if (reserva.getStatusReserva() != StatusReserva.checkin_realizado) {
            throw new CheckinNaoRealizadoException("O Checkin da respectiva reserva não foi realizado.");
        }
        BigDecimal totalPago = pagamentoRepository
                .findByReservaId(reserva.getId())
                .stream()
                .filter(p -> p.getStatusPagamento() == StatusPagamento.aprovado)
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalPago.compareTo(reserva.getValorTotal()) < 0) {
            BigDecimal saldoEmAberto = reserva.getValorTotal().subtract(totalPago);
            throw new CheckoutIndisponivelException(
                    "Não é possível realizar o checkout. Saldo em aberto: R$ " + saldoEmAberto);
        }
        reserva.setStatusReserva(StatusReserva.checkout_realizado);
        return mapper.toResponse(repositoryR.save(reserva));
    }

    public ReservasResumoDTO cancelarReserva(String id) {
        var idReserva = UUID.fromString(id);
        Reserva reserva = repositoryR.findById(idReserva)
                .orElseThrow(() ->
                        new ReservaNaoEncontradaException("Reserva não encontrada."));
        if (reserva.getStatusReserva().equals(StatusReserva.cancelada)) {
            throw new ReservaNaoDisponivelException("Esta reserva já está cancelada.");
        }
        if (reserva.getStatusReserva().equals(StatusReserva.checkin_realizado)) {
            throw new ReservaNaoDisponivelException(
                    "Não é possível cancelar uma reserva com check-in já realizado.");
        }
        if (reserva.getCheckin().isBefore(LocalDate.now())) {
            throw new DataInvalidaException(
                    "Não é possível cancelar uma reserva cuja data de check-in já passou.");
        }
        reserva.setStatusReserva(StatusReserva.cancelada);
        Reserva reservaAtualizada = repositoryR.save(reserva);

        return mapper.toResponse(reservaAtualizada);
    }
}


