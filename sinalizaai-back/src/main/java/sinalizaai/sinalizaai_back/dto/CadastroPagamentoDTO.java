package sinalizaai.sinalizaai_back.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CadastroPagamentoDTO (
    @NotNull(message = "Pedido é obrigatório")
    Long pedidoId,

    @NotBlank(message = "Plataforma é obrigatória")
    String plataforma,

    @NotBlank(message = "ID da transação é obrigatório")
    String transacaoId,

    String bandeira,
    String finalCartao,

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    BigDecimal valor
) {}
