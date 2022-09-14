package model;

import databasevendor.VendorFontSize;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating FontSize Business Objects
 * FontSize business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class FontSize implements RCMBusinessObject{


    /**
     * The primary key for the object in the database.
     */
    private int primaryKey;

    /**
     * Description of the Font Size.
     */
    private String description;

    /**
     * HashMap of all FontSizes
     */
    private static HashMap<Integer, FontSize> mapOfAll;

    /**
     * Constructor for the FontSize class.
     * @param primaryKey
     * @param description
     */
    public FontSize(String description, int primaryKey){
        this.primaryKey = primaryKey;
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

    public static HashMap<Integer, FontSize> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, FontSize> mapOfAll) {
        FontSize.mapOfAll = mapOfAll;
    }

    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorFontSize.insert(this.description);
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorFontSize.update(this.description, this.primaryKey);
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorFontSize.delete(this.primaryKey);
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, FontSize> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorFontSize.getAll();
        HashMap<Integer, FontSize> allFontSizes = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            String description = wholeTable.getString("Description");
            FontSize fontSizeToAdd = new FontSize(description, primaryKey);
            allFontSizes.put(primaryKey, fontSizeToAdd);
        }
        return allFontSizes;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }
}
