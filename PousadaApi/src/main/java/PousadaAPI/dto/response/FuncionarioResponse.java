package PousadaAPI.dto.response;

import java.util.UUID;

public record FuncionarioResponse (
        UUID id,
        String nome,
        String cargo,
        String cpf,
        String email,
        boolean ativo
){
}
