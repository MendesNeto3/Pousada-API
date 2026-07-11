package PousadaAPI.repository;

import PousadaAPI.domain.enums.StatusPagamento;
import PousadaAPI.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {
    List<Pagamento> findByReservaId(String reservaId);
    Optional<Pagamento> findByReservaIdAndStatusPagamento(StatusPagamento pagamento, String reservaId);
    List<Pagamento> findByStatusPagamento(StatusPagamento statusPagamento);
}
