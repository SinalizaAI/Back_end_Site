package sinalizaai.sinalizaai_back.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AtualizacaoProdutoDTO(
        String nome,
        String descricao,

        @Positive(message = "Preço deve ser positivo")
        BigDecimal preco
) {
}
