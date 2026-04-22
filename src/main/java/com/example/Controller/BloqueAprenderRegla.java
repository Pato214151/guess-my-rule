package com.example.Controller;

import com.example.App;
import com.example.Model.GameSession;
import com.example.Model.AprenderRegla;
import com.example.Model.AprenderRegla.ParInOut;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BloqueAprenderRegla {

    @FXML private Label labelTitulo;
    @FXML private TextField inputNumero;
    @FXML private TableView<ParInOut> tablaInOut;
    @FXML private TableColumn<ParInOut, String> colIn;
    @FXML private TableColumn<ParInOut, String> colOut;
    @FXML private Label labelFeedback;

    private AprenderRegla regla;
    private final ObservableList<ParInOut> filas = FXCollections.observableArrayList();

    private static final String[] TITULOS = {
        "",
        "Nivel 1 - Find the Rule!",
        "Nivel 2 - Find the Rule!",
        "Nivel 3 - Find the Rule!",
        "Nivel 4 - Find the Russssle!",
        "Nivel 5 - Find the Rule!",
        "Nivel 6 - Find the Rule!"
    };

@FXML
public void initialize() {
    int nivel = GameSession.getInstance().getNivel();
    regla = new AprenderRegla(nivel);

    labelTitulo.setText(TITULOS[nivel]);

    colIn.setCellValueFactory(new PropertyValueFactory<>("entrada"));
    colOut.setCellValueFactory(new PropertyValueFactory<>("salida"));
    tablaInOut.setItems(filas);
    tablaInOut.setColumnResizePolicy(
        TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

    // ✅ Cargar datos guardados correctamente
List<ParInOut> guardadas = GameSession.getInstance().getFilasGuardadas();
if (!guardadas.isEmpty()) {
    filas.addAll(guardadas); // filas es ObservableList, addAll acepta List normal
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
            filas.add(regla.evaluar(entrada));  // el modelo hace todo
            inputNumero.clear();
            inputNumero.requestFocus();

        } catch (NumberFormatException e) {
            labelFeedback.setText("Solo se permiten números.");
        }
    }

@FXML
public void handleDeclararRegla() {
    // Conviertes ObservableList → List antes de guardar
    GameSession.getInstance().setFilasGuardadas(new ArrayList<>(filas));
    try {
        App.setRoot("BloqueDeclararRegla");
    } catch (IOException e) {
        labelFeedback.setText("Error al navegar: " + e.getMessage());
    }
}

    @FXML
    public void handleVolver() throws IOException {
        App.setRoot("MenuSeleccionarNivel");
    }
}