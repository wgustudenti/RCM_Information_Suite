package sqldata;

import masterdata.SQLGeneral;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is not meant to be used directly, but to be a superclass for classes SQL driven databases
 * for specific vendors.
 *
 * The subclasses to this class interact with a database to retrieve information from the table specified
 * by the tableName variable (and the name of this specific .java file).
 */
public abstract class SQLSavedDatabase {

    /**
     * This variable represents the name of the database table from which this class will grab information.
     *
     * This is necessary to declare as it affects the methods in this class and its superclass.
     */
    private static final String tableName = "SavedDatabase";

    /**
     * This variable represents the name of the column containing the primary key for the database table represented
     * by the "String tableName" variable.
     */
    private static final String primaryKeyColName = "ID";

    /**
     * This method will create a table (the table and columns depend on the class
     * which implements this method
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        String query = "CREATE TABLE " + tableName + " (\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\tDatabaseNickname VARCHAR[50] NOT NULL\n," +
                "\tDatabaseLocation VARCHAR[500] NOT NULL,\n" +
                "\tDatabaseUsername VARCHAR[50],\n" +
                "\tDatabasePassword VARCHAR[50],\n" +
                "\tDatabaseVendor VARCHAR[50] NOT NULL\n" +
                ");";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method inserts a new row into the table.
     *
     * @param username
     * @param password
     * @param location
     * @param vendor
     * @throws SQLException
     */
    public static void insert(String nickname, String username, String password, String location, String vendor) throws SQLException {
        nickname = SQLGeneral.escapeApostrophes(nickname);
        username = SQLGeneral.escapeApostrophes(username);
        password = SQLGeneral.escapeApostrophes(password);
        location = SQLGeneral.escapeApostrophes(location);
        vendor = SQLGeneral.escapeApostrophes(vendor);
        String query = "INSERT INTO " + tableName + " (DatabaseNickname, DatabaseUsername, DatabasePassword,"
                + "DatabaseLocation, DatabaseVendor) VALUES ('" + nickname + "', ' " + username
                + "', '" + password + "', '" + location + "', '" + vendor + "');";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates a row in the table.
     *
     * @param username
     * @param password
     * @param location
     * @param vendor
     * @param primaryKey
     * @throws SQLException
     */
    public static void update(String nickname, String username, String password, String location, String vendor, int primaryKey) throws SQLException {
        nickname = SQLGeneral.escapeApostrophes(nickname);
        username = SQLGeneral.escapeApostrophes(username);
        password = SQLGeneral.escapeApostrophes(password);
        location = SQLGeneral.escapeApostrophes(location);
        vendor = SQLGeneral.escapeApostrophes(vendor);
        String query = "UPDATE " + tableName + " SET DatabaseNickname='" + nickname + "', DatabaseUsername='" + username + "', DatabasePassword='" + password
                + "', DatabaseLocation='" + location + "', DatabaseVendor='" + vendor + "' WHERE " + primaryKeyColName + " = " + primaryKey + ";";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method returns all columns and rows from the table with which this class deals.
     * @return ResultSet with all columns and rows from the table.
     * @throws SQLException
     */
    public static ResultSet getAll() throws SQLException {
        return SQLGeneral.getWholeTable(tableName);
    }

    /**
     * This takes the primary key parameter and deletes the corresponding entry in the database table
     * with which this class deals.
     * @param primaryKey
     * @throws SQLException
     */
    public static void delete(int primaryKey) throws SQLException {
        SQLGeneral.deleteFromTable(tableName, primaryKey, primaryKeyColName);
    }

    /**
     * This method inserts test data into the database for this table.
     * @throws SQLException
     */
    public static void insertTestData() throws SQLException {
        insert("Good Data", "Username", "Password", "src/resources/database/RCMInformationSuiteTestDB", "sqlite");
    }
}
