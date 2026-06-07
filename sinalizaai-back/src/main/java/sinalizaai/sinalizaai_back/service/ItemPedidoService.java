package sinalizaai.sinalizaai_back.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sinalizaai.sinalizaai_back.domain.itempedido.ItemPedido;
import sinalizaai.sinalizaai_back.domain.itempedido.ItemPedidoRepository;
import sinalizaai.sinalizaai_back.domain.pedido.PedidoRepository;
import sinalizaai.sinalizaai_back.domain.produto.ProdutoRepository;
import sinalizaai.sinalizaai_back.dto.CadastroItemPedidoDTO;
import sinalizaai.sinalizaai_back.dto.ItemPedidoResponseDTO;

import java.util.List;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public ItemPedidoResponseDTO adicionar(CadastroItemPedidoDTO dto) {
        var pedido = pedidoRepository.findById(dto.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        var produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        var item = new ItemPedido(pedido, produto, dto.quantidade());
        itemPedidoRepository.save(item);
        return new ItemPedidoResponseDTO(item);
    }

    public List<ItemPedidoResponseDTO> listarPorPedido(Long pedidoId) {
        return itemPedidoRepository.findByPedidoId(pedidoId)
                .stream()
                .map(ItemPedidoResponseDTO::new)
                .toList();
    }

    public ItemPedidoResponseDTO buscarPorId(Long id) {
        var item = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        return new ItemPedidoResponseDTO(item);
    }

    @Transactional
    public void remover(Long id) {
        if (!itemPedidoRepository.existsById(id)) {
            throw new RuntimeException("Item não encontrado");
        }
        itemPedidoRepository.deleteById(id);
    }

}
