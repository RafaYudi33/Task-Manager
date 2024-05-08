package br.com.rafaelyudi.todoList.Security;

import br.com.rafaelyudi.todoList.Errors.TokenGenerationException;
import br.com.rafaelyudi.todoList.User.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    @Value("${security.jwt.secretKey}")
    private String secretKey;

    public String generateToken(UserModel user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String token = JWT.create()
                    .withIssuer("Task-Manager")
                    .withSubject(user.getUsername())
                    .withExpiresAt(Instant.now().plusSeconds(1800))
                    .sign(algorithm);
        }catch (Exception e){
            throw new TokenGenerationException();
        }

        return null;
    }

}
