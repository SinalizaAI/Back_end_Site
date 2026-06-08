package sinalizaai.sinalizaai_back.domain.licenca;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LicencaRepository extends JpaRepository<Licenca, Long> {
    List<Licenca> findByClienteId(Long clienteId);
    Optional<Licenca> findByChave(String chave);
    List<Licenca> findByAtivoTrue();
}
