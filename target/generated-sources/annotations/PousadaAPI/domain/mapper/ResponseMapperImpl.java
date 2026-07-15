package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Hospede;
import PousadaAPI.domain.model.Pagamento;
import PousadaAPI.domain.model.Quarto;
import PousadaAPI.domain.model.Reserva;
import PousadaAPI.dto.dtoEntity.HospedeDTO;
import PousadaAPI.dto.dtoEntity.QuartoDTO;
import PousadaAPI.dto.response.ResponseDto;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-11T14:48:24-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class ResponseMapperImpl implements ResponseMapper {

    @Autowired
    private HospedeMapper hospedeMapper;
    @Autowired
    private QuartoMapper quartoMapper;

    @Override
    public ResponseDto toDto(Reserva reserva) {
        if ( reserva == null ) {
            return null;
        }

        HospedeDTO hospede = null;
        QuartoDTO quarto = null;

        hospede = hospedeMapper.toDTO( reserva.getHospede() );
        quarto = quartoMapper.toDto( reserva.getQuarto() );

        ResponseDto responseDto = new ResponseDto( hospede, quarto );

        return responseDto;
    }

    @Override
    public ResponseDto toResponse(Hospede hospede) {
        if ( hospede == null ) {
            return null;
        }

        HospedeDTO hospede1 = null;

        hospede1 = hospedeMapper.toDTO( hospede );

        QuartoDTO quarto = null;

        ResponseDto responseDto = new ResponseDto( hospede1, quarto );

        return responseDto;
    }

    @Override
    public ResponseDto toResponse(Quarto quarto) {
        if ( quarto == null ) {
            return null;
        }

        QuartoDTO quarto1 = null;

        quarto1 = quartoMapper.toDto( quarto );

        HospedeDTO hospede = null;

        ResponseDto responseDto = new ResponseDto( hospede, quarto1 );

        return responseDto;
    }

    @Override
    public Pagamento toDTO(Pagamento pagamento) {
        if ( pagamento == null ) {
            return null;
        }

        Pagamento pagamento1 = new Pagamento();

        pagamento1.setId( pagamento.getId() );
        pagamento1.setReserva( pagamento.getReserva() );
        pagamento1.setValor( pagamento.getValor() );
        pagamento1.setPagamento( pagamento.getPagamento() );
        pagamento1.setStatusPagamento( pagamento.getStatusPagamento() );

        return pagamento1;
    }

    @Override
    public Pagamento toDTO(BigDecimal saldoAberto) {
        if ( saldoAberto == null ) {
            return null;
        }

        Pagamento pagamento = new Pagamento();

        return pagamento;
    }
}
