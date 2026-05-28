package PousadaAPI.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "Funcionários", schema = "public")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "cargo", nullable = false, length = 30)
    private String cargo;

    @Email
    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Transient
    @Column(name = "ativo", nullable = false)
    private boolean ativo;

}
