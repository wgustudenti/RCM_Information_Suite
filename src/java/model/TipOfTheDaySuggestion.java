package model;

import databasevendor.VendorTipOfTheDaySuggestion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating TipOfTheDaySuggestion Business Objects
 * TipOfTheDaySuggestion business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class TipOfTheDaySuggestion extends TipOfTheDay implements RCMBusinessObject{

    /**
     * Time which the action occurred.  This will be ignored when the objected is inserted into a SQL database, as the
     * database will assign the action time.
     */
    Timestamp actionTime;

    /**
     * Map of all suggestion objects with their primaryKeys from the database as the keys.
     */
    static HashMap<Integer, TipOfTheDaySuggestion> mapOfAllSuggestions;

    /**
     * Constructor for the TipOfTheDay class.
     *
     * @param description
     * @param primaryKey
     * @param actionTime 
     */
    public TipOfTheDaySuggestion(String description, Timestamp actionTime, int primaryKey) {
        super(description, primaryKey);
        this.actionTime = actionTime;
    }

    /**
     * Constructor for the TipOfTheDay class.
     *
     * @param description
     */
    public TipOfTheDaySuggestion(String description) {
        super(description, 0);
        this.actionTime = Timestamp.valueOf(LocalDateTime.now());
    }

    public Timestamp getActionTime() {
        return actionTime;
    }

    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }

    public HashMap<Integer, TipOfTheDaySuggestion> getMapOfAllSuggestions() {
        return mapOfAllSuggestions;
    }

    public void setMapOfAllSuggestions(HashMap<Integer, TipOfTheDaySuggestion> mapOfAllSuggestions) {
        this.mapOfAllSuggestions = mapOfAllSuggestions;
    }

    /**
     * This method returns an alphabetically ordered list of all tipOfTheDaySuggestions.
     * @return ObservableList<TipOfTheDaySuggestion></TipOfTheDaySuggestion> alphabetically ordered list of all tipOfTheDaySuggestions.
     */
    public static ObservableList<TipOfTheDaySuggestion> getObservableListOfAllSuggestions(){
        ObservableList<TipOfTheDaySuggestion> listOfTipsOfTheDaySuggestion = FXCollections.observableArrayList();
        for(TipOfTheDaySuggestion tipOfTheDaySuggestion : mapOfAllSuggestions.values()){
            listOfTipsOfTheDaySuggestion.add(tipOfTheDaySuggestion);
        }
        listOfTipsOfTheDaySuggestion.sort(Comparator.comparing(TipOfTheDaySuggestion::getActionTime).reversed());
        return listOfTipsOfTheDaySuggestion;
    }

    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorTipOfTheDaySuggestion.insert(this.getDescription());
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorTipOfTheDaySuggestion.update(this.getDescription(), this.getPrimaryKey());
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorTipOfTheDaySuggestion.delete(this.getPrimaryKey());
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, TipOfTheDaySuggestion> getNewMapOfAllSuggestions() throws SQLException {
        ResultSet wholeTable = VendorTipOfTheDaySuggestion.getAll();
        HashMap<Integer, TipOfTheDaySuggestion> allTipOfTheDaySuggestions = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            String description = wholeTable.getString("Description");
            Timestamp utcActionTime = wholeTable.getTimestamp("ActionTime");
            Timestamp userActionTime = RCMBusinessObject.convertUTCtoUserTime(utcActionTime);
            TipOfTheDaySuggestion tipOfTheDaySuggestionToAdd = new TipOfTheDaySuggestion(description, userActionTime, primaryKey);
            allTipOfTheDaySuggestions.put(primaryKey, tipOfTheDaySuggestionToAdd);
        }
        return allTipOfTheDaySuggestions;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAllSuggestions = getNewMapOfAllSuggestions();
    }
}
