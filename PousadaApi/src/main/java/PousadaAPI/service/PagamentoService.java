package PousadaAPI.service;
import PousadaAPI.domain.enums.StatusPagamento;
import PousadaAPI.domain.exception.PagamentoNaoEncontradoException;
import PousadaAPI.domain.exception.ReservaNaoEncontradaException;
import PousadaAPI.domain.mapper.ResponseMapper;
import PousadaAPI.domain.model.Pagamento;
import PousadaAPI.domain.model.Reserva;
import PousadaAPI.repository.PagamentoRepository;
import PousadaAPI.repository.ReservaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
@Data
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ReservaRepository reservaRepository;
    private final ResponseMapper  responseMapper;

    public Pagamento registrarPagamento (String id) {
        var idPagamento = UUID.fromString(id);
        Pagamento pagamento = pagamentoRepository
                .findById(idPagamento)
                .orElseThrow(()
                        -> new PagamentoNaoEncontradoException("O pagamento não foi encontrado."));
        Pagamento pagamentoDTO = responseMapper.toDTO(pagamento);
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
        pagamentoDTO.setStatusPagamento(StatusPagamento.aprovado);

        return pagamentoRepository.save(pagamentoDTO);
    }

    public Object CalcularSaldoAberto (String reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(()->
                        new ReservaNaoEncontradaException("Reserva não encontrada."));
        BigDecimal pagamentoTotal = pagamentoRepository
                .findByReservaIdAndStatusPagamento(StatusPagamento.aprovado, reservaId)
                .stream()
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return reserva.getValorTotal().subtract(pagamentoTotal);
    }

    public Object excluirPagamento (String reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(()->
                        new ReservaNaoEncontradaException("Reserva não encontrada."));
        reservaRepository.delete(reserva);
        return responseMapper.toDto(reserva);
    }
}