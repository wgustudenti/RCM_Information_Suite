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
public abstract class SQLPayerSuggestion {

    /**
     * This variable represents the name of the database table from which this class will grab information.
     *
     * This is necessary to declare as it affects the methods in this class and its superclass.
     */
    private static final String tableName = "PayerSuggestion";

    /**
     * This variable represents the name of the column containing the primary key for the database table represented
     * by the "String tableName" variable.
     */
    private static final String primaryKeyColName = "ID";

    /**
     * This method will create a table (the table and columns depend on the class
     * which implements this method
     */
    public static void createTable() throws SQLException {
        String query = "CREATE TABLE " + tableName + " (\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\tClientID INTEGER NOT NULL,\n" +
                "\tNameOnDocument VARCHAR[50] NOT NULL,\n" +
                "\tNameToEnter VARCHAR[50] NOT NULL,\n" +
                "\tActionTime DateTime DEFAULT CURRENT_TIMESTAMP,\n" +
                "\tFOREIGN KEY (ClientID) REFERENCES Client(ID)\n" +
                "\tON DELETE CASCADE \n" +
                "\tON UPDATE CASCADE\n" +
                ");";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method inserts a new row into the table.
     *
     * @param nameOnDocument
     * @param nameToEnter
     * @param clientID
     */
    public static void insert(String nameOnDocument, String nameToEnter, int clientID) throws SQLException {
        nameOnDocument = SQLGeneral.escapeApostrophes(nameOnDocument);
        nameToEnter = SQLGeneral.escapeApostrophes(nameToEnter);
        String query = "INSERT INTO " + tableName + " (NameOnDocument, NameToEnter, ClientID) VALUES ('"
                + nameOnDocument + "', '" + nameToEnter + "', " + clientID + ");";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates a row in the table.
     *
     * @param nameOnDocument
     * @param nameToEnter
     * @param clientID
     * @param primaryKey
     */
    public static void update(String nameOnDocument, String nameToEnter, int clientID, int primaryKey) throws SQLException {
        nameOnDocument = SQLGeneral.escapeApostrophes(nameOnDocument);
        nameToEnter = SQLGeneral.escapeApostrophes(nameToEnter);
        String query = "UPDATE " + tableName + " SET NameOnDocument='" + nameOnDocument + "', NameToEnter='"
                + nameToEnter + "', ClientID=" + clientID + " WHERE " + primaryKeyColName + " = " + primaryKey + ";";
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
        insert("Common Payer", "Frequent Payer", 2);
        insert("Update Payer", "Insert Payer", 1);
        insert("Deleted Payer", "Existing Payer", 3);
    }
}
