package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Settings;

import java.awt.Toolkit;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.Optional;

/**
 * This class provides the backing for all other controllers for this application
 * To lessen the number of lines of code which need to be written, this class provides many methods which will be used
 * over and over again throughout the development of the GUI.
 */
public abstract class InformationSuiteController {
    /**
     * Takes the parameters (Action event is a button click or other similar event) and navigates to the resource
     * specified in the scenePath String parameter.
     * @param event
     * @param scenePath
     * @throws IOException
     */
    public void changeScene(ActionEvent event, String scenePath) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource(scenePath));
        String desiredCSSPath = Settings.getMapOfAll().get(1).getCurrentCSSPath().getDescription();
        parent.getStylesheets().add(getClass().getResource(desiredCSSPath).toExternalForm());
        stage.setScene(new Scene(parent));
        stage.show();
    }

    /**
     * This gives a pop-up confirmation alert box for a deletion action, warning the user of consequences
     * and asking for confirmation.
     * @return true if user confirms delete with button click, else false.
     */
    public static boolean confirmDelete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you would like to delete this item? All associated items will also be " +
                "removed from the database.  This cannot be undone.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            return true;
        }
        return false;
    }

    /**
     * This gives a pop-up confirmation alert box for an update action, warning the user of consequences
     * and asking for confirmation.
     * @return true if user confirms update with button click, else false.
     */
    public static boolean confirmUpdate(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Update");
        alert.setContentText("Are you sure you would like to update this item? All associated items will also be " +
                "updated in the database.  This cannot be undone.");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            return true;
        }
        return false;
    }

    /**
     * This method will open an error alert box to appear with the parameter as the text.
     * @param alertText
     */
    public static void alertUser(String alertText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ALERT!");
        alert.setContentText(alertText);
        alert.show();
    }

    /**
     * This method will open an error alert box to appear with the parameters as the title and text, respectively.
     * @param alertText
     */
    public static void alertUser(String alertTitle, String alertText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertTitle);
        alert.setContentText(alertText);
        alert.show();
    }

    /**
     * This method will open a warning alert box to appear with the parameter as the text.
     * @param warningText
     */
    public static void warnUser(String warningText){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ALERT!");
        alert.setContentText(warningText);
        alert.show();
    }

    /**
     * This method will cause a warning alert box to appear with the parameters as the title and text, respectively.
     * @param warningText
     */
    public static void warnUser(String warningTitle, String warningText){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(warningTitle);
        alert.setContentText(warningText);
        alert.show();
    }

    /**
     * Checks if the string has an equal or lesser number of characters compared to the number specified
     * in the method name.  The nameOfField parameter is used to customize the warning to the specific field.
     * Warns the user.
     * @param stringToCheck
     * @param nameOfField
     * @return true if string is less than the specified number of characters, else false.
     */
    public static Boolean checkIfSixOrLessChar(String stringToCheck, String nameOfField){
        int maxCharacters = 6;
        if (stringToCheck.length() <= maxCharacters){
            return true;
        }
        else {
            alertUser(nameOfField + " field cannot be more than " + maxCharacters + " characters.");
            return false;
        }
    }

    /**
     * Checks if the string has an equal or lesser number of characters compared to the number specified
     * in the method name.  The nameOfField parameter is used to customize the warning to the specific field.
     * Warns the user.
     * @param stringToCheck
     * @param nameOfField
     * @return true if string is less than the specified number of characters, else false.
     */
    public static Boolean checkIfTenOrLessChar(String stringToCheck, String nameOfField){
        int maxCharacters = 10;
        if (stringToCheck.length() <= maxCharacters){
            return true;
        }
        else {
            alertUser(nameOfField + " field cannot be more than " + maxCharacters + " characters.");
            return false;
        }
    }

    /**
     * Checks if the string has an equal or lesser number of characters compared to the number specified
     * in the method name.  The nameOfField parameter is used to customize the warning to the specific field.
     * Warns the user.
     * @param stringToCheck
     * @param nameOfField
     * @return true if string is less than the specified number of characters, else false.
     */
    public static Boolean checkIfFifteenOrLessChar(String stringToCheck, String nameOfField){
        int maxCharacters = 10;
        if (stringToCheck.length() <= maxCharacters){
            return true;
        }
        else {
            alertUser(nameOfField + " field cannot be more than " + maxCharacters + " characters.");
            return false;
        }
    }

    /**
     * Checks if the string has an equal or lesser number of characters compared to the number specified
     * in the method name.  The nameOfField parameter is used to customize the warning to the specific field.
     * Warns the user.
     * @param stringToCheck
     * @param nameOfField
     * @return true if string is less than the specified number of characters, else false.
     */
    public static Boolean checkIfFiftyOrLessChar(String stringToCheck, String nameOfField){
        int maxCharacters = 50;
        if (stringToCheck.length() <= maxCharacters){
            return true;
        }
        else {
            alertUser(nameOfField + " field cannot be more than " + maxCharacters + " characters.");
            return false;
        }
    }

    /**
     * Checks if the string has an equal or lesser number of characters compared to the number specified
     * in the method name.  The nameOfField parameter is used to customize the warning to the specific field.
     * Warns the user.
     * @param stringToCheck
     * @param nameOfField
     * @return true if string is less than the specified number of characters, else false.
     */
    public static Boolean checkIfHundredOrLessChar(String stringToCheck, String nameOfField){
        int maxCharacters = 100;
        if (stringToCheck.length() <= maxCharacters){
            return true;
        }
        else {
            alertUser(nameOfField + " field cannot be more than " + maxCharacters + " characters.");
            return false;
        }
    }

    /**
     * Checks if the string has an equal or lesser number of characters compared to the number specified
     * in the method name.  The nameOfField parameter is used to customize the warning to the specific field.
     * Warns the user.
     * @param stringToCheck
     * @param nameOfField
     * @return true if string is less than the specified number of characters, else false.
     */
    public static Boolean checkIfHundredSixtyOrLessChar(String stringToCheck, String nameOfField){
        int maxCharacters = 160;
        if (stringToCheck.length() <= maxCharacters){
            return true;
        }
        else {
            alertUser(nameOfField + " field cannot be more than " + maxCharacters + " characters.");
            return false;
        }
    }

    /**
     * Checks if the string has an equal or lesser number of characters compared to the number specified
     * in the method name.  The nameOfField parameter is used to customize the warning to the specific field.
     * Warns the user.
     * @param stringToCheck
     * @param nameOfField
     * @return true if string is less than the specified number of characters, else false.
     */
    public static Boolean checkIfFiveHundredOrLessChar(String stringToCheck, String nameOfField){
        int maxCharacters = 500;
        if (stringToCheck.length() <= maxCharacters){
            return true;
        }
        else {
            alertUser(nameOfField + " field cannot be more than " + maxCharacters + " characters.");
            return false;
        }
    }

    /**
     * Checks if the string has an equal or lesser number of characters compared to the number specified
     * in the method name.  The nameOfField parameter is used to customize the warning to the specific field.
     * Warns the user.
     * @param stringToCheck
     * @param nameOfField
     * @return true if string is less than the specified number of characters, else false.
     */
    public static Boolean checkIfTenThousandOrLessChar(String stringToCheck, String nameOfField){
        int maxCharacters = 10000;
        if (stringToCheck.length() <= maxCharacters){
            return true;
        }
        else {
            alertUser(nameOfField + " field cannot be more than " + maxCharacters + " characters.");
            return false;
        }
    }

    /**
     * Checks if the string is null or has length of 0.
     * Warns the user.
     * The nameOfField parameter is used to customize the warning to the specific field.
     * @param stringToCheck
     * @param nameOfField
     * @return true if string is less than or equal to zero characters, else false.
     */
    public static Boolean checkIfFieldIsNotEmpty(String stringToCheck, String nameOfField) {
        int lengthOfEmptyString = 0;
        if (stringToCheck.length() > lengthOfEmptyString) {
            return true;
        } else {
            alertUser(nameOfField + " field cannot be empty.");
            return false;
        }
    }

    /**
     * Returns true if a comboBox has a selection, else false (and warns user).
     *
     * @param comboBox
     * @param nameOfComboBox
     * @return Boolean true if a comboBox has a selection, else false
     */
    public static Boolean checkIfComboIsNotEmpty(ComboBox comboBox, String nameOfComboBox){
        if(!comboBox.getSelectionModel().isEmpty()){
            return true;
        }
        else{
            alertUser(nameOfComboBox + " must have selection.");
            return false;
        }
    }

    /**
     * This method copies the parameter string to the user's clipboard.
     * @param stringToCopy
     */
    public static void copyToClipboard(String stringToCopy){
        StringSelection cliboardString = new StringSelection(stringToCopy);
        Clipboard userClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        userClipboard.setContents(cliboardString, null);
    }
}
