package databasevendor;

import masterdata.SQLGeneral;
import sqldata.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is meant to test the methods in the classes in the databasevendor package.
 */
public abstract class VendorTest {

    /**
     * This method will call all other SQL basic CRUD methods.  Should be used on a new database with no tables.
     * This is the general class and is made to work with SQL.  May need to be overriden for different kinds of
     * databases.
     */
    public static void testAllVendorMethods() throws SQLException {
        testSQLGeneralEscapeApostrophes();
        testVendorAdminAction();
        testVendorAdminUser();
        testVendorAdminLog();
        testVendorCarcOrRarc();
        testVendorClient();
        testVendorCSSPath();
        testVendorDepartment();
        testVendorFontStyle();
        testVendorFontSize();
        testVendorSuggestionCategory();
        testVendorGeneralSuggestion();
        testVendorPayer();
        testVendorPayerSuggestion();
        testVendorProvider();
        testVendorSavedDatabase();
        testVendorSettings();
        testVendorTipOfTheDay();
        testVendorTipOfTheDaySuggestion();
        testVendorRemarkCode();
        testVendorRemarkPhrase();
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorAdminAction() throws SQLException {
        VendorAdminAction.createTable();
        VendorAdminAction.insertTestData();
        VendorAdminAction.insert("Failure");
        VendorAdminAction.insert("Successful Insert");
        VendorAdminAction.update("Successful Update", 1);
        VendorAdminAction.delete(4);
        ResultSet resultSet = VendorAdminAction.getAll();
        while (resultSet.next()){
            System.out.println("VendorAdminAction: " + resultSet.getString("Description"));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorAdminUser() throws SQLException {
        VendorAdminUser.createTable();
        VendorAdminUser.insertTestData();
        VendorAdminUser.insert("ThisShould", "BeDeleted");
        VendorAdminUser.insert("Not Failure", "Password");
        VendorAdminUser.insert("Successful Insert", "P@$$w0rd");
        VendorAdminUser.update("Not Failure", "Succesful_Update");
        VendorAdminUser.delete("ThisShould");
        ResultSet resultSet = VendorAdminUser.getAll();
        while (resultSet.next()){
            System.out.println("VendorAdminUser: " + resultSet.getString("Username"));
        };
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorAdminLog() throws SQLException {
        VendorAdminLog.createTable();
        VendorAdminLog.insertTestData();
        VendorAdminLog.insert("John", 2, "Company Name");
        VendorAdminLog.insert("Successful Insert", 2, "Company Name");
        VendorAdminLog.update("Carol", 2, "Company Name", 1);
        VendorAdminLog.delete(4);
        ResultSet resultSet = VendorAdminLog.getAll();
        while (resultSet.next()){
            System.out.println("VendorAdminLog: " + resultSet.getString("ActionTime"));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorCarcOrRarc() throws SQLException {
        VendorCarcOrRarc.createTable();
        VendorCarcOrRarc.insertTestData();
        VendorCarcOrRarc.insert("Successful Insert");
        VendorCarcOrRarc.insert("CARC");
        VendorCarcOrRarc.update("Successful Update", 2);
        VendorCarcOrRarc.delete(4);
        ResultSet resultSet = VendorCarcOrRarc.getAll();
        while (resultSet.next()){
            System.out.println("VendorCarcOrRarc: " + (resultSet.getString("Description")));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorClient() throws SQLException {
        VendorClient.createTable();
        VendorClient.insertTestData();
        VendorClient.insert("OVR");
        VendorClient.insert("Successful Insert");
        VendorClient.update("Successful Update", "NULL", "NULL", 4);
        VendorClient.delete(4);
        ResultSet resultSet = VendorClient.getAll();
        while (resultSet.next()){
            System.out.println("VendorClient: " + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorCSSPath() throws SQLException {
        VendorCSSPath.createTable();
        VendorCSSPath.insertTestData();
        VendorCSSPath.insert("in", "Path 4");
        VendorCSSPath.insert("Successful Insert", "Path 5");
        VendorCSSPath.update("Successful Update", "Path 6", 2);
        VendorCSSPath.delete(4);
        ResultSet resultSet = VendorCSSPath.getAll();
        while (resultSet.next()) {
            System.out.println("CSSPath: " + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorDepartment() throws SQLException {
        VendorDepartment.createTable();
        VendorDepartment.insertTestData();
        VendorDepartment.insert("Department of Commerce", 2);
        VendorDepartment.insert("Department of Success", 2);
        VendorDepartment.update("Department of Defence", 2, 1);
        VendorDepartment.delete(10);
        ResultSet resultSet = VendorDepartment.getAll();
        while (resultSet.next()) {
            System.out.println("VendorDepartment: " + resultSet.getString(3));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorFontSize() throws SQLException {
        VendorFontSize.createTable();
        VendorFontSize.insertTestData();
        VendorFontSize.insert("Large and in charge");
        VendorFontSize.insert("The font of success");
        VendorFontSize.insert("Successful Insert");
        VendorFontSize.update("Successful Update", 5);
        VendorFontSize.delete(4);
        ResultSet resultSet = VendorFontSize.getAll();
        while (resultSet.next()){
            System.out.println("VendorFontSize " + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorFontStyle() throws SQLException {
        VendorFontStyle.createTable();
        VendorFontStyle.insertTestData();
        VendorFontStyle.insert("Large and in charge");
        VendorFontStyle.insert("The font of success");
        VendorFontStyle.insert("Successful Insert");
        VendorFontStyle.update("Successful Update", 5);
        VendorFontStyle.delete(4);
        ResultSet resultSet = VendorFontStyle.getAll();
        while (resultSet.next()){
            System.out.println("VendorFontStyle " + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorSuggestionCategory() throws SQLException {
        VendorSuggestionCategory.createTable();
        VendorSuggestionCategory.insertTestData();
        VendorSuggestionCategory.insert("Failure");
        VendorSuggestionCategory.insert("Successful Insert");
        VendorSuggestionCategory.update("Successful Insert", 5);
        VendorSuggestionCategory.delete(4);
        ResultSet resultSet = VendorSuggestionCategory.getAll();
        while (resultSet.next()){
            System.out.println("Suggestion Category:" + resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorGeneralSuggestion() throws SQLException {
        VendorGeneralSuggestion.createTable();
        VendorGeneralSuggestion.insertTestData();
        VendorGeneralSuggestion.insert("Large and in charge", 1);
        VendorGeneralSuggestion.insert("Successful Insert", 1);
        VendorGeneralSuggestion.update("Successful Update", 1, 2);
        VendorGeneralSuggestion.delete(4);
        ResultSet resultSet = VendorGeneralSuggestion.getAll();
        while (resultSet.next()) {
            System.out.println("VendorGeneralSuggestion: " + resultSet.getString(3));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorPayer() throws SQLException {
        VendorPayer.createTable();
        VendorPayer.insertTestData();
        VendorPayer.insert("James", "Jim", 2);
        VendorPayer.insert("Successful Insert", "Even Successfuller", 2);
        VendorPayer.update("Successful Update", "Triple Success", 2, 5);
        VendorPayer.delete(4);
        ResultSet resultSet = VendorPayer.getAll();
        while (resultSet.next()){
            System.out.println(resultSet.getString(3));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorPayerSuggestion() throws SQLException {
        VendorPayerSuggestion.createTable();
        VendorPayerSuggestion.insertTestData();
        VendorPayerSuggestion.insert("James", "Jim", 2);
        VendorPayerSuggestion.insert("Successful Insert", "Even Successfuller", 2);
        VendorPayerSuggestion.update("Successful Update", "Triple Success", 2, 5);
        VendorPayerSuggestion.delete(4);
        ResultSet resultSet = VendorPayerSuggestion.getAll();
        while (resultSet.next()){
            System.out.println(resultSet.getString(3));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorProvider() throws SQLException {
        VendorProvider.createTable();
        VendorProvider.insertTestData();
        VendorProvider.insert("George", "Washington", "0765544466", 2);
        VendorProvider.insert("Success", "ful Insert", "057989979", 2);
        VendorProvider.update("Successful", "Update", "8878665764", 2, 1);
        VendorProvider.delete(4);
        ResultSet resultSet = VendorProvider.getAll();
        while (resultSet.next()){
            System.out.println("VendorProvider: " + resultSet.getString(4));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorSavedDatabase() throws SQLException {
        VendorSavedDatabase.createTable();
        VendorSavedDatabase.insertTestData();
        VendorSavedDatabase.insert("firstdata", "Failure", "Dob", "c drive", "Vendor");
        VendorSavedDatabase.insert("Successful Insert", "Success", "Sue", "P drive", "PrQl");
        VendorSavedDatabase.update("Succesful Update", "Tourn", "DO", "h drive", "PsVendor", 1);
        VendorSavedDatabase.delete(4);
        ResultSet resultSet = VendorSavedDatabase.getAll();
        while (resultSet.next()){
                System.out.println(resultSet.getString(2));
            }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorSettings() throws SQLException {
        VendorSettings.createTable();
        VendorSettings.insertTestData();
        VendorSettings.insert(2, 2, 2, 2);
        VendorSettings.insert(2, 2, 2, 2);
        VendorSettings.update(2, 2, 2, 2, 1);
        VendorSettings.delete(4);
        ResultSet resultSet = VendorSettings.getAll();
        while (resultSet.next()){
            System.out.println("Settings: " + resultSet.getString(1));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorTipOfTheDay() throws SQLException {
        VendorTipOfTheDay.createTable();
        VendorTipOfTheDay.insertTestData();
        VendorTipOfTheDay.insert("Do your hair");
        VendorTipOfTheDay.insert("Successful Insert");
        VendorTipOfTheDay.update("Successful Update", 1);
        VendorTipOfTheDay.delete(3);
        ResultSet resultSet = VendorTipOfTheDay.getAll();
        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorTipOfTheDaySuggestion() throws SQLException {
        VendorTipOfTheDaySuggestion.createTable();
        VendorTipOfTheDaySuggestion.insertTestData();
        VendorTipOfTheDaySuggestion.insert("Do your hair");
        VendorTipOfTheDaySuggestion.insert("Successful Insert");
        VendorTipOfTheDaySuggestion.update("Successful Update", 1);
        VendorTipOfTheDaySuggestion.delete(3);
        ResultSet resultSet = VendorTipOfTheDaySuggestion.getAll();
        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }
    }

    /**
     * This method tests the specified class' basic CRUD methods.
     * @throws SQLException
     */
    public static void testVendorRemarkCode() throws SQLException {
        VendorRemarkCode.createTable();
        VendorRemarkCode.insertTestData();
        VendorRemarkCode.insertRandomUsedWithData();
        VendorRemarkCode.update("1", 1, "Successful Update", "Success.");
        VendorRemarkCode.delete("1");
        ResultSet timesUsedForAll = VendorRemarkCode.getAll();
        VendorRemarkCode.addRemarkCodeColumn("DUCK");
        VendorRemarkCode.removeRemarkCodeColumn("DUCK");
        while (timesUsedForAll.next()){
            System.out.println(timesUsedForAll.getString(1) + " " + timesUsedForAll.getString(2));
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
    public static void testVendorRemarkPhrase() throws SQLException {
        VendorRemarkPhrase.createTable();
        VendorRemarkPhrase.insertTestData();
        VendorRemarkPhrase.insertRandomRemarkUseData();
        VendorRemarkPhrase.update("Successful Update", 1);
        VendorRemarkPhrase.delete(2);
        VendorRemarkPhrase.addRemarkPhraseColumn("DUCK");
        VendorRemarkPhrase.removeRemarkPhraseColumn("DUCK");
        ResultSet timesUsedForAll = VendorRemarkPhrase.getAll();
        while (timesUsedForAll.next()){
            System.out.println("Remark Phrase" + timesUsedForAll.getString(1) + " "
                    + timesUsedForAll.getString(2));
        }
    }

}
