package com.example.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.App;
import com.example.Model.GameSession;
import com.example.Model.Regla;
import com.example.Model.Regla.ParInOut;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BloqueAprenderRegla {

    @FXML private Label labelTitulo;
    @FXML private TextField inputNumero;
    @FXML private TableView<ParInOut> tablaInOut;
    @FXML private TableColumn<ParInOut, String> colIn;
    @FXML private TableColumn<ParInOut, String> colOut;
    @FXML private Label labelFeedback;

    private Regla regla;
    private final ObservableList<ParInOut> filas = FXCollections.observableArrayList();

    private static final String[] TITULOS = {
        "",
        "Nivel 1 - Find the Rule!",
        "Nivel 2 - Find the Rule!",
        "Nivel 3 - Find the Rule!",
        "Nivel 4 - Find the Rule!",
        "Nivel 5 - Find the Rule!",
        "Nivel 6 - Find the Rule!"
    };

    @FXML
    public void initialize() {
        regla = GameSession.getInstance().getRegla();
        labelTitulo.setText(TITULOS[regla.getNivel()]);

        colIn.setCellValueFactory(new PropertyValueFactory<>("entrada"));
        colOut.setCellValueFactory(new PropertyValueFactory<>("salida"));
        tablaInOut.setItems(filas);
        tablaInOut.setColumnResizePolicy(
            TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        // ── ESTILO TABLA ──
        tablaInOut.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: #c5cae9;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;" +
            "-fx-border-width: 2;"
        );

        tablaInOut.setRowFactory(tv -> new TableRow<ParInOut>() {
            @Override
            protected void updateItem(ParInOut item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setStyle("-fx-background-color: transparent;");
                } else {
                    setStyle(getIndex() % 2 == 0
                        ? "-fx-background-color: #e8eaf6;"
                        : "-fx-background-color: #f0f2ff;"
                    );
                }
            }
        });

        colIn.setCellFactory(col -> new TableCell<ParInOut, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    setText(item);
                    setAlignment(javafx.geometry.Pos.CENTER);
                    setStyle(
                        "-fx-font-size: 16px; -fx-font-weight: bold;" +
                        "-fx-text-fill: #1a237e;" +
                        "-fx-border-color: #c5cae9;" +
                        "-fx-border-width: 0 0 1 0;" +
                        "-fx-background-color: transparent;"
                    );
                }
            }
        });

        colOut.setCellFactory(col -> new TableCell<ParInOut, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    setText(item);
                    setAlignment(javafx.geometry.Pos.CENTER);
                    setStyle(
                        "-fx-font-size: 16px; -fx-font-weight: bold;" +
                        "-fx-text-fill: #3f4e85;" +
                        "-fx-border-color: #c5cae9;" +
                        "-fx-border-width: 0 0 1 0;" +
                        "-fx-background-color: transparent;"
                    );
                }
            }
        });
        // ── FIN ESTILO TABLA ──

        List<ParInOut> guardadas = GameSession.getInstance().getFilasGuardadas();
        if (!guardadas.isEmpty()) {
            filas.addAll(guardadas);
            GameSession.getInstance().setFilasGuardadas(new ArrayList<>());
        }

        inputNumero.setOnKeyPressed(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.ENTER) {
                handleGo();
            }
        });
    }

    @FXML
    public void handleGo() {
        String texto = inputNumero.getText().trim();
        labelFeedback.setText("");

        if (texto.isEmpty()) {
            labelFeedback.setText("Solo se permiten números.");
            return;
        }

        try {
            double entrada = Double.parseDouble(texto);
            filas.add(regla.evaluar(entrada));
            inputNumero.clear();
            inputNumero.requestFocus();
        } catch (NumberFormatException e) {
            labelFeedback.setText("Solo se permiten números.");
        }
    }

    @FXML
    public void handleDeclararRegla() {
        GameSession.getInstance().setFilasGuardadas(new ArrayList<>(filas));
        try {
            App.setRoot("BloqueDeclararRegla");
        } catch (IOException e) {
            labelFeedback.setText("Error al navegar: " + e.getMessage());
        }
    }

    @FXML
    public void handleVolver() {
        try {
            App.setRoot("MenuSeleccionarNivel");
        } catch (IOException e) {
            labelFeedback.setText("Error al navegar: " + e.getMessage());
        }
    }
}
