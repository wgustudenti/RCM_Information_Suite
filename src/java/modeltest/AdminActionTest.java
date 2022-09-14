package modeltest;

import model.AdminAction;

import java.sql.SQLException;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class AdminActionTest {

    /**
     * This tests the ability of the corresponding class to retrieve its objects from the database in a HashMap.
     * Tests "getNewMapOfAll()" method.
     * @throws SQLException
     */
    public static void testGetNewMapOfAll() throws SQLException {
        for (AdminAction adminAction: AdminAction.getNewMapOfAll().values()){
            System.out.println(adminAction.getDescription());
        }
    }

    /**
     * Tests the insert method for the corresponding Class.
     * @throws SQLException
     */
    public static void testInsert() throws SQLException {
        AdminAction adminAction = new AdminAction("Test Insert", 0);
        adminAction.insert();
    }

    /**
     * Tests the update method for the corresponding Class.
     * @throws SQLException
     */
    public static void testUpdate() throws SQLException {
        AdminAction adminAction = new AdminAction("Test Update", 22);
        adminAction.update();
    }

    /**
     * Tests the delete method for the corresponding Class.
     * @throws SQLException
     */
    public static void testDelete() throws SQLException{
        AdminAction adminAction = new AdminAction("Test Delete", 22);
        adminAction.delete();
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
