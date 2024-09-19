ALTER TABLE etiqueta
ADD CONSTRAINT fk_id_usuario
FOREIGN KEY (id_usuario)
REFERENCES usuario(id);