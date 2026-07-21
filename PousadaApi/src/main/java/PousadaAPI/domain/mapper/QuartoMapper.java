package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Quarto;
import PousadaAPI.dto.request.CriarQuartoRequestDto;
import PousadaAPI.dto.response.QuartoResponse;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuartoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    Quarto toEntity(@Valid CriarQuartoRequestDto quartoDTO);

    QuartoResponse toResponse(Quarto quarto);
}