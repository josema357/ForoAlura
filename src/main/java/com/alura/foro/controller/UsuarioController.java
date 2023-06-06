package com.alura.foro.controller;

import java.net.URI;

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

import com.alura.foro.modelo.Usuario;
import com.alura.foro.record.usuario.DatosActualizarUsuario;
import com.alura.foro.record.usuario.DatosListadoUsuario;
import com.alura.foro.record.usuario.DatosRegistroUsuario;
import com.alura.foro.record.usuario.DatosRespuestaUsuario;
import com.alura.foro.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	private UsuarioRepository usuarioRepository;
	@Autowired
	public UsuarioController(UsuarioRepository usuarioRepository) {
		this.usuarioRepository=usuarioRepository;
	}
	
	@PostMapping
	public ResponseEntity<DatosRespuestaUsuario> crearUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario, UriComponentsBuilder uriComponentsBuilder) {
		Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario));
		DatosRespuestaUsuario datosRespuesta = new DatosRespuestaUsuario(
				usuario.getId(), 
				usuario.getNombre(), 
				usuario.getEmail());
		URI url = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(url).body(datosRespuesta);
	}
	
	@GetMapping
	public ResponseEntity<Page<DatosListadoUsuario>> listarUsuarios(@PageableDefault(sort = "nombre") Pageable paginacion){
		return ResponseEntity.ok(usuarioRepository.findAll(paginacion).map(DatosListadoUsuario::new));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DatosRespuestaUsuario> actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {
		Usuario usuario=usuarioRepository.getReferenceById(datosActualizarUsuario.id());
		usuario.actualizarDatos(datosActualizarUsuario);
		DatosRespuestaUsuario datosUsuario=new DatosRespuestaUsuario(
				usuario.getId(), 
				usuario.getNombre(), 
				usuario.getEmail());
		return ResponseEntity.ok(datosUsuario);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
		Usuario usuario=usuarioRepository.getReferenceById(id);
		usuarioRepository.delete(usuario);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DatosRespuestaUsuario> retornarUsuario(@PathVariable Long id) {
		Usuario usuario=usuarioRepository.getReferenceById(id);
		DatosRespuestaUsuario datosUsuario=new DatosRespuestaUsuario(
				usuario.getId(), 
				usuario.getNombre(), 
				usuario.getEmail());
		return ResponseEntity.ok(datosUsuario);
	}
}
