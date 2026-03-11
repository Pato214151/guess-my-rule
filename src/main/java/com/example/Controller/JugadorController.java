package com.example.Controller;

import java.io.IOException;

import com.example.App;
import com.example.Model.JugadorLogica;
import com.example.Model.JugadorModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

public class JugadorController {

    @FXML private Label titleLabel;
    @FXML private TextField aliasField;
    @FXML private Button btnRegistrar;
    @FXML private Button btnInvitado;
    @FXML private Button btnStart;

    private final JugadorLogica jugadorLogica = new JugadorLogica();
    private JugadorModel currentPlayer;

    @FXML
    public void handleRegistrar() {
        try {
            currentPlayer = jugadorLogica.registerPlayer(aliasField.getText());
            btnStart.setDisable(false);
            showFeedback("Alias registrado: " + currentPlayer.getAlias());
        } catch (IllegalArgumentException e) {
            showFeedback("⚠️ " + e.getMessage());
        }
    }

    @FXML
    public void handleInvitado() {
        currentPlayer = jugadorLogica.enterAsGuest();
        btnStart.setDisable(false);
        showFeedback("Ingresando como Invitado");
    }

    @FXML
    public void handleStart() {
        if (currentPlayer != null) {
            try {
                FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/example/View/Menu.fxml")); // ← solo esto cambia
                App.getScene().setRoot(loader.load());
                MenuController menuCtrl = loader.getController();
                menuCtrl.setPlayer(currentPlayer);
            } catch (IOException e) {
            e.printStackTrace();
            showFeedback("Error: " + e.getMessage());
            }
        }
    }

    @FXML private Label feedbackLabel; // agrega este campo

    private void showFeedback(String message) {
    feedbackLabel.setText(message); // ya no toca titleLabel
    }
}