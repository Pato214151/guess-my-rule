package com.example.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Modelo de una fila en la tabla de prueba (BloqueDeclararRegla).
 * Contiene el valor de entrada, la respuesta ingresada por el jugador
 * y la salida esperada para verificación.
 */
public class FilaTest {

    private final double entradaReal;
    private final double salidaEsperada;
    private final StringProperty entrada;
    private final StringProperty respuesta;

    /**
     * Crea una fila de test.
     *
     * @param entradaReal       valor numérico de entrada
     * @param respuestaInicial  respuesta inicial del jugador (puede estar vacía)
     * @param salidaEsperada    resultado correcto según la regla activa
     */
    public FilaTest(double entradaReal, String respuestaInicial, double salidaEsperada) {
        this.entradaReal    = entradaReal;
        this.salidaEsperada = salidaEsperada;
        this.entrada        = new SimpleStringProperty(Regla.formatear(entradaReal));
        this.respuesta      = new SimpleStringProperty(respuestaInicial);
    }

    /** @return valor numérico real de la entrada */
    public double getEntradaReal()            { return entradaReal; }
    /** @return salida esperada según la regla */
    public double getSalidaEsperada()         { return salidaEsperada; }

    /** @return propiedad observable de la entrada (para binding en TableColumn) */
    public StringProperty entradaProperty()   { return entrada; }
    /** @return propiedad observable de la respuesta (para binding en TableColumn) */
    public StringProperty respuestaProperty() { return respuesta; }

    /** @return texto formateado de la entrada */
    public String getEntrada()                { return entrada.get(); }
    /** @return respuesta actual ingresada por el jugador */
    public String getRespuesta()              { return respuesta.get(); }
    /** @param v nueva respuesta del jugador */
    public void   setRespuesta(String v)      { respuesta.set(v); }
}