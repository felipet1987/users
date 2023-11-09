package com.example.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenService {
    public String generate( String id) throws RuntimeException {

        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withIssuer("user")
                .withClaim("userId", id)
                .withIssuedAt(new Date())
                .withJWTId(UUID.randomUUID()
                        .toString())
                .sign(algorithm);


    }

    public String validate(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("user")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        Claim claim = decodedJWT.getClaim("userId");
        return claim.asString();
    }
}
