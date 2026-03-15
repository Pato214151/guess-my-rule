package com.example.Controller;

import java.io.IOException;

import com.example.App;
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
    @FXML private Label feedbackLabel;

    private JugadorModel currentPlayer;

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
        currentPlayer = new JugadorModel(alias.trim(), false);
        btnStart.setDisable(false);
        showFeedback("Alias registrado: " + currentPlayer.getAlias());
    }

    @FXML
    public void handleInvitado() {
        currentPlayer = new JugadorModel("Invitado", true);
        btnStart.setDisable(false);
        showFeedback("Ingresando como Invitado");
    }

    @FXML
    public void handleStart() {
        if (currentPlayer != null) {
            try {
                FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/example/View/Menu.fxml"));
                App.getScene().setRoot(loader.load());
                MenuController menuCtrl = loader.getController();
                menuCtrl.setPlayer(currentPlayer);
            } catch (IOException e) {
                e.printStackTrace();
                showFeedback("Error: " + e.getMessage());
            }
        }
    }

    private void showFeedback(String message) {
        feedbackLabel.setText(message);
    }
}