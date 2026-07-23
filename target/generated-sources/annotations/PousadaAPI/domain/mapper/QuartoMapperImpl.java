package PousadaAPI.domain.mapper;

import PousadaAPI.domain.enums.StatusQuarto;
import PousadaAPI.domain.enums.TipoQuarto;
import PousadaAPI.domain.model.Quarto;
import PousadaAPI.dto.response.QuartoResponse;
import java.math.BigDecimal;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-23T14:14:30-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class QuartoMapperImpl implements QuartoMapper {

    @Override
    public QuartoResponse toResponse(Quarto quarto) {
        if ( quarto == null ) {
            return null;
        }

        UUID id = null;
        short numero = 0;
        TipoQuarto tipo = null;
        StatusQuarto status = null;
        Short capacidade = null;
        BigDecimal precoNoite = null;

        id = quarto.getId();
        numero = quarto.getNumero();
        tipo = quarto.getTipo();
        status = quarto.getStatus();
        capacidade = quarto.getCapacidade();
        precoNoite = quarto.getPrecoNoite();

        QuartoResponse quartoResponse = new QuartoResponse( id, numero, tipo, status, capacidade, precoNoite );

        return quartoResponse;
    }
}
