package modeltest;

import model.RemarkCode;
import model.RemarkPhrase;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class RemarkPhraseTest {

    /**
     * This tests the ability of the corresponding class to retrieve its objects from the database in a HashMap.
     * Tests "getNewMapOfAll()" method.
     * @throws SQLException
     */
    public static void testGetNewMapOfAll() throws SQLException {
        for (model.RemarkPhrase remarkPhrase : RemarkPhrase.getNewMapOfAll().values()) {
            System.out.println(remarkPhrase.getPhrase());
        }
    }

    /**
     * Tests the insert method for the corresponding Class.
     * @throws SQLException
     */
    public static void testInsert() throws SQLException {
        RemarkPhrase remarkPhrase = new RemarkPhrase("Test Insert", new HashMap<>(), 7);
        remarkPhrase.insert();
    }

    /**
     * Tests the update method for the corresponding Class.
     * @throws SQLException
     */
    public static void testUpdate() throws SQLException {
    RemarkPhrase remarkPhrase = new RemarkPhrase("Test Update", new HashMap<>(), 7);
        remarkPhrase.update();
    }

    /**
     * Tests the delete method for the corresponding Class.
     * @throws SQLException
     */
    public static void testDelete() throws SQLException{
        RemarkPhrase remarkPhrase = new RemarkPhrase("Test Delete", new HashMap<>(), 7);
        remarkPhrase.delete();
    }

    /**
     * This method tests the corresponding method in the corresponding class.
     * @throws SQLException
     */
    public static void testUpdatePhraseData() throws SQLException {
        RemarkPhrase.updatePhraseData("delete", RemarkCode.getMapOfAll().get("3"));
        RemarkPhrase.updatePhraseData("phonish trouble", RemarkCode.getMapOfAll().get("3"));
    }

    /**
     * Tests all methods within this class.
     * @throws SQLException
     */
    public static void testAll() throws SQLException {
        testGetNewMapOfAll();
        testInsert();
        testUpdate();
        testDelete();
        testUpdatePhraseData();
    }

}
