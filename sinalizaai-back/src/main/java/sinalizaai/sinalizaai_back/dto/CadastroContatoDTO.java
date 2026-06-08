package sinalizaai.sinalizaai_back.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CadastroContatoDTO(

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        String telefone,

        @NotBlank(message = "Mensagem é obrigatória")
        String mensagem
) {
}
