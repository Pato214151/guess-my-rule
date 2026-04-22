package com.example.Controller;

import com.example.App;
import com.example.Model.DeclararRegla;
import com.example.Model.GameSession;
import com.example.Model.AprenderRegla;

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
    @FXML private TableView<DeclararRegla> tablaTest;
    @FXML private TableColumn<DeclararRegla, String> colIn;
    @FXML private TableColumn<DeclararRegla, String> colOut;

    private AprenderRegla regla;
    private ObservableList<DeclararRegla> filas; // ✅ ObservableList solo en el Controller
    private Timeline cronometro;
    private int segundos = 0;
    private int intentos = 0;
    private boolean yaVerificado = false;

    private static final String[] TITULOS = {
        "", "Nivel 1", "Nivel 2", "Nivel 3",
        "Nivel 4", "Nivel 5", "Nivel 6"
    };

    @FXML
    public void initialize() {
        int nivel = GameSession.getInstance().getNivel();
        regla = new AprenderRegla(nivel);
        labelTitulo.setText(TITULOS[nivel] + " - Test Your Rule");

        colIn.setCellValueFactory(c -> c.getValue().entradaProperty());
        colIn.setCellFactory(column -> {
            TableCell<DeclararRegla, String> cell = new TableCell<>() {
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
            };
            return cell;
        });

        colOut.setCellValueFactory(c -> c.getValue().respuestaProperty());
        tablaTest.setEditable(true);
        colOut.setEditable(true);

        colOut.setCellFactory(column -> new TextFieldTableCell<DeclararRegla, String>(new DefaultStringConverter()) {
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

                DeclararRegla fila = getTableRow().getItem();
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

        // ✅ Persistencia: List → ObservableList al cargar
        List<DeclararRegla> guardadas = GameSession.getInstance().getFilasTest();
        if (guardadas != null) {
            filas = FXCollections.observableArrayList(guardadas); // List → ObservableList
            segundos = GameSession.getInstance().getTiempo();
            intentos = GameSession.getInstance().getIntentos();
        } else {
            filas = FXCollections.observableArrayList();
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                double entrada = random.nextInt(20) + 1;
                double salida  = regla.aplicarRegla(entrada);
                filas.add(new DeclararRegla(entrada, "", salida));
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
        labelFeedback.setText("");

        boolean todosCorrecto = true;

        for (DeclararRegla fila : filas) {
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

            int puntajeCalculado = Math.max(0, 1000 - (segundos * 5) - (intentos * 50));
            GameSession session = GameSession.getInstance();
            session.setPuntaje(puntajeCalculado);
            session.setTiempo(segundos);
            session.setIntentos(intentos);
            session.setFilasTest(null); // ✅ null limpia la sesión

            labelFeedback.setStyle("-fx-font-size: 13px; -fx-text-fill: #2e7d32;");
            labelFeedback.setText("Todo correcto!");

            try {
                App.setRoot("ResumenPuntaje");
            } catch (Exception e) {
                labelFeedback.setText("Error al navegar: " + e.getMessage());
            }

        } else {
            labelFeedback.setStyle("-fx-font-size: 13px; -fx-text-fill: #e53935;");
            labelFeedback.setText("Hay respuestas incorrectas. intentanlo de nuevo!");
        }
    }

    @FXML
    public void handleGoBack() {
        cronometro.stop();

        GameSession session = GameSession.getInstance();
        session.setFilasTest(new ArrayList<>(filas)); // ✅ ObservableList → List al guardar
        session.setTiempo(segundos);
        session.setIntentos(intentos);

        try {
            App.setRoot("BloqueAprenderRegla");
        } catch (IOException e) {
            labelFeedback.setText("Error al navegar: " + e.getMessage());
        }
    }
}