package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Hospede;
import PousadaAPI.domain.model.Quarto;
import PousadaAPI.domain.model.Reserva;
import PousadaAPI.dto.request.CriarReservasRequestDto;
import PousadaAPI.dto.response.ReservasResumoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring",
        uses = {
                HospedeMapper.class,
                QuartoMapper.class
        }
)
public interface ReservaMapper {
    @Mapping(target = "id",   ignore = true)
    @Mapping(target = "checkin",      source = "dto.checkin")
    @Mapping(target = "checkout",     source = "dto.checkout")
    @Mapping(target = "hospede",      source = "hospede")
    @Mapping(target = "quarto",       source = "quarto")
    @Mapping(target = "funcionario",  ignore = true)
    @Mapping(target = "valorTotal",   ignore = true)
    @Mapping(target = "status",       source = "dto.status")
    Reserva toEntity(CriarReservasRequestDto dto, Hospede hospede, Quarto quarto);

    ReservasResumoDTO toResponse (Reserva reservaSalva);

}