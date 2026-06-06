package sinalizaai.sinalizaai_back.domain.cliente.services;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sinalizaai.sinalizaai_back.domain.cliente.LoginDTO;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO dto) {
        // JWT será implementado no próximo passo
        return ResponseEntity.ok("Login recebido — JWT em breve");
    }
}