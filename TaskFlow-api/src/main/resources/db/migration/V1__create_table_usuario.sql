create table usuario (
    id bigint not null primary key auto_increment,
    email varchar(100) unique not null,
    nome_completo varchar(100) not null,
    data_nascimento date not null,
    tema_escuro bool default false,
    data_cadastro timestamp default current_timestamp,
    img_perfil blob
)