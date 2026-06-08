package sinalizaai.sinalizaai_back.dto;

import sinalizaai.sinalizaai_back.domain.pagamento.Pagamento;
import sinalizaai.sinalizaai_back.domain.pagamento.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponseDTO(
        Long id,
        Long pedidoId,
        String plataforma,
        String transacaoId,
        String bandeira,
        String finalCartao,
        StatusPagamento status,
        BigDecimal valor,
        LocalDateTime criadoEm
) {
    public PagamentoResponseDTO(Pagamento pagamento) {
        this(
                pagamento.getId(),
                pagamento.getPedido().getId(),
                pagamento.getPlataforma(),
                pagamento.getTransacaoId(),
                pagamento.getBandeira(),
                pagamento.getFinalCartao(),
                pagamento.getStatus(),
                pagamento.getValor(),
                pagamento.getCriadoEm()
        );
    }
}
