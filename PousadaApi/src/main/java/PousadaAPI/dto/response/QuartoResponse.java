package PousadaAPI.dto.response;

import PousadaAPI.domain.enums.StatusQuarto;
import PousadaAPI.domain.enums.TipoQuarto;

import java.math.BigDecimal;
import java.util.UUID;

public record QuartoResponse(
        UUID id,
        short numero,
        TipoQuarto tipoQuarto,
        StatusQuarto statusQuarto,
        Short capacidade,
        BigDecimal precNoite
) {
}
