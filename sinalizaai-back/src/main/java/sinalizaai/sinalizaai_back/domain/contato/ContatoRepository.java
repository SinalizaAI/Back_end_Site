package sinalizaai.sinalizaai_back.domain.contato;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
    List<Contato> findByLidoFalse();
    List<Contato> findByLidoTrue();
}
