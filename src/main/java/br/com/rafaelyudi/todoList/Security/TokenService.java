package br.com.rafaelyudi.todoList.Security;

import br.com.rafaelyudi.todoList.Errors.TokenGenerationException;
import br.com.rafaelyudi.todoList.Errors.TokenIsInvalidException;
import br.com.rafaelyudi.todoList.User.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
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

            return JWT.create()
                    .withIssuer("Task-Manager")
                    .withSubject(user.getUsername())
                    .withExpiresAt(Instant.now().plusSeconds(1800))
                    .sign(algorithm);

        }catch (JWTCreationException e){
            throw new TokenGenerationException();
        }

    }

    public String validateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            return JWT.require(algorithm)
                    .withIssuer("Task-Manager")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTVerificationException e){
            throw new TokenIsInvalidException();
        }


    }

}
