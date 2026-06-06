package sinalizaai.sinalizaai_back.service;

import sinalizaai.sinalizaai_back.dto.AtualizacaoClienteDTO;
import sinalizaai.sinalizaai_back.dto.CadastroClienteDTO;
import sinalizaai.sinalizaai_back.domain.cliente.Cliente;
import sinalizaai.sinalizaai_back.domain.cliente.ClienteRepository;
import sinalizaai.sinalizaai_back.dto.ClienteResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Cadastrar novo cliente
    @Transactional
    public ClienteResponseDTO cadastrar(CadastroClienteDTO dto) {

        if (repository.existsByEmail(dto.email())) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        if (repository.existsByCnpj(dto.cnpj())) {
            throw new RuntimeException("CNPJ já cadastrado");
        }

        var cliente = new Cliente(
                dto.razaoSocial(),
                dto.cnpj(),
                dto.nomeResponsavel(),
                dto.email(),
                passwordEncoder.encode(dto.senha()),  // senha com hash BCrypt
                dto.telefone()
        );

        repository.save(cliente);
        return new ClienteResponseDTO(cliente);
    }

    // Listar todos os clientes
    public List<ClienteResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(ClienteResponseDTO::new)
                .toList();
    }

    // Buscar cliente por ID
    public ClienteResponseDTO buscarPorId(Long id) {
        var cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return new ClienteResponseDTO(cliente);
    }

    // Desativar cliente (soft delete)
    @Transactional
    public void desativar(Long id) {
        var cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cliente.desativar();
    }

    //Atualizar dados
    @Transactional
    public ClienteResponseDTO atualizar(Long id, AtualizacaoClienteDTO dto) {
        var cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Verifica se o novo e-mail já pertence a outro cliente
        if (dto.email() != null && !dto.email().equals(cliente.getEmail())) {
            if (repository.existsByEmail(dto.email())) {
                throw new RuntimeException("E-mail já está em uso");
            }
        }

        cliente.atualizar(dto, passwordEncoder);
        return new ClienteResponseDTO(cliente);
    }
}