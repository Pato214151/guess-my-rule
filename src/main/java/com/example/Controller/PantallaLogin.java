package com.example.Controller;

import java.io.IOException;

import com.example.App;
import com.example.Model.GameSession;
import com.example.Model.Jugador;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class PantallaLogin {

    @FXML private Label     titleLabel;
    @FXML private TextField aliasField;
    @FXML private Button    btnInvitado;
    @FXML private Button    btnStart;
    @FXML private Label     feedbackLabel;

    private Jugador currentPlayer;

    @FXML
    public void initialize() {
        TranslateTransition flotar = new TranslateTransition(Duration.millis(2000), titleLabel);
        flotar.setFromY(0);
        flotar.setToY(-10);
        flotar.setAutoReverse(true);
        flotar.setCycleCount(Timeline.INDEFINITE);
        flotar.play();

        aliasField.textProperty().addListener((obs, oldVal, newVal) -> {
            String texto = newVal == null ? "" : newVal.trim();
            if (texto.isEmpty()) {
                currentPlayer = null;
                btnStart.setDisable(true);
                feedbackLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #ef9a9a;");
                feedbackLabel.setText("");
            } else if (!texto.matches("[a-zA-Z0-9]+")) {
                currentPlayer = null;
                btnStart.setDisable(true);
                feedbackLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #ef9a9a;");
                feedbackLabel.setText("⚠️ Solo letras y números, sin espacios");
            } else {
                currentPlayer = new Jugador(texto, false);
                btnStart.setDisable(false);
                feedbackLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #e2ea0d;");
                feedbackLabel.setText("¡Dale al botón Start para jugar!");
            }
        });
    }

    @FXML
    public void handleInvitado() {
        try {
            GameSession.getInstance().setJugador(new Jugador("Invitado", true));
            App.setRoot("MenuSeleccionarNivel");
        } catch (IOException e) {
            feedbackLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #ef9a9a;");
            feedbackLabel.setText("Error al navegar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleStart() {
        if (currentPlayer != null) {
            try {
                GameSession.getInstance().setJugador(currentPlayer);
                App.setRoot("MenuSeleccionarNivel");
            } catch (IOException e) {
                feedbackLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #ef9a9a;");
                feedbackLabel.setText("Error al navegar: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
