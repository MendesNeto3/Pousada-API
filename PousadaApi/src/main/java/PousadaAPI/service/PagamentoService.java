package PousadaAPI.service;
import PousadaAPI.domain.enums.StatusPagamento;
import PousadaAPI.domain.exception.PagamentoDentroDoPrazoException;
import PousadaAPI.domain.exception.PagamentoNaoEncontradoException;
import PousadaAPI.domain.exception.ReservaNaoEncontradaException;
import PousadaAPI.domain.mapper.PagamentoMapper;
import PousadaAPI.domain.mapper.ReservaMapper;
import PousadaAPI.domain.model.Pagamento;
import PousadaAPI.domain.model.Reserva;
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

    public Pagamento registrarPagamento(String id) {
        var idPagamento = UUID.fromString(id);
        Pagamento pagamento = pagamentoRepository
                .findById(idPagamento)
                .orElseThrow(()
                        -> new PagamentoNaoEncontradoException("O pagamento não foi encontrado."));
        if (pagamento.getStatusPagamento().equals(StatusPagamento.cancelado)) {
            throw new PagamentoNaoEncontradoException("O pagamento não pode ser concluído, pois foi cancelado.");
        }
        if (pagamento.getStatusPagamento().equals(StatusPagamento.aprovado)) {
            throw new PagamentoNaoEncontradoException("O pagamento já foi aprovado anteriormente.");
        }
        Reserva reserva = pagamento.getReserva();
        BigDecimal totalPago = pagamentoRepository
                .findByReservaId(reserva.getId())
                .stream()
                .filter(p -> p.getStatusPagamento().equals(StatusPagamento.aprovado))
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAtual = totalPago.add(pagamento.getValor());

        if (totalAtual.compareTo(reserva.getValorTotal()) > 0) {
            throw new PagamentoNaoEncontradoException("O pagamento não foi concluído");
        }
        pagamento.setStatusPagamento(StatusPagamento.aprovado);
        return pagamentoRepository.save(pagamento);
    }

    public BigDecimal CalcularSaldoAberto(String reservaId) {
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

    public Pagamento cancelarPagamentoExpirado (String reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() ->
                        new ReservaNaoEncontradaException("Reserva não encontrada."));
        Pagamento pagamento = pagamentoRepository.findByReserva(reserva);

        LocalDateTime DataPagamento = LocalDateTime.now();
        LocalDateTime tempoLimite = LocalDateTime.now().minusMinutes(10);

        if (DataPagamento.isAfter(tempoLimite)) {
            pagamentoRepository.deletarPagamentosExpirados(pagamento);
        } else {
            throw new PagamentoDentroDoPrazoException("Ainda dentro do prazo.");
        }
        return pagamento;
    }

    public List<Object> listaPagamento(Pagamento pagamento) {
        return pagamentoRepository
                .findByPagamento(pagamento.getId())
                .stream()
                .filter(p -> pagamento.getStatusPagamento().equals(StatusPagamento.aprovado))
                .toList();
    }
}