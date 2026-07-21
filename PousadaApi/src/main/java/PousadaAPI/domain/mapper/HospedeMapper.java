package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Hospede;
import PousadaAPI.dto.response.HospedeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HospedeMapper {

    HospedeResponse toResponse (Hospede hospede);
}
