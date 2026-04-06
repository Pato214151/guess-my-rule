package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String URL      = "jdbc:mysql://localhost:3306/guessrule";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "";

    private DataBaseConnection() {}

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
