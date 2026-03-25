package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// GMR1-155: Clase centralizada que provee la conexión JDBC
public class DatabaseConnection {

    // GMR1-156: Parámetros de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/guessrule";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    // Constructor privado — patrón Singleton
    private DatabaseConnection() {}

    // GMR1-157: Método para abrir/retornar la conexión
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión establecida con la base de datos.");
            }
        } catch (SQLException e) {
            // GMR1-158: Manejo de error sin cerrar la aplicación
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return connection;
    }

    // Cerrar la conexión de forma segura
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    // GMR1-158: Prueba de conexión — retorna true si se conectó correctamente
    public static boolean testConnection() {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Prueba exitosa: conexión disponible.");
            return true;
        }
        System.err.println("Prueba fallida: no se pudo establecer conexión.");
        return false;
    }
}
