package sinalizaai.sinalizaai_back.dto;

import org.springframework.validation.FieldError;

public record ErroValidacaoDTO(
        String campo,
        String mensagem
) {
    public ErroValidacaoDTO(FieldError erro) {
        this(erro.getField(), erro.getDefaultMessage());
    }
}
