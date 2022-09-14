package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}