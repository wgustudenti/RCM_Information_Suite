package databasevendor;

import masterdata.SQLConnection;
import masterdata.SQLGeneral;
import sqlitedata.SQLitePayer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements the appropriate method for the vendor for the database chosen in settings.
 *
 * In other words, the user chooses a database and this method makes sure the right syntax is used for
 * the vendor.
 */
public abstract class VendorPayer {

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
            SQLitePayer.createTable();
        }
    }

    /**
     * This method inserts a new row into the table.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param nameOnDocument
     * @param nameToEnter
     * @param clientID
     * @throws SQLException
     */
    public static void insert(String nameOnDocument, String nameToEnter, int clientID) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLitePayer.insert(nameOnDocument, nameToEnter, clientID);
        }
    }

    /**
     * This method inserts a new row into the table for each client which is not already associated with the payer combo.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param nameOnDocument
     * @param nameToEnter
     * @throws SQLException
     */
    public static void insertForAllClients(String nameOnDocument, String nameToEnter) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLitePayer.insertForAllClients(nameOnDocument, nameToEnter);
        }
    }

    /**
     * This method updates the row in the table for each payer associated with the oldNameOnDocument.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param oldNameOnDocument
     * @param updatedNameOnDocument
     * @param updatedNameToEnter
     * @throws SQLException
     */
    public static void updateForAllClients(String oldNameOnDocument, String updatedNameOnDocument, String updatedNameToEnter) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLitePayer.updateForAllClients(oldNameOnDocument, updatedNameOnDocument, updatedNameToEnter);
        }
    }

    /**
     * This method updates a row in the table.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param nameOnDocument
     * @param nameToEnter
     * @param clientID
     * @param primaryKey
     * @throws SQLException
     */
    public static void update(String nameOnDocument, String nameToEnter, int clientID, int primaryKey) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLitePayer.update(nameOnDocument, nameToEnter, clientID, primaryKey);
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
            return SQLitePayer.getAll();
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
            SQLitePayer.delete(primaryKey);
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
        if (SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLitePayer.insertTestData();
        }
    }
}
