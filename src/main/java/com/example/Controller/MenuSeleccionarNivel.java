package com.example.Controller;

import com.example.App;
import com.example.DAO.DAOPuntaje;
import com.example.Model.GameSession;
import com.example.Model.Puntaje;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class MenuSeleccionarNivel {

    @FXML private Label welcomeLabel;

    @FXML private TableView<Puntaje>            tablaRanking;
    @FXML private TableColumn<Puntaje, String>  colJugador;
    @FXML private TableColumn<Puntaje, Integer> colNivel;
    @FXML private TableColumn<Puntaje, Integer> colPuntaje;
    @FXML private TableColumn<Puntaje, String>  colFecha;
    @FXML private ComboBox<String>              filtroNivel;
    @FXML private Label                         labelEstadoRanking;

    @FXML
    public void initialize() {
        if (welcomeLabel != null) {
            welcomeLabel.setText("Bienvenido, " + GameSession.getInstance().getAlias());
        }

        colJugador.setCellValueFactory(new PropertyValueFactory<>("nombreJugador"));
        colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        colPuntaje.setCellValueFactory(new PropertyValueFactory<>("puntaje"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));

        tablaRanking.setColumnResizePolicy(
            TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        filtroNivel.setItems(FXCollections.observableArrayList(
            "Todos", "Nivel 1", "Nivel 2", "Nivel 3",
            "Nivel 4", "Nivel 5", "Nivel 6"
        ));
        filtroNivel.setValue("Todos");

        cargarRanking(0);
    }

    @FXML
    public void handleActualizarRanking() {
        int nivel = parseFiltro(filtroNivel.getValue());
        cargarRanking(nivel);
    }

    private void cargarRanking(int nivel) {
        DAOPuntaje dao = new DAOPuntaje();
        List<Puntaje> datos = dao.obtenerRanking(nivel);
        ObservableList<Puntaje> items = FXCollections.observableArrayList(datos);
        tablaRanking.setItems(items);

        if (datos.isEmpty()) {
            labelEstadoRanking.setText("No hay puntajes registrados aún.");
        } else {
            labelEstadoRanking.setText("Mostrando " + datos.size() + " resultado(s).");
        }
    }

    private int parseFiltro(String valor) {
        if (valor == null || valor.equals("Todos")) return 0;
        return Integer.parseInt(valor.replace("Nivel ", "").trim());
    }

@FXML
public void handleVolver() {
    try {
        GameSession.getInstance().setJugador(null); // limpia el jugador actual
        App.setRoot("PantallaLogin");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    @FXML
    public void handleNivel(ActionEvent event) {
        try {
            Button btn = (Button) event.getSource();
            String texto = btn.getText().replace("Nivel ", "").trim();
            int nivel = Integer.parseInt(texto);
            // ── resetNivel crea una nueva Regla con el nivel elegido ──
            GameSession.getInstance().resetNivel(nivel);
            App.setRoot("BloqueAprenderRegla");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleComoJugar() {
        Alert modal = new Alert(Alert.AlertType.INFORMATION);
        modal.setTitle("¿Cómo Jugar?");
        modal.setHeaderText("Instrucciones");
        modal.setContentText(
            "OBJETIVO\n" +
            "Descubre la regla matemática oculta que transforma\n" +
            "números de entrada en números de salida.\n\n" +
            "MECÁNICA\n" +
            "1. Ingresa un número y presiona Go!\n" +
            "2. Observa el par Entrada → Salida en la tabla.\n" +
            "3. Cuando creas saber la regla, pulsa ¡Creo que sé la regla!\n\n" +
            "NIVELES\n" +
            "• Nivel 1: Muy Fácil   (ej. x + 3)\n" +
            "• Nivel 2: Fácil       (ej. x × 2)\n" +
            "• Nivel 3: Intermedio  (ej. 2x + 1)\n" +
            "• Nivel 4: Difícil     (ej. x²)\n" +
            "• Nivel 5: Muy Difícil (ej. x² + x)\n" +
            "• Nivel 6: Experto 🔥  (ej. x³ − x)"
        );
        modal.showAndWait();
    }

    @FXML
    public void handleHoverOn(MouseEvent e) {
        if (e.getSource() instanceof Button btn) {
            btn.setStyle(btn.getStyle()
                .replace("-fx-background-color: #64b5f6",
                         "-fx-background-color: #1976d2"));
        } else if (e.getSource() instanceof Label lbl) {
            lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #925a05; " +
                         "-fx-cursor: hand; -fx-underline: true;");
        }
    }

    @FXML
    public void handleHoverOff(MouseEvent e) {
        if (e.getSource() instanceof Button btn) {
            btn.setStyle(btn.getStyle()
                .replace("-fx-background-color: #1976d2",
                         "-fx-background-color: #64b5f6"));
        } else if (e.getSource() instanceof Label lbl) {
            lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #d18108; " +
                         "-fx-cursor: hand; -fx-underline: true;");
        }
    }
}