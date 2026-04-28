package com.example.Controller;

import com.example.App;
import com.example.Model.FilaTest;
import com.example.Model.GameSession;
import com.example.Model.Regla;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Duration;
import javafx.util.converter.DefaultStringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BloqueDeclararRegla {

    @FXML private Label labelTitulo;
    @FXML private Label labelTiempo;
    @FXML private Label labelIntentos;
    @FXML private Label labelFeedback;
    @FXML private TableView<FilaTest> tablaTest;
    @FXML private TableColumn<FilaTest, String> colIn;
    @FXML private TableColumn<FilaTest, String> colOut;

    private Regla regla;
    private ObservableList<FilaTest> filas;
    private Timeline cronometro;
    private boolean yaVerificado = false;

    private static final String[] TITULOS = {
        "", "Nivel 1", "Nivel 2", "Nivel 3",
        "Nivel 4", "Nivel 5", "Nivel 6"
    };

    @FXML
    public void initialize() {
        // ── Obtener regla desde GameSession ───────────────────
        regla = GameSession.getInstance().getRegla();
        labelTitulo.setText(TITULOS[regla.getNivel()] + " - Test Your Rule");

        // ── Configurar columna In ──────────────────────────────
        colIn.setCellValueFactory(c -> c.getValue().entradaProperty());
        colIn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER);
                    setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
                }
            }
        });

        // ── Configurar columna Out editable ───────────────────
        colOut.setCellValueFactory(c -> c.getValue().respuestaProperty());
        tablaTest.setEditable(true);
        colOut.setEditable(true);

        colOut.setCellFactory(column -> new TextFieldTableCell<FilaTest, String>(new DefaultStringConverter()) {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setAlignment(Pos.CENTER);

                if (empty || item == null || getTableRow() == null || getTableRow().getItem() == null) {
                    setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
                    return;
                }

                if (!yaVerificado || item.isEmpty()) {
                    setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
                    return;
                }

                FilaTest fila = getTableRow().getItem();
                try {
                    double resp = Double.parseDouble(item.trim());
                    if (Math.abs(resp - fila.getSalidaEsperada()) < 0.01) {
                        setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-background-color: #a5d6a7;");
                    } else {
                        setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-background-color: #ef9a9a;");
                    }
                } catch (NumberFormatException e) {
                    setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-background-color: #ef9a9a;");
                }
            }
        });

        colOut.setOnEditCommit(e -> {
            e.getRowValue().setRespuesta(e.getNewValue());
            yaVerificado = false;
            tablaTest.refresh();
        });

        tablaTest.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        // ── Cargar filas: persistencia o nuevas ───────────────
        List<FilaTest> guardadas = GameSession.getInstance().getFilasTest();
        if (guardadas != null) {
            filas = FXCollections.observableArrayList(guardadas);
        } else {
            filas = FXCollections.observableArrayList();
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                double entrada = random.nextInt(20) + 1;
                double salida  = regla.aplicarRegla(entrada);
                filas.add(new FilaTest(entrada, "", salida));
            }
        }

        tablaTest.setItems(filas);

        // ── Restaurar estado del cronómetro e intentos ────────
        labelIntentos.setText(String.valueOf(regla.getIntentos()));
        labelTiempo.setText(regla.getTiempo() + " s");

        iniciarCronometro();
    }

    private void iniciarCronometro() {
        cronometro = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            regla.setTiempo(regla.getTiempo() + 1);
            labelTiempo.setText(regla.getTiempo() + " s");
        }));
        cronometro.setCycleCount(Timeline.INDEFINITE);
        cronometro.play();
    }

    @FXML
    public void handleCheck() {
        regla.setIntentos(regla.getIntentos() + 1);
        labelIntentos.setText(String.valueOf(regla.getIntentos()));
        labelFeedback.setText("");

        boolean todosCorrecto = true;

        for (FilaTest fila : filas) {
            String respuestaTexto = fila.getRespuesta().trim();
            try {
                double resp = Double.parseDouble(respuestaTexto);
                if (Math.abs(resp - fila.getSalidaEsperada()) >= 0.001) {
                    todosCorrecto = false;
                }
            } catch (NumberFormatException e) {
                todosCorrecto = false;
            }
        }

        yaVerificado = true;
        tablaTest.refresh();

        if (todosCorrecto) {
            cronometro.stop();

            // ── Puntaje se calcula en el modelo ───────────────
            GameSession session = GameSession.getInstance();
            session.setFilasTest(null);

            labelFeedback.setStyle("-fx-font-size: 13px; -fx-text-fill: #2e7d32;");
            labelFeedback.setText("¡Todo correcto!");

            try {
                App.setRoot("ResumenPuntaje");
            } catch (Exception e) {
                labelFeedback.setText("Error al navegar: " + e.getMessage());
            }

        } else {
            labelFeedback.setStyle("-fx-font-size: 13px; -fx-text-fill: #e53935;");
            labelFeedback.setText("Hay respuestas incorrectas. ¡Inténtalo de nuevo!");
        }
    }

    @FXML
    public void handleGoBack() {
        cronometro.stop();

        GameSession session = GameSession.getInstance();
        session.setFilasTest(new ArrayList<>(filas));

        try {
            App.setRoot("BloqueAprenderRegla");
        } catch (IOException e) {
            labelFeedback.setText("Error al navegar: " + e.getMessage());
        }
    }
}