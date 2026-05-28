package PousadaAPI.dto.dtoEntity;
import java.util.UUID;

public record HospedeDTO(
        UUID id,
        String nome,
        String telefone,
        String cpf,
        String email
) {
}
