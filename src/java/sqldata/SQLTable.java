package sqldata;

import java.sql.SQLException;

/**
 * This class is meant to create all necessary tables for this application in an EMPTY database.
 *
 */
public abstract class SQLTable {

    /**
     * This method creates all tables necessary for the database and inserts test data as well.
     * @throws SQLException
     */
    public static void createEmbeddedTablesWithTestData() throws SQLException {
        SQLAdminAction.createTable();
        SQLAdminAction.insertTestData();
        SQLAdminUser.createTable();
        SQLAdminUser.insertTestData();
        SQLAdminLog.createTable();
        SQLAdminLog.insertTestData();
        SQLCarcOrRarc.createTable();
        SQLCarcOrRarc.insertTestData();
        SQLClient.createTable();
        SQLClient.insertTestData();
        SQLCSSPath.createTable();
        SQLCSSPath.insertTestData();
        SQLDepartment.createTable();
        SQLDepartment.insertTestData();
        SQLFontSize.createTable();
        SQLFontSize.insertTestData();
        SQLFontStyle.createTable();
        SQLFontStyle.insertTestData();
        SQLSuggestionCategory.createTable();
        SQLSuggestionCategory.insertTestData();
        SQLGeneralSuggestion.createTable();
        SQLGeneralSuggestion.insertTestData();
        SQLPayer.createTable();
        SQLPayer.insertTestData();
        SQLPayerSuggestion.createTable();
        SQLPayerSuggestion.insertTestData();
        SQLProvider.createTable();
        SQLProvider.insertTestData();
        SQLRemarkCode.createTable();
        SQLRemarkCode.insertTestData();
        SQLRemarkPhrase.createTable();
        SQLRemarkPhrase.insertTestData();
        SQLSavedDatabase.createTable();
        SQLSavedDatabase.insertTestData();
        SQLSettings.createTable();
        SQLSettings.insertTestData();
        SQLTipOfTheDay.createTable();
        SQLTipOfTheDay.insertTestData();
        SQLTipOfTheDaySuggestion.createTable();
        SQLTipOfTheDaySuggestion.insertTestData();
        SQLRemarkCode.insertRandomUsedWithDataAndNotes();
        SQLRemarkPhrase.insertRandomRemarkUseData();
    }

    /**
     * This method creates all necessary tables for the database, but they are empty.
     * @throws SQLException
     */
    public static void createEmbeddedEmptyTables() throws SQLException {
        SQLAdminAction.createTable();
        SQLAdminUser.createTable();
        SQLAdminLog.createTable();
        SQLCarcOrRarc.createTable();
        SQLClient.createTable();
        SQLCSSPath.createTable();
        SQLDepartment.createTable();
        SQLFontSize.createTable();
        SQLFontStyle.createTable();
        SQLSuggestionCategory.createTable();
        SQLGeneralSuggestion.createTable();
        SQLPayer.createTable();
        SQLPayerSuggestion.createTable();
        SQLProvider.createTable();
        SQLRemarkCode.createTable();
        SQLRemarkPhrase.createTable();
        SQLSavedDatabase.createTable();
        SQLSettings.createTable();
        SQLTipOfTheDay.createTable();
        SQLTipOfTheDaySuggestion.createTable();
        SQLSuggestionCategory.insertTestData();
        SQLAdminAction.insertTestData();
    }

    /**
     * This method creates all necessary tables for the database and inserts remark codes, as that's real info
     * and not just test data.
     * @throws SQLException
     */
    public static void createEmbeddedTablesWithRemarkCodes() throws SQLException {
        SQLAdminAction.createTable();
        SQLAdminUser.createTable();
        SQLAdminLog.createTable();
        SQLCarcOrRarc.createTable();
        SQLCarcOrRarc.insertTestData();
        SQLClient.createTable();
        SQLCSSPath.createTable();
        SQLDepartment.createTable();
        SQLFontSize.createTable();
        SQLFontStyle.createTable();
        SQLSuggestionCategory.createTable();
        SQLGeneralSuggestion.createTable();
        SQLPayer.createTable();
        SQLPayerSuggestion.createTable();
        SQLProvider.createTable();
        SQLRemarkCode.createTable();
        SQLRemarkCode.insertTestData();
        SQLRemarkPhrase.createTable();
        SQLSavedDatabase.createTable();
        SQLSettings.createTable();
        SQLTipOfTheDay.createTable();
        SQLTipOfTheDaySuggestion.createTable();
        SQLSuggestionCategory.insertTestData();
        SQLAdminAction.insertTestData();
    }


