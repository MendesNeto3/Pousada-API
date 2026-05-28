package PousadaAPI.domain.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TipoQuarto {
    @JsonProperty("solteiro")
    solteiro,
    @JsonProperty("duplo")
    duplo,@JsonProperty("casal")
    casal,@JsonProperty("quadruplo")
    quadruplo,
}
