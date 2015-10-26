drop database if exists grouptalkdb;
create database grouptalkdb;

use grouptalkdb;

CREATE TABLE users (
    id BINARY(16) NOT NULL,
    loginid VARCHAR(15) NOT NULL UNIQUE,
    password BINARY(16) NOT NULL,
    email VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE grupo (
    id BINARY(16) NOT NULL,
    nombre VARCHAR(15) NOT NULL,  
    PRIMARY KEY (id)
);
CREATE TABLE tema (
    id BINARY(16) NOT NULL,
    userid BINARY(16) NOT NULL,
    grupoid BINARY(16) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    comentario VARCHAR(500) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade, 
    FOREIGN KEY (grupoid) REFERENCES grupo(id) on delete cascade
);
CREATE TABLE respuesta(
    id BINARY(16) NOT NULL,
    temaid BINARY(16) NOT NULL,
    userid BINARY(16) NOT NULL,
    respuesta VARCHAR(500) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade, 
    FOREIGN KEY (temaid) REFERENCES tema(id) on delete cascade
);
CREATE TABLE relaciones_grupo (
    grupoid BINARY(16) NOT NULL,
    userid BINARY(16) NOT NULL,
    PRIMARY KEY (grupoid, userid),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade, 
    FOREIGN KEY (grupoid) REFERENCES grupo(id) on delete cascade   
);

CREATE TABLE user_roles (
    userid BINARY(16) NOT NULL,
    role ENUM ('registrado','administrador'),
    PRIMARY KEY (userid, role),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade    
);

CREATE TABLE auth_tokens (
    userid BINARY(16) NOT NULL,
    token BINARY(16) NOT NULL,
    PRIMARY KEY (token),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade    
);





