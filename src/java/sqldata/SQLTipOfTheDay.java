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
public abstract class SQLTipOfTheDay {

    /**
     * This variable represents the name of the database table from which this class will grab information.
     *
     * This is necessary to declare as it affects the methods in this class and its superclass.
     */
    private static final String tableName = "TipOfTheDay";

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
                "\tDescription VARCHAR[160] NOT NULL\n" +
                ");";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method inserts a new row into the table.
     *
     * @param description
     * @throws SQLException
     */
    public static void insert(String description) throws SQLException {
        description = SQLGeneral.escapeApostrophes(description);
        String query = "INSERT INTO " + tableName + " (Description) VALUES ('" + description + "');";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates a row in the table.
     *
     * @param description
     * @param primaryKey
     * @throws SQLException
     */
    public static void update(String description, int primaryKey) throws SQLException {
        description = SQLGeneral.escapeApostrophes(description);
        String query = "UPDATE " + tableName + " SET Description='" + description + "' WHERE "
                + primaryKeyColName + " = " + primaryKey + ";";
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
        insert("Be better than you are");
        insert("This application may save you time");
        insert("This is a tip");
    }
}
