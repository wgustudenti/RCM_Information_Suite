package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import main.MasterMethod;
import model.Client;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class EditDepartmentNotesController extends InformationSuiteController implements Initializable {

    @FXML
    private ComboBox<Client> clientCombo;

    @FXML
    private TextArea departmentNotesTextArea;

    private static String previousScene;

    public static String getPreviousScene() {
        return previousScene;
    }

    public static void setPreviousScene(String previousScene) {
        EditDepartmentNotesController.previousScene = previousScene;
    }

    @FXML
    void backButtonClick(ActionEvent event) throws IOException {
        changeScene(event, previousScene);
    }

    @FXML
    void clientComboSelection(ActionEvent event) {
        Client updatedSelectedClient = clientCombo.getSelectionModel().getSelectedItem();
        AdminInformationSearchController.setSelectedClient(updatedSelectedClient);
        departmentNotesTextArea.setText(updatedSelectedClient.getDepartmentNotes());
    }

    @FXML
    void submitButtonClick(ActionEvent event) throws SQLException, IOException {
        if(checkIfComboIsNotEmpty(clientCombo, "Client")){
            if(checkIfTenThousandOrLessChar(departmentNotesTextArea.getText(), "Department Notes")){
                AdminInformationSearchController.getSelectedClient().setDepartmentNotes(departmentNotesTextArea.getText());
                AdminInformationSearchController.getSelectedClient().update();
                MasterMethod.initializeAllStaticData();
                changeScene(event, previousScene);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Client selectedClient = AdminInformationSearchController.getSelectedClient();
        clientCombo.setItems(Client.getObservableListOfAll());
        clientCombo.getSelectionModel().select(selectedClient);
        if(selectedClient != null) {
            departmentNotesTextArea.setText(selectedClient.getDepartmentNotes());
        }
    }
}
