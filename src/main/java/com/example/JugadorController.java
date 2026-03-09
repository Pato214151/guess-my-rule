// com/example/controller/PrimaryController.java
package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class JugadorController {

    @FXML private Label titleLabel;
    @FXML private TextField aliasField;
    @FXML private Button btnRegistrar;
    @FXML private Button btnInvitado;
    @FXML private Button btnStart;

    // El controller conoce al service, NO hace lógica directamente
    private final JugadorLogica jugadorLogica = new JugadorLogica();
    private JugadorModel currentPlayer;

    @FXML
    public void handleRegistrar() {
        try {
            currentPlayer = jugadorLogica.registerPlayer(aliasField.getText());
            btnStart.setDisable(false);
            showFeedback("✅ Alias registrado: " + currentPlayer.getAlias());
        } catch (IllegalArgumentException e) {
            showFeedback("⚠️ " + e.getMessage());
        }
    }

    @FXML
    public void handleInvitado() {
        currentPlayer = jugadorLogica.enterAsGuest();
        btnStart.setDisable(false);
        showFeedback("👤 Ingresando como Invitado");
    }

    @FXML
    public void handleStart() {
        if (currentPlayer != null) {
            // Navegar a la siguiente vista, pasando el modelo
            System.out.println("Iniciando juego con: " + currentPlayer.getAlias());
            // App.loadScene("game.fxml", currentPlayer);
        }
    }

    private void showFeedback(String message) {
        titleLabel.setText(message);
    }
}