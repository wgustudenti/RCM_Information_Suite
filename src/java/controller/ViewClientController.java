package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import main.MasterMethod;
import model.Client;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class ViewClientController extends InformationSuiteController implements Initializable {

    @FXML
    private ListView<Client> clientListView;

    @FXML
    void addButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AddClient.fxml");
    }

    @FXML
    void backButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AdminMainMenu.fxml");
    }

    @FXML
    void editButtonClick(ActionEvent event) throws IOException {
        if(!clientListView.getSelectionModel().isEmpty()) {
            Client selectedClient = clientListView.getSelectionModel().getSelectedItem();
            AdminInformationSearchController.setSelectedClient(selectedClient);
            changeScene(event, "/view/EditClient.fxml");
        }
        else{
            alertUser("No Client Selected.");
        }
    }

    @FXML
    void removeButtonClick(ActionEvent event) throws SQLException {
        if(!clientListView.getSelectionModel().isEmpty()) {
            if(confirmDelete()) {
                Client selectedClient = clientListView.getSelectionModel().getSelectedItem();
                selectedClient.delete();
                MasterMethod.initializeAllStaticData();
                clientListView.setItems(Client.getObservableListOfAll());
            }
        }
        else{
            alertUser("No Client Selected.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientListView.setItems(Client.getObservableListOfAll());
    }
}
