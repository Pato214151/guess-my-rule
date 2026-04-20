package com.example.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FilaTestModel {
    private final double entradaReal;
    private final double salidaEsperada;
    private final StringProperty entrada;
    private final StringProperty respuesta;
    
public FilaTestModel(double entradaReal, String respuestaInicial, double salidaEsperada) {
    this.entradaReal = entradaReal;
    this.salidaEsperada = salidaEsperada;
    this.entrada = new SimpleStringProperty(ReglaModel.formatear(entradaReal));
    this.respuesta = new SimpleStringProperty(respuestaInicial);
}
    public double getEntradaReal()    { return entradaReal; }
    public double getSalidaEsperada() { return salidaEsperada; }

    public StringProperty entradaProperty()   { return entrada; }
    public StringProperty respuestaProperty() { return respuesta; }

    public String getEntrada()   { return entrada.get(); }
    public String getRespuesta() { return respuesta.get(); }
    public void   setRespuesta(String v) { respuesta.set(v); }
}