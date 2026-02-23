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

    @FXML
    public void initialize() {
        btnStart.setDisable(true);
        btnRegistrar.setDisable(true);

    aliasField.textProperty().addListener((observable, oldValue, newValue) -> {
        boolean isEmpty = newValue.trim().isEmpty();
        btnStart.setDisable(isEmpty);
        btnRegistrar.setDisable(isEmpty);
    });
}

    @FXML
    private void handleStart(ActionEvent event) throws IOException {
        String alias = aliasField.getText().trim();
        System.out.println("Jugador: " + alias);
        App.setRoot("secondary");
    }

    @FXML
    private void handleRegistrar(ActionEvent event) {
        System.out.println("Registrar alias presionado");
    }

    @FXML
    private void handleInvitado(ActionEvent event) {
        aliasField.setText("Invitado");
        btnStart.setDisable(false);
    }
}