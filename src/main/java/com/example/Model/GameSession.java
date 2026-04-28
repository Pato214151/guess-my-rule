package com.example.Model;

import java.util.ArrayList;
import java.util.List;

public class GameSession {

    private static GameSession instance;
 
    private LoginJugador jugador;
    private Regla        regla;

    private List<Regla.ParInOut> filasGuardadas = new ArrayList<>();
    private List<FilaTest>       filasTest      = null;

    private GameSession() {}

    public static GameSession getInstance() {
        if (instance == null) instance = new GameSession();
        return instance;
    }

    public void setJugador(LoginJugador jugador) { this.jugador = jugador; }
    public LoginJugador getJugador()             { return jugador; }
    public String getAlias()                     { return jugador.getAlias(); }

    public void setRegla(Regla regla)  { this.regla = regla; }
    public Regla getRegla()            { return regla; }

    public int getNivel()              { return regla.getNivel(); }
    public int getTiempo()             { return regla.getTiempo(); }
    public int getIntentos()           { return regla.getIntentos(); }
    public int getPuntaje()            { return regla.calcularPuntaje(); }

    public void setTiempo(int t)       { regla.setTiempo(t); }
    public void setIntentos(int i)     { regla.setIntentos(i); }

    public List<Regla.ParInOut> getFilasGuardadas()           { return filasGuardadas; }
    public void setFilasGuardadas(List<Regla.ParInOut> filas) { this.filasGuardadas = filas; }

    public List<FilaTest> getFilasTest()                      { return filasTest; }
    public void setFilasTest(List<FilaTest> filas)            { this.filasTest = filas; }

    public void resetNivel(int nuevoNivel) {
        this.regla          = new Regla(nuevoNivel);
        this.filasGuardadas = new ArrayList<>();
        this.filasTest      = null;
    }
}
