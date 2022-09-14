package sqlitedata;

import masterdata.SQLGeneral;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class which is meant to be used in place of its superclass, as it is specific to the vendor.
 * The only methods which need to be included in this class are those which need to be overridden from its superclass
 */
public abstract class SQLiteTest {

    /**
     * This method will call all other SQL basic CRUD methods.  Should be used on a new database with no tables.
     * This is the general class and is made to work with SQL.  May need to be overriden for different kinds of
     * databases.
     */
    public static void testAllSQLiteMethods() throws SQLException {
        testSQLGeneralEscapeApostrophes();
        testSQLiteAdminAction();
        testSQLiteAdminUser();
        testSQLiteAdminLog();
        testSQLiteCarcOrRarc();
        testSQLiteClient();
        testSQLiteCSSPath();
        testSQLiteDepartment();
        testSQLiteFontStyle();
        testSQLiteFontSize();
        testSQLiteSuggestionCategory();
        testSQLiteGeneralSuggestion();
        testSQLitePayer();
        testSQLitePayerSuggestion();
        testSQLiteProvider();
        testSQLiteSavedDatabase();
        testSQLiteSettings();
        testSQLiteTipOfTheDay();
        testSQLiteTipOfTheDaySuggestion();
        testSQLiteRemarkCode();
        testSQLiteRemarkPhrase();
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteAdminAction() throws SQLException {
        SQLiteAdminAction.createTable();
        SQLiteAdminAction.insertTestData();
        SQLiteAdminAction.insert("Failure");
        SQLiteAdminAction.insert("Successful Insert");
        SQLiteAdminAction.update("Successful Update", 1);
        SQLiteAdminAction.delete(4);
        ResultSet resultSet = SQLiteAdminAction.getAll();
        while (resultSet.next()){
            System.out.println("SQLiteAdminAction: " + resultSet.getString("Description"));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteAdminUser() throws SQLException {
        SQLiteAdminUser.createTable();
        SQLiteAdminUser.insertTestData();
        SQLiteAdminUser.insert("ThisShould", "BeDeleted");
        SQLiteAdminUser.insert("Not Failure", "Password");
        SQLiteAdminUser.insert("Successful Insert", "P@$$w0rd");
        SQLiteAdminUser.update("Not Failure", "Succesful_Update");
        SQLiteAdminUser.delete("ThisShould");
        ResultSet resultSet = SQLiteAdminUser.getAll();
        while (resultSet.next()){
            System.out.println("SQLiteAdminUser: " + resultSet.getString("Username"));
        };
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteAdminLog() throws SQLException {
        SQLiteAdminLog.createTable();
        SQLiteAdminLog.insertTestData();
        SQLiteAdminLog.insert("John", 2, "Company Name");
        SQLiteAdminLog.insert("Successful Insert", 2, "Company Name");
        SQLiteAdminLog.update("Carol", 2, "Company Name", 1);
        SQLiteAdminLog.delete(4);
        ResultSet resultSet = SQLiteAdminLog.getAll();
        while (resultSet.next()){
            System.out.println("SQLiteAdminLog: " + resultSet.getString("ActionTime"));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteCarcOrRarc() throws SQLException {
        SQLiteCarcOrRarc.createTable();
        SQLiteCarcOrRarc.insertTestData();
        SQLiteCarcOrRarc.insert("Successful Insert");
        SQLiteCarcOrRarc.insert("CARC");
        SQLiteCarcOrRarc.update("Successful Update", 2);
        SQLiteCarcOrRarc.delete(4);
        ResultSet resultSet = SQLiteCarcOrRarc.getAll();
        while (resultSet.next()){
            System.out.println("SQLiteCarcOrRarc: " + (resultSet.getString("Description")));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteClient() throws SQLException {
        SQLiteClient.createTable();
        SQLiteClient.insertTestData();
        SQLiteClient.insert("OVR");
        SQLiteClient.insert("Successful Insert");
        SQLiteClient.update("Successful Update", "NULL", "NULL", 4);
        SQLiteClient.delete(4);
        ResultSet resultSet = SQLiteClient.getAll();
        while (resultSet.next()){
            System.out.println("SQLiteClient: " + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteCSSPath() throws SQLException {
        SQLiteCSSPath.createTable();
        SQLiteCSSPath.insertTestData();
        SQLiteCSSPath.insert("in", "Path 4");
        SQLiteCSSPath.insert("Successful Insert", "Path 5");
        SQLiteCSSPath.update("Successful Update", "Path 6", 2);
        SQLiteCSSPath.delete(4);
        ResultSet resultSet = SQLiteCSSPath.getAll();
        while (resultSet.next()) {
            System.out.println("CSSPath: " + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteDepartment() throws SQLException {
        SQLiteDepartment.createTable();
        SQLiteDepartment.insertTestData();
        SQLiteDepartment.insert("Department of Commerce", 2);
        SQLiteDepartment.insert("Department of Success", 2);
        SQLiteDepartment.update("Department of Defence", 2, 1);
        SQLiteDepartment.delete(10);
        ResultSet resultSet = SQLiteDepartment.getAll();
        while (resultSet.next()) {
            System.out.println("SQLiteDepartment: " + resultSet.getString(3));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteFontSize() throws SQLException {
        SQLiteFontSize.createTable();
        SQLiteFontSize.insertTestData();
        SQLiteFontSize.insert("Large and in charge");
        SQLiteFontSize.insert("The font of success");
        SQLiteFontSize.insert("Successful Insert");
        SQLiteFontSize.update("Successful Update", 5);
        SQLiteFontSize.delete(4);
        ResultSet resultSet = SQLiteFontSize.getAll();
        while (resultSet.next()){
            System.out.println("SQLFontSize " + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteFontStyle() throws SQLException {
        SQLiteFontStyle.createTable();
        SQLiteFontStyle.insertTestData();
        SQLiteFontStyle.insert("Large and in charge");
        SQLiteFontStyle.insert("The font of success");
        SQLiteFontStyle.insert("Successful Insert");
        SQLiteFontStyle.update("Successful Update", 5);
        SQLiteFontStyle.delete(4);
        ResultSet resultSet = SQLiteFontStyle.getAll();
        while (resultSet.next()){
            System.out.println("SQLiteFontStyle " + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteSuggestionCategory() throws SQLException {
        SQLiteSuggestionCategory.createTable();
        SQLiteSuggestionCategory.insertTestData();
        SQLiteSuggestionCategory.insert("Failure");
        SQLiteSuggestionCategory.insert("Successful Insert");
        SQLiteSuggestionCategory.update("Successful Insert", 5);
        SQLiteSuggestionCategory.delete(4);
        ResultSet resultSet = SQLiteSuggestionCategory.getAll();
        while (resultSet.next()){
            System.out.println("Suggestion Category:" + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteGeneralSuggestion() throws SQLException {
        SQLiteGeneralSuggestion.createTable();
        SQLiteGeneralSuggestion.insertTestData();
        SQLiteGeneralSuggestion.insert("Large and in charge", 1);
        SQLiteGeneralSuggestion.insert("Successful Insert", 1);
        SQLiteGeneralSuggestion.update("Successful Update", 1, 2);
        SQLiteGeneralSuggestion.delete(4);
        ResultSet resultSet = SQLiteGeneralSuggestion.getAll();
        while (resultSet.next()) {
            System.out.println("SQLiteGeneralSuggestion: " + resultSet.getString(3));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLitePayer() throws SQLException {
        SQLitePayer.createTable();
        SQLitePayer.insertTestData();
        SQLitePayer.insert("James", "Jim", 2);
        SQLitePayer.insert("Successful Insert", "Even Successfuller", 2);
        SQLitePayer.update("Successful Update", "Triple Success", 2, 5);
        SQLitePayer.delete(4);
        ResultSet resultSet = SQLitePayer.getAll();
        while (resultSet.next()){
            System.out.println(resultSet.getString(3));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLitePayerSuggestion() throws SQLException {
        SQLitePayerSuggestion.createTable();
        SQLitePayerSuggestion.insertTestData();
        SQLitePayerSuggestion.insert("James", "Jim", 2);
        SQLitePayerSuggestion.insert("Successful Insert", "Even Successfuller", 2);
        SQLitePayerSuggestion.update("Successful Update", "Triple Success", 2, 5);
        SQLitePayerSuggestion.delete(4);
        ResultSet resultSet = SQLitePayerSuggestion.getAll();
        while (resultSet.next()){
            System.out.println(resultSet.getString(3));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteProvider() throws SQLException {
        SQLiteProvider.createTable();
        SQLiteProvider.insertTestData();
        SQLiteProvider.insert("George", "Washington", "0765544466", 2);
        SQLiteProvider.insert("Success", "ful Insert", "057989979", 2);
        SQLiteProvider.update("Successful", "Update", "8878665764", 2, 1);
        SQLiteProvider.delete(4);
        ResultSet resultSet = SQLiteProvider.getAll();
        while (resultSet.next()){
            System.out.println("SQLiteProvider: " + resultSet.getString(4));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteSavedDatabase() throws SQLException {
        SQLiteSavedDatabase.createTable();
        SQLiteSavedDatabase.insertTestData();
        SQLiteSavedDatabase.insert("firstdata", "Failure", "Dob", "c drive", "SQLite");
        SQLiteSavedDatabase.insert("Successful Insert", "Success", "Sue", "P drive", "PrQl");
        SQLiteSavedDatabase.update("Succesful Update", "Tourn", "DO", "h drive", "PsSQLite", 1);
        SQLiteSavedDatabase.delete(4);
        ResultSet resultSet = SQLiteSavedDatabase.getAll();
        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteSettings() throws SQLException {
        SQLiteSettings.createTable();
        SQLiteSettings.insertTestData();
        SQLiteSettings.insert(2, 2, 2, 2);
        SQLiteSettings.insert(2, 2, 2, 2);
        SQLiteSettings.update(2, 2, 2, 2, 1);
        SQLiteSettings.delete(4);
        ResultSet resultSet = SQLiteSettings.getAll();
        while (resultSet.next()){
            System.out.println("Settings: " + resultSet.getString(1));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteTipOfTheDay() throws SQLException {
        SQLiteTipOfTheDay.createTable();
        SQLiteTipOfTheDay.insertTestData();
        SQLiteTipOfTheDay.insert("Do your hair");
        SQLiteTipOfTheDay.insert("Successful Insert");
        SQLiteTipOfTheDay.update("Successful Update", 1);
        SQLiteTipOfTheDay.delete(3);
        ResultSet resultSet = SQLiteTipOfTheDay.getAll();
        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteTipOfTheDaySuggestion() throws SQLException {
        SQLiteTipOfTheDaySuggestion.createTable();
        SQLiteTipOfTheDaySuggestion.insertTestData();
        SQLiteTipOfTheDaySuggestion.insert("Do your hair");
        SQLiteTipOfTheDaySuggestion.insert("Successful Insert");
        SQLiteTipOfTheDaySuggestion.update("Successful Update", 1);
        SQLiteTipOfTheDaySuggestion.delete(3);
        ResultSet resultSet = SQLiteTipOfTheDaySuggestion.getAll();
        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteRemarkCode() throws SQLException {
        SQLiteRemarkCode.createTable();
        SQLiteRemarkCode.insertTestData();
        SQLiteRemarkCode.insertRandomUsedWithDataAndNotes();
        SQLiteRemarkCode.update("1", 1, "Successful Update", "Success.");
        SQLiteRemarkCode.delete("1");
        ResultSet timesUsedForAll = SQLiteRemarkCode.getAll();
        SQLiteRemarkCode.addRemarkCodeColumn("DUCK");
        SQLiteRemarkCode.removeRemarkCodeColumn("DUCK");
        for (String remarkCode : SQLiteRemarkCode.getJustRemarkCodes()) {
            System.out.println(remarkCode);
            while (timesUsedForAll.next()) {
                System.out.println(timesUsedForAll.getString(1) + " " + timesUsedForAll.getString(2));
            }
        }
    }

    /**
     * This method tests the "escapeApostrophes()" method in the SQLGeneral class.
     */
    public static void testSQLGeneralEscapeApostrophes(){
        System.out.println(SQLGeneral.escapeApostrophes("Missing/incomplete/invalid 'from' date(s) of service."));
        System.out.println(SQLGeneral.escapeApostrophes("'Missing/incomplete/invalid 'from' date(s) of service."));
        System.out.println(SQLGeneral.escapeApostrophes("Missing/incomplete/invalid 'from' date(s) of service.'"));
        System.out.println(SQLGeneral.escapeApostrophes("Missing/incomplete/invalid from date(s) of service."));
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLiteRemarkPhrase() throws SQLException {
        SQLiteRemarkPhrase.createTable();
        SQLiteRemarkPhrase.insertTestData();
        SQLiteRemarkPhrase.insertRandomRemarkUseData();
        SQLiteRemarkPhrase.update("Successful Update", 1);
        SQLiteRemarkPhrase.delete(2);
        SQLiteRemarkPhrase.addRemarkPhraseColumn("DUCK");
        SQLiteRemarkPhrase.removeRemarkPhraseColumn("DUCK");
        ResultSet timesUsedForAll = SQLiteRemarkPhrase.getAll();
        while (timesUsedForAll.next()){
            System.out.println("Remark Phrase" + timesUsedForAll.getString(1) + " "
                    + timesUsedForAll.getString(2));
        }
    }
}
