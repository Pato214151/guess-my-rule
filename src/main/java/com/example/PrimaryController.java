package com.example;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController {

    @FXML
    private Button btnStart;

    @FXML
    private void handleStart() throws IOException {
        System.out.println("Botón presionado!");
        App.setRoot("secondary");
    }
}