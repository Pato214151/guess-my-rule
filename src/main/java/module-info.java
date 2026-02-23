module com.guessrule {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens com.guessrule to javafx.fxml;
    opens com.guessrule.controller to javafx.fxml;
    opens com.guessrule.model to javafx.fxml;

    exports com.guessrule;
    exports com.guessrule.controller;
    exports com.guessrule.model;
}
