package com.example.Controller;

import com.example.App;
import com.example.Model.GameSession;
import com.example.Model.PuntajeModelo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class ResumenPuntaje {

    @FXML private Label labelMensaje;
    @FXML private Label labelPuntaje;
    @FXML private Label labelTiempo;
    @FXML private Label labelEstado;

    @FXML
    public void initialize() {
        GameSession session = GameSession.getInstance();

        int nivel   = session.getNivel();
        int puntaje = session.getPuntaje(); // corregido
        int seg     = session.getTiempo();
        int intentos = session.getIntentos();
        String alias = session.getAlias();

        int min = seg / 60;
        int s   = seg % 60;

        labelMensaje.setText("¡Felicidades, " + alias + "!");
        labelPuntaje.setText("Puntaje: " + puntaje + " pts");
        labelTiempo.setText(String.format("Tiempo: %02d:%02d", min, s));

        boolean guardado = PuntajeModelo.guardarPuntaje(alias, nivel, puntaje, intentos, seg);
        if (guardado) {
            labelEstado.setStyle("-fx-text-fill: #1b5e20; -fx-font-size: 14px;");
            labelEstado.setText("Puntaje guardado en la base de datos.");
        } else {
            labelEstado.setStyle("-fx-text-fill: #e65100; -fx-font-size: 14px;");
            labelEstado.setText("No se pudo guardar el puntaje (sin conexión a BD).");
        }
    }

    @FXML
public void handleMenuPrincipal() {
    try {
        GameSession.getInstance().setFilasGuardadas(
            javafx.collections.FXCollections.observableArrayList());
        GameSession.getInstance().setFilasTest(null);
        App.setRoot("MenuSeleccionarNivel");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}