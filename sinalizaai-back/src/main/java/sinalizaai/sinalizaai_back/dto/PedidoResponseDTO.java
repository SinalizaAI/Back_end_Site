package sinalizaai.sinalizaai_back.dto;

import sinalizaai.sinalizaai_back.domain.pedido.Pedido;
import sinalizaai.sinalizaai_back.domain.pedido.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponseDTO(
        Long id,
        Long clienteId,
        String nomeCliente,
        StatusPedido status,
        BigDecimal valorTotal,
        LocalDateTime criadoEm
) {
    public PedidoResponseDTO(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNomeResponsavel(),
                pedido.getStatus(),
                pedido.getValorTotal(),
                pedido.getCriadoEm()
        );
    }
}
