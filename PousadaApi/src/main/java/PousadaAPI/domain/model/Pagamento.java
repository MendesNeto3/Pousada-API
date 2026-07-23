package PousadaAPI.domain.model;
import PousadaAPI.domain.enums.MetodoPagamento;
import PousadaAPI.domain.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagamentos", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "pagamento", nullable = false, length = 20)
    private MetodoPagamento pagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento",length = 20)
    private StatusPagamento statusPagamento;

    @Column(name = "data_geracao", nullable = false, updatable = false)
    private LocalDateTime dataGeracao;
}
