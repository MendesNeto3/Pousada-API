package PousadaAPI.repository;

import PousadaAPI.domain.enums.StatusPagamento;
import PousadaAPI.domain.model.Pagamento;
import PousadaAPI.domain.model.Reserva;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {

    @Modifying
    @Transactional
    static void deletarPagamentosExpirados(Pagamento tempoLimite) {
    }
    List<Pagamento> findByReservaId(String reservaId);
    List<Pagamento> findByStatusPagamento(StatusPagamento statusPagamento);
    List<Pagamento> findByReservaIdAndStatusPagamento(String reservaId, boolean status);
    @Override
    Optional<Pagamento> findById(UUID uuid);
    Pagamento findByReserva (Reserva reserva);

    Collection<Object> findByPagamento(UUID id);
}
