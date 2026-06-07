package sinalizaai.sinalizaai_back.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import sinalizaai.sinalizaai_back.domain.cliente.Cliente;
import sinalizaai.sinalizaai_back.dto.LoginDTO;
import sinalizaai.sinalizaai_back.dto.TokenResponseDTO;
import sinalizaai.sinalizaai_back.service.TokenService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(
            @RequestBody @Valid LoginDTO dto) {

        var authToken = new UsernamePasswordAuthenticationToken(
                dto.email(),
                dto.senha()
        );

        var autenticacao = authenticationManager.authenticate(authToken);
        var cliente = (Cliente) autenticacao.getPrincipal();
        var token = tokenService.gerarToken(cliente);

        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}