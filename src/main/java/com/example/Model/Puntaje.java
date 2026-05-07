package com.example.Model;

/**
 * Entidad inmutable que representa un registro de la tabla {@code puntajes} en la base de datos.
 * <p>
 * Los getters siguen la convención JavaBean para que {@code PropertyValueFactory}
 * realice el binding automático con las columnas del ranking en {@code MenuSeleccionarNivel}.
 * </p>
 */
public class Puntaje {

    private final String nombreJugador;
    private final int    nivel;
    private final int    puntaje;
    private final String fechaRegistro;

    public Puntaje(String nombreJugador, int nivel, int puntaje, String fechaRegistro) {
        this.nombreJugador = nombreJugador;
        this.nivel         = nivel;
        this.puntaje       = puntaje;
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombreJugador() { return nombreJugador; }
    public int    getNivel()         { return nivel; }
    public int    getPuntaje()       { return puntaje; }
    public String getFechaRegistro() { return fechaRegistro; }
}