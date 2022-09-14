package databasevendor;

import masterdata.SQLConnection;
import sqlitedata.SQLiteDepartment;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements the appropriate method for the vendor for the database chosen in settings.
 *
 * In other words, the user chooses a database and this method makes sure the right syntax is used for
 * the vendor.
 */
public abstract class VendorDepartment {

    /**
     * This method inserts a new row into the table.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param description
     * @param clientID
     * @throws SQLException
     */
    public static void insert(String description, int clientID) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLiteDepartment.insert(description, clientID);
        }
    }

    /**
     * This method updates a row in the table.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param description
     * @param clientID
     * @param primaryKey
     * @throws SQLException
     */
    public static void update(String description, int clientID, int primaryKey) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLiteDepartment.update(description, clientID, primaryKey);
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
            SQLiteDepartment.createTable();
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
            return SQLiteDepartment.getAll();
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
            SQLiteDepartment.delete(primaryKey);
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
            SQLiteDepartment.insertTestData();
        }
    }
}
