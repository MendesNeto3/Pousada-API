package PousadaAPI.dto.request;

import org.hibernate.validator.constraints.br.CPF;

public record CriarHospedeRequestDto(
        String nome,
        @CPF
        String cpf,
        String email,
        String telefone
) {
}
