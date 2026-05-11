package com.example.Model;

/**
 * Representa un registro de puntaje almacenado en la base de datos.
 * Es inmutable: todos los campos se asignan en el constructor.
 */
public class Puntaje {

    private final String nombreJugador;
    private final int    nivel;
    private final int    puntaje;
    private final String fechaRegistro;

    /**
     * Crea un registro de puntaje.
     *
     * @param nombreJugador alias del jugador
     * @param nivel         nivel en el que se obtuvo el puntaje
     * @param puntaje       puntos obtenidos
     * @param fechaRegistro fecha y hora del registro (formato de la BD)
     */
    public Puntaje(String nombreJugador, int nivel, int puntaje, String fechaRegistro) {
        this.nombreJugador = nombreJugador;
        this.nivel         = nivel;
        this.puntaje       = puntaje;
        this.fechaRegistro = fechaRegistro;
    }

    /** @return alias del jugador */
    public String getNombreJugador() { return nombreJugador; }
    /** @return nivel en el que se obtuvo el puntaje */
    public int    getNivel()         { return nivel; }
    /** @return puntos obtenidos */
    public int    getPuntaje()       { return puntaje; }
    /** @return fecha y hora del registro */
    public String getFechaRegistro() { return fechaRegistro; }
}