-- GMR1-158: Prueba de conexión desde la base de datos
-- Verificar que la conexión JDBC puede acceder a guessrule

USE guessrule;

-- P1: Verificar que la base de datos existe y es accesible
SELECT DATABASE() AS base_de_datos_activa;

-- P2: Verificar que la tabla puntajes existe y tiene datos
SELECT COUNT(*) AS total_registros FROM puntajes;

-- P3: Verificar que los parámetros de conexión son correctos
SHOW VARIABLES LIKE 'hostname';
SHOW VARIABLES LIKE 'port';

-- P4: Verificar usuario activo
SELECT USER() AS usuario_conectado;
