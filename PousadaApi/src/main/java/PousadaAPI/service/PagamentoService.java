package PousadaAPI.service;
import PousadaAPI.domain.enums.StatusPagamento;
import PousadaAPI.domain.exception.PagamentoDentroDoPrazoException;
import PousadaAPI.domain.exception.ReservaNaoEncontradaException;
import PousadaAPI.domain.exception.ValorQuartoInvalidoException;
import PousadaAPI.domain.mapper.PagamentoMapper;
import PousadaAPI.domain.mapper.ReservaMapper;
import PousadaAPI.domain.model.Pagamento;
import PousadaAPI.domain.model.Reserva;
import PousadaAPI.dto.request.CriarPagamentoRequest;
import PousadaAPI.dto.response.PagamentoResponse;
import PousadaAPI.repository.PagamentoRepository;
import PousadaAPI.repository.ReservaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Data
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final PagamentoMapper pagamentoMapper;

    public PagamentoResponse registrarPagamento(CriarPagamentoRequest request) {
        Reserva reserva = reservaRepository.findById(request.reservaId())
                .orElseThrow(() -> new ReservaNaoEncontradaException("Reserva não encontrada."));

        BigDecimal totalPago = pagamentoRepository.findByReservaId(reserva.getId() )
                .stream()
                .filter(p -> p.getStatusPagamento() == StatusPagamento.aprovado)
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAtual = totalPago.add(request.valor());

        if (totalAtual.compareTo(reserva.getValorTotal()) > 0) {
            throw new ValorQuartoInvalidoException("O valor ultrapassa o total da reserva.");
        }

        Pagamento pagamento = Pagamento.builder()
                .reserva(reserva)
                .valor(request.valor())
                .pagamento(request.metodoPagamento())
                .statusPagamento(StatusPagamento.aprovado)
                .build();

        return pagamentoMapper.toResponse(pagamentoRepository.save(pagamento));
    }

    public BigDecimal CalcularSaldoAberto(UUID reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() ->
                        new ReservaNaoEncontradaException("Reserva não encontrada."));
        BigDecimal pagamentoTotal = pagamentoRepository
                .findByStatusPagamento(StatusPagamento.aprovado)
                .stream()
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return reserva.getValorTotal().subtract(pagamentoTotal);
    }

    public PagamentoResponse cancelarPagamentoExpirado (UUID reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() ->
                        new ReservaNaoEncontradaException("Reserva não encontrada."));
        Pagamento pagamento = pagamentoRepository.findByReserva(reserva);

        LocalDateTime tempoLimite = pagamento.getDataGeracao().plusMinutes(10);

        if (LocalDateTime.now().isAfter(tempoLimite)) {
            pagamentoRepository.deletarPagamentosExpirados(pagamento);
            pagamentoMapper.toResponse(pagamento);
        } else {
            throw new PagamentoDentroDoPrazoException("Ainda dentro do prazo.");
        }
        return null;
    }

    public List<Object> listaPagamento(PagamentoResponse pagamentoResponse) {
        return pagamentoRepository
                .findByPagamento(pagamentoResponse.id())
                .stream()
                .filter(p -> pagamentoResponse.statusPagamento().equals(StatusPagamento.aprovado))
                .toList();
    }
}