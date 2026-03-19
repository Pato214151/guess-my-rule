package com.example.Controller;

import com.example.App;
import com.example.Model.GameSession;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MenuController {

    @FXML
public void handleVolver() throws IOException {
    App.setRoot("PantallaDeCarga");
}

    @FXML private Label welcomeLabel;

    @FXML
    public void initialize() {
        if (welcomeLabel != null) {
            welcomeLabel.setText("Bienvenido, " + GameSession.getInstance().getAlias());
        }
    }

    @FXML
    public void handleNivel(ActionEvent event) throws IOException {
        Button btn = (Button) event.getSource();
        String texto = btn.getText().replace("Nivel ", "").trim();
        int nivel = Integer.parseInt(texto);
        GameSession.getInstance().setNivel(nivel);
        App.setRoot("AprenderLaRegla");
    }

    @FXML
    public void handleComoJugar() {
        Alert modal = new Alert(AlertType.INFORMATION);
        modal.setTitle("¿Cómo Jugar?");
        modal.setHeaderText("Instrucciones");
        modal.setContentText(
            "OBJETIVO\n" +
            "Descubre la regla matemática oculta que transforma\n" +
            "números de entrada en números de salida.\n\n" +
            "MECÁNICA\n" +
            "1. Ingresa un número y presiona Go!\n" +
            "2. Observa el par Entrada → Salida en la tabla.\n" +
            "3. Cuando creas saber la regla, pulsa ¡Creo que sé la regla!\n\n" +
            "NIVELES\n" +
            "• Nivel 1: Muy Fácil   (ej. x + 3)\n" +
            "• Nivel 2: Fácil       (ej. x × 2)\n" +
            "• Nivel 3: Intermedio  (ej. 2x + 1)\n" +
            "• Nivel 4: Difícil     (ej. x²)\n" +
            "• Nivel 5: Muy Difícil (ej. x² + x)\n" +
            "• Nivel 6: Experto 🔥  (ej. x³ − x)"
        );
        modal.showAndWait();
    }

    @FXML
    public void handleHoverOn(MouseEvent e) {
        if (e.getSource() instanceof Button btn) {
            btn.setStyle(btn.getStyle()
                .replace("-fx-background-color: #64b5f6",
                         "-fx-background-color: #1976d2"));
        } else if (e.getSource() instanceof Label lbl) {
            lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #925a05; " +
                         "-fx-cursor: hand; -fx-underline: true;");
        }
    }

    @FXML
    public void handleHoverOff(MouseEvent e) {
        if (e.getSource() instanceof Button btn) {
            btn.setStyle(btn.getStyle()
                .replace("-fx-background-color: #1976d2",
                         "-fx-background-color: #64b5f6"));
        } else if (e.getSource() instanceof Label lbl) {
            lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #d18108; " +
                         "-fx-cursor: hand; -fx-underline: true;");
        }
    }
}