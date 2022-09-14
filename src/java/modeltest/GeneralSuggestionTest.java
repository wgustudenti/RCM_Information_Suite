package modeltest;

import model.GeneralSuggestion;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class GeneralSuggestionTest {
    
    /**
     * This tests the ability of the corresponding class to retrieve its objects from the database in a HashMap.
     * Tests "getNewMapOfAll()" method.
     * @throws SQLException
     */
    public static void testGetNewMapOfAll() throws SQLException {
        for (GeneralSuggestion generalSuggestion: GeneralSuggestion.getNewMapOfAll().values()){
            System.out.println(generalSuggestion.getDescription());
        }
    }

    /**
     * Tests the insert method for the corresponding Class.
     * @throws SQLException
     */
    public static void testInsert() throws SQLException {
        GeneralSuggestion generalSuggestion = new GeneralSuggestion("Test Insert", Timestamp.valueOf(LocalDateTime.now()),
                1, 4);
        generalSuggestion.insert();
    }

    /**
     * Tests the update method for the corresponding Class.
     * @throws SQLException
     */
    public static void testUpdate() throws SQLException {
        GeneralSuggestion generalSuggestion = new GeneralSuggestion("Test Update", Timestamp.valueOf(LocalDateTime.now()),
                1, 4);
        generalSuggestion.update();
    }

    /**
     * Tests the delete method for the corresponding Class.
     * @throws SQLException
     */
    public static void testDelete() throws SQLException{
        GeneralSuggestion generalSuggestion = new GeneralSuggestion("Test Insert", Timestamp.valueOf(LocalDateTime.now()),
                1, 4);
        generalSuggestion.delete();
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
