package com.alura.foro.record.respuesta;

import java.time.LocalDateTime;

public record DatosResponseRespuesta(Long id, String mensaje, LocalDateTime fechaCreacion, Boolean solucion) {

}
