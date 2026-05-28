package PousadaAPI.dto.response;

import PousadaAPI.dto.dtoEntity.HospedeDTO;
import PousadaAPI.dto.dtoEntity.QuartoDTO;

public record ResponseDto(
        HospedeDTO hospede,
        QuartoDTO quarto
) {
}
