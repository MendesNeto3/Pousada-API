package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Quarto;
import PousadaAPI.dto.request.CriarQuartoRequestDto;
import PousadaAPI.dto.response.QuartoResponse;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuartoMapper {

    Quarto toEntity(@Valid CriarQuartoRequestDto quartoDTO);

    QuartoResponse toResponse(Quarto quarto);
}