package sinalizaai.sinalizaai_back.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sinalizaai.sinalizaai_back.domain.cliente.ClienteRepository;
import sinalizaai.sinalizaai_back.domain.licenca.Licenca;
import sinalizaai.sinalizaai_back.domain.licenca.LicencaRepository;
import sinalizaai.sinalizaai_back.domain.pedido.PedidoRepository;
import sinalizaai.sinalizaai_back.dto.CadastroLicencaDTO;
import sinalizaai.sinalizaai_back.dto.LicencaResponseDTO;

import java.util.List;

@Service
public class LicencaService {

    @Autowired
    private LicencaRepository licencaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public LicencaResponseDTO gerar(CadastroLicencaDTO dto) {
        var cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        var pedido = pedidoRepository.findById(dto.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        var licenca = new Licenca(
                cliente,
                pedido,
                dto.dataInicio(),
                dto.dataExpiracao()
        );

        licencaRepository.save(licenca);
        return new LicencaResponseDTO(licenca);
    }

    public List<LicencaResponseDTO> listar() {
        return licencaRepository.findAll()
                .stream()
                .map(LicencaResponseDTO::new)
                .toList();
    }

    public List<LicencaResponseDTO> listarPorCliente(Long clienteId) {
        return licencaRepository.findByClienteId(clienteId)
                .stream()
                .map(LicencaResponseDTO::new)
                .toList();
    }

    public LicencaResponseDTO buscarPorChave(String chave) {
        var licenca = licencaRepository.findByChave(chave)
                .orElseThrow(() -> new RuntimeException("Licença não encontrada"));
        return new LicencaResponseDTO(licenca);
    }

    public LicencaResponseDTO buscarPorId(Long id) {
        var licenca = licencaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Licença não encontrada"));
        return new LicencaResponseDTO(licenca);
    }

    @Transactional
    public LicencaResponseDTO desativar(Long id) {
        var licenca = licencaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Licença não encontrada"));
        licenca.desativar();
        return new LicencaResponseDTO(licenca);
    }

    @Transactional
    public LicencaResponseDTO ativar(Long id) {
        var licenca = licencaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Licença não encontrada"));
        licenca.ativar();
        return new LicencaResponseDTO(licenca);
    }


}
