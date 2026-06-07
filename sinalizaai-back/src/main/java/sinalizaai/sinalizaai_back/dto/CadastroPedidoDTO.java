package sinalizaai.sinalizaai_back.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record CadastroPedidoDTO(
        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        @NotNull(message = "Itens são obrigatórios")
        List<Long> produtoIds,

        @NotNull(message = "Valor total é obrigatório")
        @Positive(message = "Valor total deve ser positivo")
        BigDecimal valorTotal
) {
}
