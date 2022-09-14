package modeltest;

import model.Provider;

import java.sql.SQLException;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class ProviderTest {

    /**
     * This tests the ability of the corresponding class to retrieve its objects from the database in a HashMap.
     * Tests "getNewMapOfAll()" method.
     * @throws SQLException
     */
    public static void testGetNewMapOfAll() throws SQLException {
        for (model.Provider provider: Provider.getNewMapOfAll().values()){
            System.out.println(provider.getFirstName());
        }
    }

    /**
     * Tests the corresponding method in the corresponding class.
     * @throws SQLException
     */
    public static void testSearchProviderForAll() throws SQLException {
        Provider.initilizeStaticVariables();
        for(Provider provider :  Provider.searchProviderForAll("Jeremy")){
            System.out.println(provider.getFirstName());
        }
    }

    /**
     * Tests the corresponding method in the corresponding class.
     * @throws SQLException
     */
    public static void testSearchProviderForClient() throws SQLException {
        Provider.initilizeStaticVariables();
        for(Provider provider : Provider.searchProviderForClient("Jeremy", 1)){
            System.out.println(provider.getFirstName() + " Dept. 1");
        }
        for(Provider provider : Provider.searchProviderForClient("Jeremy", 2)){
            System.out.println(provider.getFirstName() + " Dept. 2");
        }
        for(Provider provider : Provider.searchProviderForClient("", 1)){
            System.out.println(provider.getFirstName());
        }
    }

    /**
     * Tests the insert method for the corresponding Class.
     * @throws SQLException
     */
    public static void testInsert() throws SQLException {
        Provider provider = new Provider("Test Insert", "Test", "1", 1, 10);
        provider.insert();
    }

    /**
     * Tests the update method for the corresponding Class.
     * @throws SQLException
     */
    public static void testUpdate() throws SQLException {
        Provider provider = new Provider("Test Update", "Test", "1",1, 10);
        provider.update();
    }

    /**
     * Tests the delete method for the corresponding Class.
     * @throws SQLException
     */
    public static void testDelete() throws SQLException{
        Provider provider = new Provider("Test Insert", "Test", "1", 1, 10);
        provider.delete();
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
        testSearchProviderForAll();
        testSearchProviderForClient();
    }
}
