package masterdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is not meant to be used directly, but to be a superclass to classes for SQL driven databases
 * for specific vendors.  For instance, the SQLiteConnection subclass will extend this class.
 *
 * The static varbiables will need to be redefined in each subclass for the methods to work properly.
 */
public abstract class SQLConnection {

    /**
     * Database Protocol
     */
    private static String protocol = "jdbc";

    /**
     * Database vendor
     */
    private static String vendor = "sqlite";

    /**
     * Database location
     */
    private static String location = "src/resources/database/RCMInformationSuiteTestDB";

    /**
     * Database name
     */
    private static String databaseName = "";

    /**
     * Database URl
     */
    private static String jdbcUrl = protocol + ":" + vendor + ":" + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL

    /**
     * Database Drive
     */
    private static String driver = "";

    /**
     * Username
     */
    private static String userName = "";

    /**
     * Password
     */
    private static String password = "";

    /**
     * Connection object
     */
    private static Connection connection;

    /**
     *
     * @return specified static variable.
     */
    public static String getProtocol(){
        return protocol;
    }

    /**
     * Changes the specified static variable to be equal to the parameter.
     * @param newProtocol
     */
    public static void setProtocol(String newProtocol){
        protocol = newProtocol;
    }

    /**
     *
     * @return specified static variable.
     */
    public static String getLocation() {
        return location;
    }

    /**
     * Changes the specified static variable to be equal to the parameter.
     * @param location
     */
    public static void setLocation(String location) {
        SQLConnection.location = location;
    }

    /**
     *
     * @return specified static variable.
     */
    public static String getDatabaseName() {
        return databaseName;
    }

    /**
     * Changes the specified static variable to be equal to the parameter.
     * @param databaseName
     */
    public static void setDatabaseName(String databaseName) {
        SQLConnection.databaseName = databaseName;
    }

    /**
     *
     * @return specified static variable.
     */
    public static String getJdbcUrl() {
        return jdbcUrl;
    }

    /**
     * Changes the specified static variable to be equal to the parameter.
     * @param jdbcUrl
     */
    public static void setJdbcUrl(String jdbcUrl) {
        SQLConnection.jdbcUrl = jdbcUrl;
    }

    /**
     *
     * @return specified static variable.
     */
    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        SQLConnection.driver = driver;
    }

    /**
     *
     * @return specified static variable.
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * Changes the specified static variable to be equal to the parameter.
     * @param userName
     */
    public static void setUserName(String userName) {
        SQLConnection.userName = userName;
    }

    /**
     *
     * @return specified static variable.
     */
    public static String getPassword() {
        return password;
    }

    /**
     * Changes the specified static variable to be equal to the parameter.
     * @param password
     */
    public static void setPassword(String password) {
        SQLConnection.password = password;
    }

    /**
     *
     * @return specified static variable.
     */
    public static String getVendor(){
        return vendor;
    }

    /**
     * Changes the specified static variable to be equal to the parameter.
     * @param newVendor
     */
    public static void setVendor(String newVendor){
        vendor = newVendor;
    }

    /**Returns a connection object
     *
     * @return connection object
     */
    public static Connection getConnection(){
        return connection;
    }

    /**
     * Changes the specified static variable to be equal to the parameter.
     * @param connection
     */
    public static void setConnection(Connection connection) {
        SQLConnection.connection = connection;
    }

    /**
     * Opens the connections to the database
     */
    public static void openConnection() throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //System.out.println("Error:" + e.getMessage());
        }
    }


    /**
     * Closes the connection
     */
    public static void closeConnection() {
        try {
            connection.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //System.out.println("Error:" + e.getMessage());
        }
    }
}
