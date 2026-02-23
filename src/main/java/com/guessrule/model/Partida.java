package com.guessrule.model;

import java.time.LocalDateTime;

/**
 * Entidad que representa una partida del juego.
 */
public class Partida {

    private int id;
    private int jugadorId;
    private int nivel;
    private int intentos;
    private boolean completada;
    private LocalDateTime fecha;

    public Partida() {
    }

    public Partida(int jugadorId, int nivel) {
        this.jugadorId = jugadorId;
        this.nivel = nivel;
        this.intentos = 0;
        this.completada = false;
        this.fecha = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJugadorId() {
        return jugadorId;
    }

    public void setJugadorId(int jugadorId) {
        this.jugadorId = jugadorId;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Partida{id=" + id + ", jugadorId=" + jugadorId + ", nivel=" + nivel + ", intentos=" + intentos + "}";
    }
}
