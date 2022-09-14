package modeltest;


import model.CarcOrRarc;

import java.sql.SQLException;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class CarcOrRarcTest {

    /**
     * This tests the ability of the corresponding class to retrieve its objects from the database in a HashMap.
     * Tests "getNewMapOfAll()" method.
     * @throws SQLException
     */
    public static void testGetNewMapOfAll() throws SQLException {
        for (model.CarcOrRarc carcOrRarc: CarcOrRarc.getNewMapOfAll().values()){
            System.out.println(carcOrRarc.getDescription());
        }
    }

    /**
     * Tests the insert method for the corresponding Class.
     * @throws SQLException
     */
    public static void testInsert() throws SQLException {
        CarcOrRarc carcOrRarc = new CarcOrRarc("Test Insert", 3);
        carcOrRarc.insert();
    }

    /**
     * Tests the update method for the corresponding Class.
     * @throws SQLException
     */
    public static void testUpdate() throws SQLException {
        CarcOrRarc carcOrRarc = new CarcOrRarc("Test Update", 3);
        carcOrRarc.update();
    }

    /**
     * Tests the delete method for the corresponding Class.
     * @throws SQLException
     */
    public static void testDelete() throws SQLException{
        CarcOrRarc carcOrRarc = new CarcOrRarc("Test Delete", 3);
        carcOrRarc.delete();
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
