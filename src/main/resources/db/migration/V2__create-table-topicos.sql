create table topicos(

    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(100) not null,
    fechaCreacion datetime not null,
    status tinyint not null,

    primary key(id)

);