package modeltest;

import model.TipOfTheDaySuggestion;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class TipOfTheDaySuggestionTest {

    /**
     * This tests the ability of the corresponding class to retrieve its objects from the database in a HashMap.
     * Tests "getNewMapOfAllSuggestions()" method.
     * @throws SQLException
     */
    public static void testGetNewMapOfAllSuggestions() throws SQLException {
        for (model.TipOfTheDaySuggestion tipOfTheDaySuggestion : TipOfTheDaySuggestion.getNewMapOfAllSuggestions().values()) {
            System.out.println(tipOfTheDaySuggestion.getDescription() + " " + tipOfTheDaySuggestion.getActionTime());
        }
    }

    /**
     * Tests the insert method for the corresponding Class.
     * @throws SQLException
     */
    public static void testInsert() throws SQLException {
        TipOfTheDaySuggestion tipOfTheDaySuggestion = new TipOfTheDaySuggestion("Test Insert",
                Timestamp.valueOf(LocalDateTime.now()), 4);
        tipOfTheDaySuggestion.insert();
    }

    /**
     * Tests the update method for the corresponding Class.
     * @throws SQLException
     */
    public static void testUpdate() throws SQLException {
        TipOfTheDaySuggestion tipOfTheDaySuggestion = new TipOfTheDaySuggestion("Test Update",
                Timestamp.valueOf(LocalDateTime.now()), 4);
        tipOfTheDaySuggestion.update();
    }

    /**
     * Tests the delete method for the corresponding Class.
     * @throws SQLException
     */
    public static void testDelete() throws SQLException{
        TipOfTheDaySuggestion tipOfTheDaySuggestion = new TipOfTheDaySuggestion("Test Delete",
                Timestamp.valueOf(LocalDateTime.now()), 4);
        tipOfTheDaySuggestion.delete();
    }

    /**
     * Tests all methods within this class.
     * @throws SQLException
     */
    public static void testAll() throws SQLException{
        testGetNewMapOfAllSuggestions();
        testInsert();
        testUpdate();
        testDelete();
    }
}
