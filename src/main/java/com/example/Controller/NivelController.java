package com.example.Controller;

import java.io.IOException;

import com.example.App;
import com.example.Model.JugadorModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

public class NivelController {

    @FXML private Label nivelLabel; // opcional si quieres usarlo luego

    private JugadorModel currentPlayer;

    public void setPlayer(JugadorModel player) {
        this.currentPlayer = player;
    }

    @FXML
    public void handleVolver() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/example/View/Menu.fxml"));
        App.getScene().setRoot(loader.load());
        MenuController menuCtrl = loader.getController();
        if (currentPlayer != null) {
            menuCtrl.setPlayer(currentPlayer);
        }
    }
}