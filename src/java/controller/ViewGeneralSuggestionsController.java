package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.MasterMethod;
import model.GeneralSuggestion;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class ViewGeneralSuggestionsController extends InformationSuiteController implements Initializable {

    @FXML
    private Label timeZoneLabel;

    @FXML
    private TableColumn<GeneralSuggestion, String> categoryColumn;

    @FXML
    private TableColumn<GeneralSuggestion, String> suggestionColumn;

    @FXML
    private TableColumn<GeneralSuggestion, Timestamp> timeColumn;

    @FXML
    private TableView<GeneralSuggestion> suggestionTableView;

    @FXML
    private TextArea suggestionTextArea;

    @FXML
    void addSuggestionButtonClick(ActionEvent event) throws IOException {
        AdminGeneralSuggestionController.setPreviousScene("/view/ViewGeneralSuggestions.fxml");
        changeScene(event, "/view/AdminGeneralSuggestion.fxml");
    }

    @FXML
    void backButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AdminMainMenu.fxml");
    }

    @FXML
    void removeButtonClick(ActionEvent event) throws SQLException {
        if(!suggestionTableView.getSelectionModel().isEmpty()){
            if(confirmDelete()) {
                suggestionTableView.getSelectionModel().getSelectedItem().delete();
                MasterMethod.initializeAllStaticData();
                populateSuggestionTableView(GeneralSuggestion.getObservableListOfAll());
                suggestionTextArea.clear();
            }
        }
        else{
            alertUser("No Suggestion Selected.");
        }
    }

    /**
     * Sets the text area next to the suggestionTableView to represent the selected generalSuggestion's description when
     * a user presses any key while the table is selected.
     * @param event
     */
    @FXML
    void suggestionTableUpOrDownArrowPress(KeyEvent event) {
        if(!suggestionTableView.getSelectionModel().isEmpty()){
            suggestionTextArea.setText(suggestionTableView.getSelectionModel().getSelectedItem().getDescription());
        }
    }

    /**
     * Sets the text area next to the suggestionTableView to represent the selected generalSuggestion's description when
     * a user clicks in the table.
     * @param event
     */
    @FXML
    void suggestionTableClick(MouseEvent event) {
        if(!suggestionTableView.getSelectionModel().isEmpty()){
            suggestionTextArea.setText(suggestionTableView.getSelectionModel().getSelectedItem().getDescription());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateSuggestionTableView(GeneralSuggestion.getObservableListOfAll());

        String userTimeZone = ZoneId.systemDefault().toString();
        timeZoneLabel.setText(userTimeZone);
    }

    /**
     * Populates teh suggestionsTableView with all the necessary information.
     * @param listToPopulate
     */
    public void populateSuggestionTableView(ObservableList<GeneralSuggestion> listToPopulate){
        suggestionTableView.setItems(listToPopulate);
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryDescription"));
        suggestionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("actionTime"));
        timeColumn.setSortType(TableColumn.SortType.DESCENDING);
        suggestionTableView.getSortOrder().add(timeColumn);
    }
}
