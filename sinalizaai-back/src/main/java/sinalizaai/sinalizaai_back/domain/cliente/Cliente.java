package sinalizaai.sinalizaai_back.domain.cliente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sinalizaai.sinalizaai_back.dto.AtualizacaoClienteDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Table(name = "clientes")
@Entity(name = "Cliente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente implements UserDetails {

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

    @Column(length = 100)
    private String cidade;

    @Column(length = 100)
    private String pais;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

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

    public void atualizar(AtualizacaoClienteDTO dto, BCryptPasswordEncoder encoder) {
        if (dto.razaoSocial() != null)    this.razaoSocial    = dto.razaoSocial();
        if (dto.nomeResponsavel() != null) this.nomeResponsavel = dto.nomeResponsavel();
        if (dto.email() != null)          this.email          = dto.email();
        if (dto.telefone() != null)       this.telefone       = dto.telefone();
        if (dto.senha() != null)          this.senhaHash      = encoder.encode(dto.senha());
        if (dto.cidade() != null)           this.cidade           = dto.cidade();   // novo
        if (dto.pais() != null)             this.pais             = dto.pais();     // novo
        if (dto.dataNascimento() != null)   this.dataNascimento   = dto.dataNascimento();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

    @Override
    public String getPassword() {
        return this.senhaHash; // Spring Security usa esse método para validar a senha
    }

    @Override
    public String getUsername() {
        return this.getEmail();  // usamos e-mail como identificador de login
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.ativo;
    }
}