package com.example.Controller;

import com.example.App;
import com.example.Model.PuntajeModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class RankingController {

    @FXML private TableView<PuntajeModel>            tablaRanking;
    @FXML private TableColumn<PuntajeModel, String>  colJugador;
    @FXML private TableColumn<PuntajeModel, Integer> colNivel;
    @FXML private TableColumn<PuntajeModel, Integer> colPuntaje;
    @FXML private TableColumn<PuntajeModel, String>  colFecha;
    @FXML private ComboBox<String>                   filtroNivel;
    @FXML private Label                              labelEstado;

    @FXML
    public void initialize() {
        colJugador.setCellValueFactory(new PropertyValueFactory<>("nombreJugador"));
        colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        colPuntaje.setCellValueFactory(new PropertyValueFactory<>("puntaje"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));

        tablaRanking.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        filtroNivel.setItems(FXCollections.observableArrayList(
            "Todos", "Nivel 1", "Nivel 2", "Nivel 3", "Nivel 4", "Nivel 5", "Nivel 6"
        ));
        filtroNivel.setValue("Todos");

        cargarDatos(0);
    }

    @FXML
    public void handleActualizar() {
        int nivel = parseFiltro(filtroNivel.getValue());
        cargarDatos(nivel);
    }

    @FXML
    public void handleVolver() throws IOException {
        App.setRoot("MenuSeleccionarNivel");
    }

    private void cargarDatos(int nivel) {
        List<PuntajeModel> datos = PuntajeModel.obtenerRanking(nivel);
        ObservableList<PuntajeModel> items = FXCollections.observableArrayList(datos);
        tablaRanking.setItems(items);

        if (datos.isEmpty()) {
            labelEstado.setText("No hay puntajes registrados aún.");
        } else {
            labelEstado.setText("Mostrando " + datos.size() + " resultado(s).");
        }
    }

    private int parseFiltro(String valor) {
        if (valor == null || valor.equals("Todos")) return 0;
        return Integer.parseInt(valor.replace("Nivel ", "").trim());
    }
}
