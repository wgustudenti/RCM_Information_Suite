package model;

import databasevendor.VendorFontStyle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating FontStyle Business Objects
 * FontStyle business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class FontStyle implements RCMBusinessObject{


    /**
     * The primary key for the object in the database.
     */
    private int primaryKey;

    /**
     * Description of the Font Style.
     */
    private String description;

    /**
     * HashMap of all FontStyles
     */
    private static HashMap<Integer, FontStyle> mapOfAll;

    /**
     * Constructor for the FontStyle class.
     * @param primaryKey
     * @param description
     */
    public FontStyle(String description, int primaryKey){
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

    public static HashMap<Integer, FontStyle> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, FontStyle> mapOfAll) {
        FontStyle.mapOfAll = mapOfAll;
    }

    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorFontStyle.insert(this.description);
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorFontStyle.update(this.description, this.primaryKey);
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorFontStyle.delete(this.primaryKey);
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, FontStyle> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorFontStyle.getAll();
        HashMap<Integer, FontStyle> allFontStyles = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            String description = wholeTable.getString("Description");
            FontStyle fontStyleToAdd = new FontStyle(description, primaryKey);
            allFontStyles.put(primaryKey, fontStyleToAdd);
        }
        return allFontStyles;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }
}
