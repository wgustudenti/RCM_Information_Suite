package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.MasterMethod;
import model.TipOfTheDay;
import model.TipOfTheDaySuggestion;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class ViewTipOfTheDaySuggestionController extends InformationSuiteController implements Initializable {

    @FXML
    private Label timeZoneLabel;

    @FXML
    private TableColumn<TipOfTheDaySuggestion, String> suggestedTipOfTheDayColumn;

    @FXML
    private TableColumn<TipOfTheDaySuggestion, Timestamp> timeColumn;

    @FXML
    private TableView<TipOfTheDaySuggestion> tipOfTheDayTableView;

    private static TipOfTheDay selectedSuggestion;

    public static TipOfTheDay getSelectedSuggestion() {
        return selectedSuggestion;
    }

    public static void setSelectedSuggestion(TipOfTheDay selectedSuggestion) {
        ViewTipOfTheDaySuggestionController.selectedSuggestion = selectedSuggestion;
    }

    /**
     * Creates an actual tipOfTheDay which matches the tipOfTheDaySuggestion which is selected in the table.
     * @param event
     * @throws SQLException
     */
    @FXML
    void addButtonClick(ActionEvent event) throws SQLException {
        if(!tipOfTheDayTableView.getSelectionModel().isEmpty()) {
            selectedSuggestion = tipOfTheDayTableView.getSelectionModel().getSelectedItem();
            TipOfTheDay tipOfTheDay = new TipOfTheDay(selectedSuggestion.getDescription(), selectedSuggestion.getPrimaryKey());
            tipOfTheDay.insert();
            selectedSuggestion.delete();
            MasterMethod.initializeAllStaticData();
            populateSuggestionTableView(TipOfTheDaySuggestion.getObservableListOfAllSuggestions());
        }
        else{
            alertUser("No Tip Selected.");
        }
    }

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AdminMainMenu.fxml");
    }

    /**
     * Navigates the user to a scene where they can edit and then add as a real tipOfTheDay the tipOfTheDaySuggestion
     * @param event
     * @throws IOException
     */
    @FXML
    void editAndAddButtonClick(ActionEvent event) throws IOException {
        if(!tipOfTheDayTableView.getSelectionModel().isEmpty()) {
            selectedSuggestion = tipOfTheDayTableView.getSelectionModel().getSelectedItem();
            changeScene(event, "/view/EditAndAddTipOfTheDay.fxml");
        }
        else{
            alertUser("No Tip Selected.");
        }
    }

    @FXML
    void removeButtonClick(ActionEvent event) throws SQLException {
        if(!tipOfTheDayTableView.getSelectionModel().isEmpty()){
            if(confirmDelete()) {
                selectedSuggestion = tipOfTheDayTableView.getSelectionModel().getSelectedItem();
                selectedSuggestion.delete();
                MasterMethod.initializeAllStaticData();
                populateSuggestionTableView(TipOfTheDaySuggestion.getObservableListOfAllSuggestions());
            }
        }
        else{
            alertUser("No Tip Selected.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateSuggestionTableView(TipOfTheDaySuggestion.getObservableListOfAllSuggestions());

        String userTimeZone = ZoneId.systemDefault().toString();
        timeZoneLabel.setText(userTimeZone);
    }

    /**
     * Populates the suggestionTableView with the appropriate data.
     * @param listToPopulate
     */
    public void populateSuggestionTableView(ObservableList<TipOfTheDaySuggestion> listToPopulate){
        tipOfTheDayTableView.setItems(listToPopulate);
        suggestedTipOfTheDayColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("actionTime"));
        timeColumn.setSortType(TableColumn.SortType.DESCENDING);
        tipOfTheDayTableView.getSortOrder().add(timeColumn);
    }
}
