package databasevendor;

import masterdata.SQLConnection;
import sqlitedata.SQLiteConnection;

import java.sql.SQLException;

/**
 * This class implements the appropriate method for the vendor for the database chosen in SQLConnection.
 *
 * In other words, the user chooses a database and this method makes sure the right syntax is used for
 * the vendor.
 */
public abstract class VendorConnection {

    /**
     * Implements the appropriate method to open a connection the users desired database.
     * @throws SQLException
     */
    public static void openConnection () throws SQLException {
        if (SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLiteConnection.openConnection();
        }
    }

    /**
     * Implements the appropriate method to close a connection the users desired database.
     * @throws SQLException
     */
    public static void closeConnection () {
        if (SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLiteConnection.closeConnection();
        }
    }

}
