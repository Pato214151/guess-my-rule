package com.example.util;

import com.example.Model.PuntajeModelo;
import java.sql.*;
import java.util.List;

public class DAOPuntaje implements CRUD<PuntajeModelo> {

    private final Connection conexion;

    public DAOPuntaje() {
    Connection temp = null;
    try {
        temp = DataBaseConnection.obtenerConexion();
    } catch (SQLException e) {
        System.err.println("Error al conectar: " + e.getMessage());
    }
    this.conexion = temp;
}
    @Override
public String create(PuntajeModelo p) {
    String sql = "INSERT INTO puntajes (nombre_jugador, puntos, nivel, intentos, tiempo_segundos) " +
                 "VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, p.getNombreJugador());
        ps.setInt(2, p.getPuntaje());
        ps.setInt(3, p.getNivel());
        ps.setInt(4, 1);  
        ps.setInt(5, 1);  
        ps.executeUpdate();
        return "Puntaje guardado correctamente";
    } catch (SQLException e) {
        return "Error al guardar puntaje: " + e.getMessage();
    }
}

    @Override
    public <K> PuntajeModelo readOne(K id) {
        String sql = "SELECT * FROM puntajes WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new PuntajeModelo(
                    rs.getString("nombre_jugador"),
                    rs.getInt("nivel"),
                    rs.getInt("puntos"),
                    rs.getString("fecha_registro")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<PuntajeModelo> readAll() {
        return PuntajeModelo.obtenerRanking(0);
    }

    public List<PuntajeModelo> obtenerRanking(int nivel) {
        return PuntajeModelo.obtenerRanking(nivel);
    }
}