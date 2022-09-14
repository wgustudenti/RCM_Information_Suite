package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.MasterMethod;
import model.Client;
import model.Department;
import model.Provider;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class EditProviderController extends InformationSuiteController implements Initializable {

    @FXML
    private ComboBox<Client> clientCombo;

    @FXML
    private ComboBox<Department> departmentCombo;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField npiTextField;

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AdminInformationSearch.fxml");
    }

    /**
     * This method is called whenever a selection is made from the clientCombo box.  It populates the departmentCombo box
     * with the related departments.
     * @param event
     */
    @FXML
    void clientComboSelect(ActionEvent event) {
        Client selectedClient = clientCombo.getSelectionModel().getSelectedItem();
        departmentCombo.setItems(Department.getObservableListOfAllForClient(selectedClient.getPrimaryKey()));
    }

    /**
     * Verifies that the combo boxes have a selection and that the fields contain information which meets system requirements,
     * then updates the provider with the provided attributes.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void submitButtonClick(ActionEvent event) throws SQLException, IOException {
        if(checkIfComboIsNotEmpty(departmentCombo, "Department")) {
            if (checkIfFieldIsNotEmpty(firstNameTextField.getText(), "First Name")) {
                if (checkIfFieldIsNotEmpty(lastNameTextField.getText(), "Last Name")) {
                    if (checkIfFieldIsNotEmpty(npiTextField.getText(), "NPI")) {
                        if (checkIfFiftyOrLessChar(firstNameTextField.getText(), "First Name")) {
                            if (checkIfFiftyOrLessChar(lastNameTextField.getText(), "Last Name")) {
                                if(checkIfFifteenOrLessChar(npiTextField.getText(), "NPI")) {
                                    Provider provider = new Provider(firstNameTextField.getText(), lastNameTextField.getText(),
                                            npiTextField.getText(), departmentCombo.getSelectionModel().getSelectedItem().getPrimaryKey(),
                                            AdminInformationSearchController.getSelectedProvider().getPrimaryKey());
                                    provider.update();
                                    MasterMethod.initializeAllStaticData();
                                    changeScene(event, "/view/AdminInformationSearch.fxml");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Provider selectedProvider = AdminInformationSearchController.getSelectedProvider();
        clientCombo.setItems(Client.getObservableListOfAll());
        clientCombo.getSelectionModel().select(Client.getMapOfAll().get(selectedProvider.getClientID()));
        if(!clientCombo.getSelectionModel().isEmpty()){
            departmentCombo.setItems(Department.getObservableListOfAllForClient(
                    clientCombo.getSelectionModel().getSelectedItem().getPrimaryKey()));
            departmentCombo.getSelectionModel().select(Department.getMapOfAll().get(selectedProvider.getDepartmentID()));
        }
        firstNameTextField.setText(selectedProvider.getFirstName());
        lastNameTextField.setText(selectedProvider.getLastName());
        npiTextField.setText(selectedProvider.getNpi());
    }
}
