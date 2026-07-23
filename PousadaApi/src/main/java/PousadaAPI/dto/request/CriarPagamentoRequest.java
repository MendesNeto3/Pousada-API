package PousadaAPI.dto.request;

import PousadaAPI.domain.enums.MetodoPagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CriarPagamentoRequest(
        @NotNull(message = "ID da reserva é obrigatório.")
        UUID reservaId,

        @NotNull(message = "O método de pagamento é obrigatório.")
        MetodoPagamento metodoPagamento,

        @NotNull(message = "O valor do pagamento é obrigatório.")
        @Positive(message = "O valor deve ser positivo.")
        BigDecimal valor


) {}
