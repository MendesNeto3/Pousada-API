package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Hospede;
import PousadaAPI.dto.response.HospedeResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-23T14:12:35-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class HospedeMapperImpl implements HospedeMapper {

    @Override
    public HospedeResponse toResponse(Hospede hospede) {
        if ( hospede == null ) {
            return null;
        }

        String nome = null;
        String cpf = null;
        String telefone = null;
        String endereco = null;
        String email = null;

        nome = hospede.getNome();
        cpf = hospede.getCpf();
        telefone = hospede.getTelefone();
        endereco = hospede.getEndereco();
        email = hospede.getEmail();

        HospedeResponse hospedeResponse = new HospedeResponse( nome, cpf, telefone, endereco, email );

        return hospedeResponse;
    }
}
