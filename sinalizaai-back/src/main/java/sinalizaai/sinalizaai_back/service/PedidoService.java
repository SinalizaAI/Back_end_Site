package sinalizaai.sinalizaai_back.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sinalizaai.sinalizaai_back.domain.cliente.ClienteRepository;
import sinalizaai.sinalizaai_back.domain.pedido.Pedido;
import sinalizaai.sinalizaai_back.domain.pedido.PedidoRepository;
import sinalizaai.sinalizaai_back.dto.CadastroPedidoDTO;
import sinalizaai.sinalizaai_back.dto.PedidoResponseDTO;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public PedidoResponseDTO cadastrar(CadastroPedidoDTO dto) {
        var cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        var pedido = new Pedido(cliente, dto.valorTotal());
        pedidoRepository.save(pedido);
        return new PedidoResponseDTO(pedido);
    }

    public List<PedidoResponseDTO> listar() {
        return pedidoRepository.findAll()
                .stream()
                .map(PedidoResponseDTO::new)
                .toList();
    }

    public List<PedidoResponseDTO> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId)
                .stream()
                .map(PedidoResponseDTO::new)
                .toList();
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        var pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return new PedidoResponseDTO(pedido);
    }

    @Transactional
    public PedidoResponseDTO pagar(Long id) {
        var pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.pagar();
        return new PedidoResponseDTO(pedido);
    }

    @Transactional
    public PedidoResponseDTO cancelar(Long id) {
        var pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.cancelar();
        return new PedidoResponseDTO(pedido);
    }
}
