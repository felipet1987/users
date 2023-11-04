package com.example.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenService {
    public String generate() throws RuntimeException {

        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withIssuer("user")
                .withSubject("User Details")
                .withClaim("userId", "1234")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 5000L))
                .withJWTId(UUID.randomUUID()
                        .toString())
                .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
                .sign(algorithm);


    }
}
