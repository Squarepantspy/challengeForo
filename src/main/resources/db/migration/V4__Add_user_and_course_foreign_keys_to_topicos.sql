ALTER TABLE topicos
ADD COLUMN usuario_id BIGINT,
ADD COLUMN curso_id BIGINT,
ADD CONSTRAINT fk_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
ADD CONSTRAINT fk_curso
    FOREIGN KEY (curso_id) REFERENCES cursos(id);
