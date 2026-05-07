package com.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria que centraliza la configuración de conexión JDBC a MySQL.
 * <p>
 * El puerto y la contraseña son configurables mediante las variables de entorno
 * {@code DB_PORT} y {@code DB_PASS}. Si no están definidas, se usan los valores
 * por defecto {@code 3306} y cadena vacía respectivamente.
 * </p>
 * <p>
 * El constructor privado impide la instanciación; todos los métodos son estáticos.
 * </p>
 */
public class DataBaseConnection {

    private static final String PUERTO   = System.getenv("DB_PORT") != null 
                                           ? System.getenv("DB_PORT") : "3306";
    private static final String URL      = "jdbc:mysql://localhost:" + PUERTO + "/guessrule";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = System.getenv("DB_PASS") != null
                                           ? System.getenv("DB_PASS") : "";

    private DataBaseConnection() {}

    /**
     * Abre y retorna una nueva conexión JDBC a la base de datos {@code guessrule}.
     * <p>
     * Usar siempre dentro de un {@code try-with-resources} para garantizar el cierre.
     * </p>
     *
     * @return una {@link java.sql.Connection} activa
     * @throws SQLException si el servidor MySQL no está disponible o las credenciales son incorrectas
     */
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}