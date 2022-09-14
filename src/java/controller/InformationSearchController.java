package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class InformationSearchController extends InformationSuiteController implements Initializable  {

    @FXML
    private ToggleGroup filterByRow;

    @FXML
    private ToggleGroup sortByRow;

    @FXML
    private RadioButton alphanumericRadioButton;

    @FXML
    private RadioButton carcRARCRadioButton;

    @FXML
    private ComboBox<Client> clientCombo;

    @FXML
    private Label clientLabel;

    @FXML
    private ListView<String> codesListView;

    @FXML
    private CheckBox includeAlertsCheckBox;

    @FXML
    private RadioButton justCARCsRadioButton;

    @FXML
    private RadioButton justRARCSRadioButton;

    @FXML
    private TableColumn<RemarkCodeMostUsedWith, String> mcuCodeColumn;

    @FXML
    private TableColumn<RemarkCodeMostUsedWith, String> mcuDescriptionColumn;

    @FXML
    private TableColumn<RemarkCodeMostUsedWith, Integer> mcuNumberColumn;

    @FXML
    private TableColumn<RemarkCodeMostUsedWith, String> mcuPrefixColumn;

    @FXML
    private TableColumn<RemarkCodeMostUsedWith, Integer> mcuSuffixColumn;

    @FXML
    private TableView<RemarkCodeMostUsedWith> mcuTableView;

    @FXML
    private TableColumn<RemarkCodeMostUsedWith, String> mcuTagColumn;

    @FXML
    private TableColumn<RemarkCodeMostUsedWith, Integer> mcuTotalColumn;

    @FXML
    private RadioButton mostCommonRadioButton;

    @FXML
    private TextArea notesTextArea;

    @FXML
    private TableColumn<Payer, String> payerAllDocNameColumn;

    @FXML
    private TableColumn<Payer, String> payerAllEnterNameColumn;

    @FXML
    private TableColumn<Payer, String> payerAllTableClientColumn;

    @FXML
    private TableView<Payer> payerAllTableView;

    @FXML
    private TableColumn<Payer, String> payerClientDocNameColumn;

    @FXML
    private TableColumn<Payer, String> payerClientEnterNameColumn;

    @FXML
    private Label payerClientNameLabel;

    @FXML
    private TableColumn<Payer, String> payerClientTableClientColumn;

    @FXML
    private TableView<Payer> payerClientTableView;

    @FXML
    private TextField payerSearchTextField;

    @FXML
    private TableColumn<Provider, String> providerAllDeptColumn;

    @FXML
    private TableColumn<Provider, String> providerAllFirstNameColumn;

    @FXML
    private TableColumn<Provider, String> providerAllLastNameColumn;

    @FXML
    private TableColumn<Provider, String> providerAllNPIColumn;

    @FXML
    private TableColumn<Provider, String> providerAllTableClientColumn;

    @FXML
    private TableView<Provider> providerAllTableView;

    @FXML
    private TableColumn<Provider, String> providerClientDeptColumn;

    @FXML
    private TableColumn<Provider, String> providerClientFirstNameColumn;

    @FXML
    private TableColumn<Provider, String> providerClientLastNameColumn;

    @FXML
    private TableColumn<Provider, String> providerClientNPIColumn;

    @FXML
    private Label providerClientNameLabel;

    @FXML
    private TableColumn<Provider, String> providerClientTableClientColumn;

    @FXML
    private TableView<Provider> providerClientTableView;

    @FXML
    private TextField providerSearchTextField;

    @FXML
    private TextArea remarkMCUDescriptionTextArea;

    @FXML
    private TextArea remarkResultsDescriptionTextArea;

    @FXML
    private TextField remarkSearchTextField;

    @FXML
    private TableColumn<RemarkCodeResult, String> resultsCodeColumn;

    @FXML
    private TableColumn<RemarkCodeResult, String> resultsDescriptionColumn;

    @FXML
    private TableColumn<RemarkCodeResult, Integer> resultsNumberColumn;

    @FXML
    private TableColumn<RemarkCodeResult, String> resultsPrefixColumn;

    @FXML
    private TableColumn<RemarkCodeResult, Integer> resultsSuffixColumn;

    @FXML
    private TableColumn<RemarkCodeResult, Integer> resultsTotalColumn;

    @FXML
    private TableColumn<RemarkCodeResult, String> resultsTagColumn;

    @FXML
    private TableView<RemarkCodeResult> resultsTableView;

    @FXML
    private Label tipOfTheDayLabel;

    private static Client selectedClient;

    public TableView<RemarkCodeMostUsedWith> getMcuTableView() {
        return mcuTableView;
    }

    public void setMcuTableView(TableView<RemarkCodeMostUsedWith> mcuTableView) {
        this.mcuTableView = mcuTableView;
    }

    public TableView<Payer> getPayerAllTableView() {
        return payerAllTableView;
    }

    public void setPayerAllTableView(TableView<Payer> payerAllTableView) {
        this.payerAllTableView = payerAllTableView;
    }

    public TableView<Payer> getPayerClientTableView() {
        return payerClientTableView;
    }

    public void setPayerClientTableView(TableView<Payer> payerClientTableView) {
        this.payerClientTableView = payerClientTableView;
    }

    public TableView<Provider> getProviderAllTableView() {
        return providerAllTableView;
    }

    public void setProviderAllTableView(TableView<Provider> providerAllTableView) {
        this.providerAllTableView = providerAllTableView;
    }

    public TableView<Provider> getProviderClientTableView() {
        return providerClientTableView;
    }

    public void setProviderClientTableView(TableView<Provider> providerClientTableView) {
        this.providerClientTableView = providerClientTableView;
    }

    public TableView<RemarkCodeResult> getResultsTableView() {
        return resultsTableView;
    }

    public void setResultsTableView(TableView<RemarkCodeResult> resultsTableView) {
        this.resultsTableView = resultsTableView;
    }

    public static Client getSelectedClient() {
        return selectedClient;
    }

    public static void setSelectedClient(Client selectedClient) {
        InformationSearchController.selectedClient = selectedClient;
    }

    /**
     * This method will be called by all the add buttons in the column on the right of the RemarkTab.
     *
     * This is for the full buttons which interact with the results table, not the "2" buttons next to them.
     */
    @FXML
    void ResultsOAAddButtonClick(ActionEvent event) throws SQLException {
        resultsAddButtonClick("OA");
    }

    @FXML
    void adminButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AdminLogin.fxml");
    }

    /**
     * Copies the selected payer's name from the all tableview in the payer tab.
     * @param event
     */
    @FXML
    void allPayerCopyNameButtonClick(ActionEvent event) {
        String stringToCopy = "COPY ERROR";
        if(!payerAllTableView.getSelectionModel().isEmpty()){
            stringToCopy = payerAllTableView.getSelectionModel().getSelectedItem().getNameToEnter();
            copyToClipboard(stringToCopy);
        }
        else{
            alertUser("No Payer Selected");
        }
    }

    /**
     * Copies the selected provider's npi from the all tableview in the provider tab.
     * @param event
     */
    @FXML
    void allProviderCopyNPIButtonClick(ActionEvent event) {
        String stringToCopy = "COPY ERROR";
        if(!providerAllTableView.getSelectionModel().isEmpty()){
            stringToCopy = providerAllTableView.getSelectionModel().getSelectedItem().getNpi();
            copyToClipboard(stringToCopy);
        }
        else{
            alertUser("No Provider Selected");
        }
    }

    /**
     * Sorts both tableviews on the Remark tab alphanumerically with the Remark Code suffix (alphabet characters) as
     * first priority and the Remark Code suffix (numbers) as the second priority.
     *
     * @param event
     */
    @FXML
    void alphanumericRadioButtonSelection(ActionEvent event) {
        determineResultsSortOrder();
        determineMCUSortOrder();
    }

    /**
     * When the CARCs and RARCs radio button is selected in the Filter By: selection in the Remark tab,
     * This method is called.
     *
     * This method changes the contents of the corresponding tableview to match the filter criteria.
     * @param event
     */
    @FXML
    void carcRARCRadioButtonSelection(ActionEvent event) {
        if(!resultsTableView.getItems().isEmpty()) {
            remarkSearchTextFieldAction(event);
        }
    }

    @FXML
    void chartsButtonClick(ActionEvent event) throws IOException {
        ViewChartsController.setPreviousScene("/view/InformationSearch.fxml");
        changeScene(event, "/view/ViewCharts.fxml");
    }

    /**
     * This method is called when the "Clear Last" button is clicked on the RemarkCode tab.
     * It will clear all codes which back the codesListView.
     *
     * @param event
     */
    @FXML
    void clearAllCodesButtonClick(ActionEvent event) {
        codesListView.getItems().clear();
    }

    /**
     * This method is called when the "Clear Last" button is clicked on the RemarkCode tab.
     * It will clear the last entry in the list which backs the codesListView.
     *
     * @param event
     */
    @FXML
    void clearLastCodeButtonClick(ActionEvent event) {
        if(!codesListView.getItems().isEmpty()) {
            int indexOfLastRemarkCode = codesListView.getItems().size() - 1;
            codesListView.getItems().remove(indexOfLastRemarkCode);
        }
    }

    /**
     * This method is called whenever a selection is made in the clientCombo.
     *
     * If the payer or provider tab has a tableview which contains results, the method will call the tab's search function.
     * @param event
     */
    @FXML
    void clientComboSelection(ActionEvent event) {
        payerClientNameLabel.setText(clientCombo.getSelectionModel().getSelectedItem().getName());
        providerClientNameLabel.setText(clientCombo.getSelectionModel().getSelectedItem().getName());
        if(!payerClientTableView.getItems().isEmpty() || !payerAllTableView.getItems().isEmpty()){
            payerSearchTextFieldAction(event);
        }
        if(!providerClientTableView.getItems().isEmpty() || !providerAllTableView.getItems().isEmpty()){
            providerSearchTextFieldAction(event);
        }
        setSelectedClient(clientCombo.getSelectionModel().getSelectedItem());
    }

    /**
     * Copies the selected payer's name from the client tableview in the payer tab.
     * @param event
     */
    @FXML
    void clientPayerCopyNameButtonClick(ActionEvent event) {
        String stringToCopy = "COPY ERROR";
        if(!payerClientTableView.getSelectionModel().isEmpty()){
            stringToCopy = payerClientTableView.getSelectionModel().getSelectedItem().getNameToEnter();
            copyToClipboard(stringToCopy);
        }
        else{
            alertUser("No Payer Selected");
        }
    }

    /**
     * Copies the selected provider's npi from the client tableview in the provider tab.
     * @param event
     */
    @FXML
    void clientProviderCopyNPIButtonClick(ActionEvent event) {
        String stringToCopy = "COPY ERROR";
        if(!providerClientTableView.getSelectionModel().isEmpty()){
            stringToCopy = providerClientTableView.getSelectionModel().getSelectedItem().getNpi();
            copyToClipboard(stringToCopy);
        }
        else{
            alertUser("No Provider Selected");
        }
    }

    /**
     * This method is called when the copy button above the codesListView is clicked.
     *
     * It copies the remark codes to the clipboard in a user-friendly manner and updates the tableviews on the same tab.
     * @param event
     * @throws SQLException
     */
    @FXML
    void codesCopyButtonClick(ActionEvent event) throws SQLException {
        String stringToCopy = "";
        for(String remarkCodeString : codesListView.getItems()){
            stringToCopy = stringToCopy + remarkCodeString + ",";
        }
        if(!stringToCopy.isEmpty()) {
            int indexOfLastComma = stringToCopy.length() - 1;
            stringToCopy = stringToCopy.substring(0, indexOfLastComma);
        }
        copyToClipboard(stringToCopy);

        //Takes the added prefixes out of the remark codes in the listview.
        ObservableList<String> listOfPureCodes = FXCollections.observableArrayList();
        for(String remarkCodeString : codesListView.getItems()) {
            String pureRemarkCode = remarkCodeString.replace("PR", "");
            pureRemarkCode = pureRemarkCode.replace("OA", "");
            pureRemarkCode = pureRemarkCode.replace("CO", "");
            listOfPureCodes.add(pureRemarkCode);
        }

        updateRemarkTableViewsWithList(listOfPureCodes);
        RemarkCode.updateMostUsedDataForList(listOfPureCodes);
    }

    @FXML
    void deptNotesButtonClick(ActionEvent event) throws IOException {
        if(selectedClient != null) {
            changeScene(event, "/view/ViewDepartmentNotes.fxml");
        }
        else{
            alertUser("No Client Selected.");
        }
    }

    @FXML
    void specialtyNotesButtonClick(ActionEvent event) throws IOException {
        if(selectedClient != null) {
            changeScene(event, "/view/ViewSpecialtyNotes.fxml");
        }
        else{
            alertUser("No Client Selected.");
        }
    }

    /**
     * When this checkbox is selected or unselected, if the tableview has information in it, the tableview will be refreshed with a
     * fresh search so that it shows what the user wants.
     * @param event
     */
    @FXML
    void includeAlertsCheckBoxSelection(ActionEvent event) {
        if(!resultsTableView.getItems().isEmpty()) {
            remarkSearchTextFieldAction(event);
        }
    }


    /**
     * When the Just CARCS radio button is selected in the Filter By: selection in the Remark tab,
     * This method is called.
     *
     * This method changes the contents of the corresponding tableview to match the filter criteria.
     * @param event
     */
    @FXML
    void justCARCsRadioButtonSelection(ActionEvent event) {
        //It's just more visually pleasing if the empty table doesn't change when this radiobutton is selected.
        Boolean tableWasEmpty = resultsTableView.getItems().isEmpty();

        if(!tableWasEmpty) {
            ArrayList<RemarkCode> remarkCodesToRemove = new ArrayList<>();
            for (RemarkCode remarkCode : resultsTableView.getItems()) {
                if (!remarkCode.getCarcOrRarcString().toLowerCase().contains("CARC".toLowerCase())) {
                    remarkCodesToRemove.add(remarkCode);
                }
            }
            for (RemarkCode remarkCode : remarkCodesToRemove){
                resultsTableView.getItems().remove(remarkCode);
            }
        }

        //If switching from "Just RARCS," the CARCs will not all be captured.  This refreshes the list in that case.
        if(resultsTableView.getItems().isEmpty() && !tableWasEmpty){
            remarkSearchTextFieldAction(event);
        }
    }

    /**
     * When the Just RARCs radio button is selected in the Filter By: selection in the Remark tab,
     * This method is called.
     *
     * This method changes the contents of the corresponding tableview to match the filter criteria.
     * @param event
     */
    @FXML
    void justRARCSRadioButtonSelection(ActionEvent event) {
        //It's just more visually pleasing if the empty table doesn't change when this radiobutton is selected.
        Boolean tableWasEmpty = resultsTableView.getItems().isEmpty();

        if(!tableWasEmpty) {
            ArrayList<RemarkCode> remarkCodesToRemove = new ArrayList<>();
            for (RemarkCode remarkCode : resultsTableView.getItems()) {
                if (!remarkCode.getCarcOrRarcString().toLowerCase().contains("RARC".toLowerCase())) {
                    remarkCodesToRemove.add(remarkCode);
                }
            }
            for (RemarkCode remarkCode : remarkCodesToRemove){
                resultsTableView.getItems().remove(remarkCode);
            }
        }

        //If switching from "Just CARCS," the RARCs will not all be captured.  This refreshes the list in that case.
        if(resultsTableView.getItems().isEmpty() && !tableWasEmpty){
            remarkSearchTextFieldAction(event);
        }
    }

    @FXML
    void mcuCOAddButtonClick(ActionEvent event) throws SQLException {
        mcuAddButtonClick("CO");
    }

    @FXML
    void mcuCOCopyButtonClick(ActionEvent event) throws SQLException {
        mcuCopyButtonClick("CO");
    }

    @FXML
    void mcuOAAddButtonClick(ActionEvent event) throws SQLException {
        mcuAddButtonClick("OA");
    }

    @FXML
    void mcuOACopyButtonClick(ActionEvent event) throws SQLException {
        mcuCopyButtonClick("OA");
    }

    @FXML
    void mcuPRAddButtonClick(ActionEvent event) throws SQLException {
        mcuAddButtonClick("PR");
    }

    @FXML
    void mcuPRCopyButtonClick(ActionEvent event) throws SQLException {
        mcuCopyButtonClick("PR");
    }

    /**
     * When a user clicks on a row in the mcuTableView, the text area to the left populates with the description of their
     * selection.
     *
     * Additionally, if there are any notes associated with the RemarkCode, they are populated in the notesTextArea.
     * @param event
     */
    @FXML
    void mcuTableClick(MouseEvent event) {
        RemarkCodeMostUsedWith remarkSelection = mcuTableView.getSelectionModel().getSelectedItem();
        if(remarkSelection != null) {
            remarkMCUDescriptionTextArea.setText(prepareDescription(remarkSelection));
            notesTextArea.setText(prepareRemarkNotes(remarkSelection));
        }
    }

    /**
     * Refreshes the remarkMCUDescriptionTextArea and the notesTextArea when any key is hit on the keyboard while the
     * mcuTableView is selected.
     */
    @FXML
    void mcuUpOrDownArrowPress(){
        RemarkCodeMostUsedWith remarkSelection = mcuTableView.getSelectionModel().getSelectedItem();
        remarkMCUDescriptionTextArea.setText(prepareDescription(remarkSelection));
        notesTextArea.setText(prepareRemarkNotes(remarkSelection));
    }

    /**
     * When this radiobutton is selected, the sort order will change to match this setting.
     * @param event
     */
    @FXML
    void mostCommonRadioButtonSelection(ActionEvent event) {
        determineResultsSortOrder();
        determineMCUSortOrder();
    }

    /**
     * Moves the selection to the next item for the payerAllTableView
     * @param event
     */
    @FXML
    void nextAllPayerButtonClick(ActionEvent event) {
        if (!payerAllTableView.getItems().isEmpty()) {
            payerAllTableView.getSelectionModel().selectNext();
            Payer payer = payerAllTableView.getSelectionModel().getSelectedItem();
            payerAllTableView.scrollTo(payer);
        }
        else{
            payerSearchTextFieldAction(event);
        }
    }

    /**
     * Moves the selection to the next item for the providerAllTableView
     * @param event
     */
    @FXML
    void nextAllProviderButtonClick(ActionEvent event) {
        if (!providerAllTableView.getItems().isEmpty()) {
            providerAllTableView.getSelectionModel().selectNext();
            Provider provider = providerAllTableView.getSelectionModel().getSelectedItem();
            providerAllTableView.scrollTo(provider);
        }
        else{
            providerSearchTextFieldAction(event);
        }
    }

    /**
     * Moves the selection to the next item for the payerClientTableView
     * @param event
     */
    @FXML
    void nextClientPayerButtonClick(ActionEvent event) {
        if (!payerClientTableView.getItems().isEmpty()) {
            payerClientTableView.getSelectionModel().selectNext();
            Payer payer = payerClientTableView.getSelectionModel().getSelectedItem();
            payerClientTableView.scrollTo(payer);
        }
        else{
            payerSearchTextFieldAction(event);
        }
    }

    /**
     * Moves the selection to the next item for the payerClientTableView
     * @param event
     */
    @FXML
    void nextClientProviderButtonClick(ActionEvent event) {
        if (!providerClientTableView.getItems().isEmpty()) {
            providerClientTableView.getSelectionModel().selectNext();
            Provider provider = providerClientTableView.getSelectionModel().getSelectedItem();
            providerClientTableView.scrollTo(provider);
        }
        else{
            providerSearchTextFieldAction(event);
        }

    }

    /**
     * This method advances the selection to the next row for the mcu table.
     * This method also ensures that the selection remains in view in the table.
     * @param event
     */
    @FXML
    void nextMCUButtonClick(ActionEvent event) {
        if (!mcuTableView.getItems().isEmpty()) {
            mcuTableView.getSelectionModel().selectNext();
            RemarkCodeMostUsedWith remarkSelection = mcuTableView.getSelectionModel().getSelectedItem();
            mcuTableView.scrollTo(remarkSelection);
            if(!mcuTableView.getSelectionModel().isEmpty()) {
                remarkMCUDescriptionTextArea.setText(prepareDescription(remarkSelection));
                notesTextArea.setText(prepareRemarkNotes(remarkSelection));
            }
        }
    }

    /**
     * This method advances the selection to the next row for the results table.
     * This method also ensures that the selection remains in view in the table.
     * This method also populates the mcuTableView with RemarkCodes pertaining to the new selection.
     * @param event
     */
    @FXML
    void nextResultButtonClick(ActionEvent event) {
        if (!resultsTableView.getItems().isEmpty()) {
            resultsTableView.getSelectionModel().selectNext();
            RemarkCodeResult remarkSelection = resultsTableView.getSelectionModel().getSelectedItem();
            resultsTableView.scrollTo(remarkSelection);

            HashMap<String, RemarkCode> mapToSearch = getMapToSearch();

            ObservableList<RemarkCodeMostUsedWith> relatedRemarkCodes
                    = RemarkCodeMostUsedWith.getRemarkCodesUsedWithPrimary(remarkSelection, mapToSearch);
            populateMCUTable(relatedRemarkCodes);
            determineMCUSortOrder();
            remarkResultsDescriptionTextArea.setText(prepareDescription(remarkSelection));
            notesTextArea.setText(prepareRemarkNotes(remarkSelection));
            remarkMCUDescriptionTextArea.clear();
        }
        else{
            remarkSearchTextFieldAction(event);
        }
    }

    /**
     * When the "Clear" button is clicked on the payer tab, this method clears the tableviews and the searchTextArea
     * on the same tab.
     * @param event
     */
    @FXML
    void payerClearButtonClick(ActionEvent event) {
        payerSearchTextField.clear();
        payerClientTableView.getItems().clear();
        payerAllTableView.getItems().clear();
    }

    /**
     * This method populates the "client" and "all" tables in the payer tab with payers relating to the search
     * criteria.
     * @param event
     */
    @FXML
    void payerSearchTextFieldAction(ActionEvent event) {
        String searchPhrase = payerSearchTextField.getText();
        if (!clientCombo.getSelectionModel().isEmpty()) {
            int clientID = clientCombo.getSelectionModel().getSelectedItem().getPrimaryKey();
            populatePayerClientTableView(Payer.searchPayerForClient(searchPhrase, clientID));
        }
        populatePayerAllTableView(Payer.searchPayerForAll(searchPhrase));
    }

    /**
     * Moves the selection to the previous item for the payerAllTableView
     * @param event
     */
    @FXML
    void previousAllPayerButtonClick(ActionEvent event) {
        if(!payerAllTableView.getItems().isEmpty()) {
            payerAllTableView.getSelectionModel().selectPrevious();
            Payer payer = payerAllTableView.getSelectionModel().getSelectedItem();
            payerAllTableView.scrollTo(payer);
        }
        else{
            payerSearchTextFieldAction(event);
        }
    }

    /**
     * Moves the selection to the previous item for the providerAllTableView
     * @param event
     */
    @FXML
    void previousAllProviderButtonClick(ActionEvent event) {
        if(!providerAllTableView.getItems().isEmpty()) {
            providerAllTableView.getSelectionModel().selectPrevious();
            Provider provider = providerAllTableView.getSelectionModel().getSelectedItem();
            providerAllTableView.scrollTo(provider);
        }
        else{
            providerSearchTextFieldAction(event);
        }
    }

    /**
     * Moves the selection to the previous item for the payerClientTableView
     * @param event
     */
    @FXML
    void previousClientPayerButtonClick(ActionEvent event) {
        if(!payerClientTableView.getItems().isEmpty()) {
            payerClientTableView.getSelectionModel().selectPrevious();
            Payer payer = payerClientTableView.getSelectionModel().getSelectedItem();
            payerClientTableView.scrollTo(payer);
        }
        else{
            payerSearchTextFieldAction(event);
        }
    }

    /**
     * Moves the selection to the previous item for the providerClientTableView
     * @param event
     */
    @FXML
    void previousClientProviderButtonClick(ActionEvent event) {
        if(!providerClientTableView.getItems().isEmpty()) {
            providerClientTableView.getSelectionModel().selectPrevious();
            Provider provider = providerClientTableView.getSelectionModel().getSelectedItem();
            providerClientTableView.scrollTo(provider);
        }
        else{
            providerSearchTextFieldAction(event);
        }
    }

    /**
     * This method the selection to the previous row for the mcu table.
     * This method also ensures that the selection remains in view in the table.
     * @param event
     */
    @FXML
    void previousMCUButtonClick(ActionEvent event) {
        if(!mcuTableView.getItems().isEmpty() || !mcuTableView.getSelectionModel().isEmpty()) {
            mcuTableView.getSelectionModel().selectPrevious();
            RemarkCodeMostUsedWith remarkSelection = mcuTableView.getSelectionModel().getSelectedItem();
            mcuTableView.scrollTo(remarkSelection);
            if(remarkSelection != null) {
                remarkMCUDescriptionTextArea.setText(prepareDescription(remarkSelection));
                notesTextArea.setText(prepareRemarkNotes(remarkSelection));
            }
        }
    }

    /**
     * This method the selection to the previous row for the results table.
     * This method also ensures that the selection remains in view in the table.
     * @param event
     */
    @FXML
    void previousResultButtonClick(ActionEvent event) {
        if(!resultsTableView.getItems().isEmpty() || !resultsTableView.getSelectionModel().isEmpty()) {
            resultsTableView.getSelectionModel().selectPrevious();
            RemarkCodeResult remarkSelection = resultsTableView.getSelectionModel().getSelectedItem();
            resultsTableView.scrollTo(remarkSelection);
            if(remarkSelection != null) {
                HashMap<String, RemarkCode> mapToSearch = getMapToSearch();

                ObservableList<RemarkCodeMostUsedWith> relatedRemarkCodes
                        = RemarkCodeMostUsedWith.getRemarkCodesUsedWithPrimary(remarkSelection, mapToSearch);
                populateMCUTable(relatedRemarkCodes);
                determineMCUSortOrder();
                remarkResultsDescriptionTextArea.setText(prepareDescription(remarkSelection));
                notesTextArea.setText(prepareRemarkNotes(remarkSelection));
                remarkMCUDescriptionTextArea.clear();
            }
        }
        else if(resultsTableView.getSelectionModel().isEmpty()){
            remarkSearchTextFieldAction(event);
        }
    }

    /**
     * When the "Clear" button is clicked on the provider tab, this method clears the tableviews and the searchTextArea
     * on the same tab.
     * @param event
     */
    @FXML
    void providerClearButtonClick(ActionEvent event) {
        providerSearchTextField.clear();
        providerClientTableView.getItems().clear();
        providerAllTableView.getItems().clear();
    }


    /**
     * This method populates the "client" and "all" tables in the provider tab with providers relating to the search
     * criteria.
     * @param event
     */
    @FXML
    void providerSearchTextFieldAction(ActionEvent event) {
        String searchPhrase = providerSearchTextField.getText();
        if (!clientCombo.getSelectionModel().isEmpty()) {
            int clientID = clientCombo.getSelectionModel().getSelectedItem().getPrimaryKey();
            populateProviderClientTableView(Provider.searchProviderForClient(searchPhrase, clientID));
        }
        populateProviderAllTableView(Provider.searchProviderForAll(searchPhrase));
    }

    /**
     * Clears the text in the search field for this tab and clears various display elements
     * @param event
     */
    @FXML
    void remarkClearButtonClick(ActionEvent event) {
        remarkSearchTextField.clear();
        resultsTableView.getItems().clear();
        mcuTableView.getItems().clear();
        remarkResultsDescriptionTextArea.clear();
        remarkMCUDescriptionTextArea.clear();
        notesTextArea.clear();
    }

    /**
     * When a user clicks on a row in the resultsTableView, the text area to the left populates with the description of their
     * selection.
     *
     * Then, the mcuTableView populates with results which correspond to the new resultsTableView selection.
     *
     * Additionally, if there are any notes associated with the RemarkCode, they are populated in the notesTextArea.
     * @param event
     */
    @FXML
    void remarkResultTableClick(MouseEvent event) {
        RemarkCodeResult remarkSelection = resultsTableView.getSelectionModel().getSelectedItem();
        if(remarkSelection != null){
            HashMap<String, RemarkCode> mapToSearch = getMapToSearch();

            ObservableList<RemarkCodeMostUsedWith> relatedRemarkCodes
                    = RemarkCodeMostUsedWith.getRemarkCodesUsedWithPrimary(remarkSelection, mapToSearch);
            populateMCUTable(relatedRemarkCodes);
            determineMCUSortOrder();
            mcuTableView.scrollTo(0);
            remarkResultsDescriptionTextArea.setText(prepareDescription(remarkSelection));
            notesTextArea.setText(prepareRemarkNotes(remarkSelection));
            remarkMCUDescriptionTextArea.clear();
        }
    }

    /**
     * This method is called whenever the user makes a search on the remark code tab.
     *
     * The results populate in the results table, the description text areas clear, and the results are sorted.
     * @param event
     */
    @FXML
    void remarkSearchTextFieldAction(ActionEvent event) {
        String searchPhrase = remarkSearchTextField.getText();
        ObservableList<RemarkCodeResult> searchResults = RemarkCodeResult.searchAll(searchPhrase, getMapToSearch());

        populateResultsTable(searchResults);
        mcuTableView.getItems().clear();
        determineResultsSortOrder();
        resultsTableView.scrollTo(0);
        remarkResultsDescriptionTextArea.clear();
        remarkMCUDescriptionTextArea.clear();
        notesTextArea.clear();
    }

    /**
     * This method will be called by all the add buttons in the column on the right of the RemarkTab.
     *
     * This is for the full buttons which interact with the results table, not the "2" buttons next to them.
     */
    @FXML
    void resultsCOAddButtonClick(ActionEvent event) throws SQLException {
        resultsAddButtonClick("CO");
    }

    /**
     * This method copies the selected remark code from the resultsTableView to the user's clipboard with the desired
     * prefix.
     *
     * Then it updates the phrase-use data in the tableView for the user's benefit.
     * @param event
     * @throws SQLException
     */
    @FXML
    void resultsCOCopyButtonClick(ActionEvent event) throws SQLException {
        resultsCopyButtonClick("CO");
    }

    /**
     * This method copies the selected remark code from the resultsTableView to the user's clipboard with the desired
     * prefix.
     *
     * Then it updates the phrase-use data in the tableView for the user's benefit.
     * @param event
     * @throws SQLException
     */
    @FXML
    void resultsOACopyButtonClick(ActionEvent event) throws SQLException {
        resultsCopyButtonClick("OA");
    }

    /**
     * This method will be called by all the add buttons in the column on the right of the RemarkTab.
     *
     * This is for the full buttons which interact with the results table, not the "2" buttons next to them.
     */
    @FXML
    void resultsPRAddButtonClick(ActionEvent event) throws SQLException {
        resultsAddButtonClick("PR");
    }

    /**
     * When any key is hit on the keyboard while the Results tableview is selected, the mcuTableView is populated
     * with the remarkCodes most used with the selection and all text areas on the Remark Code tab are refreshed.
     * @param event
     */
    @FXML
    void resultsUpOrDownArrowPress(KeyEvent event) {
        RemarkCodeResult remarkSelection = resultsTableView.getSelectionModel().getSelectedItem();
        HashMap<String, RemarkCode> mapToSearch = getMapToSearch();
        ObservableList<RemarkCodeMostUsedWith> relatedRemarkCodes
                = RemarkCodeMostUsedWith.getRemarkCodesUsedWithPrimary(remarkSelection, mapToSearch);
        populateMCUTable(relatedRemarkCodes);
        determineMCUSortOrder();
        remarkResultsDescriptionTextArea.setText(prepareDescription(remarkSelection));
        notesTextArea.setText(prepareRemarkNotes(remarkSelection));
        remarkMCUDescriptionTextArea.clear();
    }

    /**
     * This method copies the selected remark code from the resultsTableView to the user's clipboard with the desired
     * prefix.
     *
     * Then it updates the phrase-use data in the tableView for the user's benefit.
     * @param event
     * @throws SQLException
     */
    @FXML
    void resultsPRCopyButtonClick(ActionEvent event) throws SQLException {
        resultsCopyButtonClick("PR");
    }

    @FXML
    void settingsButtonClick(ActionEvent event) {

    }

    @FXML
    void suggestPayerComboButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/PayerSuggestion.fxml");
    }

    @FXML
    void suggestionButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/GeneralSuggestion.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int numberOfTips = TipOfTheDay.getMapOfAll().size();
        Random random = new Random();
        int indexOfTipToPopulate = random.nextInt(numberOfTips);
        ArrayList<TipOfTheDay> tipOfTheDayArrayList = new ArrayList<>();
        for(TipOfTheDay tipOfTheDay : TipOfTheDay.getMapOfAll().values()){
            tipOfTheDayArrayList.add(tipOfTheDay);
        }
        tipOfTheDayLabel.setText("TIP OF THE DAY: " + tipOfTheDayArrayList.get(indexOfTipToPopulate).getDescription());
        RemarkCodeResult.getPhrasesSearchedRecently().clear();
        clientCombo.setItems(Client.getObservableListOfAll());
        if(selectedClient != null) {
            clientCombo.getSelectionModel().select(selectedClient);
            payerClientNameLabel.setText(selectedClient.getName());
            providerClientNameLabel.setText(selectedClient.getName());
        }
        carcRARCRadioButton.setSelected(true);
        mostCommonRadioButton.setSelected(true);
    }

    /**
     * Populates the resultsTableView with RemarkCodeResults contained in the listToPopulate parameter.
     * @param listToPopulate
     */
    public void populateResultsTable(ObservableList<RemarkCodeResult> listToPopulate){
        resultsTableView.setItems(listToPopulate);
        resultsTotalColumn.setCellValueFactory(new PropertyValueFactory<>("totalTimesUsed"));
        resultsNumberColumn.setCellValueFactory(new PropertyValueFactory<>("timesUsedWithThisPhrase"));
        resultsCodeColumn.setCellValueFactory(new PropertyValueFactory<>("remarkCode"));
        resultsDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        resultsTagColumn.setCellValueFactory(new PropertyValueFactory<>("carcOrRarcString"));
        resultsPrefixColumn.setCellValueFactory(new PropertyValueFactory<>("codePrefix"));
        resultsSuffixColumn.setCellValueFactory(new PropertyValueFactory<>("codeSuffix"));
    }

    /**
     * Orders the Results table by the codes most commonly used with the search phrase.
     * Then by most total uses.
     * Then by whether they're CARCs or RARCs.
     * Then alphabetically by code prefix.
     * Then numerically by code suffix.
     */
    public void sortResultsTableByMCU(){
        resultsTableView.getSortOrder().clear();
        resultsNumberColumn.setSortType(TableColumn.SortType.DESCENDING);
        resultsTotalColumn.setSortType(TableColumn.SortType.DESCENDING);
        resultsTableView.getSortOrder().add(resultsNumberColumn);
        resultsTableView.getSortOrder().add(resultsTotalColumn);
        resultsTableView.getSortOrder().add(resultsTagColumn);
        resultsTableView.getSortOrder().add(resultsPrefixColumn);
        resultsTableView.getSortOrder().add(resultsSuffixColumn);
    }

    /**
     * Orders the Results table alphabetically by code prefix.
     * Then numerically by code suffix.
     */
    public void sortResultsTableAlphanumerically(){
        resultsTableView.getSortOrder().clear();
        resultsTableView.getSortOrder().add(resultsPrefixColumn);
        resultsTableView.getSortOrder().add(resultsSuffixColumn);
    }

    /**
     * Determines the sort order for the Results table based on the selected "Sort" Radio Button.
     */
    public void determineResultsSortOrder(){
        if(mostCommonRadioButton.isSelected()){
            sortResultsTableByMCU();
        }
        else if (alphanumericRadioButton.isSelected()){
            sortResultsTableAlphanumerically();
        }
    }

    /**
     * Populates the mcuTableView with RemarkCodeMostUsedWiths contained in the listToPopulate parameter.
     * @param listToPopulate
     */
    public void populateMCUTable(ObservableList<RemarkCodeMostUsedWith> listToPopulate){
        mcuTableView.setItems(listToPopulate);
        mcuTotalColumn.setCellValueFactory(new PropertyValueFactory<>("totalTimesUsed"));
        mcuNumberColumn.setCellValueFactory(new PropertyValueFactory<>("timesUsedWithPrimaryCode"));
        mcuCodeColumn.setCellValueFactory(new PropertyValueFactory<>("remarkCode"));
        mcuDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        mcuTagColumn.setCellValueFactory(new PropertyValueFactory<>("carcOrRarcString"));
        mcuPrefixColumn.setCellValueFactory(new PropertyValueFactory<>("codePrefix"));
        mcuSuffixColumn.setCellValueFactory(new PropertyValueFactory<>("codeSuffix"));
    }

    /**
     * Orders the Most Commonly Used with Your Selection table by the codes most commonly used with the search phrase.
     * Then by most total uses.
     * Then by whether they're CARCs or RARCs.
     * Then alphabetically by code prefix.
     * Then numerically by code suffix.
     */
    public void sortMCUTableByMCU(){
        mcuTableView.getSortOrder().clear();
        mcuNumberColumn.setSortType(TableColumn.SortType.DESCENDING);
        mcuTotalColumn.setSortType(TableColumn.SortType.DESCENDING);
        mcuTableView.getSortOrder().add(mcuNumberColumn);
        mcuTableView.getSortOrder().add(mcuTotalColumn);
        mcuTableView.getSortOrder().add(mcuTagColumn);
        mcuTableView.getSortOrder().add(mcuPrefixColumn);
        mcuTableView.getSortOrder().add(mcuSuffixColumn);
    }

    /**
     * Orders the Most Commonly Used with Your Selection table alphabetically by code prefix.
     * Then numerically by code suffix.
     */
    public void sortMCUTableAlphanumerically(){
        mcuTableView.getSortOrder().clear();
        mcuTableView.getSortOrder().add(mcuPrefixColumn);
        mcuTableView.getSortOrder().add(mcuSuffixColumn);
    }

    /**
     * Determines the sort order for the MCU table based on the selected "Sort" Radio Button.
     */
    public void determineMCUSortOrder(){
        if(mostCommonRadioButton.isSelected()){
            sortMCUTableByMCU();
        }
        else if (alphanumericRadioButton.isSelected()){
            sortMCUTableAlphanumerically();
        }
    }

    /**
     * This function evaluates the checkbox/radiobox selections on the Remark Codes tab and returns
     * a map containing only values which the user wishes to consider.
     *
     * @return HashMap<String, RemarkCode> which contains all RemarkCodes that the user wishes to consider.
     */
    public HashMap<String, RemarkCode> getMapToSearch(){
        HashMap<String, RemarkCode> mapToSearch = RemarkCode.getMapOfAll();
        if(!includeAlertsCheckBox.isSelected()){
            mapToSearch = RemarkCode.filterAlertsFromMap(mapToSearch);
        }
        if(justCARCsRadioButton.isSelected()){
            mapToSearch = RemarkCode.getOnlyCARCsFromMap(mapToSearch);
        }
        if(justRARCSRadioButton.isSelected()){
            mapToSearch = RemarkCode.getOnlyRARCsFromMap(mapToSearch);
        }
        return mapToSearch;
    }

    /**
     * Makes the description of a RemarkCode presentable for the user.
     * @param remarkCode
     * @return String preparedDescription.
     */
    public static String prepareDescription(RemarkCode remarkCode){
        String preparedDescription = remarkCode.getCarcOrRarcString() + ": "
                + remarkCode.getDescription();
        return preparedDescription;
    }

    /**
     * Makes the notes of a RemarkCode presentable for the user.
     * @param remarkCode
     * @return String preparedNotes.
     */
    public String prepareRemarkNotes(RemarkCode remarkCode) {
        if (remarkCode.getNotes() != null && !remarkCode.getNotes().isEmpty()) {
            String preparedNotes = remarkCode.getRemarkCode() + ": " + remarkCode.getNotes();
            return preparedNotes;
        }
        else {
            return "";
        }
    }

    /**
     * This method will be called by all the copy buttons next to the resultsTableView.
     *
     * It copies the remark code to the clipboard with the desired prefix and updates the Most Commonly Used With data
     * in the database/all relevant program data.
     *
     * @param prefix
     * @throws SQLException
     */
    public void resultsCopyButtonClick(String prefix) throws SQLException {
        if(!resultsTableView.getSelectionModel().isEmpty()) {
            String stringToCopy = "COPY ERROR";
            RemarkCodeResult selectedRemarkCode = resultsTableView.getSelectionModel().getSelectedItem();
            if (selectedRemarkCode.getCarcOrRarcString().toLowerCase().contains("CARC".toLowerCase())) {
                stringToCopy = prefix + selectedRemarkCode.getRemarkCode();
            } else if (selectedRemarkCode.getCarcOrRarcString().toLowerCase().contains("RARC".toLowerCase())) {
                stringToCopy = selectedRemarkCode.getRemarkCode();
            }
            copyToClipboard(stringToCopy);
            if(RemarkCodeResult.getPhrasesSearchedRecently().isEmpty()) {
                RemarkCodeResult.getPhrasesSearchedRecently().add(remarkSearchTextField.getText());
            }
            for (String searchPhrase : RemarkCodeResult.getPhrasesSearchedRecently()) {
                RemarkPhrase.updatePhraseData(searchPhrase, selectedRemarkCode);
            }
            selectedRemarkCode.setTimesUsedWithThisPhrase(selectedRemarkCode.getTimesUsedWithThisPhrase() + 1);
            resultsTableView.refresh();
            RemarkCodeResult.getPhrasesSearchedRecently().clear();
        }
        else{
            alertUser("No Remark Selected.");
        }
    }

    /**
     * This method will be called by all the add buttons in the column on the right of the RemarkTab.
     *
     * This is for the full buttons which interact with the results table, not the "2" buttons next to them.
     *
     * If the remark is already in the list, regardless of added prefix, the function will not add the remark code again.
     * @param prefix
     * @throws SQLException
     */
    public void resultsAddButtonClick(String prefix) throws SQLException {
        if(!resultsTableView.getSelectionModel().isEmpty()) {
            String stringToCopy = "COPY ERROR";
            RemarkCodeResult selectedRemarkCode = resultsTableView.getSelectionModel().getSelectedItem();
            if (selectedRemarkCode.getCarcOrRarcString().toLowerCase().contains("CARC".toLowerCase())) {
                stringToCopy = prefix + selectedRemarkCode.getRemarkCode();
            } else if (selectedRemarkCode.getCarcOrRarcString().toLowerCase().contains("RARC".toLowerCase())) {
                stringToCopy = selectedRemarkCode.getRemarkCode();
            }
            Boolean remarkAlreadyInListview = false;
            for(String remarkCodeString : codesListView.getItems()){
                String pureRemarkCode = remarkCodeString.replace("PR", "");
                pureRemarkCode = pureRemarkCode.replace("OA", "");
                pureRemarkCode = pureRemarkCode.replace("CO", "");
                if(pureRemarkCode.equals(selectedRemarkCode.getRemarkCode())){
                    remarkAlreadyInListview = true;
                }
            }
            if(!remarkAlreadyInListview){
                codesListView.getItems().add(stringToCopy);
            }
            if(RemarkCodeResult.getPhrasesSearchedRecently().isEmpty()) {
                RemarkCodeResult.getPhrasesSearchedRecently().add(remarkSearchTextField.getText());
            }
            for (String searchPhrase : RemarkCodeResult.getPhrasesSearchedRecently()) {
                RemarkPhrase.updatePhraseData(searchPhrase, selectedRemarkCode);
            }
            selectedRemarkCode.setTimesUsedWithThisPhrase(selectedRemarkCode.getTimesUsedWithThisPhrase() + 1);
            resultsTableView.refresh();
            RemarkCodeResult.getPhrasesSearchedRecently().clear();
        }
        else{
            alertUser("No Remark Selected.");
        }
    }

    /**
     * This method will be called by all the copy buttons next to the mcuTableView
     *
     * It copies the remark code to the clipboard with the desired prefix and updates the Most Commonly Used With data
     * in the database/all relevant program data.
     *
     * @param prefix
     * @throws SQLException
     */
    public void mcuCopyButtonClick(String prefix) throws SQLException {
        if(!mcuTableView.getSelectionModel().isEmpty()) {
            String stringToCopy = "COPY ERROR";
            RemarkCodeMostUsedWith selectedRemarkCode = mcuTableView.getSelectionModel().getSelectedItem();
            if (selectedRemarkCode.getCarcOrRarcString().toLowerCase().contains("CARC".toLowerCase())) {
                stringToCopy = prefix + selectedRemarkCode.getRemarkCode();
            } else if (selectedRemarkCode.getCarcOrRarcString().toLowerCase().contains("RARC".toLowerCase())) {
                stringToCopy = selectedRemarkCode.getRemarkCode();
            }
            copyToClipboard(stringToCopy);

            selectedRemarkCode.setTimesUsedWithPrimaryCode(selectedRemarkCode.getTimesUsedWithPrimaryCode() + 1);
            for (RemarkCodeResult remarkCodeResult : resultsTableView.getItems()) {
                if (remarkCodeResult.getRemarkCode().equals(selectedRemarkCode.getRemarkCode())) {
                    remarkCodeResult.setTotalTimesUsed(remarkCodeResult.getTotalTimesUsed() + 1);
                    break;
                }
            }

            RemarkCode resultSelection = resultsTableView.getSelectionModel().getSelectedItem();
            RemarkCode mcuSelection = mcuTableView.getSelectionModel().getSelectedItem();
            mcuSelection.updateMostUsedWithData(resultSelection);
            mcuTableView.refresh();
            resultsTableView.refresh();
        }
        else{
            alertUser("No Remark Selected.");
        }
    }

    /**
     * This function will be called when the mcuAdd buttons are clicked.
     *
     * It will copy the selected remark code from the mcuTableView to the codesListView.
     *
     * @param prefix
     * @throws SQLException
     */
    public void mcuAddButtonClick(String prefix) throws SQLException {
        if(!mcuTableView.getSelectionModel().isEmpty()) {
            String stringToCopy = "COPY ERROR";
            RemarkCodeMostUsedWith selectedRemarkCode = mcuTableView.getSelectionModel().getSelectedItem();
            if (selectedRemarkCode.getCarcOrRarcString().toLowerCase().contains("CARC".toLowerCase())) {
                stringToCopy = prefix + selectedRemarkCode.getRemarkCode();
            } else if (selectedRemarkCode.getCarcOrRarcString().toLowerCase().contains("RARC".toLowerCase())) {
                stringToCopy = selectedRemarkCode.getRemarkCode();
            }
            Boolean remarkAlreadyInListview = false;
            for (String remarkCodeString : codesListView.getItems()) {
                String pureRemarkCode = remarkCodeString.replace("PR", "");
                pureRemarkCode = pureRemarkCode.replace("OA", "");
                pureRemarkCode = pureRemarkCode.replace("CO", "");
                if (pureRemarkCode.equals(selectedRemarkCode.getRemarkCode())) {
                    remarkAlreadyInListview = true;
                }
            }
            if (!remarkAlreadyInListview) {
                codesListView.getItems().add(stringToCopy);
            }
        }
        else{
            alertUser("No Remark Selected.");
        }
    }

    /**
     * This method takes a list of remark code strings and increments their total use attributes in the mcuTableView.
     *
     * This method also increments the timesUsedWithPrimary attribute for the mcuTableView
     * @param listOfRemarkStrings
     */
    public void updateRemarkTableViewsWithList(ObservableList<String> listOfRemarkStrings){
        //The following doesn't get updated in the database.  May be best to leave it out so the user understands.
/*        for (String remarkCodeString : listOfRemarkStrings){
            for (RemarkCode remarkCode : mcuTableView.getItems()){
                if(remarkCode.getRemarkCode().equalsIgnoreCase(remarkCodeString)){
                    remarkCode.setTotalTimesUsed(remarkCode.getTotalTimesUsed() + 1);
                    break;
                }
            }
        }*/
        for (String remarkCodeString : listOfRemarkStrings){
            for (RemarkCodeMostUsedWith remarkCode : mcuTableView.getItems()){
                if(remarkCode.getRemarkCode().equalsIgnoreCase(remarkCodeString)){
                    remarkCode.setTimesUsedWithPrimaryCode(remarkCode.getTimesUsedWithPrimaryCode() + 1);
                    break;
                }
            }
        }
        //The following is updated when RemarkCodeResults are added to the codesListView, so it can be left out.
        /*
        for (String remarkCodeString : listOfRemarkStrings){
            for (RemarkCode remarkCode : resultsTableView.getItems()){
                if(remarkCode.getRemarkCode().equalsIgnoreCase(remarkCodeString)){
                    remarkCode.setTotalTimesUsed(remarkCode.getTotalTimesUsed() + 1);
                    break;
                }
            }
        }*/
        resultsTableView.refresh();
        mcuTableView.refresh();
    }

    /**
     * Populates the Client Specific table on the payer tab.
     *
     * Then sorts the data
     *
     * @param listToPopulate
     */
    public void populatePayerClientTableView(ObservableList<Payer> listToPopulate){
        payerClientTableView.setItems(listToPopulate);
        payerClientTableClientColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        payerClientDocNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameOnDocument"));
        payerClientEnterNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameToEnter"));
        payerClientTableView.getSortOrder().add(payerClientDocNameColumn);
        payerClientTableView.getSortOrder().add(payerClientEnterNameColumn);
    }

    /**
     * Populates the All Client table on the payer tab.
     *
     * Then sorts the data
     *
     * @param listToPopulate
     */
    public void populatePayerAllTableView(ObservableList<Payer> listToPopulate){
        payerAllTableView.setItems(listToPopulate);
        payerAllTableClientColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        payerAllDocNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameOnDocument"));
        payerAllEnterNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameToEnter"));
        payerAllTableView.getSortOrder().add(payerAllDocNameColumn);
        payerAllTableView.getSortOrder().add(payerAllEnterNameColumn);
    }

    /**
     * Populates the Client Specific table on the provider tab.
     *
     * Then sorts the data
     *
     * @param listToPopulate
     */
    public void populateProviderClientTableView(ObservableList<Provider> listToPopulate){
        providerClientTableView.setItems(listToPopulate);
        providerClientTableClientColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        providerClientDeptColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        providerClientFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        providerClientLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        providerClientNPIColumn.setCellValueFactory(new PropertyValueFactory<>("npi"));
        providerClientTableView.getSortOrder().add(providerClientDeptColumn);
        providerClientTableView.getSortOrder().add(providerClientLastNameColumn);
        providerClientTableView.getSortOrder().add(providerClientFirstNameColumn);
        
    }

    /**
     * Populates the All Client table on the provider tab.
     *
     * Then sorts the data
     *
     * @param listToPopulate
     */
    public void populateProviderAllTableView(ObservableList<Provider> listToPopulate){
        providerAllTableView.setItems(listToPopulate);
        providerAllTableClientColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        providerAllDeptColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        providerAllFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        providerAllLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        providerAllNPIColumn.setCellValueFactory(new PropertyValueFactory<>("npi"));
        providerAllTableView.getSortOrder().add(providerAllTableClientColumn);
        providerAllTableView.getSortOrder().add(providerAllDeptColumn);
        providerAllTableView.getSortOrder().add(providerAllLastNameColumn);
        providerAllTableView.getSortOrder().add(providerAllFirstNameColumn);
    }

}
