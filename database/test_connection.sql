USE guessrule;

--Verificar que la base de datos existe y es accesible
SELECT DATABASE() AS base_de_datos_activa;

--Verificar que la tabla puntajes existe y tiene datos
SELECT COUNT(*) AS total_registros FROM puntajes;

--Verificar que los parámetros de conexión son correctos
SHOW VARIABLES LIKE 'hostname';
SHOW VARIABLES LIKE 'port';

--Verificar usuario activo
SELECT USER() AS usuario_conectado;
