package sinalizaai.sinalizaai_back.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sinalizaai.sinalizaai_back.domain.contato.Contato;
import sinalizaai.sinalizaai_back.domain.contato.ContatoRepository;
import sinalizaai.sinalizaai_back.dto.CadastroContatoDTO;
import sinalizaai.sinalizaai_back.dto.ContatoResponseDTO;

import java.util.List;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository repository;

    @Transactional
    public ContatoResponseDTO enviar(CadastroContatoDTO dto) {
        var contato = new Contato(
                dto.nome(),
                dto.email(),
                dto.telefone(),
                dto.mensagem()
        );
        repository.save(contato);
        return new ContatoResponseDTO(contato);
    }

    public List<ContatoResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(ContatoResponseDTO::new)
                .toList();
    }

    public List<ContatoResponseDTO> listarNaoLidos() {
        return repository.findByLidoFalse()
                .stream()
                .map(ContatoResponseDTO::new)
                .toList();
    }

    public List<ContatoResponseDTO> listarLidos() {
        return repository.findByLidoTrue()
                .stream()
                .map(ContatoResponseDTO::new)
                .toList();
    }

    public ContatoResponseDTO buscarPorId(Long id) {
        var contato = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));
        return new ContatoResponseDTO(contato);
    }

    @Transactional
    public ContatoResponseDTO marcarComoLido(Long id) {
        var contato = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));
        contato.marcarComoLido();
        return new ContatoResponseDTO(contato);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Contato não encontrado");
        }
        repository.deleteById(id);
    }
}
