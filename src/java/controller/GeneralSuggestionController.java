package controller;

import databasevendor.VendorGeneralSuggestion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import main.MasterMethod;
import model.GeneralSuggestion;
import model.SuggestionCategory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class GeneralSuggestionController extends InformationSuiteController implements Initializable {

    @FXML
    private ComboBox<SuggestionCategory> categoryCombo;

    @FXML
    private TextArea suggestionTextArea;

    public ComboBox<SuggestionCategory> getCategoryCombo() {
        return categoryCombo;
    }

    public void setCategoryCombo(ComboBox<SuggestionCategory> categoryCombo) {
        this.categoryCombo = categoryCombo;
    }

    public TextArea getSuggestionTextArea() {
        return suggestionTextArea;
    }

    public void setSuggestionTextArea(TextArea suggestionTextArea) {
        this.suggestionTextArea = suggestionTextArea;
    }

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/InformationSearch.fxml");
    }

    /**
     * Runs data validation checks on the provided data and then creates a new generalSuggestion
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void submitButtonClick(ActionEvent event) throws IOException, SQLException {
        if(!categoryCombo.getSelectionModel().isEmpty()) {
            if(!suggestionTextArea.getText().isEmpty()) {
                if(checkIfFiveHundredOrLessChar(suggestionTextArea.getText(), "Suggestion")) {
                    GeneralSuggestion generalSuggestion = new GeneralSuggestion(suggestionTextArea.getText(),
                            categoryCombo.getSelectionModel().getSelectedItem().getPrimaryKey());
                    generalSuggestion.insert();
                    MasterMethod.initializeAllStaticData();
                    changeScene(event, "/view/InformationSearch.fxml");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryCombo.setItems(SuggestionCategory.getObservableListOfAll());
    }
}
