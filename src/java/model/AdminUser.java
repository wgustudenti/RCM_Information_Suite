package model;


import databasevendor.VendorAdminUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating Admin User Business Objects
 * Admin User business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class AdminUser implements RCMBusinessObject {

    /**
     * Username for an administrator.
     */
    private String username;

    /**
     * Password fo ran administrator.
     */
    private String password;

    /**
     * HashMap of all AdminActions
     */
    private static HashMap<String, AdminUser> mapOfAll;

    /**
     * Represents the current admin user.
     */
    private static AdminUser currentUser;

    /**
     * Constructor for the AdminUser class.
     * @param username
     * @param password
     */
    public AdminUser(String username, String password){
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static HashMap<String, AdminUser> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<String, AdminUser> mapOfAll) {
        AdminUser.mapOfAll = mapOfAll;
    }

    public static AdminUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(AdminUser currentUser) {
        AdminUser.currentUser = currentUser;
    }

    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorAdminUser.insert(this.username, this.password);
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorAdminUser.update(this.username, this.password);
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorAdminUser.delete(this.username);
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<String, AdminUser> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorAdminUser.getAll();
        HashMap<String, AdminUser> allAdminUsers = new HashMap<>();
        while (wholeTable.next()){
            String username = wholeTable.getString("Username");
            String password = wholeTable.getString("Password");
            AdminUser adminUserToAdd = new AdminUser(username, password);
            allAdminUsers.put(username, adminUserToAdd);
        }
        return allAdminUsers;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }
}
