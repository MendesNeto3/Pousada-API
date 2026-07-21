package PousadaAPI.repository;

import PousadaAPI.domain.enums.StatusPagamento;
import PousadaAPI.domain.model.Pagamento;
import PousadaAPI.domain.model.Reserva;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, String> {
    Optional<Object> findById(@Valid Reserva id);
}
