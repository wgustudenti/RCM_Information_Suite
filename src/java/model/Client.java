package model;

import databasevendor.VendorClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating Client Business Objects
 * Client business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class Client implements RCMBusinessObject{

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private int primaryKey;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String name;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String departmentNotes;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String specialtyNotes;

    /**
     * HashMap of all Clients
     */
    private static HashMap<Integer, Client> mapOfAll;

    /**
     * Constructor for the client class.
     * @param name
     * @param departmentNotes
     * @param specialtyNotes
     * @param primaryKey
     */
    public Client (String name, String departmentNotes, String specialtyNotes, int primaryKey){
        this.name = name;
        this.departmentNotes = departmentNotes;
        this.specialtyNotes = specialtyNotes;
        this.primaryKey = primaryKey;
    }

    /**
     * Constructor for the client class.
     * @param name
     */
    public Client (String name){
        this.name = name;
        this.departmentNotes = "";
        this.specialtyNotes = "";
        this.primaryKey = 0;
    }

    /**
     * Constructor for the client class.
     * @param name
     * @param primaryKey
     */
    public Client (String name, int primaryKey){
        this.name = name;
        this.departmentNotes = "";
        this.specialtyNotes = "";
        this.primaryKey = primaryKey;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentNotes() {
        return departmentNotes;
    }

    public void setDepartmentNotes(String departmentNotes) {
        this.departmentNotes = departmentNotes;
    }

    public String getSpecialtyNotes() {
        return specialtyNotes;
    }

    public void setSpecialtyNotes(String specialtyNotes) {
        this.specialtyNotes = specialtyNotes;
    }

    public static HashMap<Integer, Client> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, Client> mapOfAll) {
        Client.mapOfAll = mapOfAll;
    }

    /**
     * This method returns an alphabetically ordered list of all clients.
     * @return ObservableList<Client></Client> alphabetically ordered list of all clients.
     */
    public static ObservableList<Client> getObservableListOfAll(){
        ObservableList<Client> listOfClients = FXCollections.observableArrayList();
        for(Client client : mapOfAll.values()){
            listOfClients.add(client);
        }
        listOfClients.sort(Comparator.comparing(Client::getName));
        return listOfClients;
    }

    /**Returns the string which can describe this object.
     *
     * @return String which should describe.
     */
    @Override
    public String toString(){
        return name;
    }

    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorClient.insert(this.name);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.name;
        int actionID = 1;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorClient.update(this.name, this.departmentNotes, this.specialtyNotes, this.primaryKey);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.name;
        int actionID = 2;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorClient.delete(this.primaryKey);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.name;
        int actionID = 3;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, Client> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorClient.getAll();
        HashMap<Integer, Client> allClients = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            String name = wholeTable.getString("Name");
            String departmentNotes = wholeTable.getString("DepartmentNotes");
            String specialtyNotes = wholeTable.getString("SpecialtyNotes");
            Client clientToAdd = new Client(name, departmentNotes, specialtyNotes, primaryKey);
            allClients.put(primaryKey, clientToAdd);
        }
        return allClients;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }
}
