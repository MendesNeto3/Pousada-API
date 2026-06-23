package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Hospede;
import PousadaAPI.dto.dtoEntity.HospedeDTO;
import PousadaAPI.dto.request.CriarHospedeRequestDto;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-23T16:36:23-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class HospedeMapperImpl implements HospedeMapper {

    @Override
    public Hospede toEntity(CriarHospedeRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Hospede hospede = new Hospede();

        hospede.setNome( dto.nome() );
        hospede.setCpf( dto.cpf() );
        hospede.setTelefone( dto.telefone() );
        hospede.setEmail( dto.email() );

        return hospede;
    }

    @Override
    public HospedeDTO toDTO(Hospede hospede) {
        if ( hospede == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;
        String telefone = null;
        String cpf = null;
        String email = null;

        id = hospede.getId();
        nome = hospede.getNome();
        telefone = hospede.getTelefone();
        cpf = hospede.getCpf();
        email = hospede.getEmail();

        HospedeDTO hospedeDTO = new HospedeDTO( id, nome, telefone, cpf, email );

        return hospedeDTO;
    }
}
