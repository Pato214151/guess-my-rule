package com.example.Model;

import com.example.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PuntajeModel {

    private final String nombreJugador;
    private final int    nivel;
    private final int    puntaje;
    private final String fechaRegistro;

    public PuntajeModel(String nombreJugador, int nivel, int puntaje, String fechaRegistro) {
        this.nombreJugador = nombreJugador;
        this.nivel         = nivel;
        this.puntaje       = puntaje;
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombreJugador() { return nombreJugador; }
    public int    getNivel()         { return nivel; }
    public int    getPuntaje()       { return puntaje; }
    public String getFechaRegistro() { return fechaRegistro; }

    // Guardar puntaje con todos los campos requeridos por la BD
    public static boolean guardarPuntaje(String alias, int nivel, int puntaje, int intentos, int tiempoSegundos) {
        // La constraint chk_intentos_positivos requiere intentos > 0
        // La constraint chk_tiempo_positivo requiere tiempo_segundos > 0
        int intentosSafe = Math.max(1, intentos);
        int tiempoSafe   = Math.max(1, tiempoSegundos);

        String sql = "INSERT INTO puntajes (nombre_jugador, puntos, nivel, intentos, tiempo_segundos) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DataBaseConnection.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, alias);
            ps.setInt(2, puntaje);
            ps.setInt(3, nivel);
            ps.setInt(4, intentosSafe);
            ps.setInt(5, tiempoSafe);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al guardar puntaje: " + e.getMessage());
            return false;
        }
    }

    // Obtener ranking global o por nivel
    public static List<PuntajeModel> obtenerRanking(int nivel) {
        List<PuntajeModel> lista = new ArrayList<>();
        String sql;
        if (nivel == 0) {
            sql = "SELECT nombre_jugador, nivel, puntos, fecha_registro " +
                  "FROM puntajes ORDER BY puntos DESC LIMIT 50";
        } else {
            sql = "SELECT nombre_jugador, nivel, puntos, fecha_registro " +
                  "FROM puntajes WHERE nivel = ? ORDER BY puntos DESC LIMIT 50";
        }
        try (Connection con = DataBaseConnection.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (nivel != 0) ps.setInt(1, nivel);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new PuntajeModel(
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