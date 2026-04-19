package com.example.Controller;

import com.example.App;
import com.example.GameSession;
import com.example.Model.FilaTestModel;
import com.example.Model.ReglaModel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Duration;

import java.io.IOException;

public class TestYourRuleController {

    @FXML private Label labelTitulo;
    @FXML private Label labelTiempo;
    @FXML private Label labelIntentos;
    @FXML private Label labelFeedback;
    @FXML private TableView<FilaTestModel> tablaTest;
    @FXML private TableColumn<FilaTestModel, String> colIn;
    @FXML private TableColumn<FilaTestModel, String> colOut;

    private ReglaModel regla;
    private ObservableList<FilaTestModel> filas;

    private Timeline cronometro;
    private int segundos;
    private int intentos;

    private static final double[] ENTRADAS_FIJAS = {2, 5, 10, 15, 20};

    private static final String[] TITULOS = {
        "", "Nivel 1", "Nivel 2", "Nivel 3",
        "Nivel 4", "Nivel 5", "Nivel 6"
    };

    @FXML
    public void initialize() {
        int nivel = GameSession.getInstance().getNivel();
        regla = new ReglaModel(nivel);
        labelTitulo.setText(TITULOS[nivel] + " - Test Your Rule");

        // =========================
        // CONFIGURAR TABLA
        // =========================
        colIn.setCellValueFactory(c -> c.getValue().entradaProperty());
        colOut.setCellValueFactory(c -> c.getValue().respuestaProperty());

        tablaTest.setEditable(true);
        colOut.setCellFactory(TextFieldTableCell.forTableColumn());
        colOut.setOnEditCommit(e ->
            e.getRowValue().setRespuesta(e.getNewValue())
        );

        // 🔥 Colorear SOLO la celda OUT
        colOut.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                    setStyle("");
                    return;
                }

                setText(item);

                FilaTestModel fila = getTableRow().getItem();

                try {
                    double resp = Double.parseDouble(item);
                    double esperado = fila.getSalidaEsperada();

                    if (Math.abs(resp - esperado) < 0.001) {
                        setStyle("-fx-background-color: #a5d6a7;"); // verde
                    } else {
                        setStyle("-fx-background-color: #ef9a9a;"); // rojo
                    }
                } catch (Exception e) {
                    setStyle("-fx-background-color: #ef9a9a;");
                }
            }
        });

        tablaTest.setColumnResizePolicy(
            TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        // =========================
        // PERSISTENCIA
        // =========================
        if (GameSession.getInstance().getFilasTest() != null) {
            filas = GameSession.getInstance().getFilasTest();
            segundos = GameSession.getInstance().getTiempo();
            intentos = GameSession.getInstance().getIntentos();
        } else {
            filas = FXCollections.observableArrayList();

            for (double entrada : ENTRADAS_FIJAS) {
                double salida = regla.aplicarRegla(entrada);

                // 👇 NO mostramos la salida
                filas.add(new FilaTestModel(entrada, "", salida));
            }

            segundos = 0;
            intentos = 0;
        }

        tablaTest.setItems(filas);
        labelIntentos.setText(String.valueOf(intentos));
        labelTiempo.setText(segundos + " s");

        iniciarCronometro();
    }

    private void iniciarCronometro() {
        cronometro = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            segundos++;
            labelTiempo.setText(segundos + " s");
        }));
        cronometro.setCycleCount(Timeline.INDEFINITE);
        cronometro.play();
    }

    @FXML
    public void handleCheck() {
        intentos++;
        labelIntentos.setText(String.valueOf(intentos));

        boolean todosCorrecto = true;

        for (FilaTestModel fila : filas) {
            try {
                double resp = Double.parseDouble(fila.getRespuesta());
                double esperado = fila.getSalidaEsperada();

                if (Math.abs(resp - esperado) >= 0.001) {
                    todosCorrecto = false;
                }
            } catch (Exception e) {
                todosCorrecto = false;
            }
        }

        tablaTest.refresh();

        if (todosCorrecto) {
            cronometro.stop();

            int puntaje = Math.max(0, 1000 - (segundos * 5) - (intentos * 50));

            GameSession session = GameSession.getInstance();
            session.setPuntaje(puntaje);
            session.setTiempo(segundos);
            session.setIntentos(intentos);
            session.setFilasTest(filas);

            labelFeedback.setStyle("-fx-text-fill: green;");
            labelFeedback.setText("¡Correcto!");

            try {
                App.setRoot("Logro");
            } catch (IOException e) {
                labelFeedback.setText("Error al navegar");
            }
        }
    }

    @FXML
    public void handleGoBack() {
        cronometro.stop();

        GameSession session = GameSession.getInstance();
        session.setFilasTest(filas);
        session.setTiempo(segundos);
        session.setIntentos(intentos);

        try {
            App.setRoot("AprenderLaRegla");
        } catch (IOException e) {
            labelFeedback.setText("Error al navegar");
        }
    }
}