package com.example.DAO;

import com.example.Model.GameSession;
import com.example.Model.Puntaje;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOPuntaje implements CRUD<Puntaje, GameSession> {

    @Override
    public boolean create(GameSession session) {
        int intentosSafe = Math.max(1, session.getIntentos());
        int tiempoSafe   = Math.max(1, session.getTiempo());

        String sql = "INSERT INTO puntajes (nombre_jugador, puntos, nivel, intentos, tiempo_segundos) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DataBaseConnection.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, session.getAlias());
            ps.setInt(2, session.getPuntaje());
            ps.setInt(3, session.getNivel());
            ps.setInt(4, intentosSafe);
            ps.setInt(5, tiempoSafe);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al guardar puntaje: " + e.getMessage());
            return false;
        }
    }

    @Override
    public <K> Puntaje readOne(K id) {
        String sql = "SELECT * FROM puntajes WHERE id = ?";
        try (Connection con = DataBaseConnection.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Puntaje(
                    rs.getString("nombre_jugador"),
                    rs.getInt("nivel"),
                    rs.getInt("puntos"),
                    rs.getString("fecha_registro")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al leer puntaje: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Puntaje> readAll() {
        return obtenerRanking(0);
    }

    public List<Puntaje> obtenerRanking(int nivel) {
        List<Puntaje> lista = new ArrayList<>();
        String sql = (nivel == 0)
            ? "SELECT nombre_jugador, nivel, puntos, fecha_registro " +
              "FROM puntajes ORDER BY puntos DESC LIMIT 50"
            : "SELECT nombre_jugador, nivel, puntos, fecha_registro " +
              "FROM puntajes WHERE nivel = ? ORDER BY puntos DESC LIMIT 50";

        try (Connection con = DataBaseConnection.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (nivel != 0) ps.setInt(1, nivel);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Puntaje(
                    rs.getString("nombre_jugador"),
                    rs.getInt("nivel"),
                    rs.getInt("puntos"),
                    rs.getString("fecha_registro")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ranking: " + e.getMessage());
        }
        return lista;
    }
}