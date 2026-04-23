package com.example.Model;

import com.example.Model.AprenderRegla.ParInOut;

import java.util.ArrayList;
import java.util.List;

public class GameSession {

    private static GameSession instance;

    private String alias    = "Invitado";
    private int    nivel    = 1;
    private int    tiempo   = 0;
    private int    intentos = 0;
    private int    puntaje  = 0;

    // List de Java puro, sin JavaFX
    private List<ParInOut>      filasGuardadas = new ArrayList<>();
    private List<DeclararRegla> filasTest      = null;

    private GameSession() {}

    public static GameSession getInstance() {
        if (instance == null) instance = new GameSession();
        return instance;
    } // 

    public String getAlias()             { return alias; }
    public void   setAlias(String a)     { this.alias = a; }

    public int  getNivel()               { return nivel; }
    public void setNivel(int n)          { this.nivel = n; }

    public int  getTiempo()              { return tiempo; }
    public void setTiempo(int t)         { this.tiempo = t; }

    public int  getIntentos()            { return intentos; }
    public void setIntentos(int i)       { this.intentos = i; }

    public int  getPuntaje()             { return puntaje; }
    public void setPuntaje(int p)        { this.puntaje = p; }

    public List<ParInOut> getFilasGuardadas()           { return filasGuardadas; }
    public void setFilasGuardadas(List<ParInOut> filas) { this.filasGuardadas = filas; }

    public List<DeclararRegla> getFilasTest()                  { return filasTest; }
    public void setFilasTest(List<DeclararRegla> filasTest)    { this.filasTest = filasTest; }

    // Limpia la sesión para un nuevo nivel
    public void resetNivel() {
        tiempo   = 0;
        intentos = 0;
        puntaje  = 0;
        filasGuardadas = new ArrayList<>();
        filasTest      = null;
    }
}