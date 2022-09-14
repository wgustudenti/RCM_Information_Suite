package sqldata;

import masterdata.SQLGeneral;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * This class is not meant to be used directly, but to be a superclass for classes SQL driven databases
 * for specific vendors.
 *
 * The subclasses to this class interact with a database to retrieve information from the table specified
 * by the tableName variable (and the name of this specific .java file).
 */
public abstract class SQLAdminLog {

    /**
     * This variable represents the name of the database table from which this class will grab information.
     *
     * This is necessary to declare as it affects the methods in this class and its superclass.
     */
    private static final String tableName = "AdminLog";

    /**
     * This variable represents the name of the column containing the primary key for the database table represented
     * by the "String tableName" variable.
     */
    private static final String primaryKeyColName = "ID";

    /**
     * This method inserts a new row into the table.
     *
     * @param username
     * @param actionID
     * @param detail
     * @throws SQLException
     */
    public static void insert(String username, int actionID, String detail) throws SQLException {
        username = SQLGeneral.escapeApostrophes(username);
        detail = SQLGeneral.escapeApostrophes(detail);
        String query = "INSERT INTO " + tableName + " (Username, ActionID, Detail) VALUES ('" + username + "', " + actionID
                + ", '" + detail + "')" + ";";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates a row in the table.
     *
     * @param username
     * @param actionID
     * @param detail
     * @param primaryKey
     * @throws SQLException
     */
    public static void update(String username, int actionID, String detail, int primaryKey) throws SQLException {
        username = SQLGeneral.escapeApostrophes(username);
        detail = SQLGeneral.escapeApostrophes(detail);
        String query = "UPDATE " + tableName + " SET Username='" + username + "', ActionID=" + actionID + ", Detail='"
                + detail + "' WHERE " + primaryKeyColName + " = " + primaryKey + ";";
         SQLGeneral.executeUpdate(query);
    }

    /**
     * This method will create a table (the table and columns depend on the class
     * which implements this method
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        String query = "CREATE TABLE " + tableName + " (\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\tActionID INTEGER NOT NULL,\n" +
                "\tUsername VARCHAR[50] NOT NULL,\n" +
                "\tDetail VARCHAR[160],\n" +
                "\tActionTime DateTime DEFAULT CURRENT_TIMESTAMP,\n" +
                "\tFOREIGN KEY (ActionID) REFERENCES AdminAction(ID) \n" +
                "\tON DELETE CASCADE \n" +
                "\tON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (Username) REFERENCES AdminUser(Username)\n" +
                "\tON DELETE CASCADE \n" +
                "\tON UPDATE CASCADE);";
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
        insert("Stephen", 1, "Carter, LLC");
        insert("John", 2, "Pterodactyl, LLC");
        insert("Carol", 3, "This, LLC");
    }
}
