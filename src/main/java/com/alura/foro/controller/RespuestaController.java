package com.alura.foro.controller;

import java.net.URI;
import java.util.Optional;

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

import com.alura.foro.modelo.Respuesta;
import com.alura.foro.modelo.Topico;
import com.alura.foro.modelo.Usuario;
import com.alura.foro.record.respuesta.DatosActualizarRespuesta;
import com.alura.foro.record.respuesta.DatosListadoRespuesta;
import com.alura.foro.record.respuesta.DatosRegistroRespuesta;
import com.alura.foro.record.respuesta.DatosResponseRespuesta;
import com.alura.foro.repository.RespuestaRepository;
import com.alura.foro.repository.TopicoRepository;
import com.alura.foro.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/respuesta")
public class RespuestaController {
	
	private RespuestaRepository respuestaRepository;
	private TopicoRepository topicoRepository;
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	public RespuestaController(RespuestaRepository respuestaRepository, TopicoRepository topicoRepository, UsuarioRepository usuarioRepository) {
		this.respuestaRepository=respuestaRepository;
		this.topicoRepository=topicoRepository;
		this.usuarioRepository=usuarioRepository;
	}
	
	@PostMapping
	public ResponseEntity<DatosResponseRespuesta> crearRepsuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta, UriComponentsBuilder uriComponentsBuilder) {
		Optional<Topico> topico=topicoRepository.findById(datosRegistroRespuesta.topico());
		Optional<Usuario> usuario=usuarioRepository.findById(datosRegistroRespuesta.autor());
		Respuesta respuesta;
		if(topico.isPresent() && usuario.isPresent()) {
			Topico topicoEncontrado=topico.get();
			Usuario usuarioEncontrado=usuario.get();
			respuesta=new Respuesta(datosRegistroRespuesta);
			respuesta.setTopico(topicoEncontrado);
			respuesta.setAutor(usuarioEncontrado);
			respuestaRepository.save(respuesta);
		}else {
			throw new RuntimeException("El usuario con el ID " + datosRegistroRespuesta.autor() 
			+ " o el topico con el ID "+ datosRegistroRespuesta.topico()+" no existe");
		}
		DatosResponseRespuesta datosRespuesta = new DatosResponseRespuesta(respuesta.getId(), 
				respuesta.getMensaje(), 
				respuesta.getfechaCreacion(), 
				respuesta.getSolucion());
		URI url = uriComponentsBuilder.path("/respuesta/{id}").buildAndExpand(respuesta.getId()).toUri();
		return ResponseEntity.created(url).body(datosRespuesta);
	}
	@GetMapping
	public ResponseEntity<Page<DatosListadoRespuesta>> listarRepsuestas(Pageable paginacion){
		return ResponseEntity.ok(respuestaRepository.findAll(paginacion).map(DatosListadoRespuesta::new));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DatosResponseRespuesta> actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta) {
		Respuesta respuesta=respuestaRepository.getReferenceById(datosActualizarRespuesta.id());
		respuesta.actualizarDatos(datosActualizarRespuesta);
		DatosResponseRespuesta datosRespuesta= new DatosResponseRespuesta(
				respuesta.getId(), 
				respuesta.getMensaje(), 
				respuesta.getfechaCreacion(), 
				respuesta.getSolucion());
		return ResponseEntity.ok(datosRespuesta);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> eliminarRespuesta(@PathVariable Long id) {
		Respuesta respuesta=respuestaRepository.getReferenceById(id);
		respuestaRepository.delete(respuesta);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DatosResponseRespuesta> obtenerRespuesta(@PathVariable Long id) {
		Respuesta respuesta=respuestaRepository.getReferenceById(id);
		DatosResponseRespuesta datosRespuesta=new DatosResponseRespuesta(
				respuesta.getId(), 
				respuesta.getMensaje(), 
				respuesta.getfechaCreacion(), 
				respuesta.getSolucion());
		return ResponseEntity.ok(datosRespuesta);
	}
}
