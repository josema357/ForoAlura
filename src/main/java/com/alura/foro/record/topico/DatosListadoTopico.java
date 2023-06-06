package com.alura.foro.record.topico;

import java.time.LocalDateTime;

import java.util.List;

import com.alura.foro.modelo.Curso;
import com.alura.foro.modelo.Respuesta;
import com.alura.foro.modelo.StatusTopico;
import com.alura.foro.modelo.Topico;
import com.alura.foro.modelo.Usuario;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

public record DatosListadoTopico(
		Long id,
		String titulo, 
		String mensaje, 
		LocalDateTime fechaCreacion, 
		StatusTopico status, 
		@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
		@JsonIdentityReference(alwaysAsId = true)
		Usuario autor, 
		Curso curso, 
		List<Respuesta> respuestas) {
	
	public DatosListadoTopico(Topico topico) {
		this(topico.getId(), 
				topico.getTitulo(), 
				topico.getMensaje(), 
				topico.getfechaCreacion(), 
				topico.getStatus(), 
				topico.getAutor(), 
				topico.getCurso(), 
				topico.getRespuestas());
	}
}
