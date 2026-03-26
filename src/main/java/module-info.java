module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens com.example             to javafx.fxml, javafx.graphics;
    opens com.example.Controller  to javafx.fxml, javafx.base;
    opens com.example.Model       to javafx.fxml;
    opens com.example.util        to javafx.fxml;

    exports com.example;
    exports com.example.Controller;
    exports com.example.Model;
}
