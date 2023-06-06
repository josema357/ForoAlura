package com.alura.foro.record.usuario;

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
		@Size(min= 8,max=30)
		String contrasena) {

}
