package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    private TextField aliasField;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnInvitado;

    @FXML
    private Button btnRegistrar;

    private boolean aliasConfirmado = false;

    @FXML
    public void initialize() {
        btnStart.setDisable(true);
        btnRegistrar.setDisable(true);

    aliasField.textProperty().addListener((observable, oldValue, newValue) -> {
        boolean isEmpty = newValue.trim().isEmpty();
        btnRegistrar.setDisable(isEmpty);

        aliasConfirmado = false;
        btnStart.setDisable(true);
        });
    }

    @FXML
    private void handleRegistrar(ActionEvent event) {
        aliasConfirmado = true;
        btnStart.setDisable(false);
        System.out.println("Alias registrado: " + aliasField.getText().trim());
    }

    @FXML
    private void handleInvitado(ActionEvent event) {
        aliasField.setText("Invitado");
        aliasConfirmado = true;
        btnStart.setDisable(false);
        btnRegistrar.setDisable(true);
    }

    @FXML
    private void handleStart(ActionEvent event) throws IOException {
        String alias = aliasField.getText().trim();
        System.out.println("Jugador: " + alias);
        try {
        App.setRoot("secondary");
        } catch (IOException e) {
        throw new NavigationException("Error al cargar la pantalla de juego", e);
        }
    }
}