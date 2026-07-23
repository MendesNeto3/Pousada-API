package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Hospede;
import PousadaAPI.domain.model.Quarto;
import PousadaAPI.domain.model.Reserva;
import PousadaAPI.dto.request.CriarReservasRequestDto;
import PousadaAPI.dto.response.ReservasResumoDTO;
import java.time.LocalDate;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-23T14:12:34-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class ReservaMapperImpl implements ReservaMapper {

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
    public ReservasResumoDTO toResponse(Reserva reservaSalva) {
        if ( reservaSalva == null ) {
            return null;
        }

        UUID id = null;
        LocalDate checkin = null;
        LocalDate checkout = null;

        id = reservaSalva.getId();
        checkin = reservaSalva.getCheckin();
        checkout = reservaSalva.getCheckout();

        ReservasResumoDTO reservasResumoDTO = new ReservasResumoDTO( id, checkin, checkout );

        return reservasResumoDTO;
    }
}
