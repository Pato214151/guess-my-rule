module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.example to javafx.fxml, javafx.graphics;
    opens com.example.Controller to javafx.fxml;
    exports com.example;
}