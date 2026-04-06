package com.example.Controller;

import com.example.App;
import com.example.GameSession;
import com.example.Model.ReglaModel;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeclararReglaController {

    @FXML private Label labelNivel;
    @FXML private Label labelTimer;
    @FXML private Label labelIntentos;
    @FXML private Label labelFeedback;
    @FXML private VBox contenedorPreguntas;
    @FXML private Button btnVerificar;

    private ReglaModel regla;
    private Timeline cronometro;
    private int segundos = 0;
    private int intentosFallidos = 0;

    private static final int[] ENTRADAS_FIJAS = {1, 2, 3, 4, 5};
    private final List<TextField> camposRespuesta = new ArrayList<>();

    @FXML
    public void initialize() {
        int nivel = GameSession.getInstance().getNivel();
        regla = new ReglaModel(nivel);
        labelNivel.setText("Nivel " + nivel + " – Test Your Rule!");

        construirPreguntas();
        iniciarCronometro();
    }

    private void construirPreguntas() {
        contenedorPreguntas.getChildren().clear();
        camposRespuesta.clear();

        for (int entrada : ENTRADAS_FIJAS) {
            HBox fila = new HBox(12);
            fila.setStyle("-fx-alignment: center-left; -fx-padding: 4 0 4 0;");

            Label lblEntrada = new Label("f(" + entrada + ") = ");
            lblEntrada.setStyle("-fx-font-size: 16px; -fx-min-width: 80px;");

            TextField campo = new TextField();
            campo.setPromptText("Tu respuesta");
            campo.setStyle("-fx-font-size: 16px; -fx-min-width: 140px;");
            campo.setOnAction(e -> handleVerificar());

            camposRespuesta.add(campo);
            fila.getChildren().addAll(lblEntrada, campo);
            contenedorPreguntas.getChildren().add(fila);
        }
    }

    private void iniciarCronometro() {
        cronometro = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            segundos++;
            int min = segundos / 60;
            int seg = segundos % 60;
            labelTimer.setText(String.format("%02d:%02d", min, seg));
        }));
        cronometro.setCycleCount(Animation.INDEFINITE);
        cronometro.play();
    }

    @FXML
    public void handleVerificar() {
        boolean todoCorrecto = true;

        for (int i = 0; i < ENTRADAS_FIJAS.length; i++) {
            String texto = camposRespuesta.get(i).getText().trim();
            double esperado = regla.aplicarRegla(ENTRADAS_FIJAS[i]);

            try {
                double ingresado = Double.parseDouble(texto);
                if (Math.abs(ingresado - esperado) > 0.0001) {
                    todoCorrecto = false;
                    camposRespuesta.get(i).setStyle(
                        "-fx-font-size: 16px; -fx-min-width: 140px; -fx-border-color: #e53935;");
                } else {
                    camposRespuesta.get(i).setStyle(
                        "-fx-font-size: 16px; -fx-min-width: 140px; -fx-border-color: #43a047;");
                }
            } catch (NumberFormatException ex) {
                todoCorrecto = false;
                camposRespuesta.get(i).setStyle(
                    "-fx-font-size: 16px; -fx-min-width: 140px; -fx-border-color: #e53935;");
            }
        }

        if (todoCorrecto) {
            cronometro.stop();
            int puntaje = calcularPuntaje();
            GameSession.getInstance().setPuntaje(puntaje);
            GameSession.getInstance().setSegundos(segundos);
            labelFeedback.setStyle("-fx-text-fill: #1b5e20; -fx-font-size: 14px;");
            labelFeedback.setText("¡Correcto! Puntaje: " + puntaje);
            btnVerificar.setDisable(true);

            try {
                App.setRoot("ResultadoNivel");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            intentosFallidos++;
            labelIntentos.setText("Intentos: " + intentosFallidos);
            labelFeedback.setStyle("-fx-text-fill: #b71c1c; -fx-font-size: 14px;");
            labelFeedback.setText("Algunas respuestas son incorrectas. ¡Inténtalo de nuevo!");
        }
    }

    private int calcularPuntaje() {
        int base = 1000;
        int penalizacionTiempo = segundos * 5;
        int penalizacionIntentos = intentosFallidos * 50;
        return Math.max(100, base - penalizacionTiempo - penalizacionIntentos);
    }

    @FXML
    public void handleVolver() throws IOException {
        cronometro.stop();
        App.setRoot("AprenderLaRegla");
    }
}
