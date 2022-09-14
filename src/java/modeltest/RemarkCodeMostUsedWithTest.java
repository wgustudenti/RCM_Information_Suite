package modeltest;

import model.RemarkCode;
import model.RemarkCodeMostUsedWith;

import java.sql.SQLException;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class RemarkCodeMostUsedWithTest {

    /**
     * Tests the corresponding method in the corrresponding class.
     * @throws SQLException
     */
    public static void testGetRemarkCodesUsedWithPrimary() throws SQLException {
        RemarkCode.initilizeStaticVariables();
        for(RemarkCode remarkCode : RemarkCode.getMapOfAll().values()) {
            for (RemarkCodeMostUsedWith remarkCodeMostUsedWith :
                    RemarkCodeMostUsedWith.getRemarkCodesUsedWithPrimary(remarkCode, RemarkCode.getMapOfAll())){
                System.out.println(remarkCode.getRemarkCode() + " USED WITH " + remarkCodeMostUsedWith.getRemarkCode()
                        + " " + remarkCodeMostUsedWith.getTimesUsedWithPrimaryCode() + " TIMES.");
            }
        }
    }


    /**
     * Tests all methods within this class.
     * @throws SQLException
     */
    public static void testAll() throws SQLException{
        testGetRemarkCodesUsedWithPrimary();
    }
}
