package PousadaAPI.dto.dtoEntity;

import PousadaAPI.domain.enums.MetodoPagamento;
import PousadaAPI.domain.enums.StatusPagamento;
import PousadaAPI.domain.model.Reserva;

import java.math.BigDecimal;

public record PagamentoDTO(
        Reserva reserva,
        MetodoPagamento pagamento,
        BigDecimal valor,
        StatusPagamento status) {
}
