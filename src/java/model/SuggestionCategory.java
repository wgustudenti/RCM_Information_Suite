package model;

import databasevendor.VendorSuggestionCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating Settings Business Objects
 * Settings business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class SuggestionCategory implements RCMBusinessObject {

    /**
     * The primary key for the object in the database.
     */
    private int primaryKey;

    /**
     * Description of the Suggestion Category.
     */
    private String description;

    /**
     * HashMap of all SuggestionCategories
     */
    private static HashMap<Integer, SuggestionCategory> mapOfAll;

    /**
     * Constructor for the SuggestionCategory class.
     * @param primaryKey
     * @param description
     */
    public SuggestionCategory(String description, int primaryKey){
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

    public static HashMap<Integer, SuggestionCategory> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, SuggestionCategory> mapOfAll) {
        SuggestionCategory.mapOfAll = mapOfAll;
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
        VendorSuggestionCategory.insert(this.description);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.description;
        int actionID = 19;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorSuggestionCategory.update(this.description, this.primaryKey);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.description;
        int actionID = 20;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorSuggestionCategory.delete(this.primaryKey);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.description;
        int actionID = 21;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, SuggestionCategory> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorSuggestionCategory.getAll();
        HashMap<Integer, SuggestionCategory> allSuggestionCategories = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            String description = wholeTable.getString("Description");
            SuggestionCategory suggestionCategoryToAdd = new SuggestionCategory(description, primaryKey);
            allSuggestionCategories.put(primaryKey, suggestionCategoryToAdd);
        }
        return allSuggestionCategories;
    }

    public static ObservableList<SuggestionCategory> getObservableListOfAll(){
        ObservableList<SuggestionCategory> listOfCategories = FXCollections.observableArrayList();
        for (SuggestionCategory suggestionCategory : getMapOfAll().values()){
            listOfCategories.add(suggestionCategory);
        }
        listOfCategories.sort(Comparator.comparing(SuggestionCategory::getDescription));
        return listOfCategories;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }
}
