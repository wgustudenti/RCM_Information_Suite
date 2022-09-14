package model;

import databasevendor.VendorGeneralSuggestion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating GeneralSuggestion Business Objects
 * GeneralSuggestion business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class GeneralSuggestion implements RCMBusinessObject{

    /**
     * The primary key for the object in the database.
     */
    private int primaryKey;

    /**
     * Represents the corresponding attribute in the database.
     */
    private int categoryID;

    /**
     * Represents the corresponding attribute in the database.
     */
    private String description;

    /**
     *
     */
    private Timestamp actionTime;

    /**
     * HashMap of all GeneralSuggestions
     */
    private static HashMap<Integer, GeneralSuggestion> mapOfAll;

    /**
     * Constructor for the GeneralSuggestion class.
     * @param primaryKey
     * @param actionTime
     * @param description
     * @param categoryID
     */
    public GeneralSuggestion(String description, Timestamp actionTime, int categoryID, int primaryKey){
        this.primaryKey = primaryKey;
        this.description = description;
        this.actionTime = actionTime;
        this.categoryID = categoryID;
    }

    /**
     * Constructor for the GeneralSuggestion class.
     * @param description
     * @param categoryID
     */
    public GeneralSuggestion(String description, int categoryID){
        this.primaryKey = 0;
        this.description = description;
        this.actionTime = Timestamp.valueOf(LocalDateTime.now());
        this.categoryID = categoryID;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public Timestamp getActionTime() {
        return actionTime;
    }

    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }

    public static HashMap<Integer, GeneralSuggestion> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, GeneralSuggestion> mapOfAll) {
        GeneralSuggestion.mapOfAll = mapOfAll;
    }

    /**
     * This method returns an alphabetically ordered list of all GeneralSuggestions.
     * @return ObservableList<GeneralSuggestion></GeneralSuggestion> alphabetically ordered list of all GeneralSuggestions.
     */
    public static ObservableList<GeneralSuggestion> getObservableListOfAll(){
        ObservableList<GeneralSuggestion> listOfGeneralSuggestions = FXCollections.observableArrayList();
        for(GeneralSuggestion GeneralSuggestion : mapOfAll.values()){
            listOfGeneralSuggestions.add(GeneralSuggestion);
        }
        listOfGeneralSuggestions.sort(Comparator.comparing(GeneralSuggestion::getActionTime).reversed());
        return listOfGeneralSuggestions;
    }

    /**
     * Returns a String representing the description of the category associated with this suggestion
     *
     * @return String representing the description of the category associated with this suggestion
     */
    public String getCategoryDescription(){
        return SuggestionCategory.getMapOfAll().get(this.categoryID).getDescription();
    }
    
    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorGeneralSuggestion.insert(this.description, this.categoryID);
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorGeneralSuggestion.update(this.description, this.categoryID, this.primaryKey);
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorGeneralSuggestion.delete(this.primaryKey);
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, GeneralSuggestion> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorGeneralSuggestion.getAll();
        HashMap<Integer, GeneralSuggestion> allGeneralSuggestions = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            int categoryID = wholeTable.getInt("CategoryID");
            String description = wholeTable.getString("Description");
            Timestamp utcActionTime = wholeTable.getTimestamp("ActionTime");
            Timestamp userActionTime = RCMBusinessObject.convertUTCtoUserTime(utcActionTime);
            GeneralSuggestion generalSuggestionToAdd = new GeneralSuggestion(description, userActionTime, categoryID,
                    primaryKey);
            allGeneralSuggestions.put(primaryKey, generalSuggestionToAdd);
        }
        return allGeneralSuggestions;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }
}
