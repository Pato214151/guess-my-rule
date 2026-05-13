package com.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Proveedor de conexiones JDBC a la base de datos MySQL {@code guessrule}.
 * El puerto se configura mediante la variable de entorno {@code DB_PORT}
 * (por defecto {@code 3306}).
 */
public class DataBaseConnection {

    private static final String PUERTO   = System.getenv("DB_PORT") != null
                                           ? System.getenv("DB_PORT") : "3306";
    private static final String URL      = "jdbc:mysql://localhost:" + PUERTO + "/guessrule";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "1234";

    private DataBaseConnection() {}

    /**
     * Abre y devuelve una nueva conexión a la base de datos.
     *
     * @return conexión activa lista para usar
     * @throws SQLException si no se puede establecer la conexión
     */
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}