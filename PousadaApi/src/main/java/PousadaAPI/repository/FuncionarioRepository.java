package PousadaAPI.repository;

import PousadaAPI.domain.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FuncionarioRepository extends JpaRepository <Funcionario, UUID> {
    Optional<Funcionario> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByName(String name);

    Funcionario delete(Funcionario responseDto);
}
