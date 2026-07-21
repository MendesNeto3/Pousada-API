package PousadaAPI.repository;

import PousadaAPI.domain.enums.StatusPagamento;
import PousadaAPI.domain.model.Pagamento;
import PousadaAPI.domain.model.Reserva;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {

    Pagamento findByReserva(Reserva reserva);

    List<Pagamento> findByReservaId(String reservaId);

    List<Pagamento> findByStatusPagamento(StatusPagamento statusPagamento);

    @Modifying
    @Transactional
    @Query("DELETE FROM Pagamento p WHERE p.dataGeracao < :tempoLimite")
    void deletarPagamentosExpirados(@Param("tempoLimite") Pagamento tempoLimite);

    Collection<Object> findByPagamento(UUID id);
}