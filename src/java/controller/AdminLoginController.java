package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.AdminUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class AdminLoginController extends InformationSuiteController implements Initializable {

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/InformationSearch.fxml");
    }

    /**
     * This method is called whenever the login button is clicked.  It checks that the correct fields are filled
     * and then checks that the username/password combo exists; if so, the user is navigated to the AdminInformationSearch
     * and if not, the user is given an error message.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void loginButtonClick(ActionEvent event) throws IOException {
        if(checkIfFieldIsNotEmpty(usernameTextField.getText(), "Username")) {
            if(checkIfFieldIsNotEmpty(passwordTextField.getText(), "Password")) {
                Boolean userFound = false;
                for (AdminUser adminUser : AdminUser.getMapOfAll().values()) {
                    String enteredUsername = usernameTextField.getText();
                    String enteredPassword = passwordTextField.getText();
                    if (adminUser.getUsername().equals(enteredUsername) && adminUser.getPassword().equals(enteredPassword)) {
                        changeScene(event, "/view/AdminInformationSearch.fxml");
                        userFound = true;
                        AdminUser.setCurrentUser(adminUser);
                        break;
                    }
                }
                if(!userFound) {
                    alertUser("Incorrect Username Or Password.");
                }
            }
        }
    }

    /**
     * If the user hits enter when the passwordField is selected, the application will attempt to log them in with the
     * credentials provided in the GUI.
     * @param event
     * @throws IOException
     */
    @FXML
    void passwordTextFieldAction(ActionEvent event) throws IOException {
        loginButton.fire();
    }

    /**
     * If the user hits enter when the usernameTextField is selected, the application will attempt to log them in with the
     * credentials provided in the GUI.
     * @param event
     * @throws IOException
     */
    @FXML
    void usernameTextFieldAction(ActionEvent event) throws IOException {
        loginButton.fire();
    }

        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameTextField.setText("Stephen");
        passwordTextField.setText("password");
    }
}
