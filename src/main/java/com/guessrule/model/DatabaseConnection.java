package com.guessrule.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexion a la base de datos MySQL.
 * Implementa patron Singleton para una unica instancia de conexion.
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/guessrule";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    private DatabaseConnection() {
    }

    /**
     * Obtiene la conexion a la base de datos.
     * Si no existe, la crea. Si existe, la retorna.
     *
     * @return Connection activa a MySQL
     * @throws SQLException si ocurre un error de conexion
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexion a MySQL establecida correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al conectar con MySQL: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    /**
     * Cierra la conexion a la base de datos.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexion a MySQL cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
    }
}
