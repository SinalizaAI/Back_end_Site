package sinalizaai.sinalizaai_back.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sinalizaai.sinalizaai_back.domain.pagamento.Pagamento;
import sinalizaai.sinalizaai_back.domain.pagamento.PagamentoRepository;
import sinalizaai.sinalizaai_back.domain.pedido.PedidoRepository;
import sinalizaai.sinalizaai_back.dto.CadastroPagamentoDTO;
import sinalizaai.sinalizaai_back.dto.PagamentoResponseDTO;

import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public PagamentoResponseDTO registrar(CadastroPagamentoDTO dto) {
        var pedido = pedidoRepository.findById(dto.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        var pagamento = new Pagamento(
                pedido,
                dto.plataforma(),
                dto.transacaoId(),
                dto.bandeira(),
                dto.finalCartao(),
                dto.valor()
        );

        pagamentoRepository.save(pagamento);
        return new PagamentoResponseDTO(pagamento);
    }

    public List<PagamentoResponseDTO> listarPorPedido(Long pedidoId) {
        return pagamentoRepository.findByPedidoId(pedidoId)
                .stream()
                .map(PagamentoResponseDTO::new)
                .toList();
    }

    public PagamentoResponseDTO buscarPorId(Long id) {
        var pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        return new PagamentoResponseDTO(pagamento);
    }

    @Transactional
    public PagamentoResponseDTO aprovar(Long id) {
        var pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        pagamento.aprovar();

        // Marca o pedido como pago automaticamente
        pagamento.getPedido().pagar();
        return new PagamentoResponseDTO(pagamento);
    }

    @Transactional
    public PagamentoResponseDTO recusar(Long id) {
        var pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        pagamento.recusar();
        return new PagamentoResponseDTO(pagamento);
    }

    @Transactional
    public PagamentoResponseDTO estornar(Long id) {
        var pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        pagamento.estornar();
        return new PagamentoResponseDTO(pagamento);
    }

}
