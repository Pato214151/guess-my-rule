package com.example.Controller;

import java.io.IOException;
import java.util.List;

import com.example.App;
import com.example.DAO.DAOPuntaje;
import com.example.Model.GameSession;
import com.example.Model.Puntaje;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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

        tablaRanking.setStyle(
            "-fx-background-color: #1a237e;" +
            "-fx-border-color: #5c6bc0;" +
            "-fx-border-radius: 12;" +
            "-fx-background-radius: 12;" +
            "-fx-border-width: 2;"
        );

        tablaRanking.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Puntaje item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setStyle("-fx-background-color: transparent;");
                } else {
                    String base = getIndex() % 2 == 0
                        ? "-fx-background-color: #1a237e;"
                        : "-fx-background-color: #283593;";
                    setStyle(isSelected() ? "-fx-background-color: #5c6bc0;" : base);
                }
            }

            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                if (getItem() != null) {
                    String base = getIndex() % 2 == 0
                        ? "-fx-background-color: #1a237e;"
                        : "-fx-background-color: #283593;";
                    setStyle(selected ? "-fx-background-color: #5c6bc0;" : base);
                }
            }
        });

        estilizarColumna(colJugador);
        estilizarColumna(colNivel);
        estilizarColumna(colPuntaje);
        estilizarColumna(colFecha);

        filtroNivel.setItems(FXCollections.observableArrayList(
            "Todos", "Nivel 1", "Nivel 2", "Nivel 3",
            "Nivel 4", "Nivel 5", "Nivel 6"
        ));
        filtroNivel.setValue("Todos");

        cargarRanking(0);
    }

    private <T> void estilizarColumna(TableColumn<Puntaje, T> columna) {
        columna.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    setText(item.toString());
                    setStyle(
                        "-fx-text-fill: #e8eaf6;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-color: #3949ab;" +
                        "-fx-border-width: 0 0 1 0;" +
                        "-fx-background-color: transparent;" +
                        "-fx-alignment: CENTER;" +
                        "-fx-padding: 8 4 8 4;"
                    );
                }
            }
        });
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
            GameSession.getInstance().resetNivel(nivel);
            App.setRoot("BloqueAprenderRegla");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

@FXML
public void handleHoverOn(MouseEvent e) {
    if (e.getSource() instanceof Button btn) {
        btn.setStyle(btn.getStyle()
            .replace("-fx-background-color: #3f4e85",
                     "-fx-background-color: #1a237e"));
    }
}

@FXML
public void handleHoverOff(MouseEvent e) {
    if (e.getSource() instanceof Button btn) {
        btn.setStyle(btn.getStyle()
            .replace("-fx-background-color: #1a237e",
                     "-fx-background-color: #3f4e85"));
    }
}
}