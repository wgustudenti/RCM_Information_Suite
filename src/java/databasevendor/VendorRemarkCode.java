package databasevendor;

import masterdata.SQLConnection;
import sqlitedata.SQLiteRemarkCode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class implements the appropriate method for the vendor for the database chosen in settings.
 *
 * In other words, the user chooses a database and this method makes sure the right syntax is used for
 * the vendor.
 */
public abstract class VendorRemarkCode {

    /**
     * This returns a ResultSet from the RemarkCode table with only one column: RemarkCode.  This should be faster than
     * retrieving the thousand columns which the table contains.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @return ResultSet with only the RemarkCode column
     * @throws SQLException
     */
    private static ResultSet getJustRemarkCodesResultSet() throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            return SQLiteRemarkCode.getAll();
        }
        return null;
    }

    /**
     * This method inserts a new row into the table.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param remarkCode
     * @param carcRarcID
     * @param description
     * @param notes
     * @throws SQLException
     */
    public static void insert(String remarkCode, int carcRarcID, String description, String notes) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLiteRemarkCode.insert(remarkCode, carcRarcID, description, notes);
        }
    }

    /**
     * This method updates a row in the table.
     *
     * This method will not update the remarkCode String, but everything else pertaining to that RemarkCode.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param remarkCode
     * @param carcRarcID
     * @param description
     * @param notes
     * @throws SQLException
     */
    public static void update(String remarkCode, int carcRarcID, String description, String notes) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLiteRemarkCode.update(remarkCode, carcRarcID, description, notes);
        }
    }

    /**
     * This returns a ResultSet of column names for the table with which this class deals.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @return ResultSet of column names for the table with which this class deals.
     * @throws SQLException
     */
    public static ResultSet getColumnNames() throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            return SQLiteRemarkCode.getColumnNames();
        }
        return null;
    }

    /**
     * This method updates the "most commonly used with" data in the RemarkCode table in the database.
     * It does two updates, one with the first remark code as the row/second remark code as column and vice versa.
     *
     * If no "numberToAdd" (third parameter) is inserted, the method will add increment the use data by one.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param remarkCodeOne
     * @param remarkCodeTwo
     * @throws SQLException
     */
    public static void updateMostCommonlyUsedWith(String remarkCodeOne, String remarkCodeTwo) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLiteRemarkCode.updateMostCommonlyUsedWith(remarkCodeOne, remarkCodeTwo);
        }
    }

    /**
     * This method updates the "most commonly used with" data in the RemarkCode table in the database.
     * It does two updates, one with the first remark code as the row/second remark code as column and vice versa.
     *
     * If no "numberToAdd" (third parameter) is inserted, the method will add increment the use data by one.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param remarkCodeOne
     * @param remarkCodeTwo
     * @param numberToAdd
     * @throws SQLException
     */
    public static void updateMostCommonlyUsedWith(String remarkCodeOne, String remarkCodeTwo, int numberToAdd) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLiteRemarkCode.updateMostCommonlyUsedWith(remarkCodeOne, remarkCodeTwo, numberToAdd);
        }
    }

    /**
     * This method returns all columns and rows from the table with which this class deals.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @return ResultSet with all columns and rows from the table.
     * @throws SQLException
     */
    public static ResultSet getAll() throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            return SQLiteRemarkCode.getAll();
        }
        return null;
    }

    /**
     * This takes the primary key parameter and deletes the corresponding entry in the database table
     * with which this class deals.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param remarkCode
     * @throws SQLException
     */
    public static void delete(String remarkCode) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkCode.delete(remarkCode);
        }
    }

    /**
     * This method alters the RemarkCode table by adding a column to it.
     * This is done when a new remark code is added to the database for the most commonly used with data.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param remarkCode
     * @throws SQLException
     */
    public static void addRemarkCodeColumn(String remarkCode) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkCode.addRemarkCodeColumn(remarkCode);
        }
    }

    /**
     * This method alters the RemarkCode table by removing a column from it.
     * This is done when a new remark code is added to the database for the most commonly used with data.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param remarkCode
     * @throws SQLException
     */
    public static void removeRemarkCodeColumn(String remarkCode) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkCode.removeRemarkCodeColumn(remarkCode);
        }
    }

    /**
     * Iterates through whole RemarkCode database table and returns an arraylist of all RemarkCodes.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @return arrayListContaining just remarkCodes.
     * @throws SQLException
     */
    public static ArrayList<String> getJustRemarkCodes() throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            return SQLiteRemarkCode.getJustRemarkCodes();
        }
        return null;
    }

    /**
     * This method inserts random "most commonly used with" data into the RemarkCode table in the database.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @throws SQLException
     */
    public static void insertRandomUsedWithData() throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkCode.insertRandomUsedWithDataAndNotes();
        }
    }

    /**
     * This method will create a table (the table and columns depend on the class
     * which implements this method
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkCode.createTable();
        }

    }

    /**
     * This method inserts test data into the database for this table.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @throws SQLException
     */
    public static void insertTestData() throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkCode.insertTestData();
        }
    }

    public static void insertWithColumn(String remarkCode, int carcRarcID, String description, String notes) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkCode.insertWithColumn(remarkCode, carcRarcID, description, notes);
        }
    }

    public static void deleteWithColumn(String remarkCode) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkCode.deleteWithColumn(remarkCode);
        }
    }
}
