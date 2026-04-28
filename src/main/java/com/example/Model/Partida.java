package com.example.Model;

public class Partida {

    private int tiempo   = 0;
    private int intentos = 0;

    public int getTiempo()         { return tiempo; }
    public void setTiempo(int t)   { this.tiempo = t; }

    public int getIntentos()       { return intentos; }
    public void setIntentos(int i) { this.intentos = i; }

    public int calcularPuntaje() {
        return Math.max(0, 1000 - (tiempo * 5) - (intentos * 50));
    }
}