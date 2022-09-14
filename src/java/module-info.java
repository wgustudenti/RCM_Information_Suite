module is.rcminformationsuite {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens main to javafx.fxml;
    exports main;
    exports controller;
    opens controller to javafx.fxml;

    opens model to javafx.base;
}