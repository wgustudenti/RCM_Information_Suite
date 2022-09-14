package sqldata;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import masterdata.SQLGeneral;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class exists to test the methods declared for the classes in the sqldata package.
 */
public abstract class SQLTest {

    /**
     * This method will call all other SQL basic CRUD methods.  Should be used on a new database with no tables.
     * This is the general class and is made to work with SQL.  May need to be overriden for different kinds of
     * databases.
     */
    public static void testAllSQLMethods() throws SQLException {
        testSQLGeneralEscapeApostrophes();
        testSQLAdminAction();
        testSQLAdminUser();
        testSQLAdminLog();
        testSQLCarcOrRarc();
        testSQLClient();
        testSQLCSSPath();
        testSQLDepartment();
        testSQLFontStyle();
        testSQLFontSize();
        testSQLSuggestionCategory();
        testSQLGeneralSuggestion();
        testSQLPayer();
        testSQLPayerSuggestion();
        testSQLProvider();
        testSQLSavedDatabase();
        testSQLSettings();
        testSQLTipOfTheDay();
        testSQLTipOfTheDaySuggestion();
        testSQLRemarkCode();
        testSQLRemarkPhrase();
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLAdminAction() throws SQLException {
        SQLAdminAction.createTable();
        SQLAdminAction.insertTestData();
        SQLAdminAction.insert("Failure");
        SQLAdminAction.insert("Successful Insert");
        SQLAdminAction.update("Successful Update", 1);
        SQLAdminAction.delete(4);
        ResultSet resultSet = SQLAdminAction.getAll();
        while (resultSet.next()){
            System.out.println("SQLAdminAction: " + resultSet.getString("Description"));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLAdminUser() throws SQLException {
        SQLAdminUser.createTable();
        SQLAdminUser.insertTestData();
        SQLAdminUser.insert("ThisShould", "BeDeleted");
        SQLAdminUser.insert("Not Failure", "Password");
        SQLAdminUser.insert("Successful Insert", "P@$$w0rd");
        SQLAdminUser.update("Not Failure", "Succesful_Update");
        SQLAdminUser.delete("ThisShould");
        ResultSet resultSet = SQLAdminUser.getAll();
        while (resultSet.next()){
            System.out.println("SQLAdminUser: " + resultSet.getString("Username"));
        };
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLAdminLog() throws SQLException {
        SQLAdminLog.createTable();
        SQLAdminLog.insertTestData();
        SQLAdminLog.insert("John", 2, "Company Name");
        SQLAdminLog.insert("Successful Insert", 2, "Company Name");
        SQLAdminLog.update("Carol", 2, "Company Name", 1);
        SQLAdminLog.delete(4);
        ResultSet resultSet = SQLAdminLog.getAll();
        while (resultSet.next()){
            System.out.println("SQLAdminLog: " + resultSet.getString("ActionTime"));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLCarcOrRarc() throws SQLException {
        SQLCarcOrRarc.createTable();
        SQLCarcOrRarc.insertTestData();
        SQLCarcOrRarc.insert("Successful Insert");
        SQLCarcOrRarc.insert("CARC");
        SQLCarcOrRarc.update("Successful Update", 2);
        SQLCarcOrRarc.delete(4);
        ResultSet resultSet = SQLCarcOrRarc.getAll();
        while (resultSet.next()){
            System.out.println("SQLCarcOrRarc: " + (resultSet.getString("Description")));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLClient() throws SQLException {
        SQLClient.createTable();
        SQLClient.insertTestData();
        SQLClient.insert("OVR");
        SQLClient.insert("Successful Insert");
        SQLClient.update("Successful Update", "NULL", "NULL", 4);
        SQLClient.delete(4);
        ResultSet resultSet = SQLClient.getAll();
        while (resultSet.next()){
            System.out.println("SQLClient: " + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLCSSPath() throws SQLException {
        SQLCSSPath.createTable();
        SQLCSSPath.insertTestData();
        SQLCSSPath.insert("in", "Path 4");
        SQLCSSPath.insert("Successful Insert", "Path 5");
        SQLCSSPath.update("Successful Update", "Path 6", 2);
        SQLCSSPath.delete(4);
        ResultSet resultSet = SQLCSSPath.getAll();
        while (resultSet.next()) {
            System.out.println("CSSPath: " + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLDepartment() throws SQLException {
        SQLDepartment.createTable();
        SQLDepartment.insertTestData();
        SQLDepartment.insert("Department of Commerce", 2);
        SQLDepartment.insert("Department of Success", 2);
        SQLDepartment.update("Department of Defence", 2, 1);
        SQLDepartment.delete(10);
        ResultSet resultSet = SQLDepartment.getAll();
        while (resultSet.next()) {
            System.out.println("SQLDepartment: " + resultSet.getString(3));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLFontSize() throws SQLException {
        SQLFontSize.createTable();
        SQLFontSize.insertTestData();
        SQLFontSize.insert("Large and in charge");
        SQLFontSize.insert("The font of success");
        SQLFontSize.insert("Successful Insert");
        SQLFontSize.update("Successful Update", 5);
        SQLFontSize.delete(4);
        ResultSet resultSet = SQLFontSize.getAll();
        while (resultSet.next()){
            System.out.println("SQLFontSize " + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLFontStyle() throws SQLException {
        SQLFontStyle.createTable();
        SQLFontStyle.insertTestData();
        SQLFontStyle.insert("Large and in charge");
        SQLFontStyle.insert("The font of success");
        SQLFontStyle.insert("Successful Insert");
        SQLFontStyle.update("Successful Update", 5);
        SQLFontStyle.delete(4);
        ResultSet resultSet = SQLFontStyle.getAll();
        while (resultSet.next()){
            System.out.println("SQLFontStyle " + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLSuggestionCategory() throws SQLException {
        SQLSuggestionCategory.createTable();
        SQLSuggestionCategory.insertTestData();
        SQLSuggestionCategory.insert("Failure");
        SQLSuggestionCategory.insert("Successful Insert");
        SQLSuggestionCategory.update("Successful Insert", 5);
        SQLSuggestionCategory.delete(4);
        ResultSet resultSet = SQLSuggestionCategory.getAll();
        while (resultSet.next()){
            System.out.println("Suggestion Category:" + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLGeneralSuggestion() throws SQLException {
        SQLGeneralSuggestion.createTable();
        SQLGeneralSuggestion.insertTestData();
        SQLGeneralSuggestion.insert("Large and in charge", 1);
        SQLGeneralSuggestion.insert("Successful Insert", 1);
        SQLGeneralSuggestion.update("Successful Update", 1, 2);
        SQLGeneralSuggestion.delete(4);
        ResultSet resultSet = SQLGeneralSuggestion.getAll();
        while (resultSet.next()) {
            System.out.println("SQLGeneralSuggestion: " + resultSet.getString(3));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLPayer() throws SQLException {
        SQLPayer.createTable();
        SQLPayer.insertTestData();
        SQLPayer.insert("James", "Jim", 2);
        SQLPayer.insert("Successful Insert", "Even Successfuller", 2);
        SQLPayer.update("Successful Update", "Triple Success", 2, 5);
        SQLPayer.delete(4);
        ResultSet resultSet = SQLPayer.getAll();
        while (resultSet.next()){
            System.out.println(resultSet.getString(3));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLPayerSuggestion() throws SQLException {
        SQLPayerSuggestion.createTable();
        SQLPayerSuggestion.insertTestData();
        SQLPayerSuggestion.insert("James", "Jim", 2);
        SQLPayerSuggestion.insert("Successful Insert", "Even Successfuller", 2);
        SQLPayerSuggestion.update("Successful Update", "Triple Success", 2, 5);
        SQLPayerSuggestion.delete(4);
        ResultSet resultSet = SQLPayerSuggestion.getAll();
        while (resultSet.next()){
            System.out.println(resultSet.getString(3));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLProvider() throws SQLException {
        SQLProvider.createTable();
        SQLProvider.insertTestData();
        SQLProvider.insert("George", "Washington", "0765544466", 2);
        SQLProvider.insert("Success", "ful Insert", "057989979", 2);
        SQLProvider.update("Successful", "Update", "8878665764", 2, 1);
        SQLProvider.delete(4);
        ResultSet resultSet = SQLProvider.getAll();
        while (resultSet.next()){
            System.out.println("SQLProvider: " + resultSet.getString(4));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLSavedDatabase() throws SQLException {
        SQLSavedDatabase.createTable();
        SQLSavedDatabase.insertTestData();
        SQLSavedDatabase.insert("firstdata", "Failure", "Dob", "c drive", "SQL");
        SQLSavedDatabase.insert("Successful Insert", "Success", "Sue", "P drive", "PrQl");
        SQLSavedDatabase.update("Succesful Update", "Tourn", "DO", "h drive", "PsSQL", 1);
        SQLSavedDatabase.delete(4);
        ResultSet resultSet = SQLSavedDatabase.getAll();
        while (resultSet.next()){
                System.out.println(resultSet.getString(2));
            }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLSettings() throws SQLException {
        SQLSettings.createTable();
        SQLSettings.insertTestData();
        SQLSettings.insert(2, 2, 2, 2);
        SQLSettings.insert(2, 2, 2, 2);
        SQLSettings.update(2, 2, 2, 2, 1);
        SQLSettings.delete(4);
        ResultSet resultSet = SQLSettings.getAll();
        while (resultSet.next()){
            System.out.println("Settings: " + resultSet.getString(1));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLTipOfTheDay() throws SQLException {
        SQLTipOfTheDay.createTable();
        SQLTipOfTheDay.insertTestData();
        SQLTipOfTheDay.insert("Do your hair");
        SQLTipOfTheDay.insert("Successful Insert");
        SQLTipOfTheDay.update("Successful Update", 1);
        SQLTipOfTheDay.delete(3);
        ResultSet resultSet = SQLTipOfTheDay.getAll();
        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testSQLTipOfTheDaySuggestion() throws SQLException {
        SQLTipOfTheDaySuggestion.createTable();
        SQLTipOfTheDaySuggestion.insertTestData();
        SQLTipOfTheDaySuggestion.insert("Do your hair");
        SQLTipOfTheDaySuggestion.insert("Successful Insert");
        SQLTipOfTheDaySuggestion.update("Successful Update", 1);
        SQLTipOfTheDaySuggestion.delete(3);
        ResultSet resultSet = SQLTipOfTheDaySuggestion.getAll();
        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     *
     * Also tests not-so-basic CRUD methods.
     *
     * @throws SQLException
     */
    public static void testSQLRemarkCode() throws SQLException {
        SQLRemarkCode.createTable();
        SQLRemarkCode.insertTestData();
        SQLRemarkCode.insertRandomUsedWithDataAndNotes();
        SQLRemarkCode.update("1", 1, "Successful Update", "Success.");
        SQLRemarkCode.delete("1");
        ResultSet timesUsedForAll = SQLRemarkCode.getAll();
        SQLRemarkCode.addRemarkCodeColumn("DUCK");
        SQLRemarkCode.removeRemarkCodeColumn("DUCK");

        ObservableList<String> testList = FXCollections.observableArrayList();
        testList.add("1");
        testList.add("2");
        testList.add("3");
        SQLRemarkCode.updateMostCommonlyUsedWith(testList);

        for (String remarkCode : SQLRemarkCode.getJustRemarkCodes()) {
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
    public static void testSQLRemarkPhrase() throws SQLException {
        SQLRemarkPhrase.createTable();
        SQLRemarkPhrase.insertTestData();
        SQLRemarkPhrase.insertRandomRemarkUseData();
        SQLRemarkPhrase.update("Successful Update", 1);
        SQLRemarkPhrase.delete(2);
        SQLRemarkPhrase.updateRemarkPhraseUseDataFromSearch("insert", "3");
        SQLRemarkPhrase.updateRemarkPhraseUseDataFromSearch("car trouble", "3");
        SQLRemarkPhrase.addRemarkPhraseColumn("DUCK");
        SQLRemarkPhrase.removeRemarkPhraseColumn("DUCK");
        ResultSet timesUsedForAll = SQLRemarkPhrase.getAll();
        while (timesUsedForAll.next()){
            System.out.println("Remark Phrase" + timesUsedForAll.getString(1) + " "
                    + timesUsedForAll.getString(2));
        }
        SQLRemarkPhrase.updateRemarkPhraseUseDataFromSearch("insert", "3");
    }

}
