package sinalizaai.sinalizaai_back.domain.contato;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "contatos")
@Entity(name = "Contato")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Column(nullable = false)
    private boolean lido = false;

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    public Contato(String nome, String email, String telefone, String mensagem) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.mensagem = mensagem;
    }

    public void marcarComoLido() {this.lido = true; }

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }
}
