CREATE TABLE users (
    id bigint not null,
    name varchar(150) not null,
    login varchar(150) not null unique,
    password varchar(255) not null,
    primary key(id)
);
