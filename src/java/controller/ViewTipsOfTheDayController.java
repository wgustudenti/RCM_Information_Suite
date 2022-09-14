package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import main.MasterMethod;
import model.TipOfTheDay;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class ViewTipsOfTheDayController extends InformationSuiteController implements Initializable {

    @FXML
    private ListView<TipOfTheDay> tipListView;

    private static TipOfTheDay selectedTip;

    public static TipOfTheDay getSelectedTip() {
        return selectedTip;
    }

    public static void setSelectedTip(TipOfTheDay selectedTip) {
        ViewTipsOfTheDayController.selectedTip = selectedTip;
    }


    @FXML
    void addButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AddTipOfTheDay.fxml");
    }

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AdminMainMenu.fxml");
    }

    @FXML
    void editButtonClick(ActionEvent event) throws SQLException, IOException {
        if(!tipListView.getSelectionModel().isEmpty()){
            selectedTip = tipListView.getSelectionModel().getSelectedItem();
            changeScene(event, "/view/EditTipOfTheDay.fxml");
        }
        else{
            alertUser("No Tip Selected.");
        }
    }

    @FXML
    void removeButtonClick(ActionEvent event) throws SQLException {
        if(!tipListView.getSelectionModel().isEmpty()){
            if(confirmDelete()){
                tipListView.getSelectionModel().getSelectedItem().delete();
                MasterMethod.initializeAllStaticData();
                tipListView.setItems(TipOfTheDay.getObservableListOfAll());
            }
        }
        else{
            alertUser("No Tip Selected.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipListView.setItems(TipOfTheDay.getObservableListOfAll());
    }
}
