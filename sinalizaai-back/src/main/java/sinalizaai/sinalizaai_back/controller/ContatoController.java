package sinalizaai.sinalizaai_back.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sinalizaai.sinalizaai_back.dto.CadastroContatoDTO;
import sinalizaai.sinalizaai_back.dto.ContatoResponseDTO;
import sinalizaai.sinalizaai_back.service.ContatoService;

import java.util.List;

@RestController
@RequestMapping("/api/contatos")
@Tag(name = "Contatos", description = "Gerenciamento de contatos do site")
public class ContatoController {

    @Autowired
    private ContatoService service;

    @PostMapping
    @Operation(summary = "Enviar mensagem de contato", description = "Envia uma mensagem")
    public ResponseEntity<ContatoResponseDTO> enviar(
            @RequestBody @Valid CadastroContatoDTO dto) {
        return ResponseEntity.status(201).body(service.enviar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos os contatos", description = "Lista as mensagems de todos os contatos")
    public ResponseEntity<List<ContatoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/nao-lidos")
    @Operation(summary = "Listar contatos não lidos", description = "Lista as mensagens de contatos não lidos")
    public ResponseEntity<List<ContatoResponseDTO>> listarNaoLidos() {
        return ResponseEntity.ok(service.listarNaoLidos());
    }

    @GetMapping("/lidos")
    @Operation(summary = "Listar contatos lidos", description = "Lista as mensagens de contatos já lidas")
    public ResponseEntity<List<ContatoResponseDTO>> listarLidos() {
        return ResponseEntity.ok(service.listarLidos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar contato por ID", description = "Busca a mensagem de contato pelo Id")
    public ResponseEntity<ContatoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PatchMapping("/{id}/lido")
    @Operation(summary = "Marcar contato como lido", description = "Marca a mensagem de contato como lida")
    public ResponseEntity<ContatoResponseDTO> marcarComoLido(@PathVariable Long id) {
        return ResponseEntity.ok(service.marcarComoLido(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar contato", description = "Deleta uma mensagem de contato")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
