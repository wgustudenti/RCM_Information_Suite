package model;

import databasevendor.VendorTipOfTheDay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating TipOfTheDay Business Objects
 * TipOfTheDay business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class TipOfTheDay implements RCMBusinessObject {


    /**
     * The primary key for the object in the database.
     */
    private int primaryKey;

    /**
     * Description of the TipOfTheDay.
     */
    private String description;

    /**
     * HashMap of all TipOfTheDays
     */
    private static HashMap<Integer, TipOfTheDay> mapOfAll;

    /**
     * Constructor for the TipOfTheDay class.
     * @param primaryKey
     * @param description
     */
    public TipOfTheDay(String description, int primaryKey){
        this.primaryKey = primaryKey;
        this.description = description;
    }

    /**
     * Constructor for the TipOfTheDay class.
     * @param description
     */
    public TipOfTheDay(String description){
        this.primaryKey = 0;
        this.description = description;
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

    public static HashMap<Integer, TipOfTheDay> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, TipOfTheDay> mapOfAll) {
        TipOfTheDay.mapOfAll = mapOfAll;
    }

    /**
     * This method returns an alphabetically ordered list of all tipOfTheDays.
     * @return ObservableList<TipOfTheDay></TipOfTheDay> alphabetically ordered list of all tipOfTheDays.
     */
    public static ObservableList<TipOfTheDay> getObservableListOfAll(){
        ObservableList<TipOfTheDay> listOfTipsOfTheDay = FXCollections.observableArrayList();
        for(TipOfTheDay tipOfTheDay : mapOfAll.values()){
            listOfTipsOfTheDay.add(tipOfTheDay);
        }
        listOfTipsOfTheDay.sort(Comparator.comparing(TipOfTheDay::getDescription));
        return listOfTipsOfTheDay;
    }    

    /**Returns the string which can describe this object.
     *
     * @return String which should describe.
     */
    @Override
    public String toString(){
        return description;
    }
    
    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorTipOfTheDay.insert(this.description);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.description;
        int actionID = 16;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorTipOfTheDay.update(this.description, this.primaryKey);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.description;
        int actionID = 17;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorTipOfTheDay.delete(this.primaryKey);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.description;
        int actionID = 18;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, TipOfTheDay> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorTipOfTheDay.getAll();
        HashMap<Integer, TipOfTheDay> allTipOfTheDays = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            String description = wholeTable.getString("Description");
            TipOfTheDay tipOfTheDayToAdd = new TipOfTheDay(description, primaryKey);
            allTipOfTheDays.put(primaryKey, tipOfTheDayToAdd);
        }
        return allTipOfTheDays;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }
}
