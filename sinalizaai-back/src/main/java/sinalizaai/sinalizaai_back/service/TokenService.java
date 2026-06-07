package sinalizaai.sinalizaai_back.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sinalizaai.sinalizaai_back.domain.cliente.Cliente;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    // Gera o token JWT após login bem sucedido
    public String gerarToken(Cliente cliente) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("sinalizaai-site")
                    .withSubject(cliente.getEmail())
                    .withClaim("id", cliente.getId())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    // Valida o token e retorna o email do cliente
    public String validarToken(String token) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);

            return JWT.require(algoritmo)
                    .withIssuer("sinalizaai-site")
                    .build()
                    .verify(token)
                    .getSubject(); // retorna o email

        } catch (JWTVerificationException e) {
            return null; // token inválido ou expirado
        }
    }

    // Token expira em 24 horas
    private Instant dataExpiracao() {
        return LocalDateTime.now()
                .plusHours(24)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
