package com.aluracursos.challengeforo.infra.security;


import com.aluracursos.challengeforo.domain.usuarios.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;


    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("foro alu")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            throw new RuntimeException();
        }

    }

    //metodo para validar token
    public String getSubject(String token){
        //String token2 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJQYWJsby5nb256YSIsImlzcyI6InZvbGwgbWVkIiwiaWQiOjEsImV4cCI6MTcyMTEzMDYzMn0.XCAUGHNQCh_3viKnEnSa23CuPAkjtGXUg1V3fXSPW0c";
        //System.out.println("Aqui el token" + token);
        //System.out.println("Aqui el token fijo" + token2 + "longitud" +token2.length());

        if (token == null) {
            throw new RuntimeException("El token no puede ser nulo");
        }
        System.out.println(token.length());
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("foro alu")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String subject = decodedJWT.getSubject();

            if (subject == null) {
                throw new RuntimeException("el sub es nulo");
            }

            return subject;
        } catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
            throw new RuntimeException("Fallo en la verificacion del token");
        }

    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-04:00"));
    }

}
