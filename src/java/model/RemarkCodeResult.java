package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sqldata.SQLRemarkCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimerTask;

/**
 * Subclass used to populate tables with the variables which are unique to these objects
 * (as opposed to their superclass).
 */
public class RemarkCodeResult extends RemarkCode{

    /**
     * Represents the total times this RemarkCode has been used with the most recently searched phrase.
     */
    private int timesUsedWithThisPhrase;

    /**
     * Represents a list of the phrases searched recently.  Phrases will be kept on this
     * list for a certain amount of time, determined by the setTimerForPhrase() method.
     */
    private static ArrayList<String> phrasesSearchedRecently = new ArrayList();

    /**
     * Constructor for this class.
     * @param remarkCode
     * @param description
     * @param notes
     * @param carcRarcID
     * @param totalTimesUsed
     * @param timesUsedWithRemarkCode
     * @param timesUsedWithThisPhrase
     */
    public RemarkCodeResult(String remarkCode, String description, String notes, int carcRarcID, int totalTimesUsed,
                            HashMap<String, Integer> timesUsedWithRemarkCode, int timesUsedWithThisPhrase) {
        super(remarkCode, description, notes, carcRarcID, totalTimesUsed, timesUsedWithRemarkCode);
        this.timesUsedWithThisPhrase = timesUsedWithThisPhrase;
    }

    public static ArrayList<String> getPhrasesSearchedRecently() {
        return phrasesSearchedRecently;
    }

    public static void setPhrasesSearchedRecently(ArrayList<String> phrasesSearchedRecently) {
        RemarkCodeResult.phrasesSearchedRecently = phrasesSearchedRecently;
    }

    public int getTimesUsedWithThisPhrase() {
        return timesUsedWithThisPhrase;
    }

    public void setTimesUsedWithThisPhrase(int timesUsedWithThisPhrase) {
        this.timesUsedWithThisPhrase = timesUsedWithThisPhrase;
    }

    /**
     * Searches by RemarkCode using the search phrase.
     * @param searchPhrase
     * @return ObservableList of remark codes relating to the search phrase by remark code ID.
     */
    public static ObservableList<RemarkCodeResult> searchByRemarkCode(String searchPhrase, HashMap<String, RemarkCode> mapToSearch){
        ObservableList<RemarkCodeResult> resultsOfSearch = FXCollections.observableArrayList();
        for(RemarkCode remarkCode : mapToSearch.values()){
            if(remarkCode.getRemarkCode().toLowerCase().contains(searchPhrase)){
                String remarkCodeIdentifier = remarkCode.getRemarkCode();
                String description = remarkCode.getDescription();
                String notes = remarkCode.getNotes();
                int carcRarcID = remarkCode.getCarcRarcID();
                int totalTimesUsed = remarkCode.getTotalTimesUsed();
                HashMap<String, Integer> timesUsedWithRemarkCodeMap = remarkCode.getTimesUsedWithRemarkCodeMap();
                RemarkCodeResult remarkCodeResult = new RemarkCodeResult(remarkCodeIdentifier, description, notes,
                        carcRarcID, totalTimesUsed, timesUsedWithRemarkCodeMap, 0);
                resultsOfSearch.add(remarkCodeResult);
            }
        }
        return resultsOfSearch;
    }

