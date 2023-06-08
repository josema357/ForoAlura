package com.alura.foro.record.usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DatosRegistroUsuario (
		@NotEmpty
		@NotBlank
		@Size(min = 1,max=60)
		String nombre, 
		@NotEmpty
		@NotBlank
		@Email
		String email,
		@NotEmpty
		@NotBlank
		@Size(min= 8)
		String contrasena) {
		
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public DatosRegistroUsuario(String nombre, String email, String contrasena) {
        this.nombre = nombre;
        this.email = email;
        this.contrasena = passwordEncoder.encode(contrasena);
    }
}
