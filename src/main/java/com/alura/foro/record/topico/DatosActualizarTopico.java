package com.alura.foro.record.topico;

import com.alura.foro.modelo.StatusTopico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
		@NotNull
		Long id,
		String titulo, 
		String mensaje, 
		StatusTopico status) {

}
