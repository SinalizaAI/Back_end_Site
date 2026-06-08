package sinalizaai.sinalizaai_back.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CadastroLicencaDTO(

        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        @NotNull(message = "Pedido é obrigatório")
        Long pedidoId,

        @NotNull(message = "Data de inicio é obrigatória")
        LocalDateTime dataInicio,

        @NotNull(message = "Data de expiação é obrigatória")
        LocalDateTime dataExpiracao
) {
}
