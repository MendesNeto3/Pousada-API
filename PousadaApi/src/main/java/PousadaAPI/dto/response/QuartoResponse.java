package PousadaAPI.dto.response;

import PousadaAPI.domain.enums.StatusQuarto;
import PousadaAPI.domain.enums.TipoQuarto;

import java.math.BigDecimal;
import java.util.UUID;

public record QuartoResponse(
        UUID id,
        short numero,
        TipoQuarto tipo,
        StatusQuarto status,
        Short capacidade,
        BigDecimal precoNoite
) {
}