    /**
     * This method creates all tables necessary for the database and inserts test data as well.
     *
     * This method does not include data meant only for embedded databases.
     * @throws SQLException
     */
    public static void createTablesWithTestData() throws SQLException {
        SQLAdminAction.createTable();
        SQLAdminAction.insertTestData();
        SQLAdminUser.createTable();
        SQLAdminUser.insertTestData();
        SQLAdminLog.createTable();
        SQLAdminLog.insertTestData();
        SQLCarcOrRarc.createTable();
        SQLCarcOrRarc.insertTestData();
        SQLClient.createTable();
        SQLClient.insertTestData();
        SQLDepartment.createTable();
        SQLDepartment.insertTestData();
        SQLSuggestionCategory.createTable();
        SQLSuggestionCategory.insertTestData();
        SQLGeneralSuggestion.createTable();
        SQLGeneralSuggestion.insertTestData();
        SQLPayer.createTable();
        SQLPayer.insertTestData();
        SQLPayerSuggestion.createTable();
        SQLPayerSuggestion.insertTestData();
        SQLProvider.createTable();
        SQLProvider.insertTestData();
        SQLRemarkCode.createTable();
        SQLRemarkCode.insertTestData();
        SQLRemarkPhrase.createTable();
        SQLRemarkPhrase.insertTestData();
        SQLTipOfTheDay.createTable();
        SQLTipOfTheDay.insertTestData();
        SQLTipOfTheDaySuggestion.createTable();
        SQLTipOfTheDaySuggestion.insertTestData();
        SQLRemarkCode.insertRandomUsedWithDataAndNotes();
        SQLRemarkPhrase.insertRandomRemarkUseData();
    }

    /**
     * This method creates all necessary tables for the database, but they are empty.
     *
     * This method does not include data meant only for embedded databases.
     * @throws SQLException
     */
    public static void createEmptyTables() throws SQLException {
        SQLAdminAction.createTable();
        SQLAdminUser.createTable();
        SQLAdminLog.createTable();
        SQLCarcOrRarc.createTable();
        SQLClient.createTable();
        SQLDepartment.createTable();
        SQLSuggestionCategory.createTable();
        SQLGeneralSuggestion.createTable();
        SQLPayer.createTable();
        SQLPayerSuggestion.createTable();
        SQLProvider.createTable();
        SQLRemarkCode.createTable();
        SQLRemarkPhrase.createTable();
        SQLTipOfTheDay.createTable();
        SQLTipOfTheDaySuggestion.createTable();
        SQLSuggestionCategory.insertTestData();
        SQLAdminAction.insertTestData();
    }

    /**
     * This method creates all necessary tables for the database and inserts remark codes, as that's real info
     * and not just test data.
     *
     * This method does not include data meant only for embedded databases.
     * @throws SQLException
     */
    public static void createTablesWithRemarkCodes() throws SQLException {
        SQLAdminAction.createTable();
        SQLAdminUser.createTable();
        SQLAdminLog.createTable();
        SQLCarcOrRarc.createTable();
        SQLCarcOrRarc.insertTestData();
        SQLClient.createTable();
        SQLDepartment.createTable();
        SQLSuggestionCategory.createTable();
        SQLGeneralSuggestion.createTable();
        SQLPayer.createTable();
        SQLPayerSuggestion.createTable();
        SQLProvider.createTable();
        SQLRemarkCode.createTable();
        SQLRemarkCode.insertTestData();
        SQLRemarkPhrase.createTable();
        SQLTipOfTheDay.createTable();
        SQLTipOfTheDaySuggestion.createTable();
        SQLSuggestionCategory.insertTestData();
        SQLAdminAction.insertTestData();
    }
}
