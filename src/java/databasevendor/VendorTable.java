package databasevendor;

import sqldata.*;

import java.sql.SQLException;

/**
 * This class implements the appropriate method for the vendor for the database chosen in settings.
 *
 * In other words, the user chooses a database and this method makes sure the right syntax is used for
 * the vendor.
 */
public abstract class VendorTable {

    /**
     * This method creates all tables necessary for the database and inserts test data as well.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @throws SQLException
     */
    public static void createEmbeddedTablesWithTestData() throws SQLException {
        VendorAdminAction.createTable();
        VendorAdminAction.insertTestData();
        VendorAdminUser.createTable();
        VendorAdminUser.insertTestData();
        VendorAdminLog.createTable();
        VendorAdminLog.insertTestData();
        VendorCarcOrRarc.createTable();
        VendorCarcOrRarc.insertTestData();
        VendorClient.createTable();
        VendorClient.insertTestData();
        VendorCSSPath.createTable();
        VendorCSSPath.insertTestData();
        VendorDepartment.createTable();
        VendorDepartment.insertTestData();
        VendorFontSize.createTable();
        VendorFontSize.insertTestData();
        VendorFontStyle.createTable();
        VendorFontStyle.insertTestData();
        VendorSuggestionCategory.createTable();
        VendorSuggestionCategory.insertTestData();
        VendorGeneralSuggestion.createTable();
        VendorGeneralSuggestion.insertTestData();
        VendorPayer.createTable();
        VendorPayer.insertTestData();
        VendorPayerSuggestion.createTable();
        VendorPayerSuggestion.insertTestData();
        VendorProvider.createTable();
        VendorProvider.insertTestData();
        VendorRemarkCode.createTable();
        VendorRemarkCode.insertTestData();
        VendorRemarkPhrase.createTable();
        VendorRemarkPhrase.insertTestData();
        VendorSavedDatabase.createTable();
        VendorSavedDatabase.insertTestData();
        VendorSettings.createTable();
        VendorSettings.insertTestData();
        VendorTipOfTheDay.createTable();
        VendorTipOfTheDay.insertTestData();
        VendorTipOfTheDaySuggestion.createTable();
        VendorTipOfTheDaySuggestion.insertTestData();
        VendorRemarkCode.insertRandomUsedWithData();
        VendorRemarkPhrase.insertRandomRemarkUseData();
    }

    /**
     * This method creates all necessary tables for the database, but they are empty.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @throws SQLException
     */
    public static void createEmbeddedEmptyTables() throws SQLException {
        VendorAdminAction.createTable();
        VendorAdminUser.createTable();
        VendorAdminLog.createTable();
        VendorCarcOrRarc.createTable();
        VendorClient.createTable();
        VendorCSSPath.createTable();
        VendorDepartment.createTable();
        VendorFontSize.createTable();
        VendorFontStyle.createTable();
        VendorSuggestionCategory.createTable();
        VendorGeneralSuggestion.createTable();
        VendorPayer.createTable();
        VendorPayerSuggestion.createTable();
        VendorProvider.createTable();
        VendorRemarkCode.createTable();
        VendorRemarkPhrase.createTable();
        VendorSavedDatabase.createTable();
        VendorSettings.createTable();
        VendorTipOfTheDay.createTable();
        VendorTipOfTheDaySuggestion.createTable();
    }

    /**
     * This method creates all necessary tables for the database and inserts remark codes, as that's real info
     * and not just test data.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * @throws SQLException
     */
    public static void createEmbeddedTablesWithRemarkCodes() throws SQLException {
        VendorAdminAction.createTable();
        VendorAdminUser.createTable();
        VendorAdminLog.createTable();
        VendorCarcOrRarc.createTable();
        VendorCarcOrRarc.insertTestData();
        VendorClient.createTable();
        VendorCSSPath.createTable();
        VendorDepartment.createTable();
        VendorFontSize.createTable();
        VendorFontStyle.createTable();
        VendorSuggestionCategory.createTable();
        VendorGeneralSuggestion.createTable();
        VendorPayer.createTable();
        VendorPayerSuggestion.createTable();
        VendorProvider.createTable();
        VendorRemarkCode.createTable();
        VendorRemarkCode.insertTestData();
        VendorRemarkPhrase.createTable();
        VendorSavedDatabase.createTable();
        VendorSettings.createTable();
        VendorTipOfTheDay.createTable();
        VendorTipOfTheDaySuggestion.createTable();
    }


