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
public class AddTipOfTheDayController extends InformationSuiteController implements Initializable {

    @FXML
    private TextArea tipTextArea;

    public TextArea getTipTextArea() {
        return tipTextArea;
    }

    public void setTipTextArea(TextArea tipTextArea) {
        this.tipTextArea = tipTextArea;
    }

    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        changeScene(event, "/view/ViewTipsOfTheDay.fxml");
    }

    /**
     * Checks that all fields have valid data and creates a new tipOfTheDay
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void submitButtonClick(ActionEvent event) throws IOException, SQLException {
        if (checkIfFieldIsNotEmpty(tipTextArea.getText(), "Tip")) {
            if (checkIfHundredSixtyOrLessChar(tipTextArea.getText(), "Tip")) {
                TipOfTheDay tipOfTheDay = new TipOfTheDay(tipTextArea.getText());
                tipOfTheDay.insert();
                MasterMethod.initializeAllStaticData();
                changeScene(event, "/view/ViewTipsOfTheDay.fxml");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
