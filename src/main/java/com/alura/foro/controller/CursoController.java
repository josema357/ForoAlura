package com.alura.foro.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alura.foro.modelo.Curso;
import com.alura.foro.record.curso.DatosActualizarCurso;
import com.alura.foro.record.curso.DatosListadoCurso;
import com.alura.foro.record.curso.DatosRegistroCurso;
import com.alura.foro.record.curso.DatosRespuestaCurso;
import com.alura.foro.repository.CursoRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/curso")
public class CursoController {

	private CursoRepository cursoRepository;
	@Autowired
	public CursoController(CursoRepository cursoRepository) {
		this.cursoRepository=cursoRepository;
	}
	@PostMapping
	public ResponseEntity<DatosRespuestaCurso> crearCurso(@RequestBody @Valid DatosRegistroCurso datosRegistroCurso, UriComponentsBuilder uriCompoenentsBuilder) {
		Curso curso = cursoRepository.save(new Curso(datosRegistroCurso));
		DatosRespuestaCurso datosCurso = new DatosRespuestaCurso(
				curso.getId(), 
				curso.getNombre(), 
				curso.getCategoria(), 
				curso.getActivo());
		URI url = uriCompoenentsBuilder.path("/curso/{id}").buildAndExpand(curso.getId()).toUri();
		return ResponseEntity.created(url).body(datosCurso);
	}
	@GetMapping
	public ResponseEntity<Page<DatosListadoCurso>> listarCursos(Pageable paginacion){
		return ResponseEntity.ok(cursoRepository.findByActivoTrue(paginacion).map(DatosListadoCurso::new));
	}
	@PutMapping
	@Transactional
	public ResponseEntity<DatosRespuestaCurso> actualizarCurso(@RequestBody @Valid DatosActualizarCurso datosActualizarCurso) {
		Curso curso=cursoRepository.getReferenceById(datosActualizarCurso.id());
		curso.actualizarDatos(datosActualizarCurso);
		DatosRespuestaCurso datosCurso= new DatosRespuestaCurso(
				curso.getId(), 
				curso.getNombre(), 
				curso.getCategoria(), 
				curso.getActivo());
		return ResponseEntity.ok(datosCurso);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> eliminarCurso(@PathVariable Long id) {
		Curso curso= cursoRepository.getReferenceById(id);
		curso.desactivarCurso();
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerCurso(@PathVariable Long id) {
		Curso curso= cursoRepository.getReferenceById(id);
		DatosRespuestaCurso datosCurso=new DatosRespuestaCurso(curso.getId(), 
				curso.getNombre(), 
				curso.getCategoria(), 
				curso.getActivo());
		return ResponseEntity.ok(datosCurso);
	}

}
