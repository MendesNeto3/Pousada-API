package PousadaAPI.dto.dtoEntity;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
     @NotBlank(message = "O campo login é obrigatório")
     String login,
     @NotBlank(message = "A senha é obrigatória")
     String senha
) {
}
