package sinalizaai.sinalizaai_back.infra;

import sinalizaai.sinalizaai_back.dto.ErroResponseDTO;
import sinalizaai.sinalizaai_back.dto.ErroValidacaoDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroValidacaoDTO>> tratarErroValidacao(
            MethodArgumentNotValidException ex) {
        var erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErroValidacaoDTO::new)
                .toList();
        return ResponseEntity.status(400).body(erros);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResponseDTO> tratarErroNotFound(
            EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponseDTO(404, ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResponseDTO> tratarErroRegrasNegocio(
            RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponseDTO(400, ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroResponseDTO> tratarErroBadCredentials() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErroResponseDTO(401, "E-mail ou senha inválidos"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResponseDTO> tratarErroAcessoNegado() {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErroResponseDTO(403, "Acesso negado"));
    }

    // ← Handler genérico REMOVIDO — era ele que quebrava o Swagger
}