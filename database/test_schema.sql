-- GMR1-78: Pruebas del script en MySQL Workbench
-- Ejecutar DESPUÉS de schema.sql
USE guessrule;

-- ─────────────────────────────────────────────
-- PRUEBAS POSITIVAS (deben insertarse sin error)
-- ─────────────────────────────────────────────

-- P1: Registro válido mínimo (puntos = 0 permitido)
INSERT INTO puntajes (nombre_jugador, puntos, nivel, intentos, tiempo_segundos)
VALUES ('TestMin', 0, 1, 1, 1);

-- P2: Registro válido máximo de nivel
INSERT INTO puntajes (nombre_jugador, puntos, nivel, intentos, tiempo_segundos)
VALUES ('TestMax', 999, 6, 10, 3600);

-- P3: fecha_registro se genera sola
INSERT INTO puntajes (nombre_jugador, puntos, nivel, intentos, tiempo_segundos)
VALUES ('TestFecha', 100, 2, 3, 60);

SELECT 'PRUEBAS POSITIVAS OK' AS resultado;

-- ─────────────────────────────────────────────
-- PRUEBAS NEGATIVAS (deben fallar con error)
-- ─────────────────────────────────────────────

-- N1: puntos negativo → viola chk_puntos_positivos
INSERT INTO puntajes (nombre_jugador, puntos, nivel, intentos, tiempo_segundos)
VALUES ('TestNeg', -1, 1, 1, 10);

-- N2: nivel = 0 → viola chk_nivel_valido
INSERT INTO puntajes (nombre_jugador, puntos, nivel, intentos, tiempo_segundos)
VALUES ('TestNivel0', 100, 0, 1, 10);

-- N3: nivel = 7 → viola chk_nivel_valido
INSERT INTO puntajes (nombre_jugador, puntos, nivel, intentos, tiempo_segundos)
VALUES ('TestNivel7', 100, 7, 1, 10);

-- N4: intentos = 0 → viola chk_intentos_positivos
INSERT INTO puntajes (nombre_jugador, puntos, nivel, intentos, tiempo_segundos)
VALUES ('TestIntentos', 100, 1, 0, 10);

-- N5: tiempo_segundos = 0 → viola chk_tiempo_positivo
INSERT INTO puntajes (nombre_jugador, puntos, nivel, intentos, tiempo_segundos)
VALUES ('TestTiempo', 100, 1, 1, 0);

-- N6: nombre_jugador NULL → viola NOT NULL
INSERT INTO puntajes (nombre_jugador, puntos, nivel, intentos, tiempo_segundos)
VALUES (NULL, 100, 1, 1, 10);

-- N7: puntos NULL → viola NOT NULL
INSERT INTO puntajes (nombre_jugador, puntos, nivel, intentos, tiempo_segundos)
VALUES ('TestNull', NULL, 1, 1, 10);

-- ─────────────────────────────────────────────
-- VERIFICACIÓN FINAL
-- Solo deben aparecer los 3 registros válidos
-- ─────────────────────────────────────────────
SELECT id, nombre_jugador, puntos, nivel, intentos, tiempo_segundos, fecha_registro
FROM puntajes
WHERE nombre_jugador LIKE 'Test%'
ORDER BY id;
