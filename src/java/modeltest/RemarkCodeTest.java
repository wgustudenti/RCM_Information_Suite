package modeltest;

import model.RemarkCode;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class RemarkCodeTest {

    /**
     * This tests the ability of the corresponding class to retrieve its objects from the database in a HashMap.
     * Tests "getNewMapOfAll()" method.
     * @throws SQLException
     */
    public static void testGetNewMapOfAll() throws SQLException {
        for (model.RemarkCode remarkCode: RemarkCode.getNewMapOfAll().values()){
            System.out.println(remarkCode.getDescription());
        }
    }
    
    /**
     * This method tests the getRemarkCodeUsedWith
     * @throws SQLException
     */
    public static void testRemarkCodeUsedWithMap() throws SQLException {
        HashMap<String, HashMap<String, Integer>> codeUseWithData = RemarkCode.getMapOfMapOfUseData();
        Set<String> keySet = codeUseWithData.keySet();
        for (String key:keySet){
            for (String keyTwo:keySet) {
                Integer value = codeUseWithData.get(key).get(keyTwo);
                System.out.println("Key 1: " + key + " Key 2: " + keyTwo + " Value: " + value);
            }
        }
    }

    /**
     * Tests the corresponding method in the corresponding class.
     * @throws SQLException
     */
    public static void testGetTimesUsedWithRemarkCodeMap() throws SQLException {
        HashMap mapOfAllRemarkCodes = RemarkCode.getNewMapOfAll();
        RemarkCode remarkCode = (RemarkCode) mapOfAllRemarkCodes.get("6");
        System.out.println(remarkCode.getTimesUsedWithRemarkCodeMap().get("14"));
    }

    /**
     * Tests the corresponding method in the corresponding class.
     * @throws SQLException
     */
    public static void testGetPrefixAndGetSuffix() throws SQLException {
        RemarkCode.initilizeStaticVariables();
        for(RemarkCode remarkCode : RemarkCode.getMapOfAll().values()){
            System.out.println(remarkCode.getRemarkCode() + ": Prefix - " + remarkCode.getCodePrefix() + "  Suffix - "
                    + remarkCode.getCodeSuffix());
        }
    }

    /**
     * Tests the insert method for the corresponding Class.
     * @throws SQLException
     */
    public static void testInsert() throws SQLException {
        RemarkCode remarkCode = new RemarkCode("TestInsert", "Test", "Test", 1,
                9, new HashMap<>());
        remarkCode.insert();
    }

    /**
     * Tests the update method for the corresponding Class.
     * @throws SQLException
     */
    public static void testUpdate() throws SQLException {
        RemarkCode remarkCode = new RemarkCode("TestInsert", "Test Update", "Test", 1,
                9, new HashMap<>());
        remarkCode.update();
    }

    /**
     * Tests the delete method for the corresponding Class.
     * @throws SQLException
     */
    public static void testDelete() throws SQLException{
        RemarkCode remarkCode = new RemarkCode("TestInsert", "Test Delete", "Test", 1,
                9, new HashMap<>());
        remarkCode.delete();
    }

    /**
     * Tests the corresponding method in the corresponding class.
     * @throws SQLException
     */
    public static void testFilterAlertsFromMapOfAll() throws SQLException {
        model.RemarkCode.initilizeStaticVariables();
        for (RemarkCode remarkCode : model.RemarkCode.filterAlertsFromMap(RemarkCode.getMapOfAll()).values()) {
            System.out.println(remarkCode.getDescription());
        }
    }

    /**
     * Tests all methods within this class.
     * @throws SQLException
     */
    public static void testAll() throws SQLException{
        testGetNewMapOfAll();
        testRemarkCodeUsedWithMap();
        testFilterAlertsFromMapOfAll();
        testGetPrefixAndGetSuffix();
        testGetTimesUsedWithRemarkCodeMap();
        testInsert();
        testUpdate();
        testDelete();
    }
}
