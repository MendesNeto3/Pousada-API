package PousadaAPI.repository;

import PousadaAPI.domain.model.Hospede;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, UUID> {

    boolean existsByCpf(@NotBlank(message = "CPF já está cadastrado") String cpf);

    boolean existsByEmail(@NotBlank(message = "Email já está cadastrado") String dto);

    Optional<Hospede> findByNome(String nome);

}
