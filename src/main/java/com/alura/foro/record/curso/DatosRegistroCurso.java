package com.alura.foro.record.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatosRegistroCurso(
		@NotBlank
		@Size(min=1,max=150)
		String nombre, 
		@NotBlank
		String categoria) {

}
