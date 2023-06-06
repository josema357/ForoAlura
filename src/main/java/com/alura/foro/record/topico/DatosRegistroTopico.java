package com.alura.foro.record.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DatosRegistroTopico(
		@NotBlank
		@Size(min=1,max=150)
		String titulo,
		@NotBlank
		String mensaje,
		@NotNull
		Long autor,
		@NotNull
		Long curso) {

}
