package model;

import databasevendor.VendorDepartment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating CSSPath Business Objects
 * CSSPath business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class Department implements RCMBusinessObject {

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private int primaryKey;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private int clientID;

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private String description;

    /**
     * HashMap of all Departments
     */
    private static HashMap<Integer, Department> mapOfAll;

    /**
     * Constructor for this class.
     * @param description
     * @param clientID
     * @param primaryKey
     */
    public Department(String description, int clientID, int primaryKey){
        this.description = description;
        this.clientID = clientID;
        this.primaryKey = primaryKey;
    }

    /**
     * Constructor for this class.
     * @param description
     * @param clientID
     */
    public Department(String description, int clientID){
        this.description = description;
        this.clientID = clientID;
        this.primaryKey = 0;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static HashMap<Integer, Department> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, Department> mapOfAll) {
        Department.mapOfAll = mapOfAll;
    }

    /**
     * This method returns an alphabetically ordered list of all departments.
     * @return ObservableList<Department></Department> alphabetically ordered list of all departments.
     */
    public static ObservableList<Department> getObservableListOfAll(){
        ObservableList<Department> listOfDepartments = FXCollections.observableArrayList();
        for(Department department : mapOfAll.values()){
            listOfDepartments.add(department);
        }
        listOfDepartments.sort(Comparator.comparing(Department::getDescription));
        return listOfDepartments;
    }
    
    /**
     * This method returns an alphabetically ordered list of all departments.
     * @return ObservableList<Department></Department> alphabetically ordered list of all departments for the given client.
     */
    public static ObservableList<Department> getObservableListOfAllForClient(int clientID){
        ObservableList<Department> listOfDepartments = FXCollections.observableArrayList();
        for(Department department : mapOfAll.values()){
            if(department.getClientID() == clientID) {
                listOfDepartments.add(department);
            }
        }
        listOfDepartments.sort(Comparator.comparing(Department::getDescription));
        return listOfDepartments;
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
        VendorDepartment.insert(this.description, this.clientID);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.description;
        int actionID = 4;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorDepartment.update(this.description, this.clientID, this.primaryKey);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.description;
        int actionID = 5;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorDepartment.delete(this.primaryKey);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.description;
        int actionID = 6;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, Department> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorDepartment.getAll();
        HashMap<Integer, Department> allDepartments = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            int clientID = wholeTable.getInt("ClientID");
            String description = wholeTable.getString("Description");
            Department departmentToAdd = new Department(description, clientID, primaryKey);
            allDepartments.put(primaryKey, departmentToAdd);
        }
        return allDepartments;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }
}
