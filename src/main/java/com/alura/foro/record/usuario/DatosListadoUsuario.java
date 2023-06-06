package com.alura.foro.record.usuario;

import com.alura.foro.modelo.Usuario;

public record DatosListadoUsuario(Long id, String nombre) {
	public DatosListadoUsuario(Usuario usuario) {
		this(usuario.getId(),usuario.getNombre());
	}
}
