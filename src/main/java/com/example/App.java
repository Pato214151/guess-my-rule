package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(getClass().getResourceAsStream("/com/example/fonts/SIXTY.TTF"), 14);
        Font.loadFont(getClass().getResourceAsStream("/com/example/fonts/Roboto-Regular.ttf"), 14);


    scene = new Scene(loadFXML("PantallaDeCarga"), 640, 480);
    stage.setScene(scene);
    stage.show();
}

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        var resource = App.class.getResource("/com/example/View/" + fxml + ".fxml");

        if (resource == null) {
            throw new IOException("FXML no encontrado: /com/example/View/" + fxml + ".fxml");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        return fxmlLoader.load();
    }

    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
}