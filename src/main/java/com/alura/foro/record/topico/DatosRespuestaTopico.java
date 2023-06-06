package com.alura.foro.record.topico;

import com.alura.foro.modelo.StatusTopico;

public record DatosRespuestaTopico(Long id, String titulo, String mensaje, StatusTopico status) {

}
