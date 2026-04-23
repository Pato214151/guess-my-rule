package com.example.Controller;

// Controlador de la pantalla de registro de jugador (PantallaLogin.fxml).
// Su única responsabilidad: capturar el alias, pedir validación al modelo
// y transferir el alias a GameSession antes de navegar.

import java.io.IOException;

import com.example.App;
import com.example.Model.GameSession;
import com.example.Model.LoginJugador;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PantallaLogin {

    @FXML private Label     titleLabel;
    @FXML private TextField aliasField;
    @FXML private Button    btnRegistrar;
    @FXML private Button    btnInvitado;
    @FXML private Button    btnStart;
    @FXML private Label     feedbackLabel;

    private LoginJugador currentPlayer;

    @FXML
    public void handleRegistrar() {
        LoginJugador jugador = new LoginJugador(aliasField.getText(), false);

        if (!jugador.isValid()) {
            showFeedback("⚠️ " + jugador.getMensajeError());
            return;
        }

        currentPlayer = jugador;
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