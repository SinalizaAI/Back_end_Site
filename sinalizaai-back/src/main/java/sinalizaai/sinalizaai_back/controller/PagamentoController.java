package sinalizaai.sinalizaai_back.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sinalizaai.sinalizaai_back.dto.CadastroPagamentoDTO;
import sinalizaai.sinalizaai_back.dto.PagamentoResponseDTO;
import sinalizaai.sinalizaai_back.service.PagamentoService;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
@Tag(name = "Pagamentos", description = "Gerenciamento de pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @PostMapping
    @Operation(summary = "Registrar pagamento", description = "Registra um novo pagamento")
    public ResponseEntity<PagamentoResponseDTO> registrar(
            @RequestBody @Valid CadastroPagamentoDTO dto) {
        return ResponseEntity.status(201).body(service.registrar(dto));
    }

    @GetMapping("/pedido/{pedidoId}")
    @Operation(summary = "Listar pagamentos de um pedido", description = "Lista o pagamento de um pedido por ID")
    public ResponseEntity<List<PagamentoResponseDTO>> listarPorPedido(
            @PathVariable Long pedidoId) {
        return ResponseEntity.ok(service.listarPorPedido(pedidoId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento por ID", description = "Busca um pagamento por ID")
    public ResponseEntity<PagamentoResponseDTO> buscarPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PatchMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar pagamento", description = "Aprova um pagamento pelo ID")
    public ResponseEntity<PagamentoResponseDTO> aprovar(@PathVariable Long id) {
        return ResponseEntity.ok(service.aprovar(id));
    }

    @PatchMapping("/{id}/recusar")
    @Operation(summary = "Recusar pagamento", description = "Recusa um pagamento por ID")
    public ResponseEntity<PagamentoResponseDTO> recusar(@PathVariable Long id) {
        return ResponseEntity.ok(service.recusar(id));
    }

    @PatchMapping("/{id}/estornar")
    @Operation(summary = "Estornar pagamento", description = "Estorna um pagamento por ID")
    public ResponseEntity<PagamentoResponseDTO> estornar(@PathVariable Long id) {
        return ResponseEntity.ok(service.estornar(id));
    }
}
