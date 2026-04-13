package com.example;

import com.example.Model.JugadorModel; // Importante importar tu modelo

public class GameSession {
    private static GameSession instance;
    
    // CAMBIO CLAVE: En lugar de 'private String alias', usamos el objeto
    private JugadorModel jugador; 
    private int nivel;

    private GameSession() {
        // Valores iniciales
        this.nivel = 1;
    }

    public static GameSession getInstance() {
        if (instance == null) {
            instance = new GameSession();
        }
        return instance;
    }

    // Ahora los métodos gestionan al objeto JugadorModel
    public void setJugador(JugadorModel jugador) {
        this.jugador = jugador;
    }

    public JugadorModel getJugador() {
        return jugador;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}