package sinalizaai.sinalizaai_back.domain.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByPedidoId(Long pedidoId);
    List<Pagamento> findByStatus(StatusPagamento status);
}
