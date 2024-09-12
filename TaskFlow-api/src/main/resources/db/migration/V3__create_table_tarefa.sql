create table tarefa (
    id bigint not null primary key auto_increment,
    nome varchar(100) not null,
    descricao varchar(500) not null,
    id_etiqueta bigint,
    id_usuario bigint not null,
    foreign key(id_etiqueta) references etiqueta(id),
    foreign key(id_usuario) references usuario(id)
)