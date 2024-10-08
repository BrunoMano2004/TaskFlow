package TaskFlow_api.TaskFlow_api.security;

import TaskFlow_api.TaskFlow_api.exception.InvalidJwtTokenException;
import TaskFlow_api.TaskFlow_api.model.Login;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Login login){
        try {
            return JWT.create()
                    .withIssuer("TaskFlow API")
                    .withSubject(login.getUsername())
                    .withExpiresAt(dataExpiracao())
                    .sign(Algorithm.HMAC256(secret));
        } catch (JWTCreationException e){
            throw new SecurityException("Erro ao gerar o token JWT");
        }
    }

    public String getSubject(String token){
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("TaskFlow API")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e){
            throw new InvalidJwtTokenException("Token inválido ou expirado");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
