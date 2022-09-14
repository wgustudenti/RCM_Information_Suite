package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.MasterMethod;
import model.Client;
import model.Payer;
import model.PayerSuggestion;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class EditAndAddPayerController extends InformationSuiteController implements Initializable {

    @FXML
    private ComboBox<Client> clientCombo;

    @FXML
    private TextField documentNameTextField;

    @FXML
    private TextField enteredNameTextField;

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/ViewPayerSuggestion.fxml");
    }

    /**
     * Runs validation checks on the provided data and then creates a payer for the chosen client.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void submitButtonClick(ActionEvent event) throws SQLException, IOException {
        if(!clientCombo.getSelectionModel().isEmpty()) {
            if (checkIfFieldIsNotEmpty(documentNameTextField.getText(), "Name On Document")) {
                if (checkIfFieldIsNotEmpty(enteredNameTextField.getText(), "Name To Enter")) {
                    if (checkIfFiftyOrLessChar(documentNameTextField.getText(), "Name On Document")) {
                        if (checkIfFiftyOrLessChar(enteredNameTextField.getText(), "Name To Enter")) {
                            int clientID = clientCombo.getSelectionModel().getSelectedItem().getPrimaryKey();
                            String nameOnDocument = documentNameTextField.getText();
                            String nameToEnter = enteredNameTextField.getText();
                            Payer payer = new Payer(nameOnDocument, nameToEnter, clientID);
                            payer.insert();
                            ViewPayerSuggestionController.getSelectedSuggestion().delete();
                            MasterMethod.initializeAllStaticData();
                            changeScene(event, "/view/ViewPayerSuggestion.fxml");
                        }
                    }
                }
            }
        }
        else {
            alertUser("No Client Selected");
        }
    }

    /**
     * Runs validation checks on the provided data and then creates a payer for all clients.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void submitForAllClientsButtonClick(ActionEvent event) throws SQLException, IOException {
        if(!clientCombo.getSelectionModel().isEmpty()) {
            if (checkIfFieldIsNotEmpty(documentNameTextField.getText(), "Name On Document")) {
                if (checkIfFieldIsNotEmpty(enteredNameTextField.getText(), "Name To Enter")) {
                    if (checkIfFiftyOrLessChar(documentNameTextField.getText(), "Name On Document")) {
                        if (checkIfFiftyOrLessChar(enteredNameTextField.getText(), "Name To Enter")) {
                            String nameOnDocument = documentNameTextField.getText();
                            String nameToEnter = enteredNameTextField.getText();
                            Payer payer = new Payer(nameOnDocument, nameToEnter, 0);
                            payer.insertForAllClients();
                            ViewPayerSuggestionController.getSelectedSuggestion().delete();
                            MasterMethod.initializeAllStaticData();
                            changeScene(event, "/view/AdminInformationSearch.fxml");
                        }
                    }
                }
            }
        }
        else {
            alertUser("No Client Selected");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientCombo.setItems(Client.getObservableListOfAll());
        clientCombo.getSelectionModel().select(Client.getMapOfAll().get(ViewPayerSuggestionController.getSelectedSuggestion().getClientID()));
        documentNameTextField.setText(ViewPayerSuggestionController.getSelectedSuggestion().getNameOnDocument());
        enteredNameTextField.setText(ViewPayerSuggestionController.getSelectedSuggestion().getNameToEnter());
    }
}
