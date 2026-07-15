package PousadaAPI.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarFuncionarioRequestDTO(
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O e-mail inserido é inválido.")
        String email,

        @NotBlank(message = "nome do funcionario")
        String nome,

        @NotBlank(message = "cargo do funcionario")
        String cargo,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres.")
        String senha
) {
}
