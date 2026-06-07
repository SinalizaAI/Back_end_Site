package sinalizaai.sinalizaai_back.domain.produto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sinalizaai.sinalizaai_back.dto.AtualizacaoProdutoDTO;

import java.math.BigDecimal;

@Table(name = "produtos")
@Entity(name = "Produto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Boolean ativo = true;

    public Produto(String nome, String descricao, BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public void atualizar(AtualizacaoProdutoDTO dto) {
        if (dto.nome() != null)      this.nome      = dto.nome();
        if (dto.descricao() != null) this.descricao = dto.descricao();
        if (dto.preco() != null)     this.preco     = dto.preco();
    }

    public void desativar() { this.ativo = false; }


}
