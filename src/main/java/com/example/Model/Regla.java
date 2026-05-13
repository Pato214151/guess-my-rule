package com.example.Model;

/**
 * Encapsula la regla matemática de un nivel del juego.
 * Cada nivel define una función {@code f(x)} que el jugador debe descubrir.
 */
public class Regla {

    private final int nivel;

    /**
     * Crea la regla correspondiente al nivel indicado.
     *
     * @param nivel número de nivel (1–6)
     */
    public Regla(int nivel) { this.nivel = nivel; }

    /** @return número de nivel de esta regla */
    public int getNivel() { return nivel; }

    /**
     * Aplica la función matemática del nivel sobre el valor dado.
     * <ul>
     *   <li>Nivel 1: x + 3</li>
     *   <li>Nivel 2: x × 2</li>
     *   <li>Nivel 3: 2x + 1</li>
     *   <li>Nivel 4: x²</li>
     *   <li>Nivel 5: x² + x</li>
     *   <li>Nivel 6: x³ − x</li>
     * </ul>
     *
     * @param x valor de entrada
     * @return resultado de aplicar la regla
     */
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

    /**
     * Formatea un {@code double} eliminando el decimal cuando es entero exacto.
     *
     * @param v valor a formatear
     * @return representación textual sin decimales innecesarios
     */
    public static String formatear(double v) {
        return (v == Math.floor(v) && !Double.isInfinite(v))
                ? String.valueOf((long) v)
                : String.valueOf(v);
    }

    /**
     * Evalúa la regla sobre {@code entrada} y devuelve el par In/Out formateado.
     *
     * @param entrada valor numérico ingresado por el jugador
     * @return par {@link ParInOut} con entrada y salida como texto
     */
    public ParInOut evaluar(double entrada) {
        double salida = aplicarRegla(entrada);
        return new ParInOut(formatear(entrada), formatear(salida));
    }

    /**
     * Par inmutable de valores entrada/salida formateados como texto,
     * utilizado para poblar la tabla In/Out.
     */
    public static class ParInOut {
        private final String entrada;
        private final String salida;

        /**
         * @param entrada valor de entrada formateado
         * @param salida  valor de salida formateado
         */
        public ParInOut(String entrada, String salida) {
            this.entrada = entrada;
            this.salida  = salida;
        }

        /** @return valor de entrada formateado */
        public String getEntrada() { return entrada; }
        /** @return valor de salida formateado */
        public String getSalida()  { return salida; }
    }
}