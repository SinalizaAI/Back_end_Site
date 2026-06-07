package sinalizaai.sinalizaai_back.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sinalizaai.sinalizaai_back.domain.produto.ProdutoRepository;
import sinalizaai.sinalizaai_back.dto.AtualizacaoProdutoDTO;
import sinalizaai.sinalizaai_back.dto.CadastroProdutoDTO;
import sinalizaai.sinalizaai_back.dto.ProdutoResponseDTO;
import sinalizaai.sinalizaai_back.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Gerenciamento de produtos SinalizaAI")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping
    @Operation(summary = "Cadastrar produto",
            description = "Cria um novo produto no sistema")
    public ResponseEntity<ProdutoResponseDTO> cadastrar(
            @RequestBody @Valid CadastroProdutoDTO dto)    {
                return ResponseEntity.status(201).body(service.cadastrar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar produtos ativos",
            description = "Lista os produtos ativos no sistema")
    public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID",
            description = "Busca os produtos ativos no sistema pelo ID")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto",
            description = "Atualiza o produto no sistema pelo ID")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AtualizacaoProdutoDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar produto",
            description = "Desativa o produto no sistema pelo ID")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
