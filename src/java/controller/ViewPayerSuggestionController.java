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
import model.Payer;
import model.PayerSuggestion;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class ViewPayerSuggestionController extends InformationSuiteController implements Initializable {

    @FXML
    private Label timeZoneLabel;

    @FXML
    private TableColumn<PayerSuggestion, String> clientColumn;

    @FXML
    private TableColumn<PayerSuggestion, String> docNameColumn;

    @FXML
    private TableColumn<PayerSuggestion, String> enteredNameColumn;

    @FXML
    private TableView<PayerSuggestion> payerComboTableView;

    @FXML
    private TableColumn<PayerSuggestion, Timestamp> timeColumn;

    private static PayerSuggestion selectedSuggestion;

    public static PayerSuggestion getSelectedSuggestion() {
        return selectedSuggestion;
    }

    public static void setSelectedSuggestion(PayerSuggestion selectedSuggestion) {
        ViewPayerSuggestionController.selectedSuggestion = selectedSuggestion;
    }

    /**
     * Adds the suggested payer as an actual payer for the specified client.
     * @param event
     * @throws SQLException
     */
    @FXML
    void addButtonClick(ActionEvent event) throws SQLException {
        if(!payerComboTableView.getSelectionModel().isEmpty()){
            selectedSuggestion = payerComboTableView.getSelectionModel().getSelectedItem();
            Payer payer = new Payer(selectedSuggestion.getNameOnDocument(), selectedSuggestion.getNameToEnter(), selectedSuggestion.getClientID());
            payer.insert();
            selectedSuggestion.delete();
            MasterMethod.initializeAllStaticData();
            populateSuggestionTableView(PayerSuggestion.getObservableListOfAllSuggestions());
        }
        else{
            alertUser("No Suggestion Selected.");
        }
    }

    /**
     * Adds the suggested payer as an actual payer for all clients.
     * @param event
     * @throws SQLException
     */
    @FXML
    void addForAllClientsButtonClick(ActionEvent event) throws SQLException {
        if(!payerComboTableView.getSelectionModel().isEmpty()){
            selectedSuggestion = payerComboTableView.getSelectionModel().getSelectedItem();
            Payer payer = new Payer(selectedSuggestion.getNameOnDocument(), selectedSuggestion.getNameToEnter(), selectedSuggestion.getClientID());
            payer.insertForAllClients();
            selectedSuggestion.delete();
            MasterMethod.initializeAllStaticData();
            populateSuggestionTableView(PayerSuggestion.getObservableListOfAllSuggestions());
        }
        else{
            alertUser("No Suggestion Selected.");
        }
    }

    @FXML
    void backButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AdminMainMenu.fxml");
    }

    /**
     * Navigates the user to a screen where they may edit the suggested payer and add it as an actual payer for
     * a single client or all clients.
     * @param event
     * @throws SQLException
     */
    @FXML
    void editAndAddButtonClick(ActionEvent event) throws IOException {
        if(!payerComboTableView.getSelectionModel().isEmpty()){
            selectedSuggestion = payerComboTableView.getSelectionModel().getSelectedItem();
            changeScene(event, "/view/EditAndAddPayer.fxml");
        }
        else{
            alertUser("No Suggestion Selected.");
        }
    }

    @FXML
    void removeButtonClick(ActionEvent event) throws SQLException {
        if(!payerComboTableView.getSelectionModel().isEmpty()){
            if(confirmDelete()){
                selectedSuggestion = payerComboTableView.getSelectionModel().getSelectedItem();
                selectedSuggestion.delete();
                MasterMethod.initializeAllStaticData();
                populateSuggestionTableView(PayerSuggestion.getObservableListOfAllSuggestions());
            }
        }
        else{
            alertUser("No Suggestion Selected.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateSuggestionTableView(PayerSuggestion.getObservableListOfAllSuggestions());

        String userTimeZone = ZoneId.systemDefault().toString();
        timeZoneLabel.setText(userTimeZone);
    }

    /**
     * Populates the suggestionTableView with the appropriate data.
     * @param listToPopulate
     */
    public void populateSuggestionTableView(ObservableList<PayerSuggestion> listToPopulate){
        payerComboTableView.setItems(listToPopulate);
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        docNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameOnDocument"));
        enteredNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameToEnter"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("actionTime"));
        timeColumn.setSortType(TableColumn.SortType.DESCENDING);
        payerComboTableView.getSortOrder().add(timeColumn);
    }
}
