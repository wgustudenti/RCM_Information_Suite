package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.AdminLog;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class ViewAdminLogController extends InformationSuiteController implements Initializable {

    @FXML
    private Label timeZoneLabel;

    @FXML
    private TableColumn<AdminLog, String> actionColumn;

    @FXML
    private TableView<AdminLog> adminLogTableView;

    @FXML
    private TableColumn<AdminLog, Timestamp> timeColumn;

    @FXML
    private TableColumn<AdminLog, String> usernameColumn;

    @FXML
    void backButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/AdminMainMenu.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateAdminLogTableView(AdminLog.getObservableListOfAll());
        timeColumn.setSortType(TableColumn.SortType.DESCENDING);
        adminLogTableView.getSortOrder().add(timeColumn);

        String userTimeZone = ZoneId.systemDefault().toString();
        timeZoneLabel.setText(userTimeZone);
    }

    /**
     * Populates the information in the adminLogTableView.
     * @param listToPopulate
     */
    public void populateAdminLogTableView(ObservableList<AdminLog> listToPopulate){
        adminLogTableView.setItems(listToPopulate);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("fullDescription"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("actionTime"));
    }
}
