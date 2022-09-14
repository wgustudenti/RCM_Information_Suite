package model;

import databasevendor.VendorRemarkCode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sqldata.SQLRemarkCode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating RemarkCode Business Objects
 * RemarkCode business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class RemarkCode implements RCMBusinessObject{

    /**
     * String representing the actual Remark Code.
     */
    private String remarkCode;

    /**
     * String representing the description of the Remark Code.
     */
    private String description;

    /**
     * Represents any notes that pertain to a RemarkCode.
     */
    private String notes;

    /**
     * Represents the primary key from the CarcOrRarc table.
     */
    private int carcRarcID;

    /**
     * Represents the total times that this remark code has been used.
     */
    private int totalTimesUsed;

    /**
     * A HashMap whose values contain the number of times this remark code has been used with the key Remark Code String.
     */
    private HashMap<String, Integer> timesUsedWithRemarkCodeMap;

    /**
     * HashMap of all RemarkCodes
     */
    private static HashMap<String, RemarkCode> mapOfAll;

    /**
     * Observable list of RemarkCodes.
     */
    private static ObservableList<RemarkCode> viewableRemarkCodes;

    /**
     * Constructor for this class.
     * @param remarkCode
     * @param description
     * @param notes
     * @param carcRarcID
     * @param totalTimesUsed
     * @param timesUsedWithRemarkCode
     */
    public RemarkCode(String remarkCode, String description, String notes, int carcRarcID, int totalTimesUsed,
                      HashMap<String, Integer> timesUsedWithRemarkCode){
        this.remarkCode = remarkCode;
        this.description = description;
        this.notes = notes;
        this.carcRarcID = carcRarcID;
        this.totalTimesUsed = totalTimesUsed;
        this.timesUsedWithRemarkCodeMap = timesUsedWithRemarkCode;
    }

    /**
     * Constructor for this class.
     * @param remarkCode
     * @param description
     * @param notes
     * @param carcRarcID
     */
    public RemarkCode(String remarkCode, String description, String notes, int carcRarcID){
        this.remarkCode = remarkCode;
        this.description = description;
        this.notes = notes;
        this.carcRarcID = carcRarcID;
        this.totalTimesUsed = totalTimesUsed = 0;
        HashMap<String, Integer> timesUsedRemarkCodeWithMap = new HashMap<String, Integer>();
        for(String remarkCodeString : SQLRemarkCode.getJustRemarkCodes()){
            timesUsedRemarkCodeWithMap.put(remarkCodeString, 0);
        }
        this.timesUsedWithRemarkCodeMap = timesUsedRemarkCodeWithMap;
    }

    public String getRemarkCode() {
        return remarkCode;
    }

    public void setRemarkCode(String remarkCode) {
        this.remarkCode = remarkCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getCarcRarcID() {
        return carcRarcID;
    }

    public void setCarcRarcID(int carcRarcID) {
        this.carcRarcID = carcRarcID;
    }

    public int getTotalTimesUsed() {
        return totalTimesUsed;
    }

    public void setTotalTimesUsed(int timesUsedTotal) {
        this.totalTimesUsed = timesUsedTotal;
    }

    public HashMap<String, Integer> getTimesUsedWithRemarkCodeMap() {
        return timesUsedWithRemarkCodeMap;
    }

    public void setTimesUsedWithRemarkCodeMap(HashMap<String, Integer> timesUsedWithRemarkCode) {
        this.timesUsedWithRemarkCodeMap = timesUsedWithRemarkCode;
    }

    public static HashMap<String, RemarkCode> getMapOfAll() {
        return mapOfAll;
    }

    public static void setMapOfAll(HashMap<String, RemarkCode> mapOfAll) {
        RemarkCode.mapOfAll = mapOfAll;
    }

    /**
     * This method returns an alphabetically ordered list of all remarkCodes.
     * @return ObservableList<RemarkCode></RemarkCode> list of all remarkCodes ordered in descending order by totalTimesUsed.
     */
    public static ObservableList<RemarkCode> getObservableListOfAll(){
        ObservableList<RemarkCode> listOfRemarkCodes = FXCollections.observableArrayList();
        for(RemarkCode remarkCode : mapOfAll.values()){
            listOfRemarkCodes.add(remarkCode);
        }
        listOfRemarkCodes.sort(Comparator.comparing(RemarkCode::getTotalTimesUsed).reversed());
        return listOfRemarkCodes;
    }
    
    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorRemarkCode.insertWithColumn(this.remarkCode, this.carcRarcID, this.description, this.notes);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.remarkCode;
        int actionID = 13;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorRemarkCode.update(this.remarkCode, this.carcRarcID, this.description, this.notes);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.remarkCode;
        int actionID = 14;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorRemarkCode.deleteWithColumn(this.remarkCode);
        String username = AdminUser.getCurrentUser().getUsername();
        String detail = this.remarkCode;
        int actionID = 15;
        AdminLog adminLog = new AdminLog(username, detail, actionID);
        adminLog.insert();
    }

    /**
     * This method returns a String with the leading alphabetic characters from the string representing the full
     * RemarkCode.
     * @return String representing prefix of this RemarkCode.
     */
    public String getCodePrefix(){
        String remarkCodeForThisObject = this.remarkCode;
        String prefix = "";
        for(int i = 0; i<remarkCodeForThisObject.length(); i++){
            char currentCharacter = remarkCodeForThisObject.charAt(i);
            if(Character.isAlphabetic(currentCharacter)){
                prefix = prefix + currentCharacter;
            }
            else{
                break;
            }
        }
        return prefix;
    }

    /**
     * This method returns an int with the latter numeric characters from the string representing the full
     * RemarkCode.
     * @return int representing suffix of this RemarkCode.
     */
    public int getCodeSuffix(){
        String remarkCodeForThisObject = this.remarkCode;
        String suffixString = "";
        for(int i = 0; i<remarkCodeForThisObject.length(); i++){
            char currentCharacter = remarkCodeForThisObject.charAt(i);
            if(Character.isDigit(currentCharacter)){
                suffixString = suffixString + currentCharacter;
            }
        }
        int suffix = Integer.parseInt(suffixString);
        return suffix;
    }

    /**
     * Returns a string representing whether this RemarkCode is a CARC or a RARC
     * @return String representing whether this RemarkCode is a CARC or a RARC
     */
    public String getCarcOrRarcString(){
        return CarcOrRarc.getMapOfAll().get(this.carcRarcID).getDescription();
    }

    /**
     * Refreshes the information in the viewable list of remark codes for all clients using the HashMap of all
     * RemarkCodes.
     *
     * Does not take searches into account.
     */
    public static void refreshViewableList(){
        ObservableList<RemarkCode> newViewableListOfRemarkCodes = FXCollections.observableArrayList();
        for (RemarkCode remarkCode : mapOfAll.values()){
            newViewableListOfRemarkCodes.add(remarkCode);
        }
        viewableRemarkCodes = newViewableListOfRemarkCodes;
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<String, RemarkCode> getNewMapOfAll() throws SQLException {
        ResultSet wholeTable = VendorRemarkCode.getAll();
        HashMap<String, RemarkCode> allRemarkCodes = new HashMap<>();
        HashMap<String, Integer> totalTimesUsedForAll = RemarkPhrase.getTimesUsedForAll();
        while (wholeTable.next()){
            String remarkCode = wholeTable.getString("RemarkCode");
            String description = wholeTable.getString("Description");
            String notes = wholeTable.getString("Notes");
            int carcRarcID = wholeTable.getInt("CarcRarcID");
            int totalTimesUsed = totalTimesUsedForAll.get(remarkCode);
            HashMap<String, Integer> timesUsedWithRemarkCode = getNewTimesUsedWithRemarkCode(wholeTable);
            RemarkCode remarkcodetoadd = new RemarkCode(remarkCode, description, notes, carcRarcID, totalTimesUsed,
                    timesUsedWithRemarkCode);
            allRemarkCodes.put(remarkCode, remarkcodetoadd);
        }
        return allRemarkCodes;
    }

    /**
     * Filters all Remark Code alerts out of mapOfAll.
     * @return Hashmap similar to mapOfAll, but without any "Alert" remark codes.
     */
    public static HashMap<String, RemarkCode> filterAlertsFromMap(HashMap<String, RemarkCode> mapToSearch){
        HashMap<String, RemarkCode> filteredMap = new HashMap<>();
        for (RemarkCode remarkCode : mapToSearch.values()){
            if (!remarkCode.description.toLowerCase().startsWith("Alert:".toLowerCase())){
                filteredMap.put(remarkCode.getRemarkCode(), remarkCode);
            }
        }
        return filteredMap;
    }

    /**
     * Returns a map with only CARCS
     * @param mapToSearch
     * @return HashMap<String, RemarkCode> containing only CARCs
     */
    public static HashMap<String, RemarkCode> getOnlyCARCsFromMap(HashMap<String, RemarkCode> mapToSearch){
        HashMap<String, RemarkCode> filteredMap = new HashMap<>();
        for (RemarkCode remarkCode : mapToSearch.values()){
            if (remarkCode.getCarcOrRarcString().toLowerCase().contains("CARC".toLowerCase())){
                filteredMap.put(remarkCode.getRemarkCode(), remarkCode);
            }
        }
        return filteredMap;
    }

    /**
     * Returns a map with only RARCS
     * @param mapToSearch
     * @return HashMap<String, RemarkCode> containing only RARCs
     */
    public static HashMap<String, RemarkCode> getOnlyRARCsFromMap(HashMap<String, RemarkCode> mapToSearch){
        HashMap<String, RemarkCode> filteredMap = new HashMap<>();
        for (RemarkCode remarkCode : mapToSearch.values()){
            if (remarkCode.getCarcOrRarcString().toLowerCase().contains("RARC".toLowerCase())){
                filteredMap.put(remarkCode.getRemarkCode(), remarkCode);
            }
        }
        return filteredMap;
    }

    /**
     * Returns a map with remark code strings as keys and with the number of times a given remark code is used with the
     * remark code parameter as values.
     * @param wholeTable
     * @return HashMap with all data on how many times this remark codes was used with other remark codes.
     * @throws SQLException
     */
    public static HashMap<String, Integer> getNewTimesUsedWithRemarkCode(ResultSet wholeTable) throws SQLException {
        ArrayList<String> justRemarkCodes = VendorRemarkCode.getJustRemarkCodes();
        HashMap<String, Integer> timesUsedWithRemarkCodeMap = new HashMap<>();
        for (String justRemarkCode : justRemarkCodes) {
            String columnName = "R" + justRemarkCode;
            Integer timesUsedWithSingleRemark = wholeTable.getInt(columnName);
            timesUsedWithRemarkCodeMap.put(justRemarkCode, timesUsedWithSingleRemark);
        }
        return timesUsedWithRemarkCodeMap;
    }

    /**
     * This method returns a table with the "Most Commonly Used With" information for each remark code.
     *
     * @return ArrayList table "Most Commonly Used With" information"
     * @throws SQLException
     */
    public static HashMap<String, ArrayList<Integer>> getMostCommonlyUsedWithTable() throws SQLException {
        String remarkCode;
        ArrayList<Integer> mostCommonlyUsedWithData = new ArrayList<>();
        HashMap<String, ArrayList<Integer>> hashMapOfData = new HashMap<>();
        ResultSet wholeTable = VendorRemarkCode.getAll();
        while (wholeTable.next()){
            remarkCode = wholeTable.getString("RemarkCode");
            int numberOfColumns = getListOfColumns().size();
            for (int i = 0; i < numberOfColumns; i++){
                Integer singleElement = wholeTable.getInt(numberOfColumns);
                mostCommonlyUsedWithData.add(singleElement);
            }
            hashMapOfData.put(remarkCode, mostCommonlyUsedWithData);
        }
        return hashMapOfData;
    }

    /**
     * This method returns a HashMap with column indexes as keys to Remark Code values in the SQL table
     *
     * @return HashMap with column indexes as keys to Remark Code values
     * @throws SQLException
     */
    public static HashMap<Integer, String> getMapOfColIndexToCode() throws SQLException {
        ResultSet wholeTable = VendorRemarkCode.getAll();
        HashMap<Integer, String> MapOfCodeToColIndex = new HashMap<>();
            for (String remarkCode: VendorRemarkCode.getJustRemarkCodes()){
                String columnName = "R" + remarkCode;
                Integer columnIndex = wholeTable.findColumn(columnName);
                MapOfCodeToColIndex.put(columnIndex, remarkCode);
            }
        return MapOfCodeToColIndex;
    }

    /**
     * This method returns a HashMap with Remark Codes as keys to column indexes values in the SQL table
     *
     * @return HashMap with Remark Codes as keys to column indexes
     * @throws SQLException
     */
    public static HashMap<String, Integer> getMapOfCodeToColIndex() throws SQLException {
        ResultSet wholeTable = VendorRemarkCode.getAll();
        HashMap<String, Integer> MapOfCodeToColIndex = new HashMap<>();
        for (String remarkCode: VendorRemarkCode.getJustRemarkCodes()){
            String columnName = "R" + remarkCode;
            Integer columnIndex = wholeTable.findColumn(columnName);
            MapOfCodeToColIndex.put(remarkCode, columnIndex);
        }
        return MapOfCodeToColIndex;
    }

    /**
     * This method returns a HashMap within a HashMap (with the first layer's key representing the row) with more
     * HashMaps as values. The second layer of HashMaps (where the key represents the column) has values of times used.
     *
     * This means that we can use any two remark code combinations to grab the number of times those remark
     * codes were used together (HashMap1.get(rowRemarkCode).get(colRemarkCode))
     *
     * @return HashMap with Remark Codes as keys to other HashMaps where Remark Codes are keys to timesUsed values.
     * @throws SQLException
     */
    public static HashMap<String, HashMap<String, Integer>> getMapOfMapOfUseData() throws SQLException {
        ResultSet wholeTable = VendorRemarkCode.getAll();
        HashMap<String, HashMap<String, Integer>> mapOfMap = new HashMap<>();
        ArrayList<String> justRemarkCodes = VendorRemarkCode.getJustRemarkCodes();
        while (wholeTable.next()){
            String currentRowRemarkCode = (wholeTable.getString("RemarkCode"));
            HashMap<String, Integer> mapOfColNameToTimesUsed = new HashMap<>();
            for (String remarkCode: justRemarkCodes){
                String columnName = "R" + remarkCode;
                Integer timesUsed = wholeTable.getInt(columnName);
                mapOfColNameToTimesUsed.put(remarkCode, timesUsed);
            }
            mapOfMap.put(currentRowRemarkCode, mapOfColNameToTimesUsed);
        }
        return mapOfMap;
    }

    /**
     * Returns an arraylist of all columns in the RemarkCode table in the database.
     * @return arraylist of all columns in the RemarkCode table in the database.
     * @throws SQLException
     */
    public static ArrayList<String> getListOfColumns() throws SQLException {
        ResultSet remarkCodeTableNames = VendorRemarkCode.getColumnNames();
        ArrayList<String> columnNameList = new ArrayList<>();
        while (remarkCodeTableNames.next()){
            String columnName = remarkCodeTableNames.getString("name");
            columnNameList.add(columnName);
        }
        return  columnNameList;

    }


    /**
     * The object calling this class is the object whose total use data increases.
     *
     * Updates the Most Commonly Used With data in the database and in the mapOfAll for this class' super.
     * @param remarkCodeToAssociate
     * @throws SQLException
     */
    public void updateMostUsedWithData(RemarkCode remarkCodeToAssociate) throws SQLException {
        if(!this.remarkCode.equals(remarkCodeToAssociate.remarkCode)) {
            SQLRemarkCode.updateMostCommonlyUsedWith(this.getRemarkCode(), remarkCodeToAssociate.getRemarkCode());

            Integer timesAssociatedAlready = remarkCodeToAssociate.getTimesUsedWithRemarkCodeMap().get(this.getRemarkCode());
            remarkCodeToAssociate.getTimesUsedWithRemarkCodeMap().put(this.getRemarkCode(), timesAssociatedAlready + 1);
            timesAssociatedAlready = this.getTimesUsedWithRemarkCodeMap().get(remarkCodeToAssociate.getRemarkCode());
            this.getTimesUsedWithRemarkCodeMap().put(remarkCodeToAssociate.getRemarkCode(), timesAssociatedAlready + 1);


            //The following doesn't get updated in the database.  May be best to leave it out so the user understands.
/*            this.totalTimesUsed = this.totalTimesUsed + 1;
            mapOfAll.put(this.remarkCode, this);*/
        }
    }

    /**
     * This method updates the database and maps to associate all remark code strings on the list with each other for
     * "Most Commonly Used With" data.
     *
     * This method does not associate a remark code with itself.
     * @param remarkCodeList
     * @throws SQLException
     */
    public static void updateMostUsedDataForList(ObservableList<String> remarkCodeList) throws SQLException {
        SQLRemarkCode.updateMostCommonlyUsedWith(remarkCodeList);
        for(String remarkCodeString : remarkCodeList){
            for (String remarkCodeStringTwo : remarkCodeList){
                if(!remarkCodeString.equals(remarkCodeStringTwo)){
                    Integer newNumberOfAssociations
                            = mapOfAll.get(remarkCodeString).getTimesUsedWithRemarkCodeMap().get(remarkCodeStringTwo) + 1;
                    mapOfAll.get(remarkCodeString).getTimesUsedWithRemarkCodeMap().put(remarkCodeStringTwo, newNumberOfAssociations);
                }
            }
        }
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        SQLRemarkCode.setJustRemarkCodes(SQLRemarkCode.getNewJustRemarkCodes());
        mapOfAll = getNewMapOfAll();
        refreshViewableList();
    }
}
