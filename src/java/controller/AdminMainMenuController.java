package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class AdminMainMenuController extends InformationSuiteController implements Initializable {

    @FXML
    void adminLogButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/ViewAdminLog.fxml");
    }

    @FXML
    void backButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AdminInformationSearch.fxml");
    }

    @FXML
    void clientButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/ViewClient.fxml");
    }

    @FXML
    void deptNotesButtonClick(ActionEvent event) throws IOException {
        EditDepartmentNotesController.setPreviousScene("/view/AdminMainMenu.fxml");
        changeScene(event, "/view/EditDepartmentNotes.fxml");
    }

    @FXML
    void generalSuggestionButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/ViewGeneralSuggestions.fxml");
    }

    @FXML
    void logoutButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/InformationSearch.fxml");
    }

    @FXML
    void payerComboSuggestionButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/ViewPayerSuggestion.fxml");
    }

    @FXML
    void specialtyNotesButtonClick(ActionEvent event) throws IOException {
        EditSpecialtyNotesController.setPreviousScene("/view/AdminMainMenu.fxml");
        changeScene(event, "/view/EditSpecialtyNotes.fxml");
    }

    @FXML
    void tipOfTheDayButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/ViewTipsOfTheDay.fxml");
    }

    @FXML
    void tipSuggestionButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/ViewTipOfTheDaySuggestion.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
