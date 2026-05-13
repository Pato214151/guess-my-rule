package com.example.Model;

/**
 * Registra el tiempo y los intentos de una partida,
 * y calcula el puntaje final.
 */
public class Partida {

    private int tiempo   = 0;
    private int intentos = 0;

    /** @return tiempo transcurrido en segundos */
    public int getTiempo()         { return tiempo; }
    /** @param t tiempo transcurrido en segundos */
    public void setTiempo(int t)   { this.tiempo = t; }

    /** @return número de intentos realizados */
    public int getIntentos()       { return intentos; }
    /** @param i número de intentos */
    public void setIntentos(int i) { this.intentos = i; }

    /**
     * Calcula el puntaje con la fórmula {@code 1000 - (tiempo*5) - (intentos*50)},
     * con un mínimo de 0.
     *
     * @return puntaje obtenido en la partida
     */
    public int calcularPuntaje() {
        return Math.max(0, 1000 - (tiempo * 5) - (intentos * 50));
    }
}