package masterdata;

import sqlitedata.SQLiteConnection;

import java.sql.*;

/**
 * This class is not meant to be used directly, but to be a superclass to classes for SQL driven databases
 * for specific vendors.  For instance, the SQLInformationSearch subclass will extend this class.
 *
 * The static variables will need to be redefined for each immediate subclass.
 */
public abstract class SQLGeneral {
    /**
     * DO NOT CHANGE THIS, IF THE TABLE NAME IS GIVING YOU AN ERROR, GO TO THE .java FILE WHICH
     * CONCERNS THE SPECIFIC TABLE.
     *
     * I mean, if you change it, it shouldn't break anything.  But it also shouldn't help anything.
     * This is just here as a placeholder so the methods could be written - it is redefined in each
     * of this class' subclass.
     *
     * public static final String tableName = "NULL Check SQLGeneralClass.java file.";
    */
    /**
     * This method returns the results from executing the query parameter.
     *
     * @return a Result set relating to the executed query.
     */
    public static ResultSet executeQuery (String query) throws SQLException {
        Connection connection = SQLiteConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet results = preparedStatement.executeQuery();
        return results;
    }

    /**
     * Used to perform an update in a database using SQL.
     * This method executes the create, update, or delete parts of CRUD.
     * @param query
     * @throws SQLException
     */
    public static int executeUpdate (String query) throws SQLException {
        Connection connection = SQLiteConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        return preparedStatement.executeUpdate();
    }

    /**
     * Returns the whole table specified by the parameter.
     * @param tableName
     * @return whole table specified by the parameter.
     * @throws SQLException
     */
    public static ResultSet getWholeTable(String tableName) throws SQLException {
        String query = "Select * From " + tableName + ";";
        ResultSet results = SQLGeneral.executeQuery(query);
        return results;
    }


   /* public static void deleteFromTable(String tableName, Object primaryKey) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE ID = " + primaryKey + ";";
        executeUpdate(query);
    }*/

    /**
     * Deletes an item from a table using the information given for the parameters (table name, primary key,
     * primary key column name).
     * @param tableName
     * @param primaryKey
     * @param primaryKeyColName
     * @throws SQLException
     */
    public static void deleteFromTable(String tableName, Object primaryKey, String primaryKeyColName) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE " + primaryKeyColName + " LIKE '" + primaryKey + "';";
        executeUpdate(query);
    }

    /**
     * This method will add an extra apostrophe wherever a single apostrophe is in a string.
     * This is how apostrophes are escaped in SQL databases, similar to the backslash in java.
     * If apostrophes are not escaped, SQL queries will have problems.
     *
     * @param stringToAlter
     * @return String with apostrophes escaped for a SQL database
     */
    public static String escapeApostrophes(String stringToAlter) {
        String stringToReturn = stringToAlter.replace("'", "''");
        return stringToReturn;
    }

    /**
     * This method removes all spaces from a string.
     *
     * This must be done to create a column name from a string in a SQL database.
     */
    public static String removeSpaces(String stringToAlter){
        String stringToReturn = stringToAlter.replace(" ", "");
        return stringToReturn;
    }

    /**
     * This method removes all apostrophes from a string.
     *
     * This must be done to create a column name from a string in a SQL database.
     */
    public static String removeApostrophes(String stringToAlter){
        String stringToReturn = stringToAlter.replace(" ", "");
        return stringToReturn;
    }
}
