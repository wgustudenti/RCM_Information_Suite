package sqlitedata;

import sqldata.*;

import java.sql.SQLException;

/**
 * A class which is meant to be used in place of its superclass, as it is specific to the vendor.
 * The only methods which need to be included in this class are those which need to be overridden from its superclass
 *
 * This class is meant to create all necessary tables for this application in an EMPTY database.
 */
public abstract class SQLiteTable extends SQLTable {

    /**
     * This method creates all tables necessary for the database and inserts test data as well.
     * @throws SQLException
     */
    public static void createEmbeddedTablesWithTestData() throws SQLException {
        SQLiteAdminAction.createTable();
        SQLiteAdminAction.insertTestData();
        SQLiteAdminUser.createTable();
        SQLiteAdminUser.insertTestData();
        SQLiteAdminLog.createTable();
        SQLiteAdminLog.insertTestData();
        SQLiteCarcOrRarc.createTable();
        SQLiteCarcOrRarc.insertTestData();
        SQLiteClient.createTable();
        SQLiteClient.insertTestData();
        SQLiteCSSPath.createTable();
        SQLiteCSSPath.insertTestData();
        SQLiteDepartment.createTable();
        SQLiteDepartment.insertTestData();
        SQLiteFontSize.createTable();
        SQLiteFontSize.insertTestData();
        SQLiteFontStyle.createTable();
        SQLiteFontStyle.insertTestData();
        SQLiteSuggestionCategory.createTable();
        SQLiteSuggestionCategory.insertTestData();
        SQLiteGeneralSuggestion.createTable();
        SQLiteGeneralSuggestion.insertTestData();
        SQLitePayer.createTable();
        SQLitePayer.insertTestData();
        SQLitePayerSuggestion.createTable();
        SQLitePayerSuggestion.insertTestData();
        SQLiteProvider.createTable();
        SQLiteProvider.insertTestData();
        SQLiteRemarkCode.createTable();
        SQLiteRemarkCode.insertTestData();
        SQLiteRemarkPhrase.createTable();
        SQLiteRemarkPhrase.insertTestData();
        SQLiteSavedDatabase.createTable();
        SQLiteSavedDatabase.insertTestData();
        SQLiteSettings.createTable();
        SQLiteSettings.insertTestData();
        SQLiteTipOfTheDay.createTable();
        SQLiteTipOfTheDay.insertTestData();
        SQLiteTipOfTheDaySuggestion.createTable();
        SQLiteTipOfTheDaySuggestion.insertTestData();
        SQLiteRemarkCode.insertRandomUsedWithDataAndNotes();
        SQLiteRemarkPhrase.insertRandomRemarkUseData();
        SQLiteSuggestionCategory.insertTestData();
        SQLiteAdminAction.insertTestData();
    }

    /**
     * This method creates all necessary tables for the database, but they are empty.
     * @throws SQLException
     */
    public static void createEmbeddedEmptyTables() throws SQLException {
        SQLiteAdminAction.createTable();
        SQLiteAdminUser.createTable();
        SQLiteAdminLog.createTable();
        SQLiteCarcOrRarc.createTable();
        SQLiteClient.createTable();
        SQLiteCSSPath.createTable();
        SQLiteDepartment.createTable();
        SQLiteFontSize.createTable();
        SQLiteFontStyle.createTable();
        SQLiteSuggestionCategory.createTable();
        SQLiteGeneralSuggestion.createTable();
        SQLitePayer.createTable();
        SQLitePayerSuggestion.createTable();
        SQLiteProvider.createTable();
        SQLiteRemarkCode.createTable();
        SQLiteRemarkPhrase.createTable();
        SQLiteSavedDatabase.createTable();
        SQLiteSettings.createTable();
        SQLiteTipOfTheDay.createTable();
        SQLiteTipOfTheDaySuggestion.createTable();
        SQLiteSuggestionCategory.insertTestData();
        SQLiteAdminAction.insertTestData();
    }

    /**
     * This method creates all necessary tables for the database and inserts remark codes, as that's real info
     * and not just test data.
     * @throws SQLException
     */
    public static void createEmbeddedTablesWithRemarkCodes() throws SQLException {
        SQLiteAdminAction.createTable();
        SQLiteAdminUser.createTable();
        SQLiteAdminLog.createTable();
        SQLiteCarcOrRarc.createTable();
        SQLiteCarcOrRarc.insertTestData();
        SQLiteClient.createTable();
        SQLiteCSSPath.createTable();
        SQLiteDepartment.createTable();
        SQLiteFontSize.createTable();
        SQLiteFontStyle.createTable();
        SQLiteSuggestionCategory.createTable();
        SQLiteGeneralSuggestion.createTable();
        SQLitePayer.createTable();
        SQLitePayerSuggestion.createTable();
        SQLiteProvider.createTable();
        SQLiteRemarkCode.createTable();
        SQLiteRemarkCode.insertTestData();
        SQLiteRemarkPhrase.createTable();
        SQLiteSavedDatabase.createTable();
        SQLiteSettings.createTable();
        SQLiteTipOfTheDay.createTable();
        SQLiteTipOfTheDaySuggestion.createTable();
        SQLiteSuggestionCategory.insertTestData();
        SQLiteAdminAction.insertTestData();
    }


