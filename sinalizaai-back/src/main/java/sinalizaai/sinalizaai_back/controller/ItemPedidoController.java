package sinalizaai.sinalizaai_back.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sinalizaai.sinalizaai_back.dto.CadastroItemPedidoDTO;
import sinalizaai.sinalizaai_back.dto.ItemPedidoResponseDTO;
import sinalizaai.sinalizaai_back.service.ItemPedidoService;

import java.util.List;

@RestController
@RequestMapping("api/itens-pedido")
@Tag(name = "Itens do Pedido", description = "Gerenciamento de itens dos pedidos")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService service;

    @PostMapping
    @Operation(summary = "Adicionar item ao pedido", description = "Adiciona um item a um pedido")
    public ResponseEntity<ItemPedidoResponseDTO> adicionar(
            @RequestBody @Valid CadastroItemPedidoDTO dto
            ) {
        return ResponseEntity.status(201).body(service.adicionar(dto));
    }

    @GetMapping("/pedido/{pedidoId}")
    @Operation(summary = "Listar itens de um pedido", description = "Lista itens de um pedido por ID")
    public ResponseEntity<List<ItemPedidoResponseDTO>> listarPorPedido(
            @PathVariable Long pedidoId) {
        return ResponseEntity.ok(service.listarPorPedido(pedidoId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item por ID", description = "Busca um item por ID")
    public ResponseEntity<ItemPedidoResponseDTO> buscarPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover item do pedido", description = "Remove um item do pedido por ID")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
