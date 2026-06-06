package sinalizaai.sinalizaai_back.domain.cliente;

import java.time.LocalDateTime;

public record ClienteResponseDTO(
        Long id,
        String razaoSocial,
        String cnpj,
        String nomeResponsavel,
        String email,
        String telefone,
        Boolean ativo,
        LocalDateTime criadoEm
) {
    public ClienteResponseDTO(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getRazaoSocial(),
                cliente.getCnpj(),
                cliente.getNomeResponsavel(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getAtivo(),
                cliente.getCriadoEm()
        );
    }
}
