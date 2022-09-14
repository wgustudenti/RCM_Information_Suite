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
public abstract class SQLCSSPath {

    /**
     * This variable represents the name of the database table from which this class will grab information.
     *
     * This is necessary to declare as it affects the methods in this class and its superclass.
     */
    private static final String tableName = "CSSPath";

    /**
     * This variable represents the name of the column containing the primary key for the database table represented
     * by the "String tableName" variable.
     */
    private static final String primaryKeyColName = "ID";

    /**
     * This method inserts a new row into the table.
     *
     * @param description
     * @throws SQLException
     */
    public static void insert(String description, String nickname) throws SQLException {
        description = SQLGeneral.escapeApostrophes(description);
        nickname = SQLGeneral.escapeApostrophes(nickname);
        String query = "INSERT INTO " + tableName + " (Description, Nickname) VALUES ('" + description + "', '"
                + nickname + "');";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates a row in the table.
     *
     * @param description
     * @param primaryKey
     * @throws SQLException
     */
    public static void update(String description, String nickname, int primaryKey) throws SQLException {
        description = SQLGeneral.escapeApostrophes(description);
        nickname = SQLGeneral.escapeApostrophes(nickname);
        String query = "UPDATE " + tableName + " SET Description='" + description + "', Nickname='"
                + nickname + "' WHERE " + primaryKeyColName + " = " + primaryKey + ";";
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
                "\tDescription VARCHAR[100] NOT NULL UNIQUE,\n" +
                "\tNickname VARCHAR[50] NOT NULL UNIQUE\n" +
                ")";
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
        insert("/css/Style_2.css", "First CSS Sheet");
        insert("C:/css", "Second CSS Sheet");
        insert("P:/stylesheet", "Third CSS Sheet");
    }
}