    /**
     * Returns an ObservableList with RemarkCodeResults which are associated with phrases containing the searchPhrase
     * parameter.
     *
     * Returns only the shortest results.
     *
     * @param searchPhrase
     * @return an ObservableList with RemarkCodeResults which are associated with phrases containing the searchPhrase
     * parameter.
     */
    public static ObservableList<RemarkCodeResult> searchByRemarkPhrase(String searchPhrase, HashMap<String, RemarkCode> mapToSearch) {
        searchPhrase = searchPhrase.toLowerCase();
        ObservableList<RemarkCodeResult> resultsOfSearch = FXCollections.observableArrayList();
        int shortestPhraseLength = 0;
        ArrayList<RemarkPhrase> matchingPhrases = new ArrayList<>();
        //Finds phrases containing the search phrase and identifies the length of the shortest of such phrases
        for(RemarkPhrase remarkPhrase : RemarkPhrase.getMapOfAll().values()){
            if(remarkPhrase.getPhrase().toLowerCase().contains(searchPhrase)){
                int thisPhraseLength = remarkPhrase.getPhrase().length();
                if (shortestPhraseLength == 0){
                    shortestPhraseLength = thisPhraseLength;
                }
                else if (thisPhraseLength < shortestPhraseLength){
                    shortestPhraseLength = thisPhraseLength;
                }
                matchingPhrases.add(remarkPhrase);
            }
        }
        //Grabs the shortest phrases from the list of matching phrases
        ArrayList<RemarkPhrase> shortestMatchingPhrases = new ArrayList<>();
        for(RemarkPhrase remarkPhrase : matchingPhrases){
            if(remarkPhrase.getPhrase().length() == shortestPhraseLength){
                if(!shortestMatchingPhrases.contains(remarkPhrase)) {
                    shortestMatchingPhrases.add(remarkPhrase);
                }
            }
        }
        //Produces a list of remarkCodeResult objects which represent the results of the search.
        HashMap<String, Integer> consolidatedUseData = consolidateUseData(shortestMatchingPhrases);
        for(RemarkCode remarkCode: mapToSearch.values()) {
            String remarkCodeIdentifier = remarkCode.getRemarkCode();
            Integer timesUsedWithThisPhrase = consolidatedUseData.get(remarkCodeIdentifier);
            if (timesUsedWithThisPhrase != null && timesUsedWithThisPhrase != 0) {
                String description = remarkCode.getDescription();
                String notes = remarkCode.getNotes();
                int carcRarcID = remarkCode.getCarcRarcID();
                int totalTimesUsed = remarkCode.getTotalTimesUsed();
                HashMap<String, Integer> timesUsedWithRemarkCodeMap = remarkCode.getTimesUsedWithRemarkCodeMap();
                RemarkCodeResult remarkCodeResult = new RemarkCodeResult(remarkCodeIdentifier, description, notes,
                        carcRarcID, totalTimesUsed, timesUsedWithRemarkCodeMap, timesUsedWithThisPhrase);
                resultsOfSearch.add(remarkCodeResult);
            }
        }
        return resultsOfSearch;
    }

    /**
     * Searches for matching RemarkCodes by comparing the searchPhrase with the RemarkCode description.
     * @param searchPhrase
     * @return ObservableList of RemarkCodes matched by comparing the searchPhrase with the RemarkCode description.
     */
    public static ObservableList<RemarkCodeResult> searchByRemarkCodeDescription(String searchPhrase, HashMap<String, RemarkCode> mapToSearch){
        searchPhrase = searchPhrase.toLowerCase();
        ObservableList<RemarkCodeResult> resultsOfSearch = FXCollections.observableArrayList();
        for(RemarkCode remarkCode : mapToSearch.values()){
            if(remarkCode.getDescription().toLowerCase().contains(searchPhrase)){
                String remarkCodeID = remarkCode.getRemarkCode();
                String description = remarkCode.getDescription();
                String notes = remarkCode.getNotes();
                int carcRarcID = remarkCode.getCarcRarcID();
                int totalTimesUsed = remarkCode.getTotalTimesUsed();
                HashMap<String, Integer> timesUsedWithRemarkCodeMap = remarkCode.getTimesUsedWithRemarkCodeMap();
                RemarkCodeResult remarkCodeResult = new RemarkCodeResult(remarkCodeID, description, notes, carcRarcID,
                        totalTimesUsed, timesUsedWithRemarkCodeMap, 0);
                resultsOfSearch.add(remarkCodeResult);
            }
        }
        return resultsOfSearch;
    }

