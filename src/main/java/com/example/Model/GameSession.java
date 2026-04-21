package com.example.Model;

import com.example.Model.ReglaModel.ParInOut;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Esta clase es un singleton que mantiene el estado global de la sesión de juego, 
// como el alias del jugador, el nivel actual, el tiempo transcurrido, los intentos realizados, 
// el puntaje acumulado, y las filas de datos guardadas para cada nivel. Es una forma de compartir información 
// entre diferentes controladores sin necesidad de pasar parámetros constantemente, esta clase hace varias cosas
// 1. le llega el alias desde el controlador jugador que guardo el alias de model y lo asosciioa con una seccion activa, 
// de ahi en adelante empieza a guardar los datos que el juego va proporcionando como nivel seleccionado, timepo intentos etc..







public class GameSession {

    private static GameSession instance;

    private String alias    = "Invitado";
    private int    nivel    = 1;
    private int    tiempo   = 0;
    private int    intentos = 0;
    private int    puntaje  = 0;

    private ObservableList<ParInOut>      filasGuardadas = FXCollections.observableArrayList();
    private ObservableList<FilaTestModel> filasTest      = null;

    private GameSession() {}

    public static GameSession getInstance() {
        if (instance == null) instance = new GameSession();
        return instance;
    }

    public String getAlias()               { return alias; }
    public void   setAlias(String alias)   { this.alias = alias; }

    public int  getNivel()                 { return nivel; }
    public void setNivel(int nivel)        { this.nivel = nivel; }

    public int  getTiempo()                { return tiempo; }
    public void setTiempo(int tiempo)      { this.tiempo = tiempo; }

    public int  getIntentos()              { return intentos; }
    public void setIntentos(int intentos)  { this.intentos = intentos; }

    public int  getPuntaje()               { return puntaje; }
    public void setPuntaje(int puntaje)    { this.puntaje = puntaje; }

    public ObservableList<ParInOut> getFilasGuardadas()                        { return filasGuardadas; }
    public void setFilasGuardadas(ObservableList<ParInOut> filas)              { this.filasGuardadas = filas; }

    public ObservableList<FilaTestModel> getFilasTest()                        { return filasTest; }
    public void setFilasTest(ObservableList<FilaTestModel> filasTest)          { this.filasTest = filasTest; }
}