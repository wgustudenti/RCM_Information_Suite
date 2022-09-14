package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.MasterMethod;
import model.Payer;
import model.Provider;
import model.RemarkCode;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class AdminInformationSearchController extends InformationSearchController{

    /**
     * Represents the object of the specified type which is selected when the user tries to edit or remove the specified object.
     */
    private static RemarkCode selectedRemark;

    /**
     * Represents the object of the specified type which is selected when the user tries to edit or remove the specified object.
     */
    private static Payer selectedPayer;

    /**
     * Represents the object of the specified type which is selected when the user tries to edit or remove the specified object.
     */
    private static Provider selectedProvider;

    public static RemarkCode getSelectedRemark() {
        return selectedRemark;
    }

    public static void setSelectedRemark(RemarkCode selectedRemark) {
        AdminInformationSearchController.selectedRemark = selectedRemark;
    }

    public static Payer getSelectedPayer() {
        return selectedPayer;
    }

    public static void setSelectedPayer(Payer selectedPayer) {
        AdminInformationSearchController.selectedPayer = selectedPayer;
    }

    public static Provider getSelectedProvider() {
        return selectedProvider;
    }

    public static void setSelectedProvider(Provider selectedProvider) {
        AdminInformationSearchController.selectedProvider = selectedProvider;
    }

    @FXML
    void addPayerComboButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AddPayer.fxml");
    }

    @FXML
    void addProviderButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AddProvider.fxml");
    }

    @FXML
    void addRemarkCodeButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AddCarcRarc.fxml");
    }

    @FXML
    void adminMenuButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AdminMainMenu.fxml");
    }

    @FXML
    void chartsButtonClick(ActionEvent event) throws IOException {
        ViewChartsController.setPreviousScene("/view/AdminInformationSearch.fxml");
        changeScene(event, "/view/ViewCharts.fxml");
    }

    @FXML
    void deptNotesButtonClick(ActionEvent event) throws IOException {
        if(getSelectedClient() != null) {
            changeScene(event, "/view/AdminDepartmentNotes.fxml");
        }
        else{
            alertUser("No Client Selected.");
        }
    }

    @FXML
    void editAllPayerButtonClick(ActionEvent event) throws IOException {
        if(!getPayerAllTableView().getSelectionModel().isEmpty()) {
            selectedPayer = getPayerAllTableView().getSelectionModel().getSelectedItem();
            changeScene(event, "/view/EditPayer.fxml");
        }
        else{
            alertUser("No Payer Selected");
        }
    }

    @FXML
    void editAllProviderButtonClick(ActionEvent event) throws IOException {
        if(!getProviderAllTableView().getSelectionModel().isEmpty()) {
            selectedProvider = getProviderAllTableView().getSelectionModel().getSelectedItem();
            changeScene(event, "/view/EditProvider.fxml");
        }
        else{
            alertUser("No Provider Selected");
        }
    }

    @FXML
    void editClientPayerButtonClick(ActionEvent event) throws IOException {
        if(!getPayerClientTableView().getSelectionModel().isEmpty()) {
            selectedPayer = getPayerClientTableView().getSelectionModel().getSelectedItem();
            changeScene(event, "/view/EditPayer.fxml");
        }
        else{
            alertUser("No Payer Selected");
        }
    }

    @FXML
    void editClientProviderButtonClick(ActionEvent event) throws IOException {
        if(!getProviderClientTableView().getSelectionModel().isEmpty()) {
            selectedProvider = getProviderClientTableView().getSelectionModel().getSelectedItem();
            changeScene(event, "/view/EditProvider.fxml");
        }
        else{
            alertUser("No Provider Selected");
        }
    }

    @FXML
    void mcuEditRemarkButtonClick(ActionEvent event) throws IOException {
        if(!getMcuTableView().getSelectionModel().isEmpty()) {
            selectedRemark = getMcuTableView().getSelectionModel().getSelectedItem();
            changeScene(event, "/view/EditCarcRarc.fxml");
        }
        else{
            alertUser("No Remark Selected");
        }
    }

    /**
     * Checks that there is a selection and confirms with user that they wish to proceed.
     * Then deletes the selected object from memory and from the database.
     * @param event
     * @throws SQLException
     */
    @FXML
    void mcuRemoveRemarkButtonClick(ActionEvent event) throws SQLException {
        if(!getMcuTableView().getSelectionModel().isEmpty()) {
            if (confirmDelete()) {
                getMcuTableView().getSelectionModel().getSelectedItem().delete();
                getMcuTableView().getItems().clear();
                getMcuTableView().getItems().clear();
                MasterMethod.initializeAllStaticData();
            }
        }
        else{
            alertUser("No Remark Selected");
        }
    }

    /**
     * Checks that there is a selection and confirms with user that they wish to proceed.
     * Then deletes the selected object from memory and from the database.
     * @param event
     * @throws SQLException
     */
    @FXML
    void removeAllPayerButtonClick(ActionEvent event) throws SQLException {
        if(!getPayerAllTableView().getSelectionModel().isEmpty()) {
            if (confirmDelete()) {
                getPayerAllTableView().getSelectionModel().getSelectedItem().delete();
                getPayerAllTableView().getItems().clear();
                getPayerAllTableView().getItems().clear();
                MasterMethod.initializeAllStaticData();
            }
        }
        else{
            alertUser("No Payer Selected");
        }
    }

    /**
     * Checks that there is a selection and confirms with user that they wish to proceed.
     * Then deletes the selected object from memory and from the database.
     * @param event
     * @throws SQLException
     */
    @FXML
    void removeAllProviderButtonClick(ActionEvent event) throws SQLException {
        if(!getProviderAllTableView().getSelectionModel().isEmpty()) {
            if (confirmDelete()) {
                getProviderAllTableView().getSelectionModel().getSelectedItem().delete();
                getProviderAllTableView().getItems().clear();
                getProviderAllTableView().getItems().clear();
                MasterMethod.initializeAllStaticData();
            }
        }
        else{
            alertUser("No Provider Selected");
        }
    }

    /**
     * Checks that there is a selection and confirms with user that they wish to proceed.
     * Then deletes the selected object from memory and from the database.
     * @param event
     * @throws SQLException
     */
    @FXML
    void removeClientPayerButtonClick(ActionEvent event) throws SQLException {
        if(!getPayerClientTableView().getSelectionModel().isEmpty()) {
            if (confirmDelete()) {
                getPayerClientTableView().getSelectionModel().getSelectedItem().delete();
                getPayerClientTableView().getItems().clear();
                getPayerClientTableView().getItems().clear();
                MasterMethod.initializeAllStaticData();
            }
        }
        else{
            alertUser("No Payer Selected");
        }
    }

    /**
     * Checks that there is a selection and confirms with user that they wish to proceed.
     * Then deletes the selected object from memory and from the database.
     * @param event
     * @throws SQLException
     */
    @FXML
    void removeClientProviderButtonClick(ActionEvent event) throws SQLException {
        if(!getProviderClientTableView().getSelectionModel().isEmpty()) {
            if (confirmDelete()) {
                getProviderClientTableView().getSelectionModel().getSelectedItem().delete();
                getProviderClientTableView().getItems().clear();
                getProviderClientTableView().getItems().clear();
                MasterMethod.initializeAllStaticData();
            }
        }
        else{
            alertUser("No Provider Selected");
        }        
    }

    @FXML
    void resultsEditRemarkButtonClick(ActionEvent event) throws IOException {
        if(!getResultsTableView().getSelectionModel().isEmpty()) {
            selectedRemark = getResultsTableView().getSelectionModel().getSelectedItem();
            changeScene(event, "/view/EditCarcRarc.fxml");
        }
        else{
            alertUser("No Remark Selected.");
        }
    }

    /**
     * Checks that there is a selection and confirms with user that they wish to proceed.
     * Then deletes the selected object from memory and from the database.
     * @param event
     * @throws SQLException
     */
    @FXML
    void resultsRemoveRemarkButtonClick(ActionEvent event) throws SQLException {
        if(!getResultsTableView().getSelectionModel().isEmpty()) {
            if (confirmDelete()) {
                getResultsTableView().getSelectionModel().getSelectedItem().delete();
                getResultsTableView().getItems().clear();
                getMcuTableView().getItems().clear();
                MasterMethod.initializeAllStaticData();
            }
        }
        else{
            alertUser("No Remark Selected.");
        }
    }

    @FXML
    void specialtyNotesButtonClick(ActionEvent event) throws IOException {
        if(getSelectedClient() != null) {
            changeScene(event, "/view/AdminSpecialtyNotes.fxml");
        }
        else{
            alertUser("No Client Selected.");
        }
    }

    @FXML
    void suggestionButtonClick(ActionEvent event) throws IOException {
        AdminGeneralSuggestionController.setPreviousScene("/view/AdminInformationSearch.fxml");
        changeScene(event, "/view/AdminGeneralSuggestion.fxml");
    }
}
