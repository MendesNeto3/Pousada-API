package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Quarto;
import PousadaAPI.dto.response.QuartoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuartoMapper {

    QuartoResponse toResponse(Quarto quarto);
}