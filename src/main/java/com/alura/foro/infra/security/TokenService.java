package com.alura.foro.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alura.foro.modelo.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class TokenService {
	
	@Value("${api.security.secret}")
	private String apiSecret;
	
	public String generarToken(Usuario usuario) {
		try {
		    Algorithm algorithm = Algorithm.HMAC256(apiSecret);
		    return JWT.create()
		        .withIssuer("foroAlura")
		        .withSubject(usuario.getNombre())
		        .withClaim("id", usuario.getId())
		        .withExpiresAt(generarFechaDeExpiracion())
		        .sign(algorithm);
		} catch (JWTCreationException exception){
		    throw new RuntimeException();
		}
	}
	
	public String GetSubject(String token) {
		
		if(token==null) {
			throw new RuntimeException();
		}
		DecodedJWT decodedJWT = null;
		try {
		    Algorithm algorithm = Algorithm.HMAC256(apiSecret);
		    JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer("foroAlura")
		        .build();
		        
		    decodedJWT = verifier.verify(token);
		} catch (JWTVerificationException exception){
			System.out.println(exception.toString());
		}
		if(decodedJWT.getSubject()==null) {
			throw new RuntimeException("decodeJWT invalido");
		}
		return decodedJWT.getSubject();
	}
	
	private Instant generarFechaDeExpiracion() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
	}
}
