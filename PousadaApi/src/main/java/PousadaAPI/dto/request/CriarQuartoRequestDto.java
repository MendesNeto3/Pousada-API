package PousadaAPI.dto.request;

import PousadaAPI.domain.enums.TipoQuarto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CriarQuartoRequestDto(
        @JsonProperty("numero")
        @NotNull(message = "O número do quarto é obrigatório.")
        Short numero,
        @JsonProperty("tipo")
        TipoQuarto tipo,
        @JsonProperty("capacidade")
        Short capacidade,
        @JsonProperty("precoNoite")
        BigDecimal precoNoite
) {
}
