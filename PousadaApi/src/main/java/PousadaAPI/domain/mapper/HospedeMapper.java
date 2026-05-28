package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Hospede;
import PousadaAPI.dto.dtoEntity.HospedeDTO;
import PousadaAPI.dto.request.CriarHospedeRequestDto;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HospedeMapper {

    @Mapping(target = "endereco", ignore = true)
    Hospede toEntity (@Valid CriarHospedeRequestDto dto);

    HospedeDTO toDTO (Hospede hospede);
}
