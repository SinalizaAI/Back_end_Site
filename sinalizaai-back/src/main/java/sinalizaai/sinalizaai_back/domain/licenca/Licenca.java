package sinalizaai.sinalizaai_back.domain.licenca;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sinalizaai.sinalizaai_back.domain.cliente.Cliente;
import sinalizaai.sinalizaai_back.domain.pedido.Pedido;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "licencas")
@Entity(name = "Licenca")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Licenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private String chave;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_expiracao")
    private LocalDateTime dataExpiracao;

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    public Licenca(Cliente cliente, Pedido pedido, LocalDateTime dataInicio,
                   LocalDateTime dataExpiracao) {
        this.cliente = cliente;
        this.pedido = pedido;
        this.chave = UUID.randomUUID().toString(); // gera chave única automaticamente
        this.dataInicio = dataInicio;
        this.dataExpiracao = dataExpiracao;
    }

    public void desativar() {this.ativo = false;}
    public void ativar() {this.ativo = true;}

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }
}
