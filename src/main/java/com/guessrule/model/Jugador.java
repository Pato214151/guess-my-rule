package com.guessrule.model;

/**
 * Entidad que representa un jugador en el juego.
 */
public class Jugador {

    private int id;
    private String alias;
    private int puntaje;

    public Jugador() {
    }

    public Jugador(String alias) {
        this.alias = alias;
        this.puntaje = 0;
    }

    public Jugador(int id, String alias, int puntaje) {
        this.id = id;
        this.alias = alias;
        this.puntaje = puntaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return "Jugador{id=" + id + ", alias='" + alias + "', puntaje=" + puntaje + "}";
    }
}
