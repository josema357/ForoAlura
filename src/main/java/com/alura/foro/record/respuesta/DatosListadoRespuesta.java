package com.alura.foro.record.respuesta;

import java.time.LocalDateTime;

import com.alura.foro.modelo.Respuesta;
import com.alura.foro.modelo.Topico;
import com.alura.foro.modelo.Usuario;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

public record DatosListadoRespuesta(
		Long id,
		String mensaje,
		Topico topico,
		LocalDateTime fechaCreacion,
		@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
		@JsonIdentityReference(alwaysAsId = true)
		Usuario autor,
		Boolean solucion) {
	public DatosListadoRespuesta(Respuesta respuesta) {
		this(respuesta.getId(),
				respuesta.getMensaje(),
				respuesta.getTopico(),
				respuesta.getfechaCreacion(),
				respuesta.getAutor(),
				respuesta.getSolucion());
	}
}
