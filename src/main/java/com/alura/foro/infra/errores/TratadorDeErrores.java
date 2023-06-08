package com.alura.foro.infra.errores;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErrores {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> tratarError404(){
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> tratarError400(MethodArgumentNotValidException excepcion){
		var errores=excepcion.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
		return ResponseEntity.badRequest().body(errores);
	}
	
	//DTO para mostrar el campo y mensaje de cada error al realiar un badRequest 400
	private record DatosErrorValidacion(String campo, String error) {
		public DatosErrorValidacion(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
			
		}
	}
	
}
