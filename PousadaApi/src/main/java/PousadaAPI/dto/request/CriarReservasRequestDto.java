package PousadaAPI.dto.request;

import PousadaAPI.domain.enums.StatusQuarto;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record CriarReservasRequestDto(
        @NotNull(message = "ID do hóspede já está cadrastado.")
        UUID hospedeId,
        @NotNull(message = "Id do quarto desejado.")
        UUID quartoId,
        @FutureOrPresent
        @NotNull(message = "Não pode ser data passada.")
        LocalDate checkin,
        @NotNull(message = "Deve ser posterior ao Checkin.")
        @Future
        LocalDate checkout,
        @NotNull(message = "Status do quarto")
        StatusQuarto status

) {
}
