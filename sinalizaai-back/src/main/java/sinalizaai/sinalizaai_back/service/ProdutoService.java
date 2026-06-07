package sinalizaai.sinalizaai_back.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sinalizaai.sinalizaai_back.domain.produto.Produto;
import sinalizaai.sinalizaai_back.domain.produto.ProdutoRepository;
import sinalizaai.sinalizaai_back.dto.AtualizacaoProdutoDTO;
import sinalizaai.sinalizaai_back.dto.CadastroProdutoDTO;
import sinalizaai.sinalizaai_back.dto.ProdutoResponseDTO;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Transactional
    public ProdutoResponseDTO cadastrar(CadastroProdutoDTO dto) {
        var produto = new Produto(dto.nome(), dto.descricao(), dto.preco());
        repository.save(produto);
        return new ProdutoResponseDTO(produto);
    }

    public List<ProdutoResponseDTO> listar() {
        return repository.findByAtivoTrue()
                .stream()
                .map(ProdutoResponseDTO::new)
                .toList();
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        var produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, AtualizacaoProdutoDTO dto) {
        var produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.atualizar(dto);
        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public void desativar(Long id) {
        var produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.desativar();
    }

}
