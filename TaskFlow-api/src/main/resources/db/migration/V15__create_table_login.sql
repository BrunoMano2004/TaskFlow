create table login (
    id bigint not null primary key auto_increment,
    username varchar(100) not null,
    password varchar(100) not null,
    id_usuario bigint not null,
    foreign key (id_usuario) references usuario(id)
)