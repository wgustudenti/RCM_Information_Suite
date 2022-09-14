package model;


import databasevendor.VendorAdminLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;

/**
 * This class represents the admin log with individual objects representing individual admin log events.
 */
public class AdminLog implements RCMBusinessObject {

    /**
     * Represents the primary key for the corresponding table in the database.
     */
    private int primaryKey;

    /**
     * Represents the ActionID information from the corresponding table in the database.
     */
    private int actionID;

    /**
     * Represents the Username information from the corresponding table in the database.
     */
    private String username;

    /**
     * Represents the Detail information from the corresponding table in the database.
     */
    private String detail;

    /**
     * Represents the time which the action occured.  By the time it's assigned, it should be translated from UTC to
     * the user's timezone.
     */
    private Timestamp actionTime;

    /**
     * HashMap of all AdminLogs
     */
    private static HashMap<Integer, AdminLog> mapOfAll;
    
    /**
     * This method returns an alphabetically ordered list of all adminLogs.
     * @return ObservableList<AdminLog></AdminLog> alphabetically ordered list of all adminLogs.
     */
    public static ObservableList<AdminLog> getObservableListOfAll(){
        ObservableList<AdminLog> listOfAdminLogs = FXCollections.observableArrayList();
        for(AdminLog adminLog : mapOfAll.values()){
            listOfAdminLogs.add(adminLog);
        }
        listOfAdminLogs.sort(Comparator.comparing(AdminLog::getActionTime).reversed());
        return listOfAdminLogs;
    }

    /**
     * Constructor for this class.
     * @param username
     * @param detail
     * @param actionTime
     * @param actionID
     * @param primaryKey
     */
    public AdminLog(String username, String detail, Timestamp actionTime, int actionID, int primaryKey){
        this.username = username;
        this.detail = detail;
        this.actionTime = actionTime;
        this.actionID = actionID;
        this.primaryKey = primaryKey;
    }

    /**
     * Constructor for this class.
     * @param username
     * @param detail
     * @param actionID
     */
    public AdminLog(String username, String detail, int actionID){
        this.username = username;
        this.detail = detail;
        this.actionTime = Timestamp.valueOf(LocalDateTime.now());
        this.actionID = actionID;
        this.primaryKey = 0;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public int getActionID() {
        return actionID;
    }

    public void setActionID(int actionID) {
        this.actionID = actionID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Timestamp getActionTime() {
        return actionTime;
    }

    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }

    public static HashMap<Integer, AdminLog> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, AdminLog> mapOfAll) {
        AdminLog.mapOfAll = mapOfAll;
    }

    /**
     * Returns a string representing the description associated with the actionID for this object.
     * @return String actionDescription
     */
    public String getActionDescription(){
        return AdminAction.getMapOfAll().get(this.actionID).getDescription();
    }

    /**
     * Returns a string representing the description associated with the actionID for this object plus the details.
     * @return String fullActionDescription
     */
    public String getFullDescription(){
        return this.getActionDescription() + ": " + this.getDetail();
    }

    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorAdminLog.insert(this.username, this.actionID, this.detail);
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
     VendorAdminLog.update(this.username, this.actionID, this.detail, this.primaryKey);
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorAdminLog.delete(this.primaryKey);
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, AdminLog> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorAdminLog.getAll();
        HashMap<Integer, AdminLog> allAdminLogs = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            int actionID = wholeTable.getInt("ActionID");
            String username = wholeTable.getString("Username");
            String detail = wholeTable.getString("Detail");
            Timestamp utcActionTime = wholeTable.getTimestamp("ActionTime");
            Timestamp userActionTime = RCMBusinessObject.convertUTCtoUserTime(utcActionTime);
            AdminLog adminLogToAdd = new AdminLog(username, detail, userActionTime, actionID, primaryKey);
            allAdminLogs.put(primaryKey, adminLogToAdd);
        }
        return allAdminLogs;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }
}
