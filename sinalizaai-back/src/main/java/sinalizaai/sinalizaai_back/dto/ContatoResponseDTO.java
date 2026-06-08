package sinalizaai.sinalizaai_back.dto;

import sinalizaai.sinalizaai_back.domain.contato.Contato;

import java.time.LocalDateTime;

public record ContatoResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String mensagem,
        boolean lido,
        LocalDateTime criadoEm
) {
    public ContatoResponseDTO(Contato contato) {
        this(
                contato.getId(),
                contato.getNome(),
                contato.getEmail(),
                contato.getTelefone(),
                contato.getMensagem(),
                contato.isLido(),
                contato.getCriadoEm()
        );
    }
}
