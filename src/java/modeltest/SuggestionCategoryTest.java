package modeltest;

import model.SuggestionCategory;

import java.sql.SQLException;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class SuggestionCategoryTest {

    /**
     * This tests the ability of the corresponding class to retrieve its objects from the database in a HashMap.
     * Tests "getNewMapOfAll()" method.
     * @throws SQLException
     */
    public static void testGetNewMapOfAll() throws SQLException {
        for (model.SuggestionCategory suggestionCategory : SuggestionCategory.getNewMapOfAll().values()) {
            System.out.println(suggestionCategory.getDescription());
        }
    }

    /**
     * Tests the insert method for the corresponding Class.
     * @throws SQLException
     */
    public static void testInsert() throws SQLException {
        SuggestionCategory suggestionCategory = new SuggestionCategory("Test Insert", 5);
        suggestionCategory.insert();
    }

    /**
     * Tests the update method for the corresponding Class.
     * @throws SQLException
     */
    public static void testUpdate() throws SQLException {
        SuggestionCategory suggestionCategory = new SuggestionCategory("Test Update", 5);
        suggestionCategory.update();
    }

    /**
     * Tests the delete method for the corresponding Class.
     * @throws SQLException
     */
    public static void testDelete() throws SQLException{
        SuggestionCategory suggestionCategory = new SuggestionCategory("Test Delete", 5);
        suggestionCategory.delete();
    }

    /**
     * Tests all methods within this class.
     * @throws SQLException
     */
    public static void testAll() throws SQLException{
        testGetNewMapOfAll();
        testInsert();
        testUpdate();
        testDelete();
    }
}
