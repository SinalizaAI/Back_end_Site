package sinalizaai.sinalizaai_back.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sinalizaai.sinalizaai_back.dto.AtualizacaoClienteDTO;
import sinalizaai.sinalizaai_back.dto.CadastroClienteDTO;
import sinalizaai.sinalizaai_back.dto.ClienteResponseDTO;
import sinalizaai.sinalizaai_back.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Gerenciamento de clientes SinalizaAI")
@CrossOrigin(origins = "*")
public class ClienteController {

        @Autowired
        private ClienteService service;

        // POST /api/clientes/cadastro
        @PostMapping("/cadastro")
        @Operation(summary = "Cadastrar cliente",
                description = "Cria um novo cliente no sistema")
        public ResponseEntity<ClienteResponseDTO> cadastrar(
                @RequestBody @Valid CadastroClienteDTO dto) {

            var cliente = service.cadastrar(dto);
            return ResponseEntity.status(201).body(cliente);
        }

        // GET /api/clientes
        @GetMapping
        @Operation(summary = "Listar clientes",
                description = "Lista os clientes cadastrados no sistema")
        public ResponseEntity<List<ClienteResponseDTO>> listar() {
            return ResponseEntity.ok(service.listar());
        }

        // GET /api/clientes/{id}
        @GetMapping("/{id}")
        @Operation(summary = "Listar cliente por ID",
                description = "Lista os clientes cadastrados no sistema pelo ID")
        public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
            return ResponseEntity.ok(service.buscarPorId(id));
        }

        // DELETE /api/clientes/{id}
        @DeleteMapping("/{id}")
        @Operation(summary = "Desativar clientes por ID",
                description = "Desativa os clientes cadastrados no sistema pelo ID")
        public ResponseEntity<Void> desativar(@PathVariable Long id) {
            service.desativar(id);
            return ResponseEntity.noContent().build();
        }

        // PUT /api/clientes/{id} atualizar dados
        @PutMapping("/{id}")
        @Operation(summary = "Atualizar clientes por ID",
                description = "Atualiza os clientes cadastrados no sistema pelo ID")
        public ResponseEntity<ClienteResponseDTO> atualizar(
                @PathVariable Long id,
                @RequestBody @Valid AtualizacaoClienteDTO dto) {

            return ResponseEntity.ok(service.atualizar(id, dto));
        }
    }
