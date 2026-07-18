package PousadaAPI.dto.response;

import PousadaAPI.domain.exception.DadosInvalidosException;

public record HospedeResponse(
        String nome,
        String cpf,
        String telefone,
        String endereco,
        String email
) {
    public void validar() {
        if (isVazio(this.nome) || isVazio(this.telefone) || isVazio(this.cpf) || isVazio(this.email)) {
            throw new DadosInvalidosException("Os dados inseridos são inválidos.");
        }
    }
    public boolean isVazio(String valor) {
        return valor == null || valor.isBlank();
    }
}

