package com.guessrule.controller;

import java.io.IOException;

import com.guessrule.App;

import javafx.fxml.FXML;

/**
 * Controlador de la pantalla del juego.
 * Maneja la logica de interaccion durante la partida.
 */
public class JuegoController {

    @FXML
    private void switchToInicio() throws IOException {
        App.setRoot("view/inicio");
    }
}
