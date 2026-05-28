package PousadaAPI.dto.response;
import java.time.LocalDate;
import java.util.UUID;

public record ReservasResumoDTO(
        UUID id,
        LocalDate checkin,
        LocalDate checkout
) {
}
