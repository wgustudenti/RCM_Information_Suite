package main;

import databasevendor.VendorConnection;
import databasevendor.VendorTable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;
import sqldata.SQLRemarkCode;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Main method which runs when this application runs.
 */
public class Main extends Application {

    /**
     * Method which is called when the application is run and which initializes the GUI and the Data from the database.
     * @param stage
     * @throws IOException
     * @throws SQLException
     */
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        testDatabaseMethods();
        MasterMethod.initializeAllStaticData();/*
        MasterMethod.consistentlyUpdateData();*/
        Parent parent = FXMLLoader.load(getClass().getResource("/view/InformationSearch.fxml"));
        String desiredCSSPath = Settings.getMapOfAll().get(1).getCurrentCSSPath().getDescription();
        parent.getStylesheets().add(getClass().getResource(desiredCSSPath).toExternalForm());
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::stopTimer);
        stage.setTitle("RCM Information Suite");
        stage.show();
        System.out.println("Success!");
    }

    /**
     * Print function which is easier to use for testing purposes.
     * @param thingToPrint
     */
    public void print(Object thingToPrint){
        System.out.println(thingToPrint);
    }

    /**
     * Throw methods in here to test them or to run them as the program starts up.
     * @throws SQLException
     */
    public void testDatabaseMethods() throws SQLException {/*
        VendorConnection.openConnection();
        SQLTable.createEmbeddedTablesWithTestData();*/
    }

    /**
     * Stops all tasks on the timer - needed because otherwise, the application continues to run after
     * the user has closed it.
     * @param event
     */
    private void stopTimer(WindowEvent event){
        RCMTimer.getTimer().cancel();
    }

    public static void main(String[] args) {
        launch();
    }
}