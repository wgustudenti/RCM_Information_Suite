package model;

import databasevendor.VendorPayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating Payer Business Objects
 * Payer business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class Payer implements RCMBusinessObject{


    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private int primaryKey;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String nameOnDocument;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String nameToEnter;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private int clientID;

    /**
     * HashMap of all Payers
     */
    private static HashMap<Integer, Payer> mapOfAll;

    /**
     * Constructor for this class
     * @param nameOnDocument
     * @param nameToEnter
     * @param clientID
     * @param primaryKey
     */
    public Payer(String nameOnDocument, String nameToEnter, int clientID, int primaryKey){
        this.nameOnDocument = nameOnDocument;
        this.nameToEnter = nameToEnter;
        this.clientID = clientID;
        this.primaryKey = primaryKey;
    }

    /**
     * Constructor for this class
     * @param nameOnDocument
     * @param nameToEnter
     * @param clientID
     */
    public Payer(String nameOnDocument, String nameToEnter, int clientID){
        this.nameOnDocument = nameOnDocument;
        this.nameToEnter = nameToEnter;
        this.clientID = clientID;
        this.primaryKey = 0;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }


    public String getNameOnDocument() {
        return nameOnDocument;
    }

    public void setNameOnDocument(String nameOnDocument) {
        this.nameOnDocument = nameOnDocument;
    }

    public String getNameToEnter() {
        return nameToEnter;
    }

    public void setNameToEnter(String nameToEnter) {
        this.nameToEnter = nameToEnter;
    }

    public static HashMap<Integer, Payer> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, Payer> mapOfAll) {
        Payer.mapOfAll = mapOfAll;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    /**
     * This method returns the name of the client associated with this object's clientID attribute.
     * @return String nameOfClient
     */
    public String getClientName(){
        return Client.getMapOfAll().get(this.clientID).getName();
    }

    /**
     * Inserts an object into the database and creates a log of the action.
     *
     * Then updates the log.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorPayer.insert(this.nameOnDocument, this.nameToEnter, this.clientID);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.nameOnDocument + " - " + this.nameToEnter;
        int actionID = 10;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Updates an object in the database and creates a log of the action.
     *
     * Then updates the log.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorPayer.update(this.nameOnDocument, this.nameToEnter, this.clientID, this.primaryKey);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.nameOnDocument + " - " + this.nameToEnter;
        int actionID = 11;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();

    }

    /**
     * Deletes an object in the database and creates a log of the action.
     *
     * Then updates the log.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorPayer.delete(this.primaryKey);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.nameOnDocument + " - " + this.nameToEnter;
        int actionID = 12;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Inserts the object into the database relating to every client to which this object is not already related.
     *
     * Then updates the log.
     * @throws SQLException
     */
    public void insertForAllClients() throws SQLException {
        VendorPayer.insertForAllClients(nameOnDocument, nameToEnter);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.nameOnDocument + " - " + this.nameToEnter;
        int actionID = 22;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Updates the object into the database relating to every client to which this object is related.
     *
     * Then updates the log.
     * @throws SQLException
     */
    public void updateForAllClients(String oldNameOnDocument) throws SQLException {
        VendorPayer.updateForAllClients(oldNameOnDocument, nameOnDocument, nameToEnter);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.nameOnDocument + " - " + this.nameToEnter;
        int actionID = 23;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, Payer> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorPayer.getAll();
        HashMap<Integer, Payer> allPayers = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            int clientID = wholeTable.getInt("ClientID");
            String nameOnDocument = wholeTable.getString("NameOnDocument");
            String nameToEnter = wholeTable.getString("NameToEnter");
            Payer payerToAdd = new Payer(nameOnDocument, nameToEnter, clientID, primaryKey);
            allPayers.put(primaryKey, payerToAdd);
        }
        return allPayers;
    }

    /**
     * This method returns Payer objects with nameOnDocument attributes that contain the searchPhrase parameter.
     * @param searchPhrase
     * @return ObservableList of Payer objects with nameOnDocument attributes that contain the searchPhrase parameter.
     */
    public static ObservableList<Payer> searchPayerForAll(String searchPhrase){
        searchPhrase = searchPhrase.toLowerCase();
        ObservableList<Payer> resultsOfSearch = FXCollections.observableArrayList();
        for(Payer payer : mapOfAll.values()){
            if (payer.nameOnDocument.toLowerCase().contains(searchPhrase)){
                resultsOfSearch.add(payer);
            }
        }
        return resultsOfSearch;
    }

    /**
     * This method returns Payer objects with nameOnDocument attributes that contain the searchPhrase parameter
     * and are related to the clientID parameter.
     * @param searchPhrase
     * @return ObservableList of Payer objects with nameOnDocument attributes that contain the searchPhrase parameter
     * and are related to the clientID parameter.
     */
    public static ObservableList<Payer> searchPayerForClient(String searchPhrase, int clientID){
        searchPhrase = searchPhrase.toLowerCase();
        ObservableList<Payer> resultsOfSearch = FXCollections.observableArrayList();
        for(Payer payer : mapOfAll.values()){
            if (payer.nameOnDocument.toLowerCase().contains(searchPhrase)){
                if(payer.clientID == clientID){
                    resultsOfSearch.add(payer);
                }
            }
        }
        return resultsOfSearch;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }
}
