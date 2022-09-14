package model;

import databasevendor.VendorSavedDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating SavedDatabase Business Objects
 * SavedDatabase business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class SavedDatabase implements RCMBusinessObject{

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    int primaryKey;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String nickname;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String location;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String vendor;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String username;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String password;

    /**
     * HashMap of all Clients
     */
    private static HashMap<Integer, SavedDatabase> mapOfAll;

    /**
     * Constructor for this class.
     * @param location
     * @param primaryKey
     */
    public SavedDatabase(String nickname, String location, String vendor, String username, String password, int primaryKey){
        this.nickname = nickname;
        this.location = location;
        this.vendor = vendor;
        this.username = username;
        this.password = password;
        this.primaryKey = primaryKey;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static HashMap<Integer, SavedDatabase> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, SavedDatabase> mapOfAll) {
        SavedDatabase.mapOfAll = mapOfAll;
    }


    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorSavedDatabase.insert(this.nickname, this.username, this.password, this.location, this.vendor);
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorSavedDatabase.update(this.nickname, this.username, this.password, this.location, this.vendor, this.primaryKey);
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorSavedDatabase.delete(this.primaryKey);
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, SavedDatabase> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorSavedDatabase.getAll();
        HashMap<Integer, SavedDatabase> allSavedDatabases = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            String nickname = wholeTable.getString("DatabaseNickname");
            String location = wholeTable.getString("DatabaseLocation");
            String vendor = wholeTable.getString("DatabaseVendor");
            String username = wholeTable.getString("DatabaseUsername");
            String password = wholeTable.getString("DatabasePassword");
            SavedDatabase savedDatabaseToAdd = new SavedDatabase(nickname, location, vendor, username, password, primaryKey);
            allSavedDatabases.put(primaryKey, savedDatabaseToAdd);
        }
        return allSavedDatabases;
    }
    
    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }
}
