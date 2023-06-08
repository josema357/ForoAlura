package com.alura.foro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.foro.infra.security.TokenService;
import com.alura.foro.modelo.Usuario;
import com.alura.foro.record.autenticar.DatosAutenticarUsuario;
import com.alura.foro.record.autenticar.DatosJWTToken;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacionController {
	
	private AuthenticationManager authenticationManager;
	private TokenService tokenService;
	@Autowired
	public AutenticacionController(AuthenticationManager authenticationManager, TokenService tokenService) {
		this.authenticationManager=authenticationManager;
		this.tokenService=tokenService;
	}
	
	@PostMapping
	public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid DatosAutenticarUsuario datosAutenticarUsuario){
		Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticarUsuario.nombre(), datosAutenticarUsuario.contrasena()); 
		Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);
		String JWTtoken = tokenService.generarToken((Usuario)usuarioAutenticado.getPrincipal());
		return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
	}
}
