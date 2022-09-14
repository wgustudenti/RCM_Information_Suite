package main;

import controller.InformationSearchController;
import databasevendor.VendorConnection;
import masterdata.SQLConnection;
import model.*;
import sqlitedata.SQLiteConnection;

import java.sql.SQLException;
import java.util.TimerTask;

/**
 * This class contains methods which are meant to be called directly by the "Main" class.
 */
public abstract class MasterMethod {

    /**
     * Initializes the data needed for the user's settings, including to which database they would ultimately
     * like the rest of their information to come from/be stored in.
     * @throws SQLException
     */
    public static void initializeEmbeddedData() throws SQLException {
        SQLiteConnection.openConnection();
        AdminAction.initilizeStaticVariables();
        FontSize.initilizeStaticVariables();
        FontStyle.initilizeStaticVariables();
        SavedDatabase.initilizeStaticVariables();
        Settings.initilizeStaticVariables();
        SQLiteConnection.closeConnection();
    }

    /**
     * Assigns the desired values to the variables which are necessary to open a connection to the user's desired
     * database.
     */
    public static void assignDesiredDatabase(){
        for(Settings loadout : Settings.getMapOfAll().values()) {
            SavedDatabase desiredDatabase = loadout.getCurrentSavedDatabase();
            SQLConnection.setVendor(desiredDatabase.getVendor());
            SQLConnection.setLocation(desiredDatabase.getLocation());
            SQLConnection.setUserName(desiredDatabase.getUsername());
            SQLConnection.setPassword(desiredDatabase.getUsername());
            break;
        }
    }

    /**
     * Initializes all data which should come from the user's desired database.
     * @throws SQLException
     */
    public static void initializeExternalData() throws SQLException {
        try {
            VendorConnection.openConnection();
            RemarkCode.initilizeStaticVariables();
            AdminUser.initilizeStaticVariables();
            AdminLog.initilizeStaticVariables();
            CarcOrRarc.initilizeStaticVariables();
            Client.initilizeStaticVariables();
            CSSPath.initilizeStaticVariables();
            Department.initilizeStaticVariables();
            GeneralSuggestion.initilizeStaticVariables();
            Payer.initilizeStaticVariables();
            PayerSuggestion.initilizeStaticVariables();
            Provider.initilizeStaticVariables();
            RemarkPhrase.initilizeStaticVariables();
            SuggestionCategory.initilizeStaticVariables();
            TipOfTheDay.initilizeStaticVariables();
            TipOfTheDaySuggestion.initilizeStaticVariables();
        }
        catch (SQLException sqlException){
            controller.InformationSuiteController.alertUser("There was a problem connecting to the database.");
        }
    }

    /**
     * Initializes all data necessary to start the application.
     * Can also be used to refresh the underlying data (non-GUI data) in the application.
     *
     * Refreshes the selectedClient variable because if this method is called after the user has already selected a client
     * from the combo box, the combo box will still look like it has selected the right client, but it's actually a different
     * object (the old object, as opposed to the new, refreshed object) and doesn't work properly with the search functions.
     ********We don't need to do this with any other "selected" variables from the controllers because all other GUIs require a
     ********new selection before they load anyway.
     *
     * @throws SQLException
     */
    public static void initializeAllStaticData() throws SQLException {
        initializeEmbeddedData();
        assignDesiredDatabase();
        initializeExternalData();
        if(InformationSearchController.getSelectedClient() != null) {
            int selectedClientIndex = InformationSearchController.getSelectedClient().getPrimaryKey();
            Client refreshedClient = Client.getMapOfAll().get(selectedClientIndex);
            if(refreshedClient != null) {
                InformationSearchController.setSelectedClient(Client.getMapOfAll().get(selectedClientIndex));
            }
        }
    }

    /**
     * This method will set a timer to reinitialize all static data from the database every set amount of time.
     * After the data is reinitialized, the timer will call this method again so as to keep updating every set amount
     * of time perpetually.
     */
    public static void consistentlyUpdateData() {
        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    initializeAllStaticData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                consistentlyUpdateData();
            }
        };
        long fiveMinutesInMilliseconds = 30000;
        RCMTimer.getTimer().schedule(task, fiveMinutesInMilliseconds);
    }
}
