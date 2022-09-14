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
public class EditAndAddTipOfTheDayController extends InformationSuiteController implements Initializable {

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
        changeScene(event, "/view/ViewTipOfTheDaySuggestion.fxml");
    }

    @FXML
    void submitButtonClick(ActionEvent event) throws IOException, SQLException {
        if(checkIfFieldIsNotEmpty(getTipTextArea().getText(), "Tip")) {
            if (checkIfHundredSixtyOrLessChar(tipTextArea.getText(), "Tip")) {
                TipOfTheDay tipOfTheDay = new TipOfTheDay(getTipTextArea().getText());
                tipOfTheDay.insert();
                ViewTipOfTheDaySuggestionController.getSelectedSuggestion().delete();
                MasterMethod.initializeAllStaticData();
                changeScene(event, "/view/ViewTipOfTheDaySuggestion.fxml");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getTipTextArea().setText(ViewTipOfTheDaySuggestionController.getSelectedSuggestion().getDescription());
    }
}
