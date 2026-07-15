package PousadaAPI.repository;

import PousadaAPI.domain.enums.StatusPagamento;
import PousadaAPI.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {
    List<Pagamento> findByReservaId(String reservaId);
    List<Pagamento> findByStatusPagamento(StatusPagamento statusPagamento);
    List<Pagamento> findByReservaIdAndStatusPagamento(String reservaId, boolean status);
}
