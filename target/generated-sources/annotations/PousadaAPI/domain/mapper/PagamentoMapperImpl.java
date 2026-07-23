package PousadaAPI.domain.mapper;

import PousadaAPI.domain.enums.MetodoPagamento;
import PousadaAPI.domain.enums.StatusPagamento;
import PousadaAPI.domain.model.Pagamento;
import PousadaAPI.dto.response.PagamentoResponse;
import PousadaAPI.dto.response.ReservasResumoDTO;
import java.math.BigDecimal;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-23T14:12:35-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class PagamentoMapperImpl implements PagamentoMapper {

    @Autowired
    private ReservaMapper reservaMapper;

    @Override
    public PagamentoResponse toResponse(Pagamento pagamentoResponse) {
        if ( pagamentoResponse == null ) {
            return null;
        }

        UUID id = null;
        ReservasResumoDTO reserva = null;
        BigDecimal valor = null;
        MetodoPagamento pagamento = null;
        StatusPagamento statusPagamento = null;

        id = pagamentoResponse.getId();
        reserva = reservaMapper.toResponse( pagamentoResponse.getReserva() );
        valor = pagamentoResponse.getValor();
        pagamento = pagamentoResponse.getPagamento();
        statusPagamento = pagamentoResponse.getStatusPagamento();

        PagamentoResponse pagamentoResponse1 = new PagamentoResponse( id, reserva, valor, pagamento, statusPagamento );

        return pagamentoResponse1;
    }
}
