package sinalizaai.sinalizaai_back.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sinalizaai.sinalizaai_back.dto.CadastroPedidoDTO;
import sinalizaai.sinalizaai_back.dto.PedidoResponseDTO;
import sinalizaai.sinalizaai_back.service.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos SinalizaAI")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    @Operation(summary = "Criar pedido",
    description = "Cria um novo pedido no sistema")
    public ResponseEntity<PedidoResponseDTO> cadastrar(
            @RequestBody @Valid CadastroPedidoDTO dto) {
        return ResponseEntity.status(201).body(service.cadastrar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos os pedidos",
            description = "Lista todos os pedidos no sistema")
    public ResponseEntity<List<PedidoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID",
            description = "Busca os pedidos no sistema pelo ID")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar pedidos por cliente",
            description = "Lista todos os pedidos no sistema por clienteId")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorCliente(
            @PathVariable Long clienteId) {
        return ResponseEntity.ok(service.listarPorCliente(clienteId));
    }

    @PatchMapping("/{id}/pagar")
    @Operation(summary = "Marcar pedido como pago",
            description = "Marca um todos pedido como pago no sistema")
    public ResponseEntity<PedidoResponseDTO> pagar(@PathVariable Long id) {
        return ResponseEntity.ok(service.pagar(id));
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pedido",
            description = "Cancela um pedido no sistema pelo ID")
    public ResponseEntity<PedidoResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelar(id));
    }
}
