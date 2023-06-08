package com.alura.foro.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alura.foro.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService=tokenService;
		this.usuarioRepository=usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		if(token!=null) {
			token=token.replace("Bearer ", "");
			String nombreUsuario=tokenService.GetSubject(token);
			if(nombreUsuario!=null) {
				UserDetails usuario=usuarioRepository.findByNombre(nombreUsuario);
				var forzarAuthentication= new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(forzarAuthentication);
			}
		}
		filterChain.doFilter(request, response);
	}
	
}
