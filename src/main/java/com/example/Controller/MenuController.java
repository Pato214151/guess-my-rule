package com.example.Controller;

import java.io.IOException;

import com.example.App;
import com.example.Model.JugadorModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class MenuController {

    @FXML private Label welcomeLabel;

    private JugadorModel currentPlayer;

    public void setPlayer(JugadorModel player) {
        this.currentPlayer = player;
        welcomeLabel.setText("Bienvenido, " + player.getAlias());
    }

    @FXML
    public void handleNivel(ActionEvent event) throws IOException {
        Button btn = (Button) event.getSource();
        String nivelNum = btn.getText().replace("Nivel ", "").trim(); // "1", "2"...

        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/example/View/Nivel" + nivelNum + ".fxml"));
        App.getScene().setRoot(loader.load());

        NivelController nivelCtrl = loader.getController();
        if (currentPlayer != null) {
            nivelCtrl.setPlayer(currentPlayer);
        }
    }

    @FXML
    public void handleComoJugar() {
        Alert modal = new Alert(AlertType.INFORMATION);
        modal.setTitle("¿Cómo Jugar?");
        modal.setHeaderText("Instrucciones");
        modal.setContentText(
            " OBJETIVO\n" +
            "Descubre la regla matemática secreta que transforma\n" +
            "números de entrada en números de salida.\n\n" +

            "MECÁNICA\n" +
            "1. Ingresa un número en el campo de entrada.\n" +
            "2. El juego te mostrará el resultado según la regla oculta.\n" +
            "3. Analiza los pares (entrada → salida) para encontrar el patrón.\n" +
            "4. Cuando creas saber la regla, ¡declárala!\n" +
            "5. Tienes intentos limitados, ¡úsalos con sabiduría!\n\n" +

            "EJEMPLO\n" +
            "  Entrada: 3  →  Salida: 7\n" +
            "  Entrada: 5  →  Salida: 11\n" +
            "  Entrada: 8  →  Salida: 17\n" +
            "  ¿La regla? → f(x) = x * 2 + 1\n\n" +

            "CONSEJOS\n" +
            "• Prueba números pequeños primero (0, 1, 2...).\n" +
            "• Intenta números negativos para descubrir más patrones.\n" +
            "• Las reglas pueden ser sumas, restas, multiplicaciones\n" +
            "  o combinaciones de estas operaciones."
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
            lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #b44305; -fx-cursor: hand; -fx-underline: true;");
        }
    }

    @FXML
    public void handleHoverOff(MouseEvent e) {
        if (e.getSource() instanceof Button) {
            Button btn = (Button) e.getSource();
            btn.setStyle("-fx-background-color: #47aafa; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10; -fx-cursor: hand;");
        } else if (e.getSource() instanceof Label) {
            Label lbl = (Label) e.getSource();
            lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #ff9b05; -fx-cursor: hand; -fx-underline: true;");
        }
    }
}