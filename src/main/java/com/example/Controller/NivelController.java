package com.example.Controller;

import com.example.App;
import com.example.Model.GameSession;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
public class NivelController {

    @FXML private Label     labelTitulo;
    @FXML private TextField inputNumero;
    @FXML private TableView<ParInOut> tablaInOut;
    @FXML private TableColumn<ParInOut, String> colIn;
    @FXML private TableColumn<ParInOut, String> colOut;
    @FXML private Label     labelFeedback;

    private int nivel;
    private final ObservableList<ParInOut> filas = FXCollections.observableArrayList();

    private static final String[] TITULOS = {
        "",                               // índice 0 (no se usa)
        "Nivel 1 - Find the Rule!",
        "Nivel 2 - Find the Rule!",
        "Nivel 3 - Find the Rule!",
        "Nivel 4 - Find the Rule!",
        "Nivel 5 - Find the Rule!",
        "Nivel 6 - Find the Rule!"
    };

    @FXML
    public void initialize() {
        nivel = GameSession.getInstance().getNivel();

        labelTitulo.setText(TITULOS[nivel]);

        colIn.setCellValueFactory(new PropertyValueFactory<>("entrada"));
        colOut.setCellValueFactory(new PropertyValueFactory<>("salida"));
        tablaInOut.setItems(filas);
        tablaInOut.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        inputNumero.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) handleGo();
        });
    }

    private double aplicarRegla(double x) {
        return switch (nivel) {
            case 1 -> x + 3;
            case 2 -> x * 2;
            case 3 -> x * 2 + 1;
            case 4 -> x * x;
            case 5 -> x * x + x;
            case 6 -> x * x * x - x;
            default -> x;
        };
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
            double salida  = aplicarRegla(entrada);

            // Formatear: mostrar entero si es exacto
            String entradaStr = formatear(entrada);
            String salidaStr  = formatear(salida);

            filas.add(new ParInOut(entradaStr, salidaStr));
            inputNumero.clear();
            inputNumero.requestFocus();

        } catch (NumberFormatException e) {
            labelFeedback.setText("Solo se permiten números.");
        }
    }

    @FXML
    public void handleDeclararRegla() throws IOException {
        GameSession.getInstance().setNivel(nivel);
        App.setRoot("DeclararRegla");   // próxima pantalla a construir
    }

    @FXML
    public void handleVolver() throws IOException {
        App.setRoot("MenuSeleccionarNivel");
    }

    private String formatear(double v) {
        return (v == Math.floor(v) && !Double.isInfinite(v))
               ? String.valueOf((long) v)
               : String.valueOf(v);
    }

    public static class ParInOut {
        private final String entrada;
        private final String salida;

        public ParInOut(String entrada, String salida) {
            this.entrada = entrada;
            this.salida  = salida;
        }

        public String getEntrada() { return entrada; }
        public String getSalida()  { return salida;  }
    }
}