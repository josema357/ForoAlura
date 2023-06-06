package com.alura.foro.modelo;

import com.alura.foro.record.curso.DatosActualizarCurso;
import com.alura.foro.record.curso.DatosRegistroCurso;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cursos")
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String categoria;
	private Boolean activo;
	
	public Curso() {
		
	}

	public Curso(DatosRegistroCurso datosRegistroCurso) {
		this.activo=true;
		this.nombre=datosRegistroCurso.nombre();
		this.categoria=datosRegistroCurso.categoria();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public void actualizarDatos(DatosActualizarCurso datosActualizarCurso) {
		if(datosActualizarCurso.nombre()!=null) {
			this.nombre=datosActualizarCurso.nombre();
		}
		if(datosActualizarCurso.categoria()!=null) {
			this.categoria=datosActualizarCurso.categoria();
		}
		if(datosActualizarCurso.activo()!=null) {
			this.activo=datosActualizarCurso.activo();
		}
	}

	public void desactivarCurso() {
		this.activo=false;
	}

}
