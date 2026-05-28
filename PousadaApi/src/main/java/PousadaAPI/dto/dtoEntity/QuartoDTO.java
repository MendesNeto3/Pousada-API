package PousadaAPI.dto.dtoEntity;

import PousadaAPI.domain.enums.StatusQuarto;
import PousadaAPI.domain.enums.TipoQuarto;

import java.math.BigDecimal;
import java.util.UUID;

public record QuartoDTO(
        UUID id,
        short numero,
        TipoQuarto tipo,
        Short capacidade,
        BigDecimal precoNoite,
        StatusQuarto statusQuarto
) {
}