package sinalizaai.sinalizaai_back.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sinalizaai.sinalizaai_back.dto.CadastroLicencaDTO;
import sinalizaai.sinalizaai_back.dto.LicencaResponseDTO;
import sinalizaai.sinalizaai_back.service.LicencaService;

import java.util.List;

@RestController
@RequestMapping("/api/licencas")
@Tag(name = "Licenças", description = "Gerenciamento de licenças")
public class LicencaController {

    @Autowired
    private LicencaService service;

    @PostMapping
    @Operation(summary = "Gerar licenças", description = "Gera uma licença")
    public ResponseEntity<LicencaResponseDTO> gerar(
            @RequestBody @Valid CadastroLicencaDTO dto
            ) {
        return ResponseEntity.status(201).body(service.gerar(dto));
    }


    @GetMapping
    @Operation(summary = "Listar todas as licenças", description = "Lista todas as licenças")
    public ResponseEntity<List<LicencaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar licenças por cliente", description = "Lista as licenças por clienteId")
    public ResponseEntity<List<LicencaResponseDTO>> listarPorCliente(
            @PathVariable Long clienteId
    ) {
        return ResponseEntity.ok(service.listarPorCliente(clienteId));
    }

    @GetMapping("/chave/{chave}")
    @Operation(summary = "Buscar licença pela chave", description = "busca uma licença pela chave da mesma")
    public ResponseEntity<LicencaResponseDTO> buscarPorChave(
            @PathVariable String chave
    ) {
        return ResponseEntity.ok(service.buscarPorChave(chave));
    }

    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar licença", description = "Desativa uma licença pelo Id")
    public ResponseEntity<LicencaResponseDTO> desativar(@PathVariable Long id) {
        return ResponseEntity.ok(service.desativar(id));
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar licença", description = "Ativa uma licença pelo Id")
    public ResponseEntity<LicencaResponseDTO> ativar(@PathVariable Long id) {
        return ResponseEntity.ok(service.ativar(id));
    }





}
