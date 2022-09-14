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
public abstract class SQLSettings {

    /**
     * This variable represents the name of the database table from which this class will grab information.
     *
     * This is necessary to declare as it affects the methods in this class and its superclass.
     */
    private static final String tableName = "Settings";

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
                "\tTableFontStyleID INTEGER NOT NULL,\n" +
                "\tTableFontSizeID INTEGER NOT NULL,\n" +
                "\tCSSPathID INTEGER NOT NULL,\n" +
                "\tDatabaseID INTEGER NOT NULL,\n" +
                "\tFOREIGN KEY (TableFontStyleID) REFERENCES FontStyle(ID)\n" +
                "\tON DELETE CASCADE \n" +
                "\tON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (TableFontSizeID) REFERENCES FontSize(ID)\n" +
                "\tON DELETE CASCADE \n" +
                "\tON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (CSSPathID) REFERENCES CSSPath(ID)\n" +
                "\tON DELETE CASCADE \n" +
                "\tON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (DatabaseID) REFERENCES SavedDatabase(ID)\n" +
                "\tON DELETE CASCADE \n" +
                "\tON UPDATE CASCADE);";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method inserts a new row into the table.
     *
     * @param fontSizeID
     * @param fontStyleID
     * @param cssPathID
     * @param databaseID
     * @throws SQLException
     */
    public static void insert(int fontSizeID, int fontStyleID, int cssPathID, int databaseID) throws SQLException {
        String query = "INSERT INTO " + tableName + " (TableFontSizeID, TableFontStyleID, CSSPathID, DatabaseID) VALUES ("
                + fontSizeID + ", " + fontStyleID + ", " + cssPathID + ", " + databaseID + ");";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates a row in the table.
     *
     * @param fontSizeID
     * @param fontStyleID
     * @param cssPathID
     * @param databaseID
     * @param primaryKey
     * @throws SQLException
     */
    public static void update(int fontSizeID, int fontStyleID, int cssPathID, int databaseID, int primaryKey) throws SQLException {
        String query = "UPDATE " + tableName + " SET TableFontSizeID=" + fontSizeID + ", TableFontStyleID=" + fontStyleID
                + ", CSSPathID=" + cssPathID + ", DatabaseID=" + databaseID + " WHERE " + primaryKeyColName + " = "
                + primaryKey + ";";
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
        insert(1, 1, 1, 1);
        insert(2, 1, 2, 1);
        insert(3, 2, 1, 1);
    }
}
