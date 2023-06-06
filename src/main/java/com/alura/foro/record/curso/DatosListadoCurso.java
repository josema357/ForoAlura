package com.alura.foro.record.curso;

import com.alura.foro.modelo.Curso;

public record DatosListadoCurso(Long id, String nombre, String categoria) {
	public DatosListadoCurso(Curso curso) {
		this(curso.getId(),curso.getNombre(),curso.getCategoria());
	}
}
