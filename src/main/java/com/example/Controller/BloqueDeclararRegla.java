package com.example.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class BloqueDeclararRegla {

    @FXML private Label labelTitulo;
    @FXML private Label labelTiempo;
    @FXML private Label labelIntentos;
    @FXML private Label labelFeedback;
    @FXML private TableView<FilaTest> tablaTest;
    @FXML private TableColumn<FilaTest, String> colIn;
    @FXML private TableColumn<FilaTest, String> colOut;

    private Regla regla;
    private GameSession session;
    private ObservableList<FilaTest> filas;
    private Timeline cronometro;
    private boolean yaVerificado = false;

    private static final String[] TITULOS = {
        "", "Nivel 1", "Nivel 2", "Nivel 3",
        "Nivel 4", "Nivel 5", "Nivel 6"
    };

    @FXML
    public void initialize() {
        session = GameSession.getInstance();
        regla = session.getRegla();
        labelTitulo.setText(TITULOS[regla.getNivel()] + " - Test Your Rule");

        // ── ESTILO TABLA ──
        tablaTest.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: #c5cae9;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;" +
            "-fx-border-width: 2;"
        );

        tablaTest.setRowFactory(tv -> new TableRow<FilaTest>() {
            @Override
            protected void updateItem(FilaTest item, boolean empty) {
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
        // ── FIN ESTILO TABLA ──

        // Columna IN — solo lectura
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

        // Columna OUT — TextField siempre visible, un solo clic
        colOut.setCellValueFactory(c -> c.getValue().respuestaProperty());
        colOut.setCellFactory(column -> new TableCell<FilaTest, String>() {
            private final TextField textField = new TextField();

            {
                textField.setStyle(
                    "-fx-font-size: 15px; -fx-font-weight: bold;" +
                    "-fx-alignment: center; -fx-background-color: transparent;" +
                    "-fx-border-color: #9fa8da; -fx-border-radius: 6; -fx-border-width: 1;"
                );
                textField.setAlignment(Pos.CENTER);

                // Un solo clic enfoca el campo directamente
                textField.setOnMouseClicked(event -> {
                    textField.requestFocus();
                    textField.selectAll();
                    event.consume();
                });

                // Guarda al perder foco — sin necesidad de Enter
                textField.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
                    if (!isFocused && getTableRow() != null && getTableRow().getItem() != null) {
                        getTableRow().getItem().setRespuesta(textField.getText());
                        yaVerificado = false;
                        tablaTest.refresh();
                    }
                });

                // También guarda al presionar Enter
                textField.setOnAction(e -> {
                    if (getTableRow() != null && getTableRow().getItem() != null) {
                        getTableRow().getItem().setRespuesta(textField.getText());
                        yaVerificado = false;
                        tablaTest.refresh();
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                FilaTest fila = getTableRow().getItem();

                if (!textField.isFocused()) {
                    textField.setText(item == null ? "" : item);
                }

                if (yaVerificado && item != null && !item.isEmpty()) {
                    try {
                        double resp = Double.parseDouble(item.trim());
                        if (Math.abs(resp - fila.getSalidaEsperada()) < 0.01) {
                            textField.setStyle(
                                "-fx-font-size: 15px; -fx-font-weight: bold;" +
                                "-fx-alignment: center; -fx-background-color: #a5d6a7;" +
                                "-fx-border-color: #81c784; -fx-border-radius: 6; -fx-border-width: 1;"
                            );
                        } else {
                            textField.setStyle(
                                "-fx-font-size: 15px; -fx-font-weight: bold;" +
                                "-fx-alignment: center; -fx-background-color: #ef9a9a;" +
                                "-fx-border-color: #e57373; -fx-border-radius: 6; -fx-border-width: 1;"
                            );
                        }
                    } catch (NumberFormatException e) {
                        textField.setStyle(
                            "-fx-font-size: 15px; -fx-font-weight: bold;" +
                            "-fx-alignment: center; -fx-background-color: #ef9a9a;" +
                            "-fx-border-color: #e57373; -fx-border-radius: 6; -fx-border-width: 1;"
                        );
                    }
                } else {
                    textField.setStyle(
                        "-fx-font-size: 15px; -fx-font-weight: bold;" +
                        "-fx-alignment: center; -fx-background-color: transparent;" +
                        "-fx-border-color: #9fa8da; -fx-border-radius: 6; -fx-border-width: 1;"
                    );
                }

                setGraphic(textField);
                setText(null);
                setAlignment(Pos.CENTER);
            }
        });

        tablaTest.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

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

        labelIntentos.setText(String.valueOf(session.getIntentos()));
        labelTiempo.setText(session.getTiempo() + " s");

        iniciarCronometro();
    }

    private void iniciarCronometro() {
        cronometro = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            session.setTiempo(session.getTiempo() + 1);
            labelTiempo.setText(session.getTiempo() + " s");
        }));
        cronometro.setCycleCount(Timeline.INDEFINITE);
        cronometro.play();
    }

    @FXML
    public void handleCheck() {
        session.setIntentos(session.getIntentos() + 1);
        labelIntentos.setText(String.valueOf(session.getIntentos()));
        labelFeedback.setText("");

        boolean todosCorrecto = true;
        int incorrectas = 0;
        int vacias = 0;

        for (FilaTest fila : filas) {
            String respuestaTexto = fila.getRespuesta().trim();
            if (respuestaTexto.isEmpty()) {
                vacias++;
                todosCorrecto = false;
            } else {
                try {
                    double resp = Double.parseDouble(respuestaTexto);
                    if (Math.abs(resp - fila.getSalidaEsperada()) >= 0.001) {
                        incorrectas++;
                        todosCorrecto = false;
                    }
                } catch (NumberFormatException e) {
                    incorrectas++;
                    todosCorrecto = false;
                }
            }
        }

        yaVerificado = true;
        tablaTest.refresh();

        if (todosCorrecto) {
            cronometro.stop();
            session.setFilasTest(null);
            labelFeedback.setStyle(
                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");
            labelFeedback.setText("¡Todo correcto! ¡Eres un crack! 🎉");
            try {
                App.setRoot("ResumenPuntaje");
            } catch (Exception e) {
                labelFeedback.setText("Error al navegar: " + e.getMessage());
            }
        } else {
            String mensaje;
            if (vacias > 0 && incorrectas > 0) {
                mensaje = "¡Ups! Te faltan " + vacias + " casilla(s) por rellenar "
                        + "y " + incorrectas + " respuesta(s) están incorrectas. "
                        + "¡Mira las celdas rojas y las que están vacías!";
            } else if (vacias > 0) {
                mensaje = "¡Aún te faltan " + vacias + " casilla(s) por rellenar! "
                        + "Completa todas las filas antes de verificar. ¡Tú puedes!";
            } else {
                mensaje = "¡Casi! " + incorrectas + " respuesta(s) están incorrectas. "
                        + "Las ves marcadas en rojo — ¡corrígelas e inténtalo de nuevo!";
            }
            labelFeedback.setStyle(
                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #e53935;");
            labelFeedback.setText(mensaje);
        }
    }

    @FXML
    public void handleGoBack() {
        cronometro.stop();
        session.setFilasTest(new ArrayList<>(filas));
        try {
            App.setRoot("BloqueAprenderRegla");
        } catch (IOException e) {
            labelFeedback.setText("Error al navegar: " + e.getMessage());
        }
    }
}
