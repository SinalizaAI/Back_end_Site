package sinalizaai.sinalizaai_back.dto;

import sinalizaai.sinalizaai_back.domain.cliente.Cliente;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClienteResponseDTO(
        Long id,
        String razaoSocial,
        String cnpj,
        String nomeResponsavel,
        String email,
        String telefone,
        String cidade,
        String pais,
        LocalDate dataNascimento,
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
                cliente.getCidade(),
                cliente.getPais(),
                cliente.getDataNascimento(),
                cliente.getAtivo(),
                cliente.getCriadoEm()
        );
    }
}
