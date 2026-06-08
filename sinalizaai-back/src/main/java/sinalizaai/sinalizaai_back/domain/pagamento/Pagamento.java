package sinalizaai.sinalizaai_back.domain.pagamento;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sinalizaai.sinalizaai_back.domain.pedido.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "pagamentos")
@Entity(name = "Pagamento")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(nullable = false, length = 50)
    private String plataforma;

    @Column(name = "transacao_id", nullable = false)
    private String transacaoId;

    @Column(name = "token_cartao")
    private String tokenCartao;

    @Column(length = 20)
    private String bandeira;

    @Column(name = "final_cartao", length = 4)
    private String finalCartao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento status = StatusPagamento.pendente;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    public Pagamento(Pedido pedido, String plataforma, String transacaoId,
                     String bandeira, String finalCartao, BigDecimal valor) {
        this.pedido = pedido;
        this.plataforma = plataforma;
        this.transacaoId = transacaoId;
        this.bandeira = bandeira;
        this.finalCartao = finalCartao;
        this.valor = valor;
    }

    public void aprovar()   { this.status = StatusPagamento.aprovado; }
    public void recusar()   { this.status = StatusPagamento.recusado; }
    public void estornar()  { this.status = StatusPagamento.estornado; }

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
        if (this.status == null) this.status = StatusPagamento.pendente;
    }

}
