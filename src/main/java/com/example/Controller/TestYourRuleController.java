package com.example.Controller;

import com.example.App;
import com.example.GameSession;
import com.example.Model.FilaTestModel;
import com.example.Model.ReglaModel;
import com.example.Model.ReglaModel.ParInOut;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class TestYourRuleController {

    @FXML private Label labelTitulo;
    @FXML private Label labelTiempo;
    @FXML private Label labelIntentos;
    @FXML private Label labelFeedback;
    @FXML private TableView<FilaTestModel> tablaTest;
    @FXML private TableColumn<FilaTestModel, String> colIn;
    @FXML private TableColumn<FilaTestModel, String> colOut;

    private ReglaModel regla;
    private final ObservableList<FilaTestModel> filas = FXCollections.observableArrayList();
    private Timeline cronometro;
    private int segundos = 0;
    private int intentos = 0;

    private static final String[] TITULOS = {
        "", "Nivel 1", "Nivel 2", "Nivel 3",
        "Nivel 4", "Nivel 5", "Nivel 6"
    };

    @FXML
    public void initialize() {
        int nivel = GameSession.getInstance().getNivel();
        regla = new ReglaModel(nivel);
        labelTitulo.setText(TITULOS[nivel] + " - Test Your Rule");

        // Columna In (solo lectura)
        colIn.setCellValueFactory(c -> c.getValue().entradaProperty());

        // Columna Out editable
        colOut.setCellValueFactory(c -> c.getValue().respuestaProperty());
        tablaTest.setEditable(true);
        colOut.setCellFactory(TextFieldTableCell.forTableColumn());
        colOut.setOnEditCommit(e ->
            e.getRowValue().setRespuesta(e.getNewValue())
        );

        tablaTest.setColumnResizePolicy(
            TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        // Generar 5 entradas aleatorias
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            double entrada = random.nextInt(20) + 1;
            double salida  = regla.aplicarRegla(entrada);
            filas.add(new FilaTestModel(entrada, salida));
        }
        tablaTest.setItems(filas);

        // Iniciar cronómetro
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

        for (FilaTestModel fila : filas) {
            String respuestaTexto = fila.getRespuesta().trim();
            boolean correcto = false;

            try {
                double respuestaNum = Double.parseDouble(respuestaTexto);
                double esperado     = fila.getSalidaEsperada();
                correcto = Math.abs(respuestaNum - esperado) < 0.001;
            } catch (NumberFormatException e) {
                correcto = false;
            }

            // Color verde o rojo en la celda Out
            int index = filas.indexOf(fila);
            tablaTest.setRowFactory(tv -> new TableRow<>());
            if (correcto) {
                tablaTest.lookupAll(".table-row-cell").forEach(n -> {});
            }

            fila.setRespuesta(respuestaTexto); // mantener valor
            todosCorrecto &= correcto;
        }

        // Colorear filas
        tablaTest.setRowFactory(tv -> new TableRow<FilaTestModel>() {
            @Override
            protected void updateItem(FilaTestModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    String resp = item.getRespuesta().trim();
                    boolean ok = false;
                    try {
                        ok = Math.abs(Double.parseDouble(resp)
                             - item.getSalidaEsperada()) < 0.001;
                    } catch (NumberFormatException ignored) {}
                    setStyle(ok
                        ? "-fx-background-color: #a5d6a7;"  // verde
                        : "-fx-background-color: #ef9a9a;"); // rojo
                }
            }
        });
        tablaTest.refresh();

        if (todosCorrecto) {
    cronometro.stop();
    
    // Aquí calculas y guardas el puntaje
    int puntajeCalculado = Math.max(0, 1000 - (segundos * 5) - (intentos * 50));
    GameSession.getInstance().setPuntaje(puntajeCalculado);
    GameSession.getInstance().setTiempo(segundos);
    GameSession.getInstance().setIntentos(intentos);
    
    labelFeedback.setStyle("-fx-font-size: 13px; -fx-text-fill: #2e7d32;");
    labelFeedback.setText("¡Todo correcto! Guardando puntaje...");
    try {
        App.setRoot("Logro");
    } catch (IOException e) {
        labelFeedback.setText("Error al navegar: " + e.getMessage());
    }
}
    }

    @FXML
    public void handleGoBack() {
        cronometro.stop();
        try {
            App.setRoot("AprenderLaRegla");
        } catch (IOException e) {
            labelFeedback.setText("Error al navegar: " + e.getMessage());
        }
    }
}