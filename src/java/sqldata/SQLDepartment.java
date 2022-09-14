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
public abstract class SQLDepartment {

    /**
     * This variable represents the name of the database table from which this class will grab information.
     *
     * This is necessary to declare as it affects the methods in this class and its superclass.
     */
    private static final String tableName = "Department";

    /**
     * This variable represents the name of the column containing the primary key for the database table represented
     * by the "String tableName" variable.
     */
    private static final String primaryKeyColName = "ID";

    /**
     * This method inserts a new row into the table.
     *
     * @param description
     * @param clientID
     * @throws SQLException
     */
    public static void insert(String description, int clientID) throws SQLException {
        description = SQLGeneral.escapeApostrophes(description);
        String query = "INSERT INTO " + tableName + " (description, ClientID) VALUES ('"
                + description + "', " + clientID + ");";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates a row in the table.
     *
     * @param description
     * @param clientID
     * @param primaryKey
     * @throws SQLException
     */
    public static void update(String description, int clientID, int primaryKey) throws SQLException {
        description = SQLGeneral.escapeApostrophes(description);
        String query = "UPDATE " + tableName + " SET description='" + description + "', ClientID="
                + clientID + " WHERE " + primaryKeyColName + " = " + primaryKey + ";";
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
                "\tClientID INTEGER NOT NULL,\n" +
                "\tdescription VARCHAR[100] NOT NULL,\n" +
                "\tFOREIGN KEY (ClientID) REFERENCES Client(ID)\n" +
                "\tON DELETE CASCADE \n" +
                "\tON UPDATE CASCADE\n" +
                ");";
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
        insert("Radiology", 1);
        insert("Urology", 1);
        insert("Oncology", 1);
        insert("Pediatrics", 2);
        insert("Cafeteria", 2);
        insert("Proctology", 2);
        insert("Orthopedics", 3);
        insert("Urology", 3);
        insert("Pediatrics", 3);
    }
}
