package PousadaAPI.repository;

import PousadaAPI.domain.model.Quarto;
import PousadaAPI.dto.response.QuartoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuartoRepository extends JpaRepository<Quarto, UUID> {
    Optional<Quarto> findByNumero(short numero);

    @Query("""
           SELECT r
           FROM Reserva r
           WHERE r.quarto.status = 'ATIVA'
           AND r.checkin >= :inicio
           AND r.checkout <= :fim
           """)
    List<QuartoResponse> listarReservasAtivas(LocalDate inicio, LocalDate fim);


}
