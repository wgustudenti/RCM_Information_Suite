package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.MasterMethod;
import model.CarcOrRarc;
import model.RemarkCode;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class AddCarcRarcController extends InformationSuiteController implements Initializable {

    @FXML
    private ComboBox<CarcOrRarc> carcRARCCombo;

    @FXML
    private TextField codeTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextArea notesTextArea;

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AdminInformationSearch.fxml");
    }

    /**
     * Ensures all data is valid and inserts the RemarkCode.
     * Refreshes data.
     * Changes scene.
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void submitButtonClick(ActionEvent event) throws IOException, SQLException {
        if(checkIfComboIsNotEmpty(carcRARCCombo, "CARC Or RARC")){
            if(checkIfFieldIsNotEmpty(codeTextField.getText(), "Remark Code")) {
                if(checkIfFieldIsNotEmpty(descriptionTextArea.getText(), "Description")){
                    if (checkIfSixOrLessChar(codeTextField.getText(), "Remark Code")) {
                        if (checkIfFiveHundredOrLessChar(descriptionTextArea.getText(), "Description")) {
                            if (checkIfFiveHundredOrLessChar(notesTextArea.getText(), "Notes")) {
                                RemarkCode remarkCode = new RemarkCode(codeTextField.getText(), descriptionTextArea.getText(),
                                        notesTextArea.getText(), carcRARCCombo.getSelectionModel().getSelectedItem().getPrimaryKey());
                                remarkCode.insert();
                                MasterMethod.initializeAllStaticData();
                                changeScene(event, "/view/AdminInformationSearch.fxml");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carcRARCCombo.setItems(CarcOrRarc.getObservableListOfAll());
    }
}