    /**
     * This method creates all tables necessary for the database and inserts test data as well.
     *
     * This method does not include data meant only for embedded databases.
     * @throws SQLException
     */
    public static void createTablesWithTestData() throws SQLException {
        SQLiteAdminAction.createTable();
        SQLiteAdminAction.insertTestData();
        SQLiteAdminUser.createTable();
        SQLiteAdminUser.insertTestData();
        SQLiteAdminLog.createTable();
        SQLiteAdminLog.insertTestData();
        SQLiteCarcOrRarc.createTable();
        SQLiteCarcOrRarc.insertTestData();
        SQLiteClient.createTable();
        SQLiteClient.insertTestData();
        SQLiteDepartment.createTable();
        SQLiteDepartment.insertTestData();
        SQLiteSuggestionCategory.createTable();
        SQLiteSuggestionCategory.insertTestData();
        SQLiteGeneralSuggestion.createTable();
        SQLiteGeneralSuggestion.insertTestData();
        SQLitePayer.createTable();
        SQLitePayer.insertTestData();
        SQLitePayerSuggestion.createTable();
        SQLitePayerSuggestion.insertTestData();
        SQLiteProvider.createTable();
        SQLiteProvider.insertTestData();
        SQLiteRemarkCode.createTable();
        SQLiteRemarkCode.insertTestData();
        SQLiteRemarkPhrase.createTable();
        SQLiteRemarkPhrase.insertTestData();
        SQLiteTipOfTheDay.createTable();
        SQLiteTipOfTheDay.insertTestData();
        SQLiteTipOfTheDaySuggestion.createTable();
        SQLiteTipOfTheDaySuggestion.insertTestData();
        SQLiteRemarkCode.insertRandomUsedWithDataAndNotes();
        SQLiteRemarkPhrase.insertRandomRemarkUseData();
        SQLiteSuggestionCategory.insertTestData();
        SQLiteAdminAction.insertTestData();
    }

    /**
     * This method creates all necessary tables for the database, but they are empty.
     *
     * This method does not include data meant only for embedded databases.
     * @throws SQLException
     */
    public static void createEmptyTables() throws SQLException {
        SQLiteAdminAction.createTable();
        SQLiteAdminUser.createTable();
        SQLiteAdminLog.createTable();
        SQLiteCarcOrRarc.createTable();
        SQLiteClient.createTable();
        SQLiteDepartment.createTable();
        SQLiteSuggestionCategory.createTable();
        SQLiteGeneralSuggestion.createTable();
        SQLitePayer.createTable();
        SQLitePayerSuggestion.createTable();
        SQLiteProvider.createTable();
        SQLiteRemarkCode.createTable();
        SQLiteRemarkPhrase.createTable();
        SQLiteTipOfTheDay.createTable();
        SQLiteTipOfTheDaySuggestion.createTable();
        SQLiteSuggestionCategory.insertTestData();
        SQLiteAdminAction.insertTestData();
    }

    /**
     * This method creates all necessary tables for the database and inserts remark codes, as that's real info
     * and not just test data.
     *
     * This method does not include data meant only for embedded databases.
     * @throws SQLException
     */
    public static void createTablesWithRemarkCodes() throws SQLException {
        SQLiteAdminAction.createTable();
        SQLiteAdminUser.createTable();
        SQLiteAdminLog.createTable();
        SQLiteCarcOrRarc.createTable();
        SQLiteCarcOrRarc.insertTestData();
        SQLiteClient.createTable();
        SQLiteDepartment.createTable();
        SQLiteSuggestionCategory.createTable();
        SQLiteGeneralSuggestion.createTable();
        SQLitePayer.createTable();
        SQLitePayerSuggestion.createTable();
        SQLiteProvider.createTable();
        SQLiteRemarkCode.createTable();
        SQLiteRemarkCode.insertTestData();
        SQLiteRemarkPhrase.createTable();
        SQLiteTipOfTheDay.createTable();
        SQLiteTipOfTheDaySuggestion.createTable();
        SQLiteSuggestionCategory.insertTestData();
        SQLiteAdminAction.insertTestData();
    }
}
