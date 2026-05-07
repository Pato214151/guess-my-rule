package com.example.Model;

/**
 * Registra las métricas de desempeño de la partida actual.
 * <p>
 * Almacena el tiempo transcurrido en segundos y el número de intentos realizados.
 * El puntaje se calcula penalizando tanto el tiempo como los intentos, incentivando
 * que el jugador piense antes de verificar.
 * </p>
 */
public class Partida {

    private int tiempo   = 0;
    private int intentos = 0;

    public int getTiempo()         { return tiempo; }
    public void setTiempo(int t)   { this.tiempo = t; }

    public int getIntentos()       { return intentos; }
    public void setIntentos(int i) { this.intentos = i; }

    /**
     * Calcula el puntaje final de la partida.
     * <p>
     * Fórmula: {@code max(0, 1000 - (tiempo × 5) - (intentos × 50))}.
     * La penalización por intento es mayor que por segundo para incentivar
     * la reflexión antes de verificar.
     * </p>
     *
     * @return puntaje calculado, nunca negativo
     */
    public int calcularPuntaje() {
        return Math.max(0, 1000 - (tiempo * 5) - (intentos * 50));
    }
}