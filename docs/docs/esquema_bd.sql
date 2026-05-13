CREATE DATABASE IF NOT EXISTS juego;
USE juego;

CREATE TABLE jugador (
    id_jugador INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE partida (
    id_partida INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    descripcion VARCHAR(255)
);

CREATE TABLE puntaje (
    id_puntaje INT AUTO_INCREMENT PRIMARY KEY,
    id_jugador INT NOT NULL,
    id_partida INT NOT NULL,
    puntos INT NOT NULL CHECK (puntos >= 0),

    FOREIGN KEY (id_jugador)
        REFERENCES jugador(id_jugador)
        ON DELETE CASCADE,

    FOREIGN KEY (id_partida)
        REFERENCES partida(id_partida)
        ON DELETE CASCADE,

    UNIQUE (id_jugador, id_partida)
);
