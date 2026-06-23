package PousadaAPI.service;
import PousadaAPI.domain.enums.StatusQuarto;
import PousadaAPI.domain.enums.StatusReserva;
import PousadaAPI.domain.exception.*;
import PousadaAPI.domain.mapper.ReservaMapper;
import PousadaAPI.domain.mapper.ResponseMapper;
import PousadaAPI.domain.model.Hospede;
import PousadaAPI.domain.model.Quarto;
import PousadaAPI.domain.model.Reserva;
import PousadaAPI.dto.dtoEntity.reservaDTO;
import PousadaAPI.dto.request.CriarReservasRequestDto;
import PousadaAPI.repository.HospedeRepository;
import PousadaAPI.repository.QuartoRepository;
import PousadaAPI.repository.ReservaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Data
public class ReservaService {

    private final ReservaRepository repositoryR;
    private final HospedeRepository repositoryH;
    private final ReservaMapper mapper;
    private final ResponseMapper responseMapper;
    private final QuartoRepository repositoryQ;

    
    public Object criarReserva(CriarReservasRequestDto dto) {
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
        reserva = repositoryR.save(reserva);

        return responseMapper.toDto(reserva);
    }

    public Object realizarCheckin (Reserva reserva) {
         Reserva reservaId = repositoryR.findById(reserva.getId())
                 .orElseThrow(()
                         -> new ReservaNaoEncontradaException
                         ("A reserva com este respectivo identificador não foi encontrada"));

        if (!StatusReserva.confirmada.equals(reserva.getStatus())) {
            throw new ReservaJaRealizadaException("Check-in não permitido.");
        }
        if (LocalDate.now().isBefore(reserva.getCheckin())) {
            throw new CheckinAntecipadoException("Não é permitido check-in antecipado.");
        }
        reserva.setStatusReserva(StatusReserva.checkin_realizado);
        reserva.setStatus(StatusQuarto.ocupado);

        return responseMapper.toDto(repositoryR.save(reservaId));
    }
}

