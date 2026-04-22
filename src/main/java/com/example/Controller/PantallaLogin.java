package com.example.Controller;

//CONTROLADOR DE LA PANTALLA DE REGISTRO DE JUGADOR,
//  ES DECIR PANTALLAINICIALXML

import java.io.IOException;

import com.example.App;
import com.example.Model.GameSession;
import com.example.Model.LoginJugador;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PantallaLogin {

    @FXML private Label titleLabel;
    @FXML private TextField aliasField;
    @FXML private Button btnRegistrar;
    @FXML private Button btnInvitado;
    @FXML private Button btnStart;
    @FXML private Label feedbackLabel;

    private LoginJugador currentPlayer;

    @FXML
    public void handleRegistrar() {
        String alias = aliasField.getText();
        if (alias == null || alias.trim().isEmpty()) {
            showFeedback("⚠️ El alias no puede estar vacío");
            return;
        }
        if (!alias.trim().matches("[a-zA-Z0-9]+")) {
            showFeedback("⚠️ El alias solo puede contener letras y números");
            return;
        }
        currentPlayer = new LoginJugador(alias.trim(), false);
        btnStart.setDisable(false);
        showFeedback("Alias registrado: " + currentPlayer.getAlias());
    }

    @FXML
    public void handleInvitado() {
        currentPlayer = new LoginJugador("Invitado", true);
        btnStart.setDisable(false);
        showFeedback("Ingresando como Invitado");
    }

    @FXML
public void handleStart() {
    if (currentPlayer != null) {
        try {
            GameSession.getInstance().setAlias(currentPlayer.getAlias());
            App.setRoot("MenuSeleccionarNivel");
        } catch (IOException e) {
            showFeedback("Error al navegar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

    private void showFeedback(String message) {
        feedbackLabel.setText(message);
    }
}