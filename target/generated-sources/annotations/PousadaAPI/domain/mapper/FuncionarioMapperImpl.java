package PousadaAPI.domain.mapper;

import PousadaAPI.domain.model.Funcionario;
import PousadaAPI.dto.response.FuncionarioResponse;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-23T14:12:35-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class FuncionarioMapperImpl implements FuncionarioMapper {

    @Override
    public FuncionarioResponse toResponse(Funcionario funcionario) {
        if ( funcionario == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;
        String cargo = null;
        String cpf = null;
        String email = null;
        boolean ativo = false;

        id = funcionario.getId();
        nome = funcionario.getNome();
        cargo = funcionario.getCargo();
        cpf = funcionario.getCpf();
        email = funcionario.getEmail();
        ativo = funcionario.isAtivo();

        FuncionarioResponse funcionarioResponse = new FuncionarioResponse( id, nome, cargo, cpf, email, ativo );

        return funcionarioResponse;
    }
}
