package com.example.Model;

/**
 * Encapsula la regla matemática oculta de cada nivel del juego.
 * <p>
 * Define seis funciones progresivas:
 * <ul>
 *   <li>Nivel 1: x + 3</li>
 *   <li>Nivel 2: x × 2</li>
 *   <li>Nivel 3: 2x + 1</li>
 *   <li>Nivel 4: x²</li>
 *   <li>Nivel 5: x² + x</li>
 *   <li>Nivel 6: x³ − x</li>
 * </ul>
 * Contiene la inner class estática {@link ParInOut} para representar pares entrada-salida.
 * </p>
 */
public class Regla {

    private final int nivel;

    public Regla(int nivel) { this.nivel = nivel; }

    public int getNivel() { return nivel; }

    /**
     * Aplica la función matemática del nivel actual al valor de entrada.
     *
     * @param x valor de entrada proporcionado por el jugador
     * @return resultado de aplicar la regla del nivel a {@code x}
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
     * Formatea un valor {@code double} eliminando el decimal cuando es entero
     * (p. ej. {@code 9.0} → {@code "9"}).
     *
     * @param v valor numérico a formatear
     * @return representación en cadena sin decimales innecesarios
     */
    public static String formatear(double v) {
        return (v == Math.floor(v) && !Double.isInfinite(v))
                ? String.valueOf((long) v)
                : String.valueOf(v);
    }

    /**
     * Evalúa una entrada y retorna el par entrada-salida formateado.
     *
     * @param entrada valor numérico ingresado por el jugador
     * @return {@link ParInOut} con ambos valores ya formateados como cadenas
     */
    public ParInOut evaluar(double entrada) {
        double salida = aplicarRegla(entrada);
        return new ParInOut(formatear(entrada), formatear(salida));
    }

    /**
     * Par inmutable de entrada-salida formateados como cadenas.
     * <p>
     * Los getters siguen la convención JavaBean para el binding automático
     * con {@code PropertyValueFactory} en la {@code TableView}.
     * </p>
     */
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