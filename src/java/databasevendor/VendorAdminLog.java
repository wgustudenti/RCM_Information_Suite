package databasevendor;

import masterdata.SQLConnection;
import sqlitedata.SQLiteAdminLog;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements the appropriate method for the vendor for the database chosen in settings.
 *
 * In other words, the user chooses a database and this method makes sure the right syntax is used for
 * the vendor.
 */
public abstract class VendorAdminLog {

    /**
     * This method inserts a new row into the table.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param username
     * @param actionID
     * @throws SQLException
     */
    public static void insert(String username, int actionID, String detail) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLiteAdminLog.insert(username, actionID, detail);
        }
    }

    /**
     * This method updates a row in the table.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param username
     * @param actionID
     * @param primaryKey
     * @throws SQLException
     */
    public static void update(String username, int actionID, String detail, int primaryKey) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLiteAdminLog.update(username, actionID, detail, primaryKey);
        }
    }

    /**
     * This method will create a table (the table and columns depend on the class
     * which implements this method
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLiteAdminLog.createTable();
        }
    }

    /**
     * This method returns all columns and rows from the table with which this class deals.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @return ResultSet with all columns and rows from the table.
     * @throws SQLException
     */
    public static ResultSet getAll() throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
           return SQLiteAdminLog.getAll();
        }
        return null;
    }

    /**
     * This takes the primary key parameter and deletes the corresponding entry in the database table
     * with which this class deals.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param primaryKey
     * @throws SQLException
     */
    public static void delete(int primaryKey) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLiteAdminLog.delete(primaryKey);
        }
    }

    /**
     * This method inserts test data into the database for this table.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @throws SQLException
     */
    public static void insertTestData() throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLiteAdminLog.insertTestData();
        }
    }
}