    /**
     *
     * Searches all RemarkCodes and RemarkPhrases.  If any matching codes are found, they are returned and the method stops.
     * If no matching codes are found, the method searches for the shortest matching phrases, consolidates their data, and
     * returns an obserable list of all RemarkCodeResults which were used with the phrase.
     *
     * If the search phrase is empty, the method will simply return the mapToSearch parameter converted into an
     * ObservableList.  This makes the search faster if its empty, and this method is called with empty searches by
     * other methods in the InformationSearchController class.
     *
     * @param searchPhrase
     * @return ObservableList of RemarkCodeResults relating to the search phrase.
     */
    public static ObservableList<RemarkCodeResult> searchAll(String searchPhrase, HashMap<String, RemarkCode> mapToSearch) {
        ObservableList<RemarkCodeResult> resultsOfSearchByDescription;
        ObservableList<RemarkCodeResult> resultsOfSearch = FXCollections.observableArrayList();

        if(searchPhrase.isEmpty()){
            for(RemarkCode remarkCode : mapToSearch.values()){
                String remarkCodeID = remarkCode.getRemarkCode();
                String description = remarkCode.getDescription();
                String notes = remarkCode.getNotes();
                int carcRarcID = remarkCode.getCarcRarcID();
                int totalTimesUsed = remarkCode.getTotalTimesUsed();
                HashMap<String, Integer> timesUsedWithRemarkCodeMap = remarkCode.getTimesUsedWithRemarkCodeMap();
                RemarkCodeResult remarkCodeResult = new RemarkCodeResult(remarkCodeID, description, notes, carcRarcID,
                        totalTimesUsed, timesUsedWithRemarkCodeMap, 0);
                resultsOfSearch.add(remarkCodeResult);
            }
            return resultsOfSearch;
        }

        setTimerForPhrase(searchPhrase);
        searchPhrase = searchPhrase.toLowerCase();

        resultsOfSearch = searchByRemarkCode(searchPhrase, mapToSearch);
        if (resultsOfSearch.size() != 0){
            return resultsOfSearch;
        }

        resultsOfSearch = searchByRemarkPhrase(searchPhrase, mapToSearch);
        resultsOfSearchByDescription = searchByRemarkCodeDescription(searchPhrase, mapToSearch);

        for(RemarkCodeResult resultByDescription : resultsOfSearchByDescription){
            Boolean inResultsOfSearchByPhrase = false;
            for(RemarkCodeResult resultByPhrase : resultsOfSearch){
                if (resultByDescription.getRemarkCode().equals(resultByPhrase.getRemarkCode())){
                    inResultsOfSearchByPhrase = true;
                }
            }
            if(!inResultsOfSearchByPhrase){
                resultsOfSearch.add(resultByDescription);
            }
        }
        return resultsOfSearch;
    }

    /**
     * Takes a list of RemarkPhrase objects and consolidates their timesUsedWithRemarkCodeMap into one HashMap.
     * @param listOfRemarkPhraseObjects
     * @return a single HashMap of consolidated timesUsedWithRemarkCodeMap data;
     */
    public static HashMap<String, Integer> consolidateUseData(ArrayList<RemarkPhrase> listOfRemarkPhraseObjects) {
        ArrayList<String> justRemarkCodes = SQLRemarkCode.getJustRemarkCodes();
        HashMap<String, Integer> consolidatedData = new HashMap<>();
        for(String remarkCodeString:justRemarkCodes){
            for(RemarkPhrase remarkPhrase : listOfRemarkPhraseObjects) {
                Integer currentConsolidatedCodeUses = consolidatedData.get(remarkCodeString);
                HashMap<String, Integer> timesUsedWithAllPhrases = remarkPhrase.getTimesUsedWithPhrase();
                int timesUsedWithPhrase = timesUsedWithAllPhrases.get(remarkCodeString);
                if (currentConsolidatedCodeUses != null) {
                    int totalTimesUsedWithPhrase = currentConsolidatedCodeUses + timesUsedWithPhrase;
                    consolidatedData.put(remarkCodeString, totalTimesUsedWithPhrase);
                }
                else{
                    consolidatedData.put(remarkCodeString, timesUsedWithPhrase);
                }
            }
        }
        return consolidatedData;
    }

    /**
     * This adds the phrase to the phrasesRecentlySearched ArrayList and sets a timer to remove the phrase from said
     * list after five minutes have elapsed.
     *
     * This will only happen if the number of characters in the phrase is less than 500, because that's the max allowed
     * in the database.  Does not notify the user because they should be able to search anything (and the searches aren't
     * completely based on the RemarkPhrase data).
     *
     * @param searchPhrase
     */
    public static void setTimerForPhrase(String searchPhrase){
        int maxCharForDatabase = 500;
        if(searchPhrase.length() <= maxCharForDatabase) {
            Boolean containsSearchPhraseAlready = false;
            for (String phrase : phrasesSearchedRecently) {
                if (phrase.toLowerCase().contains(searchPhrase.toLowerCase())) {
                    containsSearchPhraseAlready = true;
                }
            }
            if (!containsSearchPhraseAlready) {
                phrasesSearchedRecently.add(searchPhrase);
                TimerTask task = new TimerTask() {
                    public void run() {
                        phrasesSearchedRecently.remove(searchPhrase);
                    }
                };
                long fiveMinutesInMilliseconds = 300000;
                RCMTimer.getTimer().schedule(task, fiveMinutesInMilliseconds);
            }
        }
    }
}
