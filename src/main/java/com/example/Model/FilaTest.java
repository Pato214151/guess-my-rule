package com.example.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Representa una fila editable de la tabla de evaluación en {@code BloqueDeclararRegla}.
 * <p>
 * Usa {@link javafx.beans.property.StringProperty} para soportar binding bidireccional
 * con la {@code TableView} editable de JavaFX. Los valores {@code entradaReal} y
 * {@code salidaEsperada} son inmutables y se usan para verificar la respuesta del jugador.
 * </p>
 */
public class FilaTest {

    private final double entradaReal;
    private final double salidaEsperada;
    private final StringProperty entrada;
    private final StringProperty respuesta;

    public FilaTest(double entradaReal, String respuestaInicial, double salidaEsperada) {
        this.entradaReal    = entradaReal;
        this.salidaEsperada = salidaEsperada;
        this.entrada        = new SimpleStringProperty(Regla.formatear(entradaReal));
        this.respuesta      = new SimpleStringProperty(respuestaInicial);
    }

    public double getEntradaReal()            { return entradaReal; }
    public double getSalidaEsperada()         { return salidaEsperada; }

    public StringProperty entradaProperty()   { return entrada; }
    public StringProperty respuestaProperty() { return respuesta; }

    public String getEntrada()                { return entrada.get(); }
    public String getRespuesta()              { return respuesta.get(); }
    public void   setRespuesta(String v)      { respuesta.set(v); }
}