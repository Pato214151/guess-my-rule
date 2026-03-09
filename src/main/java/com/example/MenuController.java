package com.example;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class MenuController {

    @FXML private Label welcomeLabel;

    public void setPlayer(JugadorModel player) {
        welcomeLabel.setText("Bienvenido, " + player.getAlias());
    }

    @FXML
    public void handleNivel() throws IOException {
    App.setRoot("Juego"); // el nombre de tu próximo FXML
    }

    @FXML
    public void handleComoJugar() {
        Alert modal = new Alert(AlertType.INFORMATION);
        modal.setTitle("¿Cómo Jugar?");
        modal.setHeaderText("Instrucciones");
        modal.setContentText(
            "Objetivo: Descubrir la regla matemática oculta.\n\n" +
            "Mecánica: Ingresa números y observa los resultados " +
            "para deducir la regla.\n\n" +
            "Ejemplo de regla: Si ingresas 2 y obtienes 4, " +
            "la regla podría ser x * 2."
        );
        modal.showAndWait();
    }

    @FXML
public void handleHoverOn(MouseEvent e) {
    if (e.getSource() instanceof Button) {
        Button btn = (Button) e.getSource();
        btn.setStyle("-fx-background-color: #1976d2; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10; -fx-cursor: hand;");
    } else if (e.getSource() instanceof Label) {
        Label lbl = (Label) e.getSource();
        lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #e65100; -fx-cursor: hand; -fx-underline: true;");
    }
}

@FXML
public void handleHoverOff(MouseEvent e) {
    if (e.getSource() instanceof Button) {
        Button btn = (Button) e.getSource();
        btn.setStyle("-fx-background-color: #64b5f6; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10; -fx-cursor: hand;");
    } else if (e.getSource() instanceof Label) {
        Label lbl = (Label) e.getSource();
        lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffb74d; -fx-cursor: hand; -fx-underline: true;");
    }
}
}