DROP DATABASE IF EXISTS guessrule;
CREATE DATABASE guessrule;
USE guessrule;

CREATE TABLE puntajes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_jugador VARCHAR(50) NOT NULL,
    puntos INT NOT NULL,
    nivel INT NOT NULL,
    intentos INT NOT NULL,
    tiempo_segundos INT NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_puntos_positivos CHECK (puntos >= 0),
    CONSTRAINT chk_nivel_valido CHECK (nivel BETWEEN 1 AND 6),
    CONSTRAINT chk_intentos_positivos CHECK (intentos > 0),
    CONSTRAINT chk_tiempo_positivo CHECK (tiempo_segundos > 0)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE INDEX idx_puntos_desc ON puntajes (puntos);
CREATE INDEX idx_fecha ON puntajes (fecha_registro);

INSERT INTO puntajes (nombre_jugador, puntos, nivel, intentos, tiempo_segundos)
VALUES
('JuanGamer', 150, 3, 5, 120),
('MariaWin', 200, 4, 3, 95),
('PedroMaster', 180, 3, 4, 110),
('AnaQuick', 220, 5, 2, 85),
('LuisPro', 160, 2, 6, 140);

SELECT
    id,
    nombre_jugador,
    puntos,
    nivel,
    intentos,
    tiempo_segundos,
    fecha_registro
FROM puntajes
ORDER BY puntos DESC;