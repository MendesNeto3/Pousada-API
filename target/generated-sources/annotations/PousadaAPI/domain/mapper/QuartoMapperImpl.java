package PousadaAPI.domain.mapper;

import PousadaAPI.domain.enums.StatusQuarto;
import PousadaAPI.domain.enums.TipoQuarto;
import PousadaAPI.domain.model.Quarto;
import PousadaAPI.dto.dtoEntity.QuartoDTO;
import PousadaAPI.dto.request.CriarQuartoRequestDto;
import java.math.BigDecimal;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-23T16:36:22-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class QuartoMapperImpl implements QuartoMapper {

    @Override
    public Quarto toEntity(CriarQuartoRequestDto quartoDTO) {
        if ( quartoDTO == null ) {
            return null;
        }

        Quarto quarto = new Quarto();

        if ( quartoDTO.numero() != null ) {
            quarto.setNumero( quartoDTO.numero() );
        }
        quarto.setTipo( quartoDTO.tipo() );
        quarto.setCapacidade( quartoDTO.capacidade() );
        quarto.setPrecoNoite( quartoDTO.precoNoite() );

        return quarto;
    }

    @Override
    public QuartoDTO toDto(Quarto quarto) {
        if ( quarto == null ) {
            return null;
        }

        UUID id = null;
        short numero = 0;
        TipoQuarto tipo = null;
        Short capacidade = null;
        BigDecimal precoNoite = null;

        id = quarto.getId();
        numero = quarto.getNumero();
        tipo = quarto.getTipo();
        capacidade = quarto.getCapacidade();
        precoNoite = quarto.getPrecoNoite();

        StatusQuarto statusQuarto = null;

        QuartoDTO quartoDTO = new QuartoDTO( id, numero, tipo, capacidade, precoNoite, statusQuarto );

        return quartoDTO;
    }
}
