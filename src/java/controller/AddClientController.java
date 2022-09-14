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
public class AddClientController extends InformationSuiteController implements Initializable {

    @FXML
    private TextField clientNameTextField;

    @FXML
    private ListView<String> deptListView;

    @FXML
    private TextField deptNameTextField;

    /**
     * Ensures all data is valid and inserts the Client and associated departments.
     * Refreshes data.
     * Changes scene.
     *
     * @param event
     */
    @FXML
    void addButtonClick(ActionEvent event) {
        if(checkIfFieldIsNotEmpty(deptNameTextField.getText(), "Department Name")){
            if(checkIfHundredOrLessChar(deptNameTextField.getText(), "Department Name")) {
                if (!deptListView.getItems().contains(deptNameTextField.getText())) {
                    deptListView.getItems().add(deptNameTextField.getText());
                }
            }
        }
    }

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/ViewClient.fxml");
    }

    /**
     * Checks that data is valid and removes a department from the list of departments which are supposed to be
     * associated with the new client.
     *
     * @param event
     */
    @FXML
    void removeButtonClick(ActionEvent event) {
        if(!deptListView.getSelectionModel().isEmpty()){
            deptListView.getItems().remove(deptListView.getSelectionModel().getSelectedItem());
        }
        else{
            alertUser("No Department Selected.");
        }
    }

    /**
     * Ensures all data is valid and inserts the Client.
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
                Boolean departmentNamesLessThanHundred = true;
                for(String departmentName : deptListView.getItems()){
                    if(!checkIfHundredOrLessChar(departmentName, "Department")){
                        departmentNamesLessThanHundred = false;
                    }
                }
                if(departmentNamesLessThanHundred){
                    Client client = new Client(clientNameTextField.getText());
                    client.insert();
                    Client.initilizeStaticVariables();
                    int realClientPrimaryKey = 0;
                    for(Client updatedClient : Client.getObservableListOfAll()){
                        if(updatedClient.getName().equals(client.getName())){
                            realClientPrimaryKey = updatedClient.getPrimaryKey();
                        }
                    }
                    for(String departmentName : deptListView.getItems()) {
                        Department department = new Department(departmentName, realClientPrimaryKey);
                        department.insert();
                    }
                    MasterMethod.initializeAllStaticData();
                    changeScene(event, "/view/ViewClient.fxml");
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
