package modeltest;

import model.RemarkCode;

import java.sql.SQLException;

/**
 * Class meant to test all methods in this package.
 */
public abstract class All {

    /**
     * Calls the "testAll()" method from each class in this package.
     * @throws SQLException
     */
    public static void testAllPackageMethods() throws SQLException {
        RemarkCode.initilizeStaticVariables();
        AdminActionTest.testAll();
        AdminLogTest.testAll();
        AdminUserTest.testAll();
        CarcOrRarcTest.testAll();
        ClientTest.testAll();
        CSSPathTest.testAll();
        DepartmentTest.testAll();
        FontSizeTest.testAll();
        FontStyleTest.testAll();
        GeneralSuggestionTest.testAll();
        PayerTest.testAll();
        PayerSuggestionTest.testAll();
        ProviderTest.testAll();
        RemarkCodeTest.testAll();
        RemarkCodeResultTest.testAll();
        RemarkCodeMostUsedWithTest.testAll();
        RemarkPhraseTest.testAll();
        SavedDatabaseTest.testAll();
        SettingsTest.testAll();
        SuggestionCategoryTest.testAll();
        TipOfTheDaySuggestionTest.testAll();
        TipOfTheDayTest.testAll();
    }
}
