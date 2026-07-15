package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Hospede;
import PousadaAPI.domain.model.Quarto;
import PousadaAPI.domain.model.Reserva;
import PousadaAPI.dto.dtoEntity.HospedeDTO;
import PousadaAPI.dto.dtoEntity.QuartoDTO;
import PousadaAPI.dto.request.CriarReservasRequestDto;
import PousadaAPI.dto.response.ResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-11T14:48:23-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class ReservaMapperImpl implements ReservaMapper {

    @Autowired
    private HospedeMapper hospedeMapper;
    @Autowired
    private QuartoMapper quartoMapper;

    @Override
    public Reserva toEntity(CriarReservasRequestDto dto, Hospede hospede, Quarto quarto) {
        if ( dto == null && hospede == null && quarto == null ) {
            return null;
        }

        Reserva reserva = new Reserva();

        if ( dto != null ) {
            reserva.setCheckin( dto.checkin() );
            reserva.setCheckout( dto.checkout() );
            reserva.setStatus( dto.status() );
        }
        reserva.setHospede( hospede );
        reserva.setQuarto( quarto );

        return reserva;
    }

    @Override
    public ResponseDto toDto(Reserva reservaSalva) {
        if ( reservaSalva == null ) {
            return null;
        }

        HospedeDTO hospede = null;
        QuartoDTO quarto = null;

        hospede = hospedeMapper.toDTO( reservaSalva.getHospede() );
        quarto = quartoMapper.toDto( reservaSalva.getQuarto() );

        ResponseDto responseDto = new ResponseDto( hospede, quarto );

        return responseDto;
    }
}
