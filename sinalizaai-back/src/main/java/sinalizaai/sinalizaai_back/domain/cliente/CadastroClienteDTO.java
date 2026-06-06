package sinalizaai.sinalizaai_back.domain.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroClienteDTO(
        @NotBlank(message = "Razão social é obrigatória")
        String razaoSocial,

        @NotBlank(message = "CNPJ é obrigatório")
        @Size(min = 14, max = 18, message = "CNPJ inválido")
        String cnpj,

        @NotBlank(message = "Nome do responsável é obrigatório")
        String nomeResponsavel,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
        String senha,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone
) {
}
