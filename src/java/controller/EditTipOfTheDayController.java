package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import main.MasterMethod;
import model.TipOfTheDay;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class EditTipOfTheDayController extends InformationSuiteController implements Initializable {

    @FXML
    private TextArea tipTextArea;

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/ViewTipsOfTheDay.fxml");
    }

    /**
     * Checks that all fields have valid data and updates the tipOfTheDay
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void submitButtonClick(ActionEvent event) throws IOException, SQLException {
        if(checkIfFieldIsNotEmpty(tipTextArea.getText(), "Tip")) {
            TipOfTheDay tipOfTheDay = new TipOfTheDay(tipTextArea.getText(), ViewTipsOfTheDayController.getSelectedTip().getPrimaryKey());
            tipOfTheDay.update();
            MasterMethod.initializeAllStaticData();
            changeScene(event, "/view/ViewTipsOfTheDay.fxml");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipTextArea.setText(ViewTipsOfTheDayController.getSelectedTip().getDescription());
    }
}
