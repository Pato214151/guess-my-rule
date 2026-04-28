package com.example.Model;

public class Regla {

    // ── Estado del juego en curso ──────────────────────────────
    private final int nivel;
    private int tiempo   = 0;
    private int intentos = 0;

    public Regla(int nivel) {
        this.nivel = nivel;
    }

    // ── Getters / Setters de estado ────────────────────────────
    public int getNivel()          { return nivel; }

    public int getTiempo()         { return tiempo; }
    public void setTiempo(int t)   { this.tiempo = t; }

    public int getIntentos()       { return intentos; }
    public void setIntentos(int i) { this.intentos = i; }

    // ── Lógica matemática ──────────────────────────────────────
    public double aplicarRegla(double x) {
        return switch (nivel) {
            case 1 -> x + 3;
            case 2 -> x * 2;
            case 3 -> x * 2 + 1;
            case 4 -> x * x;
            case 5 -> x * x + x;
            case 6 -> x * x * x - x;
            default -> x;
        };
    }

    public static String formatear(double v) {
        return (v == Math.floor(v) && !Double.isInfinite(v))
                ? String.valueOf((long) v)
                : String.valueOf(v);
    }

    public ParInOut evaluar(double entrada) {
        double salida = aplicarRegla(entrada);
        return new ParInOut(formatear(entrada), formatear(salida));
    }

    public int calcularPuntaje() {
        return Math.max(0, 1000 - (tiempo * 5) - (intentos * 50));
    }

    // ── Clase interna: fila tabla Find the Rule ────────────────
    public static class ParInOut {
        private final String entrada;
        private final String salida;

        public ParInOut(String entrada, String salida) {
            this.entrada = entrada;
            this.salida  = salida;
        }

        public String getEntrada() { return entrada; }
        public String getSalida()  { return salida; }
    }
}