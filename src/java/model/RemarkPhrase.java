package model;

import databasevendor.VendorRemarkPhrase;
import sqldata.SQLRemarkCode;
import sqldata.SQLRemarkPhrase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating RemarkPhrase Business Objects
 * RemarkPhrase business objects represent actions that an administrator can take which can be
 * logged by the system.
 *
 * Imports RemarkCode
 */
public class RemarkPhrase implements RCMBusinessObject {
    /**
     * This represents the primary key for the row in the RemarkPhrase table in the database.
     */
    private int primaryKey;

    /**
     * This represents a phrase which has been previously searched and is the center-point of this class.
     */
    private String phrase;

    /**
     * This is a HashMap with remark codes (represented as Strings) mapping to Integers which represent
     * the number of times the remark code has been used with the phrase.
     */
    private HashMap<String, Integer> timesUsedWithPhrase;

    /**
     * HashMap of all RemarkPhrases
     */
    private static HashMap<Integer, RemarkPhrase> mapOfAll;

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public HashMap<String, Integer> getTimesUsedWithPhrase() {
        return timesUsedWithPhrase;
    }

    public void setTimesUsedWithPhrase(HashMap<String, Integer> timesUsedWithPhrase) {
        this.timesUsedWithPhrase = timesUsedWithPhrase;
    }

