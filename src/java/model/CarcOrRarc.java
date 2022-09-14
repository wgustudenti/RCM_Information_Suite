package model;

import databasevendor.VendorCarcOrRarc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating CarcOrRarc Business Objects
 * CarcOrRarc business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class CarcOrRarc implements RCMBusinessObject {

    /**
     * The primary key for the object in the database.
     */
    private int primaryKey;

    /**
     * Description of the action.
     */
    private String description;

    /**
     * HashMap of all CarcOrRarc
     */
    private static HashMap<Integer, CarcOrRarc> mapOfAll;

    /**
     * Constructor for the CarcOrRarc class.
     * @param primaryKey
     * @param description
     */
    public CarcOrRarc(String description, int primaryKey){
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

    public static HashMap<Integer, CarcOrRarc> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, CarcOrRarc> mapOfAll) {
        CarcOrRarc.mapOfAll = mapOfAll;
    }
    
    /**
     * This method returns an alphabetically ordered list of all carcOrRarcs.
     * @return ObservableList<CarcOrRarc></CarcOrRarc> alphabetically ordered list of all carcOrRarcs.
     */
    public static ObservableList<CarcOrRarc> getObservableListOfAll(){
        ObservableList<CarcOrRarc> listOfCarcOrRarcs = FXCollections.observableArrayList();
        for(CarcOrRarc carcOrRarc : mapOfAll.values()){
            listOfCarcOrRarcs.add(carcOrRarc);
        }
        listOfCarcOrRarcs.sort(Comparator.comparing(CarcOrRarc::getDescription));
        return listOfCarcOrRarcs;
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
        VendorCarcOrRarc.insert(this.description);
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorCarcOrRarc.update(this.description, this.primaryKey);
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorCarcOrRarc.delete(this.primaryKey);
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, CarcOrRarc> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorCarcOrRarc.getAll();
        HashMap<Integer, CarcOrRarc> allCarcOrRarcs = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            String description = wholeTable.getString("Description");
            CarcOrRarc carcOrRarcToAdd = new CarcOrRarc(description, primaryKey);
            allCarcOrRarcs.put(primaryKey, carcOrRarcToAdd);
        }
        return allCarcOrRarcs;
    }


    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }

}
