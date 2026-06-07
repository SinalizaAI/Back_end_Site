package sinalizaai.sinalizaai_back.domain.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    UserDetails findByEmail(String email); // usado pelo Spring Security no login
    Optional<Cliente> findByCnpj(String cnpj);
    boolean existsByEmail(String email);
    boolean existsByCnpj(String cnpj);
}