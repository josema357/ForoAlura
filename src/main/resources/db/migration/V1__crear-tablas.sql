CREATE TABLE usuarios (
	id BIGINT  PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    contrasena VARCHAR(255) NOT NULL
);

CREATE TABLE cursos (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(255) NOT NULL,
  categoria VARCHAR(255) NOT NULL
);

CREATE TABLE topicos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    id_autor BIGINT NOT NULL,
    id_curso BIGINT NOT NULL,
    FOREIGN KEY (id_autor) REFERENCES usuarios(id),
    FOREIGN KEY (id_curso) REFERENCES cursos(id)
);

CREATE TABLE respuestas (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  mensaje TEXT NOT NULL,
  id_topico BIGINT,
  fecha_creacion DATETIME,
  id_autor BIGINT,
  solucion BOOLEAN,
  FOREIGN KEY (id_topico) REFERENCES topicos (id),
  FOREIGN KEY (id_autor) REFERENCES usuarios (id)
);