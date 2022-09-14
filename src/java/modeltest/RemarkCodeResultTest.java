package modeltest;

import model.RemarkCode;
import model.RemarkCodeResult;
import model.RemarkPhrase;

import java.sql.SQLException;

/**
 * This class is meant to test the methods in its corresponding class in the "model" package.
 */
public abstract class RemarkCodeResultTest {

    /**
     * Tests the corresponding method in the corresponding class.
     * @throws SQLException
     */
    public static void testSearchAllPhrases() throws SQLException {
        RemarkCode.initilizeStaticVariables();
        RemarkPhrase.initilizeStaticVariables();
        for(RemarkCodeResult remarkCodeResult: RemarkCodeResult.searchAll("dsfkjafkealkn", RemarkCode.getMapOfAll())){
            System.out.println(remarkCodeResult.getRemarkCode() + " " + remarkCodeResult.getDescription() + " "
                    + remarkCodeResult.getTimesUsedWithThisPhrase());
        }
        for(RemarkCodeResult remarkCodeResult: RemarkCodeResult.searchAll("Insert", RemarkCode.getMapOfAll())){
            System.out.println(remarkCodeResult.getRemarkCode() + " " + remarkCodeResult.getDescription() + " "
                    + remarkCodeResult.getTimesUsedWithThisPhrase());
        }
        for(RemarkCodeResult remarkCodeResult: RemarkCodeResult.searchAll("Missing", RemarkCode.getMapOfAll())){
            System.out.println(remarkCodeResult.getRemarkCode() + " " + remarkCodeResult.getDescription() + " "
                    + remarkCodeResult.getTimesUsedWithThisPhrase());
        }
    }

    /**
     * Tests all methods within this class.
     * @throws SQLException
     */
    public static void testAll() throws SQLException{
        testSearchAllPhrases();
    }
}