    public static HashMap<Integer, RemarkPhrase> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<Integer, RemarkPhrase> mapOfAll) {
        RemarkPhrase.mapOfAll = mapOfAll;
    }

    /**
     * Returns the length of the phrase attribute
     * <p>
     * Used for sorting
     *
     * @return int length of the phrase
     */
    public int getLengthOfPhrase() {
        return this.phrase.length();
    }

    /**
     * Inserts an object into the database and creates a log of the action.
     *
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorRemarkPhrase.insert(this.phrase);
    }

    /**
     * Updates an object in the database and creates a log of the action.
     *
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorRemarkPhrase.update(this.phrase, this.primaryKey);
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     *
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorRemarkPhrase.delete(this.primaryKey);
    }

    public RemarkPhrase(String phrase, HashMap<String, Integer> timesUsedWithPhrase, int primaryKey) {
        this.phrase = phrase;
        this.timesUsedWithPhrase = timesUsedWithPhrase;
        this.primaryKey = primaryKey;
    }

    /**
     * Returns a HashMap with counts of the total times used for each remark code.
     *
     * @return HashMap with counts of the total times used for each remark code.
     * @throws SQLException
     */
    public static HashMap<String, Integer> getTimesUsedForAll() throws SQLException {
        ResultSet wholeTable = VendorRemarkPhrase.getAll();
        HashMap<String, Integer> allTotalRemarkUses = new HashMap<>();
        while (wholeTable.next()) {
            for (String remarkCode : SQLRemarkCode.getJustRemarkCodes()) {
                String columnName = "R" + remarkCode;
                int timesUsedThisRow = wholeTable.getInt(columnName);
                Integer totalTimesUsed = allTotalRemarkUses.get(remarkCode);
                if (totalTimesUsed != null) {
                    totalTimesUsed = totalTimesUsed + timesUsedThisRow;
                } else {
                    totalTimesUsed = timesUsedThisRow;
                }
                allTotalRemarkUses.put(remarkCode, totalTimesUsed);
            }
        }
        if(allTotalRemarkUses.isEmpty()) {
            for (String remarkCode : SQLRemarkCode.getJustRemarkCodes()) {
                allTotalRemarkUses.put(remarkCode, 0);
            }
        }
        return allTotalRemarkUses;
    }

    /**
     * Interacts with the database and returns a refreshed mapOfAll.
     *
     * @return HashMap<Integer, RemarkPhrase> refreshed mapOfAll.
     * @throws SQLException
     */
    public static HashMap<Integer, RemarkPhrase> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorRemarkPhrase.getAll();
        HashMap<Integer, RemarkPhrase> allPayers = new HashMap<>();
        while (wholeTable.next()) {
            int primaryKey = wholeTable.getInt("ID");
            String phrase = wholeTable.getString("Phrase");
            HashMap<String, Integer> timesUsedWithPhrase = getNewTimesUsedWithPhrase(wholeTable);
            RemarkPhrase remarkPhraseToAdd = new RemarkPhrase(phrase, timesUsedWithPhrase, primaryKey);
            allPayers.put(primaryKey, remarkPhraseToAdd);
        }
        return allPayers;
    }

    /**
     * Returns a map with remark code strings as keys and with the number of times a given remark code is used with the
     * remark code parameter as values.
     *
     * @param wholeTable
     * @return HashMap of the number of times remark codes were used with this phrase
     * @throws SQLException
     */
    public static HashMap<String, Integer> getNewTimesUsedWithPhrase(ResultSet wholeTable) throws SQLException {
        ArrayList<String> justRemarkCodes = SQLRemarkCode.getJustRemarkCodes();
        HashMap<String, Integer> timesUsedWithRemarkPhraseMap = new HashMap<>();
        for (String justRemarkCode : justRemarkCodes) {
            String columnName = "R" + justRemarkCode;
            Integer timesUsedWithSingleRemark = wholeTable.getInt(columnName);
            timesUsedWithRemarkPhraseMap.put(justRemarkCode, timesUsedWithSingleRemark);
        }
        return timesUsedWithRemarkPhraseMap;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     *
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAll = getNewMapOfAll();
    }

    /**
     * First, this updates the phrase-use data in the database, because if that doesn't work,
     * I don't want the user to think the method worked, then:
     * <p>
     * This method updates the phrase data in the map for the shortest matching phrase.  If more than one are the same length,
     * this method picks only one to update.  It doesn't matter which one because it's consolidated when to totalUseData
     * is created for RemarkCodeResults.  If there are no matches, a new RemarkPhrase is put in the map.
     * <p>
     * Then this method updates the totalTimesUsed attribute for the remarkCode in the RemarkCode.getMapOfAll() HashMap.
     *
     * @param searchPhrase
     * @param remarkCode
     * @throws SQLException
     */
    public static void updatePhraseData(String searchPhrase, RemarkCode remarkCode) throws SQLException {
        SQLRemarkPhrase.updateRemarkPhraseUseDataFromSearch(searchPhrase, remarkCode.getRemarkCode());
        //Make HashMap for new RemarkPhrase object
        HashMap<String, Integer> mapForNewRemarkPhrase = new HashMap<>();
        for (String remarkCodeString : SQLRemarkCode.getJustRemarkCodes()) {
            mapForNewRemarkPhrase.put(remarkCodeString, 0);
        }
        RemarkPhrase shortestPhrase = new RemarkPhrase("", mapForNewRemarkPhrase, 0);
        int highestPhrasePrimaryKey = 0;
        //Find shortest RemarkPhrase which contains the search phrase.
        for (RemarkPhrase remarkPhrase : mapOfAll.values()) {
            Boolean phraseMatch = remarkPhrase.phrase.toLowerCase().contains(searchPhrase.toLowerCase());
            if (phraseMatch && shortestPhrase.phrase.isEmpty()) {
                shortestPhrase = remarkPhrase;
            }
            else if (phraseMatch && shortestPhrase.phrase.length() > remarkPhrase.phrase.length()) {
                shortestPhrase = remarkPhrase;
            }
            if (remarkPhrase.primaryKey > highestPhrasePrimaryKey) {
                highestPhrasePrimaryKey = remarkPhrase.primaryKey;
            }
        }
        //If a RemarkPhrase which contains the search phrase does not exist, a new one is created.
        int currentRemarkCodeUses = shortestPhrase.timesUsedWithPhrase.get(remarkCode.getRemarkCode());
        shortestPhrase.timesUsedWithPhrase.put(remarkCode.getRemarkCode(), currentRemarkCodeUses + 1);
        if (shortestPhrase.phrase.isEmpty()) {
            int unusedPrimaryKey = highestPhrasePrimaryKey + 1;
            shortestPhrase.phrase = searchPhrase;
            shortestPhrase.primaryKey = unusedPrimaryKey;
            mapOfAll.put(unusedPrimaryKey, shortestPhrase);
        }
        else {
            mapOfAll.put(shortestPhrase.primaryKey, shortestPhrase);
        }

        currentRemarkCodeUses = remarkCode.getTotalTimesUsed();
        remarkCode.setTotalTimesUsed(currentRemarkCodeUses + 1);
        RemarkCode.getMapOfAll().put(remarkCode.getRemarkCode(), remarkCode);
    }

}

