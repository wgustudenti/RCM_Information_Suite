package modeltest;

import model.Payer;

import java.sql.SQLException;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class PayerTest {
    
    /**
     * This tests the ability of the corresponding class to retrieve its objects from the database in a HashMap.
     * Tests "getNewMapOfAll()" method.
     * @throws SQLException
     */
    public static void testGetNewMapOfAll() throws SQLException {
        for (model.Payer payer: Payer.getNewMapOfAll().values()){
            System.out.println(payer.getNameOnDocument());
        }
    }

    /**
     * Tests the corresponding method in the corresponding class.
     * @throws SQLException
     */
    public static void testSearchPayerForAll() throws SQLException {
        Payer.initilizeStaticVariables();
        for(Payer payer :  Payer.searchPayerForAll("Rich")){
            System.out.println(payer.getNameOnDocument());
        }
    }

    /**
     * Tests the corresponding method in the corresponding class.
     * @throws SQLException
     */
    public static void testSearchPayerForClient() throws SQLException {
        Payer.initilizeStaticVariables();
        for(Payer payer : Payer.searchPayerForClient("Rich", 1)){
            System.out.println(payer.getNameOnDocument());
        }
    }

    /**
     * Tests the insert method for the corresponding Class.
     * @throws SQLException
     */
    public static void testInsert() throws SQLException {
        Payer payer = new Payer("Test Insert", "Test", 1, 9);
        payer.insert();
    }

    /**
     * Tests the update method for the corresponding Class.
     * @throws SQLException
     */
    public static void testUpdate() throws SQLException {
        Payer payer = new Payer("Test Update", "Test", 1, 9);
        payer.update();
    }

    /**
     * Tests the delete method for the corresponding Class.
     * @throws SQLException
     */
    public static void testDelete() throws SQLException{
        Payer payer = new Payer("Test Insert", "Test", 1, 9);
        payer.delete();
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
        testSearchPayerForAll();
        testSearchPayerForClient();
    }
}
