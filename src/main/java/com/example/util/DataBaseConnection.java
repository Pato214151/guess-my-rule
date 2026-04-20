package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String PUERTO   = System.getenv("DB_PORT") != null 
                                           ? System.getenv("DB_PORT") : "3306";
    private static final String URL      = "jdbc:mysql://localhost:" + PUERTO + "/guessrule";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = System.getenv("DB_PASS") != null
                                           ? System.getenv("DB_PASS") : "";

    private DataBaseConnection() {}

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}