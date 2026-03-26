package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// GMR1-155: Clase centralizada que provee la conexion JDBC
public class DatabaseConnection {

    // GMR1-156: Parametros de conexion
    private static final String URL = "jdbc:mysql://localhost:3306/guessrule";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    private DatabaseConnection() {}

    // GMR1-157: Metodo para abrir/retornar la conexion
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexion establecida con la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexion cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }

    // GMR1-158: Prueba de conexion
    public static boolean testConnection() {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Prueba exitosa: conexion disponible.");
            return true;
        }
        System.err.println("Prueba fallida: no se pudo establecer conexion.");
        return false;
    }
}
