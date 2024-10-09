create table codigo_verificacao (
    id bigint not null primary key auto_increment,
    codigo varchar(6) not null,
    data_criacao timestamp default current_timestamp,
    data_expiracao timestamp not null,
    id_usuario bigint not null,
    foreign key (id_usuario) references usuario(id)
)