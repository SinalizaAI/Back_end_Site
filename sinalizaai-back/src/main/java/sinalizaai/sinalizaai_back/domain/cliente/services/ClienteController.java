package sinalizaai.sinalizaai_back.domain.cliente.services;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sinalizaai.sinalizaai_back.domain.cliente.CadastroClienteDTO;
import sinalizaai.sinalizaai_back.domain.cliente.ClienteResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

        @Autowired
        private ClienteService service;

        // POST /api/clientes/cadastro
        @PostMapping("/cadastro")
        public ResponseEntity<ClienteResponseDTO> cadastrar(
                @RequestBody @Valid CadastroClienteDTO dto) {

            var cliente = service.cadastrar(dto);
            return ResponseEntity.status(201).body(cliente);
        }

        // GET /api/clientes
        @GetMapping
        public ResponseEntity<List<ClienteResponseDTO>> listar() {
            return ResponseEntity.ok(service.listar());
        }

        // GET /api/clientes/{id}
        @GetMapping("/{id}")
        public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
            return ResponseEntity.ok(service.buscarPorId(id));
        }

        // DELETE /api/clientes/{id}
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> desativar(@PathVariable Long id) {
            service.desativar(id);
            return ResponseEntity.noContent().build();
        }
    }
