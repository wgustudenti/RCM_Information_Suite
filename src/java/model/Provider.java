package model;

import databasevendor.VendorProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Provider implements RCMBusinessObject{

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private int primaryKey;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String firstName;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String lastName;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String npi;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private int departmentID;

    /**
     * HashMap of all Providers
     */
    private static HashMap<Integer, Provider> mapOfAll;

    /**
     * Constructor for this class
     * @param firstName
     * @param lastName
     * @param npi
     * @param departmentID
     * @param primaryKey
     */
    public Provider(String firstName, String lastName, String npi, int departmentID, int primaryKey){
        this.firstName = firstName;
        this.lastName = lastName;
        this.npi = npi;
        this.departmentID = departmentID;
        this.primaryKey = primaryKey;
    }

    /**
     * Constructor for this class
     * @param firstName
     * @param lastName
     * @param npi
     * @param departmentID
     */
    public Provider(String firstName, String lastName, String npi, int departmentID){
        this.firstName = firstName;
        this.lastName = lastName;
        this.npi = npi;
        this.departmentID = departmentID;
        this.primaryKey = 0;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNpi() {
        return npi;
    }

    public void setNpi(String npi) {
        this.npi = npi;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public static HashMap<Integer, Provider> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, Provider> mapOfAll) {
        Provider.mapOfAll = mapOfAll;
    }

    /**
     * This method uses the departmentID attribute to access the Department mapOfAll and return the corresponding
     * department name.
     *
     * @return String representing department name.
     */
    public String getDepartmentName(){
        return Department.getMapOfAll().get(this.departmentID).getDescription();
    }

    /**
     * Returns the clientID associated with the department associated with this provider.
     * @return int clientID
     */
    public int getClientID(){
        return Department.getMapOfAll().get(this.departmentID).getClientID();
    }

    /**
     * Returns the clientName associated with the department associated with this provider.
     * @return String clientName
     */
    public String getClientName(){
        int clientID = Department.getMapOfAll().get(this.departmentID).getClientID();
        return Client.getMapOfAll().get(clientID).getName();
    }

    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorProvider.insert(this.firstName, this.lastName, this.npi, this.departmentID);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.npi;
        int actionID = 7;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorProvider.update(this.firstName, this.lastName, this.npi, this.departmentID, this.primaryKey);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.npi;
        int actionID = 8;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorProvider.delete(this.primaryKey);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.npi;
        int actionID = 9;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, Provider> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorProvider.getAll();
        HashMap<Integer, Provider> allProviders = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            int departmentID = wholeTable.getInt("DepartmentID");
            String firstName = wholeTable.getString("FirstName");
            String lastName = wholeTable.getString("LastName");
            String npi = wholeTable.getString("NPI");
            Provider ProviderToAdd = new Provider(firstName, lastName, npi, departmentID, primaryKey);
            allProviders.put(primaryKey, ProviderToAdd);
        }
        return allProviders;
    }

    /**
     * This method returns Provider objects with firstName, lastName, or npi attributes that contain the searchPhrase
     * parameter.
     * @param searchPhrase
     * @return ObservableList of Provider objects with firstName, lastName, or npi attributes that contain the
     * searchPhrase parameter.
     */
    public static ObservableList<Provider> searchProviderForAll(String searchPhrase){
        searchPhrase = searchPhrase.toLowerCase();
        ObservableList<Provider> resultsOfSearch = FXCollections.observableArrayList();
        for(Provider provider : mapOfAll.values()){
            if (provider.firstName.toLowerCase().contains(searchPhrase)){
                resultsOfSearch.add(provider);
            }
            else if (provider.lastName.toLowerCase().contains(searchPhrase)){
                resultsOfSearch.add(provider);
            }
            else if (provider.npi.toLowerCase().contains(searchPhrase)){
                resultsOfSearch.add(provider);
            }
            else if (provider.getDepartmentName().toLowerCase().contains(searchPhrase)){
                resultsOfSearch.add(provider);
            }
        }
        return resultsOfSearch;
    }

    /**
     * This method returns Provider objects with firstName, lastName, or npi attributes that contain the searchPhrase
     * and are related to the clientID parameter.
     * @param searchPhrase
     * @return ObservableList of Provider objects with firstName, lastName, or npi attributes that contain the searchPhrase parameter
     * and are related to the clientID parameter.
     */
    public static ObservableList<Provider> searchProviderForClient(String searchPhrase, int clientID){
        searchPhrase = searchPhrase.toLowerCase();
        ObservableList<Provider> resultsOfSearch = FXCollections.observableArrayList();
        for(Provider provider : mapOfAll.values()){
            if(provider.getClientID() == clientID) {
                if (provider.firstName.toLowerCase().contains(searchPhrase)) {
                    resultsOfSearch.add(provider);
                }
                else if (provider.lastName.toLowerCase().contains(searchPhrase)) {
                        resultsOfSearch.add(provider);
                }
                else if (provider.npi.toLowerCase().contains(searchPhrase)) {
                        resultsOfSearch.add(provider);
                }
                else if (provider.getDepartmentName().toLowerCase().contains(searchPhrase)){
                    resultsOfSearch.add(provider);
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
