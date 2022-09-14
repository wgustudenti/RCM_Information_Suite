package modeltest;

import model.PayerSuggestion;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class PayerSuggestionTest {

    /**
     * This tests the ability of the corresponding class to retrieve its objects from the database in a HashMap.
     * Tests "getNewMapOfAll()" method.
     * @throws SQLException
     */
    public static void testGetNewMapOfAllSuggestions() throws SQLException {
        for (model.PayerSuggestion payerSuggestion: PayerSuggestion.getNewMapOfAllSuggestions().values()){
            System.out.println(payerSuggestion.getNameOnDocument() + " " + payerSuggestion.getActionTime());
        }
    }

    /**
     * Tests the insert method for the corresponding Class.
     * @throws SQLException
     */
    public static void testInsert() throws SQLException {
        PayerSuggestion payerSuggestion = new PayerSuggestion("Test Insert", "Test", 1,
                Timestamp.valueOf(LocalDateTime.now()), 4);
        payerSuggestion.insert();
    }

    /**
     * Tests the update method for the corresponding Class.
     * @throws SQLException
     */
    public static void testUpdate() throws SQLException {
        PayerSuggestion payerSuggestion = new PayerSuggestion("Test Update", "Test", 1,
                Timestamp.valueOf(LocalDateTime.now()), 4);
        payerSuggestion.update();
    }

    /**
     * Tests the delete method for the corresponding Class.
     * @throws SQLException
     */
    public static void testDelete() throws SQLException{
        PayerSuggestion payerSuggestion = new PayerSuggestion("Test Insert", "Test", 1,
                Timestamp.valueOf(LocalDateTime.now()), 4);
        payerSuggestion.delete();
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
