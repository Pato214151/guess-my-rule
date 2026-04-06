package com.example.Model;

import com.example.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PuntajeModel {

    private final String nombreJugador;
    private final int    nivel;
    private final int    puntaje;
    private final String fechaHora;

    public PuntajeModel(String nombreJugador, int nivel, int puntaje, String fechaHora) {
        this.nombreJugador = nombreJugador;
        this.nivel         = nivel;
        this.puntaje       = puntaje;
        this.fechaHora     = fechaHora;
    }

    public String getNombreJugador() { return nombreJugador; }
    public int    getNivel()         { return nivel; }
    public int    getPuntaje()       { return puntaje; }
    public String getFechaHora()     { return fechaHora; }

    // ── Guardar puntaje en BD ─────────────────────────────────────────────────
    public static boolean guardarPuntaje(String alias, int nivel, int puntaje) {
        String sql = "INSERT INTO puntajes (nombre_jugador, nivel, puntaje) VALUES (?, ?, ?)";
        try (Connection con = DataBaseConnection.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, alias);
            ps.setInt(2, nivel);
            ps.setInt(3, puntaje);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al guardar puntaje: " + e.getMessage());
            return false;
        }
    }

    // ── Obtener ranking (todos los niveles o filtrado) ────────────────────────
    public static List<PuntajeModel> obtenerRanking(int nivel) {
        List<PuntajeModel> lista = new ArrayList<>();
        String sql;
        if (nivel == 0) {
            sql = "SELECT nombre_jugador, nivel, puntaje, fecha_hora " +
                  "FROM puntajes ORDER BY puntaje DESC LIMIT 50";
        } else {
            sql = "SELECT nombre_jugador, nivel, puntaje, fecha_hora " +
                  "FROM puntajes WHERE nivel = ? ORDER BY puntaje DESC LIMIT 50";
        }
        try (Connection con = DataBaseConnection.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (nivel != 0) ps.setInt(1, nivel);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new PuntajeModel(
                    rs.getString("nombre_jugador"),
                    rs.getInt("nivel"),
                    rs.getInt("puntaje"),
                    rs.getString("fecha_hora")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ranking: " + e.getMessage());
        }
        return lista;
    }
}
