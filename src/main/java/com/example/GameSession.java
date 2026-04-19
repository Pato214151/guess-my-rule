package com.example;

import com.example.Model.ReglaModel.ParInOut;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.Model.FilaTestModel;

public class GameSession {

    private static GameSession instance;

    private ObservableList<FilaTestModel> filasTest = null;

    private String alias   = "Invitado";
    private int    nivel   = 1;
    private int    tiempo  = 0;
    private int    intentos = 0;

    private GameSession() {}

    public static GameSession getInstance() {
        if (instance == null) instance = new GameSession();
        return instance;
    }

    public String getAlias()              { return alias; }
    public void   setAlias(String alias)  { this.alias = alias; }

    public int  getNivel()               { return nivel; }
    public void setNivel(int nivel)      { this.nivel = nivel; }

    public int  getTiempo()              { return tiempo; }
    public void setTiempo(int tiempo)    { this.tiempo = tiempo; }

    public int  getIntentos()            { return intentos; }
    public void setIntentos(int intentos){ this.intentos = intentos; }

    private int puntaje = 0;

public int  getPuntaje()           { return puntaje; }
public void setPuntaje(int puntaje){ this.puntaje = puntaje; }

    private ObservableList<ParInOut> filasGuardadas = FXCollections.observableArrayList();

public ObservableList<ParInOut> getFilasGuardadas() { return filasGuardadas; }
public void setFilasGuardadas(ObservableList<ParInOut> filas) { this.filasGuardadas = filas; }


    public ObservableList<FilaTestModel> getFilasTest() {
    return filasTest;
}

public void setFilasTest(ObservableList<FilaTestModel> filasTest) {
    this.filasTest = filasTest;
}
}
