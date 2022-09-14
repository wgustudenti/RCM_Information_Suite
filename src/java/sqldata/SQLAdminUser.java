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
public abstract class SQLAdminUser {

    /**
     * This variable represents the name of the database table from which this class will grab information.
     *
     * This is necessary to declare as it affects the methods in this class and its superclass.
     */
    private static final String tableName = "AdminUser";

    /**
     * This variable represents the name of the column containing the primary key for the database table represented
     * by the "String tableName" variable.
     */
    private static final String primaryKeyColName = "Username";

    /**
     * This method inserts a new row into the table.
     *
     * @param username
     * @param password
     * @throws SQLException
     */
    public static void insert(String username, String password) throws SQLException {
        String query = "INSERT INTO " + tableName + "(Username, Password) VALUES ('" + username + "', '" + password
                + "');";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates a row in the table.
     *
     * This method only can be used to update the password.
     *
     * @param username
     * @param password
     * @throws SQLException
     */
    public static void update(String username, String password) throws SQLException {
        String query = "UPDATE " + tableName + " SET Username='" + username + "', Password='" + password
                + "' WHERE " + primaryKeyColName + " LIKE '" + username + "';";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method will create a table (the table and columns depend on the class
     * which implements this method
     *
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        String query = "CREATE TABLE " + tableName + " (\n" +
                "\tUsername VARCHAR[50] PRIMARY KEY NOT NULL,\n" +
                "\tPassword VARCHAR[50] NOT NULL\n" +
                ");";
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
     * @param username
     * @throws SQLException
     */
    public static void delete(String username) throws SQLException {
        SQLGeneral.deleteFromTable(tableName, username, primaryKeyColName);
    }

    /**
     * This method inserts test data into the database for this table.
     * @throws SQLException
     */
    public static void insertTestData() throws SQLException {
        insert("Stephen", "password");
        insert("John", "Password1");
        insert("Carol", "P@$$w0rd");
    }
}
