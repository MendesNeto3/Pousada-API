package PousadaAPI.dto.response;

import PousadaAPI.domain.enums.MetodoPagamento;
import PousadaAPI.domain.enums.StatusPagamento;
import java.math.BigDecimal;
import java.util.UUID;

public record PagamentoResponse(
       UUID id,
       ReservasResumoDTO reserva,
       BigDecimal valor,
       MetodoPagamento pagamento,
       StatusPagamento statusPagamento
) {
}
