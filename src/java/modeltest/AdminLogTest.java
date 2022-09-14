package modeltest;

import model.AdminLog;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class AdminLogTest {

    /**
     * This tests the ability of the corresponding class to retrieve its objects from the database in a HashMap.
     * Tests "getNewMapOfAll()" method.
     * @throws SQLException
     */
    public static void testGetNewMapOfAll() throws SQLException {
        for (model.AdminLog adminLog: AdminLog.getNewMapOfAll().values()){
            System.out.println(adminLog.getActionTime());
        }
    }

    /**
     * Tests the insert method for the corresponding Class.
     * @throws SQLException
     */
    public static void testInsert() throws SQLException {
        AdminLog adminLog = new AdminLog("Stephen", "Test Insert", Timestamp.valueOf(LocalDateTime.now()),
                1, 4);
        adminLog.insert();
    }

    /**
     * Tests the update method for the corresponding Class.
     * @throws SQLException
     */
    public static void testUpdate() throws SQLException {
        AdminLog adminLog = new AdminLog("Stephen", "Test Update", Timestamp.valueOf(LocalDateTime.now()),
                1, 4);
        adminLog.update();
    }

    /**
     * Tests the delete method for the corresponding Class.
     * @throws SQLException
     */
    public static void testDelete() throws SQLException{
        AdminLog adminLog = new AdminLog("Stephen", "Test Insert", Timestamp.valueOf(LocalDateTime.now()),
                1, 4);
        adminLog.delete();
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
