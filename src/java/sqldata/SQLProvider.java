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
public abstract class SQLProvider {

    /**
     * This variable represents the name of the database table from which this class will grab information.
     *
     * This is necessary to declare as it affects the methods in this class and its superclass.
     */
    private static final String tableName = "Provider";

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
        String query = "CREATE TABLE " + tableName + "(\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\tDepartmentID INTEGER NOT NULL,\n" +
                "\tNPI VARCHAR[15],\n" +
                "\tFirstName VARCHAR[50],\n" +
                "\tLastName VARCHAR[50],\n" +
                "\tFOREIGN KEY (DepartmentID) REFERENCES Department(ID)\n" +
                "\tON DELETE CASCADE \n" +
                "\tON UPDATE CASCADE\n" +
                ");";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method inserts a new row into the table.
     *
     * @param firstName
     * @param lastName
     * @param npi
     * @param departmentID
     * @throws SQLException
     */
    public static void insert(String firstName, String lastName, String npi, int departmentID) throws SQLException {
        firstName = SQLGeneral.escapeApostrophes(firstName);
        lastName = SQLGeneral.escapeApostrophes(lastName);
        npi = SQLGeneral.escapeApostrophes(npi);
        String query = "INSERT INTO " + tableName + " (FirstName, LastName, NPI, DepartmentID) VALUES ('"
                + firstName + "', '" + lastName + "', '" + npi + "', " + departmentID + ");";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates a row in the table.
     *
     * @param firstName
     * @param lastName
     * @param npi
     * @param departmentID
     * @param primaryKey
     * @throws SQLException
     */
    public static void update(String firstName, String lastName, String npi, int departmentID, int primaryKey) throws SQLException {
        firstName = SQLGeneral.escapeApostrophes(firstName);
        lastName = SQLGeneral.escapeApostrophes(lastName);
        npi = SQLGeneral.escapeApostrophes(npi);
        String query = "UPDATE " + tableName + " SET FirstName='" + firstName + "', LastName='" + lastName
                + "', NPI='" + npi + "', DepartmentID=" + departmentID + " WHERE " + primaryKeyColName
                + " = " + primaryKey;
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
        insert("John", "Carson", "1392888405", 1);
        insert("Jessica", "Stronghammer", "2392383493", 2);
        insert("Jeremy", "Shorthult", "1239403537", 3);
        insert("Romeo", "Colse", "2384305348", 4);
        insert("Rachel", "Worchester", "1209308473", 5);
        insert("Rick", "Sanchez", "2139047305", 6);
        insert("Ashalia", "Deadris", "1243093842", 7);
        insert("Alan", "Shwartz", "2934038533", 8);
        insert("Aaron", "White", "2988349202", 9);
    }
}
