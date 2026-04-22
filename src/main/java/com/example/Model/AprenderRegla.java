package com.example.Model;

public class AprenderRegla {

    private final int nivel;

    public AprenderRegla(int nivel) {
        this.nivel = nivel;
    }

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
    public static class ParInOut {
        private final String entrada;
        private final String salida;

        public ParInOut(String entrada, String salida) {
            this.entrada = entrada;
            this.salida  = salida;
        }

        public String getEntrada() { return entrada; }
        public String getSalida()  { return salida;  }
    }
}
