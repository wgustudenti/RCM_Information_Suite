package model;

import databasevendor.VendorSettings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating Settings Business Objects
 * Settings business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class Settings implements RCMBusinessObject{

    /**
     * The primary key for the object in the database.
     */
    private int primaryKey;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private int tableFontStyleID;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private int tableFontSizeID;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private int databaseID;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private int cssPathID;

    /**
     * HashMap of all Settings
     */
    private static HashMap<Integer, Settings> mapOfAll;

    /**
     * Constructor for the Settings class.
     * @param tableFontSizeID
     * @param tableFontStyleID
     * @param cssPathID
     * @param databaseID
     * @param primaryKey
     */
    public Settings(int tableFontSizeID, int tableFontStyleID, int cssPathID, int databaseID, int primaryKey){
        this.primaryKey = primaryKey;
        this.tableFontSizeID = tableFontSizeID;
        this.tableFontStyleID = tableFontStyleID;
        this.cssPathID = cssPathID;
        this.databaseID = databaseID;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public int getTableFontStyleID() {
        return tableFontStyleID;
    }

    public void setTableFontStyleID(int tableFontStyleID) {
        this.tableFontStyleID = tableFontStyleID;
    }

    public int getTableFontSizeID() {
        return tableFontSizeID;
    }

    public void setTableFontSizeID(int tableFontSizeID) {
        this.tableFontSizeID = tableFontSizeID;
    }

    public int getDatabaseID() {
        return databaseID;
    }

    public void setDatabaseID(int databaseID) {
        this.databaseID = databaseID;
    }

    public int getCssPathID() {
        return cssPathID;
    }

    public void setCssPathID(int cssPathID) {
        this.cssPathID = cssPathID;
    }

    public static HashMap<Integer, Settings> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, Settings> mapOfAll) {
        Settings.mapOfAll = mapOfAll;
    }

    /**
     * Returns the desired CSSPath for the user's chosen settings loadout.
     *
     * Choosing a different loadout is a feature that can be implemented in the future.
     * @return CSSPath in user's settings.
     */
    public CSSPath getCurrentCSSPath(){
        return CSSPath.getMapOfAll().get(this.cssPathID);
    }
    
    /**
     * Returns the desired SavedDatabase for the user's chosen settings loadout.
     *
     * Choosing a different loadout is a feature that can be implemented in the future.
     * @return SavedDatabase in user's settings.
     */
    public SavedDatabase getCurrentSavedDatabase(){
        return SavedDatabase.getMapOfAll().get(this.databaseID);
    }
    
    /**
     * Returns the desired FontSize for the user's chosen settings loadout.
     *
     * Choosing a different loadout is a feature that can be implemented in the future.
     * @return FontSize in user's settings.
     */
    public FontSize getCurrentTableFontSize(){
        return FontSize.getMapOfAll().get(this.tableFontSizeID);
    }
    
    /**
     * Returns the desired FontStyle for the user's chosen settings loadout.
     *
     * Choosing a different loadout is a feature that can be implemented in the future.
     * @return FontStyle in user's settings.
     */
    public FontStyle getCurrentTableFontStyle(){
        return FontStyle.getMapOfAll().get(this.tableFontStyleID);
    }

    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorSettings.insert(this.tableFontSizeID, this.tableFontStyleID, this.cssPathID, this.databaseID);
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorSettings.update(this.tableFontSizeID, this.tableFontStyleID, this.cssPathID, this.databaseID, this.primaryKey);
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorSettings.delete(this.primaryKey);
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, Settings> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorSettings.getAll();
        HashMap<Integer, Settings> allSettings = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            int tableFontSizeID = wholeTable.getInt("TableFontSizeID");
            int tableFontStyleID = wholeTable.getInt("TableFontStyleID");
            int cssPathID = wholeTable.getInt("CSSPathID");
            int databaseID = wholeTable.getInt("DatabaseID");

            Settings settingsToAdd = new Settings(tableFontSizeID, tableFontStyleID, cssPathID, databaseID, primaryKey);
            allSettings.put(primaryKey, settingsToAdd);
        }
        return allSettings;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }
}
