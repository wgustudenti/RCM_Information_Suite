package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.MasterMethod;
import model.Client;
import model.Department;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class EditClientController extends InformationSuiteController implements Initializable {

    @FXML
    private TextField clientNameTextField;

    @FXML
    private ListView<Department> deptListView;

    @FXML
    private TextField deptNameTextField;

    private static Department selectedDepartment;

    public static Department getSelectedDepartment() {
        return selectedDepartment;
    }

    public static void setSelectedDepartment(Department selectedDepartment) {
        EditClientController.selectedDepartment = selectedDepartment;
    }

    /**
     * Ensures all data is valid and updates the Client.
     * Refreshes data.
     * Changes scene.
     *
     * @param event
     */
    @FXML
    void addButtonClick(ActionEvent event) throws SQLException {
        if(checkIfFieldIsNotEmpty(deptNameTextField.getText(), "Department Name")){
            if(!deptListView.getItems().contains(deptNameTextField.getText())){
                int clientPrimaryID = AdminInformationSearchController.getSelectedClient().getPrimaryKey();
                Department department = new Department(deptNameTextField.getText(), clientPrimaryID);
                department.insert();
                MasterMethod.initializeAllStaticData();
                deptListView.setItems(Department.getObservableListOfAllForClient(clientPrimaryID));
            }
        }
    }

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/ViewClient.fxml");
    }

    /**
     * Navigates the user to a scene which is capable of editing a department.
     * @param event
     * @throws IOException
     */
    @FXML
    void editDepartmentButtonClick(ActionEvent event) throws IOException {
        if(!deptListView.getSelectionModel().isEmpty()) {
            selectedDepartment = deptListView.getSelectionModel().getSelectedItem();
            changeScene(event, "/view/EditDepartment.fxml");
        }
        else{
            alertUser("No Department Selected.");
        }
    }

    /**
     * Checks that data is valid and removes a department from the list of departments which are supposed to be
     * associated with the new client.
     *
     * @param event
     */
    @FXML
    void removeButtonClick(ActionEvent event) throws SQLException {
        if(!deptListView.getSelectionModel().isEmpty()){
            if(confirmDelete()){
                int clientPrimaryID = AdminInformationSearchController.getSelectedClient().getPrimaryKey();
                selectedDepartment = deptListView.getSelectionModel().getSelectedItem();
                selectedDepartment.delete();
                MasterMethod.initializeAllStaticData();
                deptListView.setItems(Department.getObservableListOfAllForClient(clientPrimaryID));
            }
        }
        else{
            alertUser("No Department Selected.");
        }
    }

    /**
     * Ensures all data is valid and updates the Client.
     * Refreshes data.
     * Changes scene.
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void submitButtonClick(ActionEvent event) throws SQLException, IOException {
        if(checkIfFieldIsNotEmpty(clientNameTextField.getText(), "Client")){
            if(checkIfHundredOrLessChar(clientNameTextField.getText(), "Client")){
                Client selectedClient = AdminInformationSearchController.getSelectedClient();
                Client client = new Client(clientNameTextField.getText(), selectedClient.getDepartmentNotes(),
                        selectedClient.getSpecialtyNotes(), selectedClient.getPrimaryKey());
                client.update();
                MasterMethod.initializeAllStaticData();
                changeScene(event, "/view/ViewClient.fxml");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientNameTextField.setText(AdminInformationSearchController.getSelectedClient().getName());
        deptListView.setItems(Department.getObservableListOfAllForClient(AdminInformationSearchController.getSelectedClient().getPrimaryKey()));
    }

}
