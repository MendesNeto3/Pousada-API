package PousadaAPI.repository;

import PousadaAPI.domain.model.Reserva;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReservaRepository extends JpaRepository<Reserva, UUID> {
    Optional<Object> findById(@Valid Reserva id);

}
