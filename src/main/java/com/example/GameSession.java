package com.example;

public class GameSession {

    private static GameSession instance;

    private String alias    = "Invitado";
    private int    nivel    = 1;
    private int    puntaje  = 0;
    private int    segundos = 0;

    private GameSession() {}

    public static GameSession getInstance() {
        if (instance == null) instance = new GameSession();
        return instance;
    }

    public String getAlias()              { return alias; }
    public void   setAlias(String alias)  { this.alias = alias; }

    public int  getNivel()               { return nivel; }
    public void setNivel(int nivel)      { this.nivel = nivel; }

    public int  getPuntaje()             { return puntaje; }
    public void setPuntaje(int puntaje)  { this.puntaje = puntaje; }

    public int  getSegundos()              { return segundos; }
    public void setSegundos(int segundos)  { this.segundos = segundos; }
}
