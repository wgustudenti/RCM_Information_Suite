package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.MasterMethod;
import model.GeneralSuggestion;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class AdminGeneralSuggestionController extends GeneralSuggestionController implements Initializable {

    private static String previousScene;

    public static String getPreviousScene() {
        return previousScene;
    }

    public static void setPreviousScene(String previousScene) {
        AdminGeneralSuggestionController.previousScene = previousScene;
    }

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, previousScene);
    }

    /**
     * Checks that all fields have valid data and creates a new generalSuggestion with the specified attributes
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void submitButtonClick(ActionEvent event) throws IOException, SQLException {
        if(!getCategoryCombo().getSelectionModel().isEmpty()) {
            if(!getSuggestionTextArea().getText().isEmpty()) {
                if(checkIfFiveHundredOrLessChar(getSuggestionTextArea().getText(), "Suggestion")) {
                    GeneralSuggestion generalSuggestion = new GeneralSuggestion(getSuggestionTextArea().getText(),
                            getCategoryCombo().getSelectionModel().getSelectedItem().getPrimaryKey());
                    generalSuggestion.insert();
                    MasterMethod.initializeAllStaticData();
                    changeScene(event, previousScene);
                }
            }
            else{
                alertUser("Suggestion Must Have Content");
            }
        }
        else{
            alertUser("No Category Selected");
        }
    }
}
