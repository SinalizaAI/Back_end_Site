package sinalizaai.sinalizaai_back.domain.cliente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Table(name = "clientes")
@Entity(name = "Cliente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(name = "nome_responsavel", nullable = false)
    private String nomeResponsavel;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Column(nullable = false)
    private Boolean ativo = true;          // tinyint(1) → Boolean

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
        if (this.ativo == null) this.ativo = true;
    }

    // Construtor usado pelo Service no cadastro
    public Cliente(String razaoSocial, String cnpj, String nomeResponsavel,
                   String email, String senhaHash, String telefone) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.nomeResponsavel = nomeResponsavel;
        this.email = email;
        this.senhaHash = senhaHash;
        this.telefone = telefone;
    }

    // Soft delete — não apaga do banco, só desativa
    public void desativar() {
        this.ativo = false;
    }
}