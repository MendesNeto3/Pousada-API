package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Hospede;
import PousadaAPI.domain.model.Quarto;
import PousadaAPI.domain.model.Reserva;
import PousadaAPI.dto.dtoEntity.HospedeDTO;
import PousadaAPI.dto.request.CriarQuartoRequestDto;
import PousadaAPI.dto.response.ResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {
                HospedeMapper.class,
                QuartoMapper.class
        }
)
public interface ResponseMapper {

  ResponseDto toDto(Reserva reserva);

  Hospede toDTO (ResponseDto responseDto );

  HospedeDTO toResponse (ResponseDto responseDto);

  ResponseDto toResponse (Hospede hospede);

  ResponseDto toResponse (Quarto quarto);

  Quarto  toDTO(CriarQuartoRequestDto dto);
}
