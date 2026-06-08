package sinalizaai.sinalizaai_back.dto;

import sinalizaai.sinalizaai_back.domain.licenca.Licenca;

import java.time.LocalDateTime;

public record LicencaResponseDTO(
        Long id,
        Long clienteId,
        String nomeCliente,
        Long PedidoId,
        String chave,
        boolean ativo,
        LocalDateTime dataInicio,
        LocalDateTime dataExpiacao,
        LocalDateTime criadoEm
) {

    public LicencaResponseDTO(Licenca licenca) {
        this(
                licenca.getId(),
                licenca.getCliente().getId(),
                licenca.getCliente().getNomeResponsavel(),
                licenca.getPedido().getId(),
                licenca.getChave(),
                licenca.isAtivo(),
                licenca.getDataInicio(),
                licenca.getDataExpiracao(),
                licenca.getCriadoEm()
        );
    }
}