    /**
     * This method creates all tables necessary for the database and inserts test data as well.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * This method does not include data meant only for embedded databases.
     * @throws SQLException
     */
    public static void createTablesWithTestData() throws SQLException {
        VendorAdminAction.createTable();
        VendorAdminAction.insertTestData();
        VendorAdminUser.createTable();
        VendorAdminUser.insertTestData();
        VendorAdminLog.createTable();
        VendorAdminLog.insertTestData();
        VendorCarcOrRarc.createTable();
        VendorCarcOrRarc.insertTestData();
        VendorClient.createTable();
        VendorClient.insertTestData();
        VendorDepartment.createTable();
        VendorDepartment.insertTestData();
        VendorSuggestionCategory.createTable();
        VendorSuggestionCategory.insertTestData();
        VendorGeneralSuggestion.createTable();
        VendorGeneralSuggestion.insertTestData();
        VendorPayer.createTable();
        VendorPayer.insertTestData();
        VendorPayerSuggestion.createTable();
        VendorPayerSuggestion.insertTestData();
        VendorProvider.createTable();
        VendorProvider.insertTestData();
        VendorRemarkCode.createTable();
        VendorRemarkCode.insertTestData();
        VendorRemarkPhrase.createTable();
        VendorRemarkPhrase.insertTestData();
        VendorTipOfTheDay.createTable();
        VendorTipOfTheDay.insertTestData();
        VendorTipOfTheDaySuggestion.createTable();
        VendorTipOfTheDaySuggestion.insertTestData();
        VendorRemarkCode.insertRandomUsedWithData();
        VendorRemarkPhrase.insertRandomRemarkUseData();
    }

    /**
     * This method creates all necessary tables for the database, but they are empty.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * This method does not include data meant only for embedded databases.
     * @throws SQLException
     */
    public static void createEmptyTables() throws SQLException {
        VendorAdminAction.createTable();
        VendorAdminUser.createTable();
        VendorAdminLog.createTable();
        VendorCarcOrRarc.createTable();
        VendorClient.createTable();
        VendorDepartment.createTable();
        VendorSuggestionCategory.createTable();
        VendorGeneralSuggestion.createTable();
        VendorPayer.createTable();
        VendorPayerSuggestion.createTable();
        VendorProvider.createTable();
        VendorRemarkCode.createTable();
        VendorRemarkPhrase.createTable();
        VendorTipOfTheDay.createTable();
        VendorTipOfTheDaySuggestion.createTable();
    }

    /**
     * This method creates all necessary tables for the database and inserts remark codes, as that's real info
     * and not just test data.
     *
     * This method makes sure the right syntax is used for
     * the vendor.
     *
     * This method does not include data meant only for embedded databases.
     * @throws SQLException
     */
    public static void createTablesWithRemarkCodes() throws SQLException {
        VendorAdminAction.createTable();
        VendorAdminUser.createTable();
        VendorAdminLog.createTable();
        VendorCarcOrRarc.createTable();
        VendorCarcOrRarc.insertTestData();
        VendorClient.createTable();
        VendorDepartment.createTable();
        VendorSuggestionCategory.createTable();
        VendorGeneralSuggestion.createTable();
        VendorPayer.createTable();
        VendorPayerSuggestion.createTable();
        VendorProvider.createTable();
        VendorRemarkCode.createTable();
        VendorRemarkCode.insertTestData();
        VendorRemarkPhrase.createTable();
        VendorTipOfTheDay.createTable();
        VendorTipOfTheDaySuggestion.createTable();
    }
}
