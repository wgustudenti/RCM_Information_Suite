package model;

import databasevendor.VendorAdminAction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating Admin Action Business Objects
 * Admin Action business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class AdminAction implements RCMBusinessObject {

    /**
     * The primary key for the object in the database.
     */
    private int primaryKey;

    /**
     * Description of the action.
     */
    private String description;

    /**
     * HashMap of all AdminActions
     */
    private static HashMap<Integer, AdminAction> mapOfAll;

    /**
     * Constructor for the AdminAction class.
     * @param primaryKey
     * @param description
     */
    public AdminAction(String description, int primaryKey){
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

    public static HashMap<Integer, AdminAction> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, AdminAction> mapOfAll) {
        AdminAction.mapOfAll = mapOfAll;
    }

    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorAdminAction.insert(this.description);
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorAdminAction.update(this.description, this.primaryKey);
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorAdminAction.delete(this.primaryKey);
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, AdminAction> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorAdminAction.getAll();
        HashMap<Integer, AdminAction> allAdminActions = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            String description = wholeTable.getString("Description");
            AdminAction adminActionToAdd = new AdminAction(description, primaryKey);
            allAdminActions.put(primaryKey, adminActionToAdd);
        }
        return allAdminActions;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }
}
