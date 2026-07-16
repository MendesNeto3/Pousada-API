package PousadaAPI.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarFuncionarioRequestDTO(
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail inserido é inválido.")
    String email,

    @NotBlank(message = "O nome é obrigatório")
    String nome,

    @NotBlank(message = "O CPF é obrigatório")
    String cpf,

    @NotBlank(message = "O cargo é obrigatório")
    String cargo,

    @NotBlank(message = "O login/e-mail é obrigatório")
    @Email(message = "O formato do e-mail é inválido")
    String login, // Será usado como o login de acesso do Usuário

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    String senha
) {
}
