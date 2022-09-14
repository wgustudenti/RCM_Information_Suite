package modeltest;

import model.SavedDatabase;

import java.sql.SQLException;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class SavedDatabaseTest {
    
    /**
     * This tests the ability of the corresponding class to retrieve its objects from the database in a HashMap.
     * Tests "getNewMapOfAll()" method.
     * @throws SQLException
     */
    public static void testGetNewMapOfAll() throws SQLException {
        for (model.SavedDatabase savedDatabase: SavedDatabase.getNewMapOfAll().values()){
            System.out.println(savedDatabase.getVendor());
        }
    }

    /**
     * Tests the insert method for the corresponding Class.
     * @throws SQLException
     */
    public static void testInsert() throws SQLException {
        SavedDatabase savedDatabase = new SavedDatabase("Test Insert", "Test", "Vendor",
                "", "password", 2);
        savedDatabase.insert();
    }

    /**
     * Tests the update method for the corresponding Class.
     * @throws SQLException
     */
    public static void testUpdate() throws SQLException {
        SavedDatabase savedDatabase = new SavedDatabase("Test Update", "Test", "Vendor",
                "", "password", 2);
        savedDatabase.update();
    }

    /**
     * Tests the delete method for the corresponding Class.
     * @throws SQLException
     */
    public static void testDelete() throws SQLException{
        SavedDatabase savedDatabase = new SavedDatabase("Test Update", "Test", "Vendor",
                "", "password", 2);
        savedDatabase.delete();
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
