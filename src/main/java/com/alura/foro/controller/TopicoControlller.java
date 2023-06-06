package com.alura.foro.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.alura.foro.modelo.Topico;
import com.alura.foro.modelo.Usuario;
import com.alura.foro.record.topico.DatosActualizarTopico;
import com.alura.foro.record.topico.DatosListadoTopico;
import com.alura.foro.record.topico.DatosRegistroTopico;
import com.alura.foro.record.topico.DatosRespuestaTopico;
import com.alura.foro.repository.CursoRepository;
import com.alura.foro.repository.TopicoRepository;
import com.alura.foro.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topico")
public class TopicoControlller {
	
	private TopicoRepository topicoRepository;
	private UsuarioRepository usuarioRepository;
	private CursoRepository cursoRepository;
	@Autowired
	public TopicoControlller(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository, CursoRepository cursoRepository) {
		this.topicoRepository=topicoRepository;
		this.usuarioRepository=usuarioRepository;
		this.cursoRepository=cursoRepository;
	}
	
	@PostMapping
	 public ResponseEntity<DatosRespuestaTopico> crearTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {
		Optional<Usuario> usuario=usuarioRepository.findById(datosRegistroTopico.autor());
		Optional<Curso> curso=cursoRepository.findById(datosRegistroTopico.curso());
		Topico topico;
		if(usuario.isPresent() && curso.isPresent()) {
			Usuario autorEncontrado=usuario.get();
			Curso cursoEncontrado=curso.get();
			topico=new Topico(datosRegistroTopico);
			topico.setAutor(autorEncontrado);
			topico.setCurso(cursoEncontrado);
			topicoRepository.save(topico);
		}else {
			throw new RuntimeException("El usuario con el ID " + datosRegistroTopico.autor() 
				+ " o el curso con el ID "+ datosRegistroTopico.curso()+" no existe");
		}
		DatosRespuestaTopico datosTopico=new DatosRespuestaTopico(topico.getId(), 
				topico.getTitulo(), 
				topico.getMensaje(), 
				topico.getStatus());
		URI url = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();
		
		return ResponseEntity.created(url).body(datosTopico);
		
	 }
	
	@GetMapping
	public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(@PageableDefault(sort = "fechaCreacion") Pageable paginacion){
		return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosListadoTopico::new));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
		Topico topico=topicoRepository.getReferenceById(datosActualizarTopico.id());
		topico.actualizarDatos(datosActualizarTopico);
		DatosRespuestaTopico datosTopico=new DatosRespuestaTopico(
				topico.getId(), 
				topico.getTitulo(), 
				topico.getMensaje(), 
				topico.getStatus());
		return ResponseEntity.ok(datosTopico);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
		Topico topico=topicoRepository.getReferenceById(id);
		topicoRepository.delete(topico);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DatosRespuestaTopico> retornarTopico(@PathVariable Long id) {
		Topico topico=topicoRepository.getReferenceById(id);
		DatosRespuestaTopico datosTopico = new DatosRespuestaTopico(
				topico.getId(), 
				topico.getTitulo(), 
				topico.getMensaje(), 
				topico.getStatus());
		return ResponseEntity.ok(datosTopico);
	}
}
