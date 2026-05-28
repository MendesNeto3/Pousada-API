package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Quarto;
import PousadaAPI.dto.dtoEntity.QuartoDTO;
import PousadaAPI.dto.request.CriarQuartoRequestDto;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuartoMapper {

    Quarto toEntity(@Valid CriarQuartoRequestDto quartoDTO);

    QuartoDTO toDto(Quarto quarto);
}