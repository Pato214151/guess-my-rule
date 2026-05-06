package com.example.Controller;

import java.io.IOException;

import com.example.App;
import com.example.Model.GameSession;
import com.example.Model.Jugador;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PantallaLogin {

    @FXML private Label titleLabel;
    @FXML private TextField aliasField;
    @FXML private Button btnRegistrar;
    @FXML private Button btnInvitado;
    @FXML private Button btnStart;
    @FXML private Label feedbackLabel;

    private Jugador currentPlayer;

    @FXML
    public void initialize() {
        // Escucha cambios en el campo para habilitar/deshabilitar "Continuar" en tiempo real
        aliasField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean valido = newValue != null && !newValue.trim().isEmpty();
            btnStart.setDisable(!valido);
        });

        // "Continuar" empieza deshabilitado hasta que haya texto válido
        btnStart.setDisable(true);
    }

    @FXML
    public void handleMostrarFormulario() {
        // Oculta los botones de selección inicial
        btnRegistrar.setVisible(false);
        btnRegistrar.setManaged(false);
        btnInvitado.setVisible(false);
        btnInvitado.setManaged(false);

        // Revela el formulario de usuario
        aliasField.setVisible(true);
        aliasField.setManaged(true);
        btnStart.setVisible(true);
        btnStart.setManaged(true);

        aliasField.requestFocus();
    }

    @FXML
    public void handleInvitado() {
        currentPlayer = new Jugador("Invitado", true);
        navigateToMenu();
    }

    @FXML
    public void handleStart() {
        String alias = aliasField.getText();

        if (alias == null || alias.trim().isEmpty()) {
            showFeedback("⚠️ El nombre no puede estar vacío");
            return;
        }

        currentPlayer = new Jugador(alias.trim(), false);
        GameSession.getInstance().setJugador(currentPlayer);
        navigateToMenu();
    }

    private void navigateToMenu() {
        try {
            App.setRoot("MenuSeleccionarNivel");
        } catch (IOException e) {
            showFeedback("Error al navegar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showFeedback(String message) {
        feedbackLabel.setText(message);
    }
}