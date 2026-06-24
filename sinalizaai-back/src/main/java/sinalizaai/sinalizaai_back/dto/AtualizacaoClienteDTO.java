package sinalizaai.sinalizaai_back.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AtualizacaoClienteDTO(
        String razaoSocial,
        String nomeResponsavel,

        @Email(message = "E-mail inválido")
        String email,

        String telefone,

        @Size(min = 8, message = "Senha deve conter no mínimo 8 caracteres")
        String senha,

        String cidade,
        String pais,
        LocalDate dataNascimento
) {
}
