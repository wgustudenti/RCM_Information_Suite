package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.MasterMethod;
import model.Client;
import model.PayerSuggestion;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class SuggestPayerController extends InformationSuiteController implements Initializable {

    @FXML
    private ComboBox<Client> clientCombo;

    @FXML
    private TextField documentNameTextField;

    @FXML
    private TextField enteredNameTextField;

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/InformationSearch.fxml");
    }

    /**
     * This method is called when the "Submit" button is clicked. Data will be validated to ensure it conforms to all rules.
     *
     * A new PayerSuggestion will be created in the database and all static variables will refresh (by grabbing them
     * from the database again).
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void submitButtonClick(ActionEvent event) throws SQLException, IOException {
        if (!clientCombo.getSelectionModel().isEmpty()) {
            if (checkIfFieldIsNotEmpty(documentNameTextField.getText(), "Name On Document")) {
                if (checkIfFieldIsNotEmpty(enteredNameTextField.getText(), "Name To Enter")) {
                    if (checkIfFiftyOrLessChar(documentNameTextField.getText(), "Name On Document")) {
                        if (checkIfFiftyOrLessChar(enteredNameTextField.getText(), "Name To Enter")) {
                            PayerSuggestion payerSuggestion = new PayerSuggestion(documentNameTextField.getText(), enteredNameTextField.getText(),
                                    clientCombo.getSelectionModel().getSelectedItem().getPrimaryKey());
                            payerSuggestion.insert();
                            MasterMethod.initializeAllStaticData();
                            changeScene(event, "/view/InformationSearch.fxml");
                        }
                    }
                }
            }
        }
        else{
            alertUser("No Client Selected");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Client> listOfClients = Client.getObservableListOfAll();
        clientCombo.setItems(listOfClients);
        clientCombo.getSelectionModel().select(InformationSearchController.getSelectedClient());
    }
}
