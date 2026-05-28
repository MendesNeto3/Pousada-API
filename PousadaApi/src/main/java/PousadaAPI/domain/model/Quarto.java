package PousadaAPI.domain.model;

import PousadaAPI.domain.enums.StatusQuarto;
import PousadaAPI.domain.enums.TipoQuarto;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Quarto", schema = "public")
public class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "numero", nullable = false, unique = true)
    private short numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoQuarto", nullable = false, length = 20)
    private TipoQuarto tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusQuarto")
    private StatusQuarto status;

    @Column(nullable = false)
    private Short capacidade;

    @Column(name = "precoNoite", nullable = false, precision = 8, scale = 2)
    private BigDecimal precoNoite;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Quarto quarto = (Quarto) o;
        return Objects.equals(id, quarto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
