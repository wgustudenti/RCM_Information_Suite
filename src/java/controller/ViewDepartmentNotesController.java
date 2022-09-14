package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class ViewDepartmentNotesController extends InformationSuiteController implements Initializable {

    @FXML
    private Label clientNameLabel;

    @FXML
    private TextArea notesTextArea;

    @FXML
    void backButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/InformationSearch.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Client selectedClient = InformationSearchController.getSelectedClient();
        clientNameLabel.setText(selectedClient.getName());
        notesTextArea.setText(selectedClient.getSpecialtyNotes());
    }
}
