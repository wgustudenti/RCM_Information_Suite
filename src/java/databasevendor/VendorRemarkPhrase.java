package databasevendor;

import masterdata.SQLConnection;
import sqlitedata.SQLiteRemarkPhrase;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements the appropriate method for the vendor for the database chosen in settings.
 *
 * In other words, the user chooses a database and this method makes sure the right syntax is used for
 * the vendor.
 */
public abstract class VendorRemarkPhrase {

    /**
     * This method inserts a new row into the table.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param phrase
     * @throws SQLException
     */
    public static void insert(String phrase) throws SQLException {
        if(SQLConnection.getVendor().toLowerCase().contains("sqlite")){
            SQLiteRemarkPhrase.insert(phrase);
        }
    }

    /**
     * This method updates a row in the table.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param phrase
     * @param primaryKey
     * @throws SQLException
     */
    public static void update(String phrase, int primaryKey) throws SQLException {
        if (SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkPhrase.update(phrase, primaryKey);
        }
    }

    /**
     * This method updates the "most commonly used with" information in the database.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param remarkCode
     * @param numberToAdd
     * @param primaryKey
     * @throws SQLException
     */
    public static void updateUsedWithCount(String remarkCode, int numberToAdd, int primaryKey) throws SQLException {
        if (SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkPhrase.updateRemarkPhraseUseData(remarkCode, numberToAdd, primaryKey);
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
        if (SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            return SQLiteRemarkPhrase.getAll();
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
     * @param primaryKey
     * @throws SQLException
     */
    public static void delete(int primaryKey) throws SQLException {
        if (SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkPhrase.delete(primaryKey);
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
        if (SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkPhrase.insertTestData();
        }
    }

    /**
     * This method alters the RemarkPhrase table by adding a column to it.
     * This is done when a new remark code is added to the database for the phrase-used-with data.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param remarkCode
     * @throws SQLException
     */
    public static void addRemarkPhraseColumn(String remarkCode) throws SQLException {
        if (SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkPhrase.addRemarkPhraseColumn(remarkCode);
        }
    }

    /**
     * This method alters the RemarkPhrase table by removing a column from it.
     * This is done when a new remark code is added to the database for the phrase-used-with data.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param remarkCode
     * @throws SQLException
     */
    public static void removeRemarkPhraseColumn(String remarkCode) throws SQLException {
        if (SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkPhrase.removeRemarkPhraseColumn(remarkCode);
        }
    }

    /**
     * This method takes the parameters and updates the use information in the RemarkPhrase table.
     * If no "numberToAdd" (third parameter) is inserted, the method will add increment the use data by one.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param primaryKey
     * @param remarkCode
     * @throws SQLException
     */
    public static void updateRemarkPhraseUseData(int primaryKey, String remarkCode) throws SQLException {
        if (SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkPhrase.updateRemarkPhraseUseData(remarkCode, primaryKey);
        }
    }

    /**
     * This method takes the parameters and updates the use information in the RemarkPhrase table.
     * If no "numberToAdd" (third parameter) is inserted, the method will add increment the use data by one.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @param primaryKey
     * @param remarkCode
     * @param numberToAdd
     * @throws SQLException
     */
    public static void updateRemarkPhraseUseData(int primaryKey, String remarkCode, int numberToAdd) throws SQLException {
        if (SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkPhrase.updateRemarkPhraseUseData(remarkCode, numberToAdd, primaryKey);
        }
    }

    /**
     * This method inserts random use data into the RemarkPhrase table in the database.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @throws SQLException
     */
    public static void insertRandomRemarkUseData() throws SQLException {
        if (SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkPhrase.insertRandomRemarkUseData();
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
        if (SQLConnection.getVendor().toLowerCase().contains("sqlite")) {
            SQLiteRemarkPhrase.createTable();
        }
    }
}
