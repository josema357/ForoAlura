ALTER TABLE cursos ADD activo tinyint;

UPDATE cursos SET activo = 1;