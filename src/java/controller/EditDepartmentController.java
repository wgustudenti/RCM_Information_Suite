package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import main.MasterMethod;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class EditDepartmentController extends InformationSuiteController implements Initializable {

    @FXML
    private TextField departmentNameTextField;

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/EditClient.fxml");
    }

    @FXML
    void submitButtonClick(ActionEvent event) throws SQLException, IOException {
        if(checkIfFieldIsNotEmpty(departmentNameTextField.getText(), "Department")) {
            if(checkIfHundredOrLessChar(departmentNameTextField.getText(), "Department")) {
                EditClientController.getSelectedDepartment().setDescription(departmentNameTextField.getText());
                EditClientController.getSelectedDepartment().update();
                MasterMethod.initializeAllStaticData();
                changeScene(event, "/view/EditClient.fxml");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departmentNameTextField.setText(EditClientController.getSelectedDepartment().getDescription());
    }
}
