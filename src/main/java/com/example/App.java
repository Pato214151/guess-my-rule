package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Punto de entrada principal de la aplicación JavaFX.
 * <p>
 * Extiende {@link javafx.application.Application} y gestiona la {@link Scene} global,
 * la carga de tipografías personalizadas y la navegación entre pantallas mediante
 * el método estático {@link #setRoot(String)}.
 * </p>
 */
public class App extends Application {

    private static Scene scene;


    /**
     * Inicializa la ventana principal de la aplicación.
     * <p>
     * Carga las tipografías SIXTY y Roboto, establece el icono de la ventana
     * y muestra la pantalla inicial {@code PantallaLogin}.
     * </p>
     *
     * @param stage el {@link Stage} principal proporcionado por el framework JavaFX
     * @throws IOException si el archivo FXML de la pantalla inicial no se encuentra
     */
    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/LogoFinal.png")));
        Font.loadFont(getClass().getResourceAsStream("/com/example/fonts/SIXTY.TTF"), 14);
        Font.loadFont(getClass().getResourceAsStream("/com/example/fonts/Roboto-Regular.ttf"), 14);


    scene = new Scene(loadFXML("PantallaLogin"), 640, 480);
    stage.setScene(scene);
    stage.show();
}

    /**
     * Cambia la pantalla activa reemplazando el nodo raíz de la {@link Scene} global.
     *
     * @param fxml nombre del archivo FXML (sin extensión ni ruta) ubicado en
     *             {@code /com/example/View/}
     * @throws IOException si el archivo FXML no existe en el classpath
     */
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

    /**
     * Retorna la {@link Scene} global de la aplicación.
     *
     * @return la escena principal activa
     */
    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
}