package com.example.util;

import com.example.Model.PuntajeModel;
import java.sql.*;
import java.util.List;

public class DAOPuntaje implements CRUD<PuntajeModel> {

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
public String create(PuntajeModel p) {
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
    public <K> PuntajeModel readOne(K id) {
        String sql = "SELECT * FROM puntajes WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new PuntajeModel(
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
    public List<PuntajeModel> readAll() {
        return PuntajeModel.obtenerRanking(0);
    }

    public List<PuntajeModel> obtenerRanking(int nivel) {
        return PuntajeModel.obtenerRanking(nivel);
    }
}