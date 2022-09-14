package sqlitedata;

import masterdata.SQLConnection;
import masterdata.SQLGeneral;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class deals with connecting to a SQLite database.
 */
public abstract class SQLiteConnection extends SQLConnection {

    /**
     * Database URl
     */
    private static String getCurrentJDBCurl() {
        return getProtocol() + ":" + getVendor() + ":" + getLocation();
    }

    /**
     * Opens the connections to the database
     * Needed to be redefined because the getConnection() method contained in this method takes different parameters than the same method
     * in the superclass.
     */
    public static void openConnection() throws SQLException {
        try {
            setConnection(DriverManager.getConnection(getCurrentJDBCurl()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //System.out.println("Error:" + e.getMessage());
        }
        String query = "PRAGMA foreign_keys = ON;";
        SQLGeneral.executeUpdate(query);
    }
}
