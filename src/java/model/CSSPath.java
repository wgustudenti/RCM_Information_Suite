package model;

import databasevendor.VendorCSSPath;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating CSSPath Business Objects
 * CSSPath business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class CSSPath implements RCMBusinessObject{

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private int primaryKey;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String description;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String nickname;

    /**
     * HashMap of all Clients
     */
    private static HashMap<Integer, CSSPath> mapOfAll;

    /**
     * Constructor for this class.
     * @param description
     * @param primaryKey
     */
    public CSSPath(String nickname, String description, int primaryKey){
        this.nickname = nickname;
        this.description = description;
        this.primaryKey = primaryKey;
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

    public static HashMap<Integer, CSSPath> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, CSSPath> mapOfAll) {
        CSSPath.mapOfAll = mapOfAll;
    }


    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorCSSPath.insert(this.description, this.nickname);
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorCSSPath.update(this.description, this.nickname, this.primaryKey);
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorCSSPath.delete(this.primaryKey);
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, CSSPath> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorCSSPath.getAll();
        HashMap<Integer, CSSPath> allCSSPaths = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            String nickname = wholeTable.getString("Nickname");
            String description = wholeTable.getString("Description");
            CSSPath cssPathToAdd = new CSSPath(nickname, description, primaryKey);
            allCSSPaths.put(primaryKey, cssPathToAdd);
        }
        return allCSSPaths;
    }
    
    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }
}
