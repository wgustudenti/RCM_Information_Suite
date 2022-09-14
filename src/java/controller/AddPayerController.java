package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.MasterMethod;
import model.Client;
import model.Payer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class AddPayerController extends InformationSuiteController implements Initializable{

    @FXML
    private ComboBox<Client> clientCombo;

    @FXML
    private TextField documentNameTextField;

    @FXML
    private TextField enteredNameTextField;

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AdminInformationSearch.fxml");
    }

    /**
     * Ensures all data is valid and inserts the Payer.
     * Refreshes data.
     * Changes scene.
     *
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

    /**
     * Ensures all data is valid and updates the Payer for all clients.
     * Refreshes data.
     * Changes scene.
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void submitForAllClientsButtonClick(ActionEvent event) throws SQLException, IOException {
        if (checkIfFieldIsNotEmpty(documentNameTextField.getText(), "Name On Document")) {
            if (checkIfFieldIsNotEmpty(enteredNameTextField.getText(), "Name To Enter")) {
                if (checkIfFiftyOrLessChar(documentNameTextField.getText(), "Name On Document")) {
                    if (checkIfFiftyOrLessChar(enteredNameTextField.getText(), "Name To Enter")) {
                        String nameOnDocument = documentNameTextField.getText();
                        String nameToEnter = enteredNameTextField.getText();
                        Payer payer = new Payer(nameOnDocument, nameToEnter, 0);
                        payer.insertForAllClients();
                        MasterMethod.initializeAllStaticData();
                        changeScene(event, "/view/AdminInformationSearch.fxml");
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientCombo.setItems(Client.getObservableListOfAll());
        clientCombo.getSelectionModel().select(InformationSearchController.getSelectedClient());
    }
}
