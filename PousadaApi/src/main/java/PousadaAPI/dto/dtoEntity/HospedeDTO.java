package PousadaAPI.dto.dtoEntity;
import PousadaAPI.domain.exception.DadosInvalidosException;

import java.util.UUID;

public record HospedeDTO(
        UUID id,
        String nome,
        String telefone,
        String cpf,
        String email
) {
    public void validar() {
        if (isVazio(this.nome) || isVazio(this.telefone) || isVazio(this.cpf) || isVazio(this.email)) {
            throw new DadosInvalidosException("Os dados inseridos são inválidos.");
        }
    }
    public boolean isVazio (String valor) {
        return valor == null | valor.isEmpty();
    }
}
