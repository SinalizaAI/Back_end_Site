package sinalizaai.sinalizaai_back.dto;

import sinalizaai.sinalizaai_back.domain.produto.Produto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean ativo
) {
    public ProdutoResponseDTO(Produto produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getAtivo()
        );
    }
}
