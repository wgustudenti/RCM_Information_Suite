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
public class EditCarcRarcController extends InformationSuiteController implements Initializable {

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
     *
     * Ensures all data is valid and updates the RemarkCode.
     * Refreshes data.
     * Changes scene.
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void submitButtonClick(ActionEvent event) throws SQLException, IOException {
        if(checkIfComboIsNotEmpty(carcRARCCombo, "CARC Or RARC")) {
            if (checkIfFieldIsNotEmpty(codeTextField.getText(), "Remark Code")) {
                if (checkIfFieldIsNotEmpty(descriptionTextArea.getText(), "Description")) {
                    if (checkIfSixOrLessChar(codeTextField.getText(), "Remark Code")) {
                        if (checkIfFiveHundredOrLessChar(descriptionTextArea.getText(), "Description")) {
                            if (checkIfFiveHundredOrLessChar(notesTextArea.getText(), "Notes")) {
                                RemarkCode remarkCode = new RemarkCode(codeTextField.getText(), descriptionTextArea.getText(),
                                        notesTextArea.getText(), carcRARCCombo.getSelectionModel().getSelectedItem().getPrimaryKey());
                                remarkCode.update();
                                MasterMethod.initializeAllStaticData();
                                changeScene(event, "/view/AdminInformationSearch.fxml");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Sets all fields so that they represent the attributes from the selected remark on th previous page.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RemarkCode selectedRemark = AdminInformationSearchController.getSelectedRemark();
        carcRARCCombo.setItems(CarcOrRarc.getObservableListOfAll());
        carcRARCCombo.getSelectionModel().select(CarcOrRarc.getMapOfAll().get(selectedRemark.getCarcRarcID()));
        codeTextField.setText(selectedRemark.getRemarkCode());
        descriptionTextArea.setText(selectedRemark.getDescription());
        notesTextArea.setText(selectedRemark.getNotes());
    }
}
