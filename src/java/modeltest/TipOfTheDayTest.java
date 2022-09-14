package modeltest;


import model.TipOfTheDay;

import java.sql.SQLException;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class TipOfTheDayTest {
    
    /**
     * This tests the ability of the corresponding class to retrieve its objects from the database in a HashMap.
     * Tests "getNewMapOfAll()" method.
     * @throws SQLException
     */
    public static void testGetNewMapOfAll() throws SQLException {
        for (model.TipOfTheDay tipOfTheDay : TipOfTheDay.getNewMapOfAll().values()) {
            System.out.println(tipOfTheDay.getDescription());
        }
    }

    /**
     * Tests the insert method for the corresponding Class.
     * @throws SQLException
     */
    public static void testInsert() throws SQLException {
        TipOfTheDay tipOfTheDay = new TipOfTheDay("Test Insert", 4);
        tipOfTheDay.insert();
    }

    /**
     * Tests the update method for the corresponding Class.
     * @throws SQLException
     */
    public static void testUpdate() throws SQLException {
        TipOfTheDay tipOfTheDay = new TipOfTheDay("Test Update", 4);
        tipOfTheDay.update();
    }

    /**
     * Tests the delete method for the corresponding Class.
     * @throws SQLException
     */
    public static void testDelete() throws SQLException{
        TipOfTheDay tipOfTheDay = new TipOfTheDay("Test Delete", 4);
        tipOfTheDay.delete();
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
