package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String URL      = "jdbc:mysql://localhost:3307/guessrule";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "";

    private DataBaseConnection() {}

    public static Connection obtenerConexion() throws SQLException {
    try {
        Connection con = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        return con;
    } catch (SQLException e) {
        System.err.println("Error de conexión: " + e.getMessage());
        System.err.println("SQLState: " + e.getSQLState());
        System.err.println("ErrorCode: " + e.getErrorCode());
        throw e;
    }
}

    
}
