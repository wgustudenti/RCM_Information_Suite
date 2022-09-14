package sqldata;

import javafx.collections.ObservableList;
import masterdata.SQLGeneral;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class is not meant to be used directly, but to be a superclass for classes SQL driven databases
 * for specific vendors.
 *
 * The subclasses to this class interact with a database to retrieve information from the table specified
 * by the tableName variable (and the name of this specific .java file).
 *
 * Imported by RemarkPhrase.
 */
public abstract class SQLRemarkCode {

    /**
     * This variable represents the name of the database table from which this class will grab information.
     *
     * This is necessary to declare as it affects the methods in this class and its superclass.
     */
    private static final String tableName = "RemarkCode";

    /**
     * This variable represents the name of the column containing the primary key for the database table represented
     * by the "String tableName" variable.
     */
    private static final String primaryKeyColName = "RemarkCode";

    /**
     * Arraylist containing only Strings representing RemarkCode IDs.
     */
    private static ArrayList<String> justRemarkCodes = new ArrayList<>();

    public static ArrayList<String> getJustRemarkCodes() {
        return SQLRemarkCode.justRemarkCodes;
    }

    public static void setJustRemarkCodes(ArrayList<String> justRemarkCodes) {
        SQLRemarkCode.justRemarkCodes = justRemarkCodes;
    }

    /**
     * This returns a ResultSet from the RemarkCode table with only one column: RemarkCode.  This should be faster than
     * retrieving the thousand columns which the table contains.
     * @return ResultSet with only the RemarkCode column
     * @throws SQLException
     */
    private static ResultSet getJustRemarkCodesResultSet() throws SQLException {
        String query = "SELECT " + primaryKeyColName + " FROM " + tableName + ";";
        return SQLGeneral.executeQuery(query);
    }

    /**
     * This method inserts a new row into the table.
     *
     * @param remarkCode
     * @param carcRarcID
     * @param description
     * @param notes
     * @throws SQLException
     */
    public static void insert(String remarkCode, int carcRarcID, String description, String notes) throws SQLException {
        remarkCode = SQLGeneral.removeApostrophes(remarkCode);
        remarkCode = SQLGeneral.removeSpaces(remarkCode);
        description = SQLGeneral.escapeApostrophes(description);
        notes = SQLGeneral.escapeApostrophes(notes);
        String query = "INSERT INTO " + tableName + " (RemarkCode, CarcRarcID, Description, Notes)" +
                " VALUES ('" + remarkCode + "', " + carcRarcID + ", '" + description + "', '" + notes + "');";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method inserts a new row into the table.
     * Then adds a new column to the RemarkCode and RemarkPhrase tables to hold the usage data for the added RemarkCode.
     *
     * @param remarkCode
     * @param carcRarcID
     * @param description
     * @param notes
     * @throws SQLException
     */
    public static void insertWithColumn(String remarkCode, int carcRarcID, String description, String notes) throws SQLException {
        remarkCode = SQLGeneral.removeApostrophes(remarkCode);
        remarkCode = SQLGeneral.removeSpaces(remarkCode);
        description = SQLGeneral.escapeApostrophes(description);
        notes = SQLGeneral.escapeApostrophes(notes);
        String query = "INSERT INTO " + tableName + " (RemarkCode, CarcRarcID, Description, Notes)" +
                " VALUES ('" + remarkCode + "', " + carcRarcID + ", '" + description + "', '" + notes + "');";
        SQLGeneral.executeUpdate(query);
        addRemarkCodeColumn(remarkCode);
        SQLRemarkPhrase.addRemarkPhraseColumn(remarkCode);
    }

    /**
     * This method updates a row in the table.
     *
     * This method will not update the remarkCode String, but everything else pertaining to that RemarkCode.
     *
     * @param remarkCode
     * @param carcRarcID
     * @param description
     * @param notes
     * @throws SQLException
     */
    public static void update(String remarkCode, int carcRarcID, String description, String notes) throws SQLException {
        remarkCode = SQLGeneral.removeApostrophes(remarkCode);
        remarkCode = SQLGeneral.removeSpaces(remarkCode);
        description = SQLGeneral.escapeApostrophes(description);
        notes = SQLGeneral.escapeApostrophes(notes);
        String query = "UPDATE " + tableName + " SET RemarkCode='" + remarkCode + "', CarcRarcID=" + carcRarcID
                + ", Description='" + description + "', Notes='" + notes + "' WHERE " + primaryKeyColName + " = '"
                + remarkCode + "';";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * MIGHT NOT NEED THIS ANYMORE - WAS TO GET INDEXES OF COLUMNS, ALREADY DONE.
     *
     * MAY HAVE TO OVERRIDE THIS FOR OTHER DATABASES!!!
     * NEED TO TEST ESPECIALLY!!!
     * @return
     * @throws SQLException
     */
    public static ResultSet getColumnNames() throws SQLException {
        String query = "SELECT name FROM pragma_table_info " + tableName + ";";
        ResultSet columnNameTable = SQLGeneral.executeQuery(query);
        return columnNameTable;
    }

    /**
     * This method updates the "most commonly used with" data in the RemarkCode table in the database.
     * It does two updates, one with the first remark code as the row/second remark code as column and vice versa.
     *
     * If no "numberToAdd" (third parameter) is inserted, the method will add increment the use data by one.
     * @param remarkCodeOne
     * @param remarkCodeTwo
     * @throws SQLException
     */
    public static void updateMostCommonlyUsedWith(String remarkCodeOne, String remarkCodeTwo) throws SQLException {
        int numberToAdd = 1;
        remarkCodeOne = SQLGeneral.removeApostrophes(remarkCodeOne);
        remarkCodeOne = SQLGeneral.removeSpaces(remarkCodeOne);
        remarkCodeTwo = SQLGeneral.removeApostrophes(remarkCodeTwo);
        remarkCodeTwo = SQLGeneral.removeSpaces(remarkCodeTwo);
        String remarkCodeOneColName = 'R' + remarkCodeOne;
        String remarkCodeTwoColName = 'R' + remarkCodeTwo;
        String query = "UPDATE " + tableName + " SET " + remarkCodeOneColName + "=" + remarkCodeOneColName + " + " + numberToAdd
                + " WHERE " + primaryKeyColName + " LIKE '" + remarkCodeTwo + "';";
        SQLGeneral.executeUpdate(query);
        query = "UPDATE " + tableName + " SET " + remarkCodeTwoColName + "=" + remarkCodeTwoColName + " + " + numberToAdd
                + " WHERE " + primaryKeyColName + " LIKE '" + remarkCodeOne + "';";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates the "most commonly used with" data in the RemarkCode table in the database.
     * It does two updates, one with the first remark code as the row/second remark code as column and vice versa.
     *
     * If no "numberToAdd" (third parameter) is inserted, the method will add increment the use data by one.
     * @param remarkCodeOne
     * @param remarkCodeTwo
     * @param numberToAdd
     * @throws SQLException
     */
    public static void updateMostCommonlyUsedWith(String remarkCodeOne, String remarkCodeTwo, int numberToAdd) throws SQLException {
        remarkCodeOne = SQLGeneral.removeApostrophes(remarkCodeOne);
        remarkCodeOne = SQLGeneral.removeSpaces(remarkCodeOne);
        remarkCodeTwo = SQLGeneral.removeApostrophes(remarkCodeTwo);
        remarkCodeTwo = SQLGeneral.removeSpaces(remarkCodeTwo);
        String remarkCodeOneColName = 'R' + remarkCodeOne;
        String remarkCodeTwoColName = 'R' + remarkCodeTwo;
        String query = "UPDATE " + tableName + " SET " + remarkCodeOneColName + "=" + remarkCodeOneColName + " + " + numberToAdd
                + " WHERE " + primaryKeyColName + " LIKE '" + remarkCodeTwo + "';";
        SQLGeneral.executeUpdate(query);
        query = "UPDATE " + tableName + " SET " + remarkCodeTwoColName + "=" + remarkCodeTwoColName + " + " + numberToAdd
                + " WHERE " + primaryKeyColName + " LIKE '" + remarkCodeOne + "';";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates the "most commonly used with" data in the RemarkCode table in the database.
     * It does one update statement per row affected.  This is faster than using the other method with the same name
     * which updates only one element at a time, instead of a whole row at a time.
     *
     * If no "numberToAdd" (third parameter) is inserted, the method will add increment the use data by one.
     * @param listOfRemarkCodes
     * @throws SQLException
     */
    public static void updateMostCommonlyUsedWith(ObservableList<String> listOfRemarkCodes) throws SQLException {
        int numberToAdd = 1;
        for (String remarkCodeOne : listOfRemarkCodes){
            String query = "UPDATE " + tableName + " SET ";
            remarkCodeOne = SQLGeneral.removeApostrophes(remarkCodeOne);
            remarkCodeOne = SQLGeneral.removeSpaces(remarkCodeOne);
            for (String remarkCodeTwo : listOfRemarkCodes){
                remarkCodeTwo = SQLGeneral.removeApostrophes(remarkCodeTwo);
                remarkCodeTwo = SQLGeneral.removeSpaces(remarkCodeTwo);
                if(!remarkCodeOne.equals(remarkCodeTwo)){
                    String remarkCodeTwoColName = 'R' + remarkCodeTwo;
                    query = query + remarkCodeTwoColName + '=' + remarkCodeTwoColName + " + " + numberToAdd + ", ";
                }
            }
            int queryLastCommaIndex = query.length()-2;
            query = query.substring(0, queryLastCommaIndex);
            query = query + " WHERE " + primaryKeyColName + " = '" + remarkCodeOne + "';";
            SQLGeneral.executeUpdate(query);
        }
    }

    /**
     * This method updates the "most commonly used with" data in the RemarkCode table in the database.
     * It does one update statement per row affected.  This is faster than using the other method with the same name
     * which updates only one element at a time, instead of a whole row at a time.
     *
     * If no "numberToAdd" (third parameter) is inserted, the method will add increment the use data by one.
     * @param listOfRemarkCodes
     * @param numberToAdd
     * @throws SQLException
     */
    public static void updateMostCommonlyUsedWith(ArrayList<String> listOfRemarkCodes, int numberToAdd) throws SQLException {
        for (String remarkCodeOne : listOfRemarkCodes){
            String query = "UPDATE " + tableName + " SET ";
            remarkCodeOne = SQLGeneral.removeApostrophes(remarkCodeOne);
            remarkCodeOne = SQLGeneral.removeSpaces(remarkCodeOne);
            for (String remarkCodeTwo : listOfRemarkCodes){
                remarkCodeTwo = SQLGeneral.removeApostrophes(remarkCodeTwo);
                remarkCodeTwo = SQLGeneral.removeSpaces(remarkCodeTwo);
                if(!remarkCodeOne.equals(remarkCodeTwo)){
                    String remarkCodeTwoColName = 'R' + remarkCodeTwo;
                    query = query + remarkCodeTwoColName + '=' + remarkCodeTwoColName + " + " + numberToAdd + ", ";
                }
            }
            int queryLastCommaIndex = query.length()-2;
            query = query.substring(0, queryLastCommaIndex);
            query = query + " WHERE " + primaryKeyColName + " = '" + remarkCodeOne + "';";
            SQLGeneral.executeUpdate(query);
        }
    }

    /**
     * This method returns all columns and rows from the table with which this class deals.
     * @return ResultSet with all columns and rows from the table.
     * @throws SQLException
     */
    public static ResultSet getAll() throws SQLException {
        return SQLGeneral.getWholeTable(tableName);
    }

    /**
     * This takes the primary key parameter and deletes the corresponding entry in the database table
     * with which this class deals.
     *
     * @param remarkCode
     * @throws SQLException
     */
    public static void delete(String remarkCode) throws SQLException {
        remarkCode = SQLGeneral.removeApostrophes(remarkCode);
        remarkCode = SQLGeneral.removeSpaces(remarkCode);
        SQLGeneral.deleteFromTable(tableName, remarkCode, primaryKeyColName);
    }

    /**
     * This takes the primary key parameter and deletes the corresponding entry in the database table
     * with which this class deals.
     *
     * Then removes a new column to the RemarkCode and RemarkPhrase tables which hold the usage data for the removed
     * RemarkCode.
     *
     * @param remarkCode
     * @throws SQLException
     */
    public static void deleteWithColumn(String remarkCode) throws SQLException {
        remarkCode = SQLGeneral.removeApostrophes(remarkCode);
        remarkCode = SQLGeneral.removeSpaces(remarkCode);
        SQLGeneral.deleteFromTable(tableName, remarkCode, primaryKeyColName);
        removeRemarkCodeColumn(remarkCode);
        SQLRemarkPhrase.removeRemarkPhraseColumn(remarkCode);
    }

    /**
     * This method alters the RemarkCode table by adding a column to it.
     * This is done when a new remark code is added to the database for the most commonly used with data.
     * @param remarkCode
     * @throws SQLException
     */
    public static void addRemarkCodeColumn(String remarkCode) throws SQLException {
        remarkCode = SQLGeneral.removeApostrophes(remarkCode);
        remarkCode = SQLGeneral.removeSpaces(remarkCode);
        String columnName = 'R' + remarkCode;
        String query = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " INTEGER DEFAULT 0;";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method alters the RemarkCode table by removing a column from it.
     * This is done when a new remark code is added to the database for the most commonly used with data.
     * @param remarkCode
     * @throws SQLException
     */
    public static void removeRemarkCodeColumn(String remarkCode) throws SQLException {
        remarkCode = SQLGeneral.removeApostrophes(remarkCode);
        remarkCode = SQLGeneral.removeSpaces(remarkCode);
        String columnName = 'R' + remarkCode;
        String query = "ALTER TABLE " + tableName + " DROP COLUMN " + columnName + ";";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * Iterates through whole RemarkCode database table and returns an arraylist of all RemarkCodes.
     * @return arrayListContaining just remarkCodes.
     * @throws SQLException
     */
    public static ArrayList<String> getNewJustRemarkCodes() throws SQLException {
        ResultSet allRemarkCodes = getJustRemarkCodesResultSet();
        ArrayList<String> justRemarkCodesList = new ArrayList<String>();
        while (allRemarkCodes.next()){
            String newRemarkCode = allRemarkCodes.getString("RemarkCode");
            justRemarkCodesList.add(newRemarkCode);
        }
        return  justRemarkCodesList;
    }

    /**
     * This method returns the row for a given remark code from the RemarkCode table.
     * @param remarkCode
     * @return ResultSet containing the row info for a given RemarkCode.
     * @throws SQLException
     */
    public static ResultSet getSingleRowInfo(String remarkCode) throws SQLException {
        remarkCode = SQLGeneral.removeApostrophes(remarkCode);
        remarkCode = SQLGeneral.removeSpaces(remarkCode);
        String query = "SELECT * FROM " + tableName +  " WHERE " + primaryKeyColName + " = '" + remarkCode + "';";
        ResultSet singleRowInfo = SQLGeneral.executeQuery(query);
        return singleRowInfo;
    }

    /**
     * This method inserts random "most commonly used with" data into the RemarkCode table in the database.
     *
     * While it would be faster to affect a whole row at a time, the inverse elements must be equal
     * (Col 1, Row 2 must be equal to Col 2, Row 1), so this method will update elements one at a time.
     *
     * Could've technically been faster but this method won't be used enough to make it worth it.  Never used by normal
     * users.
     *
     * @throws SQLException
     */
    public static void insertRandomUsedWithDataAndNotes() throws SQLException {
        ArrayList<String> justRemarkCodes = getNewJustRemarkCodes();
        int numberOfRemarkCodes = justRemarkCodes.size();
        Random random = new Random();
        int chanceOfData;
        for (String remarkCodeRow : justRemarkCodes){
            chanceOfData = random.nextInt(10);
            if (chanceOfData > 4) {
                int numberOfColumnsToUpdate = random.nextInt(16);
                for (int i = 0; i < numberOfColumnsToUpdate; i++) {
                    int randomIndex = random.nextInt(numberOfRemarkCodes);
                    int randomNumberToAdd = random.nextInt(25);
                    String randomRemarkCode = justRemarkCodes.get(randomIndex);
                    if(!randomRemarkCode.equals(remarkCodeRow)) {
                        updateMostCommonlyUsedWith(randomRemarkCode, remarkCodeRow, randomNumberToAdd);
                    }
                }
            }
        }
        setExampleNotes();
    }

    /**
     * Sets some remark codes so that they have example notes for example purposes.
     * @throws SQLException
     */
    public static void setExampleNotes() throws SQLException {
        String query = "UPDATE " + tableName + " SET Notes='Example Notes' WHERE " + primaryKeyColName + " LIKE '1%' OR "
                + primaryKeyColName + " LIKE '7%'";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method will create a table (the table and columns depend on the class
     * which implements this method
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        String query = "CREATE TABLE " + tableName + " (\n" +
                "\tRemarkCode VARCHAR[6] PRIMARY KEY NOT NULL NOT NULL,\n" +
                "\tCarcRarcID INTEGER NOT NULL,\n" +
                "\tDescription VARCHAR[500] NOT NULL,\n" +
                "\tNotes VARCHAR[500],\n" +
                "\tR1 INTEGER DEFAULT 0,\n" +
                "\tR2 INTEGER DEFAULT 0,\n" +
                "\tR3 INTEGER DEFAULT 0,\n" +
                "\tR4 INTEGER DEFAULT 0,\n" +
                "\tR5 INTEGER DEFAULT 0,\n" +
                "\tR6 INTEGER DEFAULT 0,\n" +
                "\tR7 INTEGER DEFAULT 0,\n" +
                "\tR8 INTEGER DEFAULT 0,\n" +
                "\tR9 INTEGER DEFAULT 0,\n" +
                "\tR10 INTEGER DEFAULT 0,\n" +
                "\tR11 INTEGER DEFAULT 0,\n" +
                "\tR12 INTEGER DEFAULT 0,\n" +
                "\tR13 INTEGER DEFAULT 0,\n" +
                "\tR14 INTEGER DEFAULT 0,\n" +
                "\tR16 INTEGER DEFAULT 0,\n" +
                "\tR18 INTEGER DEFAULT 0,\n" +
                "\tR19 INTEGER DEFAULT 0,\n" +
                "\tR20 INTEGER DEFAULT 0,\n" +
                "\tR21 INTEGER DEFAULT 0,\n" +
                "\tR22 INTEGER DEFAULT 0,\n" +
                "\tR23 INTEGER DEFAULT 0,\n" +
                "\tR24 INTEGER DEFAULT 0,\n" +
                "\tR26 INTEGER DEFAULT 0,\n" +
                "\tR27 INTEGER DEFAULT 0,\n" +
                "\tR29 INTEGER DEFAULT 0,\n" +
                "\tR31 INTEGER DEFAULT 0,\n" +
                "\tR32 INTEGER DEFAULT 0,\n" +
                "\tR33 INTEGER DEFAULT 0,\n" +
                "\tR34 INTEGER DEFAULT 0,\n" +
                "\tR35 INTEGER DEFAULT 0,\n" +
                "\tR39 INTEGER DEFAULT 0,\n" +
                "\tR40 INTEGER DEFAULT 0,\n" +
                "\tR44 INTEGER DEFAULT 0,\n" +
                "\tR45 INTEGER DEFAULT 0,\n" +
                "\tR49 INTEGER DEFAULT 0,\n" +
                "\tR50 INTEGER DEFAULT 0,\n" +
                "\tR51 INTEGER DEFAULT 0,\n" +
                "\tR53 INTEGER DEFAULT 0,\n" +
                "\tR54 INTEGER DEFAULT 0,\n" +
                "\tR55 INTEGER DEFAULT 0,\n" +
                "\tR56 INTEGER DEFAULT 0,\n" +
                "\tR58 INTEGER DEFAULT 0,\n" +
                "\tR59 INTEGER DEFAULT 0,\n" +
                "\tR60 INTEGER DEFAULT 0,\n" +
                "\tR61 INTEGER DEFAULT 0,\n" +
                "\tR66 INTEGER DEFAULT 0,\n" +
                "\tR69 INTEGER DEFAULT 0,\n" +
                "\tR70 INTEGER DEFAULT 0,\n" +
                "\tR74 INTEGER DEFAULT 0,\n" +
                "\tR75 INTEGER DEFAULT 0,\n" +
                "\tR76 INTEGER DEFAULT 0,\n" +
                "\tR78 INTEGER DEFAULT 0,\n" +
                "\tR85 INTEGER DEFAULT 0,\n" +
                "\tR89 INTEGER DEFAULT 0,\n" +
                "\tR90 INTEGER DEFAULT 0,\n" +
                "\tR91 INTEGER DEFAULT 0,\n" +
                "\tR94 INTEGER DEFAULT 0,\n" +
                "\tR95 INTEGER DEFAULT 0,\n" +
                "\tR96 INTEGER DEFAULT 0,\n" +
                "\tR97 INTEGER DEFAULT 0,\n" +
                "\tR100 INTEGER DEFAULT 0,\n" +
                "\tR101 INTEGER DEFAULT 0,\n" +
                "\tR102 INTEGER DEFAULT 0,\n" +
                "\tR103 INTEGER DEFAULT 0,\n" +
                "\tR104 INTEGER DEFAULT 0,\n" +
                "\tR105 INTEGER DEFAULT 0,\n" +
                "\tR106 INTEGER DEFAULT 0,\n" +
                "\tR107 INTEGER DEFAULT 0,\n" +
                "\tR108 INTEGER DEFAULT 0,\n" +
                "\tR109 INTEGER DEFAULT 0,\n" +
                "\tR110 INTEGER DEFAULT 0,\n" +
                "\tR111 INTEGER DEFAULT 0,\n" +
                "\tR112 INTEGER DEFAULT 0,\n" +
                "\tR114 INTEGER DEFAULT 0,\n" +
                "\tR115 INTEGER DEFAULT 0,\n" +
                "\tR116 INTEGER DEFAULT 0,\n" +
                "\tR117 INTEGER DEFAULT 0,\n" +
                "\tR118 INTEGER DEFAULT 0,\n" +
                "\tR119 INTEGER DEFAULT 0,\n" +
                "\tR121 INTEGER DEFAULT 0,\n" +
                "\tR122 INTEGER DEFAULT 0,\n" +
                "\tR128 INTEGER DEFAULT 0,\n" +
                "\tR129 INTEGER DEFAULT 0,\n" +
                "\tR130 INTEGER DEFAULT 0,\n" +
                "\tR131 INTEGER DEFAULT 0,\n" +
                "\tR132 INTEGER DEFAULT 0,\n" +
                "\tR133 INTEGER DEFAULT 0,\n" +
                "\tR134 INTEGER DEFAULT 0,\n" +
                "\tR135 INTEGER DEFAULT 0,\n" +
                "\tR136 INTEGER DEFAULT 0,\n" +
                "\tR137 INTEGER DEFAULT 0,\n" +
                "\tR139 INTEGER DEFAULT 0,\n" +
                "\tR140 INTEGER DEFAULT 0,\n" +
                "\tR142 INTEGER DEFAULT 0,\n" +
                "\tR143 INTEGER DEFAULT 0,\n" +
                "\tR144 INTEGER DEFAULT 0,\n" +
                "\tR146 INTEGER DEFAULT 0,\n" +
                "\tR147 INTEGER DEFAULT 0,\n" +
                "\tR148 INTEGER DEFAULT 0,\n" +
                "\tR149 INTEGER DEFAULT 0,\n" +
                "\tR150 INTEGER DEFAULT 0,\n" +
                "\tR151 INTEGER DEFAULT 0,\n" +
                "\tR152 INTEGER DEFAULT 0,\n" +
                "\tR153 INTEGER DEFAULT 0,\n" +
                "\tR154 INTEGER DEFAULT 0,\n" +
                "\tR155 INTEGER DEFAULT 0,\n" +
                "\tR157 INTEGER DEFAULT 0,\n" +
                "\tR158 INTEGER DEFAULT 0,\n" +
                "\tR159 INTEGER DEFAULT 0,\n" +
                "\tR160 INTEGER DEFAULT 0,\n" +
                "\tR161 INTEGER DEFAULT 0,\n" +
                "\tR163 INTEGER DEFAULT 0,\n" +
                "\tR164 INTEGER DEFAULT 0,\n" +
                "\tR166 INTEGER DEFAULT 0,\n" +
                "\tR167 INTEGER DEFAULT 0,\n" +
                "\tR169 INTEGER DEFAULT 0,\n" +
                "\tR170 INTEGER DEFAULT 0,\n" +
                "\tR171 INTEGER DEFAULT 0,\n" +
                "\tR172 INTEGER DEFAULT 0,\n" +
                "\tR173 INTEGER DEFAULT 0,\n" +
                "\tR174 INTEGER DEFAULT 0,\n" +
                "\tR175 INTEGER DEFAULT 0,\n" +
                "\tR176 INTEGER DEFAULT 0,\n" +
                "\tR177 INTEGER DEFAULT 0,\n" +
                "\tR178 INTEGER DEFAULT 0,\n" +
                "\tR179 INTEGER DEFAULT 0,\n" +
                "\tR180 INTEGER DEFAULT 0,\n" +
                "\tR181 INTEGER DEFAULT 0,\n" +
                "\tR182 INTEGER DEFAULT 0,\n" +
                "\tR183 INTEGER DEFAULT 0,\n" +
                "\tR184 INTEGER DEFAULT 0,\n" +
                "\tR185 INTEGER DEFAULT 0,\n" +
                "\tR186 INTEGER DEFAULT 0,\n" +
                "\tR187 INTEGER DEFAULT 0,\n" +
                "\tR188 INTEGER DEFAULT 0,\n" +
                "\tR189 INTEGER DEFAULT 0,\n" +
                "\tR190 INTEGER DEFAULT 0,\n" +
                "\tR192 INTEGER DEFAULT 0,\n" +
                "\tR193 INTEGER DEFAULT 0,\n" +
                "\tR194 INTEGER DEFAULT 0,\n" +
                "\tR195 INTEGER DEFAULT 0,\n" +
                "\tR197 INTEGER DEFAULT 0,\n" +
                "\tR198 INTEGER DEFAULT 0,\n" +
                "\tR199 INTEGER DEFAULT 0,\n" +
                "\tR200 INTEGER DEFAULT 0,\n" +
                "\tR201 INTEGER DEFAULT 0,\n" +
                "\tR202 INTEGER DEFAULT 0,\n" +
                "\tR203 INTEGER DEFAULT 0,\n" +
                "\tR204 INTEGER DEFAULT 0,\n" +
                "\tR205 INTEGER DEFAULT 0,\n" +
                "\tR206 INTEGER DEFAULT 0,\n" +
                "\tR207 INTEGER DEFAULT 0,\n" +
                "\tR208 INTEGER DEFAULT 0,\n" +
                "\tR209 INTEGER DEFAULT 0,\n" +
                "\tR210 INTEGER DEFAULT 0,\n" +
                "\tR211 INTEGER DEFAULT 0,\n" +
                "\tR212 INTEGER DEFAULT 0,\n" +
                "\tR213 INTEGER DEFAULT 0,\n" +
                "\tR215 INTEGER DEFAULT 0,\n" +
                "\tR216 INTEGER DEFAULT 0,\n" +
                "\tR219 INTEGER DEFAULT 0,\n" +
                "\tR222 INTEGER DEFAULT 0,\n" +
                "\tR223 INTEGER DEFAULT 0,\n" +
                "\tR224 INTEGER DEFAULT 0,\n" +
                "\tR225 INTEGER DEFAULT 0,\n" +
                "\tR226 INTEGER DEFAULT 0,\n" +
                "\tR227 INTEGER DEFAULT 0,\n" +
                "\tR228 INTEGER DEFAULT 0,\n" +
                "\tR229 INTEGER DEFAULT 0,\n" +
                "\tR231 INTEGER DEFAULT 0,\n" +
                "\tR232 INTEGER DEFAULT 0,\n" +
                "\tR233 INTEGER DEFAULT 0,\n" +
                "\tR234 INTEGER DEFAULT 0,\n" +
                "\tR235 INTEGER DEFAULT 0,\n" +
                "\tR236 INTEGER DEFAULT 0,\n" +
                "\tR237 INTEGER DEFAULT 0,\n" +
                "\tR238 INTEGER DEFAULT 0,\n" +
                "\tR239 INTEGER DEFAULT 0,\n" +
                "\tR240 INTEGER DEFAULT 0,\n" +
                "\tR241 INTEGER DEFAULT 0,\n" +
                "\tR242 INTEGER DEFAULT 0,\n" +
                "\tR243 INTEGER DEFAULT 0,\n" +
                "\tR245 INTEGER DEFAULT 0,\n" +
                "\tR246 INTEGER DEFAULT 0,\n" +
                "\tR247 INTEGER DEFAULT 0,\n" +
                "\tR248 INTEGER DEFAULT 0,\n" +
                "\tR249 INTEGER DEFAULT 0,\n" +
                "\tR250 INTEGER DEFAULT 0,\n" +
                "\tR251 INTEGER DEFAULT 0,\n" +
                "\tR252 INTEGER DEFAULT 0,\n" +
                "\tR253 INTEGER DEFAULT 0,\n" +
                "\tR254 INTEGER DEFAULT 0,\n" +
                "\tR256 INTEGER DEFAULT 0,\n" +
                "\tR257 INTEGER DEFAULT 0,\n" +
                "\tR258 INTEGER DEFAULT 0,\n" +
                "\tR259 INTEGER DEFAULT 0,\n" +
                "\tR260 INTEGER DEFAULT 0,\n" +
                "\tR261 INTEGER DEFAULT 0,\n" +
                "\tR262 INTEGER DEFAULT 0,\n" +
                "\tR263 INTEGER DEFAULT 0,\n" +
                "\tR264 INTEGER DEFAULT 0,\n" +
                "\tR265 INTEGER DEFAULT 0,\n" +
                "\tR266 INTEGER DEFAULT 0,\n" +
                "\tR267 INTEGER DEFAULT 0,\n" +
                "\tR268 INTEGER DEFAULT 0,\n" +
                "\tR269 INTEGER DEFAULT 0,\n" +
                "\tR270 INTEGER DEFAULT 0,\n" +
                "\tR271 INTEGER DEFAULT 0,\n" +
                "\tR272 INTEGER DEFAULT 0,\n" +
                "\tR273 INTEGER DEFAULT 0,\n" +
                "\tR274 INTEGER DEFAULT 0,\n" +
                "\tR275 INTEGER DEFAULT 0,\n" +
                "\tR276 INTEGER DEFAULT 0,\n" +
                "\tR277 INTEGER DEFAULT 0,\n" +
                "\tR278 INTEGER DEFAULT 0,\n" +
                "\tR279 INTEGER DEFAULT 0,\n" +
                "\tR280 INTEGER DEFAULT 0,\n" +
                "\tR281 INTEGER DEFAULT 0,\n" +
                "\tR282 INTEGER DEFAULT 0,\n" +
                "\tR283 INTEGER DEFAULT 0,\n" +
                "\tR284 INTEGER DEFAULT 0,\n" +
                "\tR285 INTEGER DEFAULT 0,\n" +
                "\tR286 INTEGER DEFAULT 0,\n" +
                "\tR287 INTEGER DEFAULT 0,\n" +
                "\tR288 INTEGER DEFAULT 0,\n" +
                "\tR289 INTEGER DEFAULT 0,\n" +
                "\tR290 INTEGER DEFAULT 0,\n" +
                "\tR291 INTEGER DEFAULT 0,\n" +
                "\tR292 INTEGER DEFAULT 0,\n" +
                "\tR293 INTEGER DEFAULT 0,\n" +
                "\tR294 INTEGER DEFAULT 0,\n" +
                "\tR295 INTEGER DEFAULT 0,\n" +
                "\tR296 INTEGER DEFAULT 0,\n" +
                "\tR297 INTEGER DEFAULT 0,\n" +
                "\tR298 INTEGER DEFAULT 0,\n" +
                "\tR299 INTEGER DEFAULT 0,\n" +
                "\tR300 INTEGER DEFAULT 0,\n" +
                "\tR301 INTEGER DEFAULT 0,\n" +
                "\tR302 INTEGER DEFAULT 0,\n" +
                "\tR303 INTEGER DEFAULT 0,\n" +
                "\tR304 INTEGER DEFAULT 0,\n" +
                "\tR305 INTEGER DEFAULT 0,\n" +
                "\tRA0 INTEGER DEFAULT 0,\n" +
                "\tRA1 INTEGER DEFAULT 0,\n" +
                "\tRA5 INTEGER DEFAULT 0,\n" +
                "\tRA6 INTEGER DEFAULT 0,\n" +
                "\tRA8 INTEGER DEFAULT 0,\n" +
                "\tRB1 INTEGER DEFAULT 0,\n" +
                "\tRB4 INTEGER DEFAULT 0,\n" +
                "\tRB7 INTEGER DEFAULT 0,\n" +
                "\tRB8 INTEGER DEFAULT 0,\n" +
                "\tRB9 INTEGER DEFAULT 0,\n" +
                "\tRB10 INTEGER DEFAULT 0,\n" +
                "\tRB11 INTEGER DEFAULT 0,\n" +
                "\tRB12 INTEGER DEFAULT 0,\n" +
                "\tRB13 INTEGER DEFAULT 0,\n" +
                "\tRB14 INTEGER DEFAULT 0,\n" +
                "\tRB15 INTEGER DEFAULT 0,\n" +
                "\tRB16 INTEGER DEFAULT 0,\n" +
                "\tRB20 INTEGER DEFAULT 0,\n" +
                "\tRB22 INTEGER DEFAULT 0,\n" +
                "\tRB23 INTEGER DEFAULT 0,\n" +
                "\tRP1 INTEGER DEFAULT 0,\n" +
                "\tRP2 INTEGER DEFAULT 0,\n" +
                "\tRP3 INTEGER DEFAULT 0,\n" +
                "\tRP4 INTEGER DEFAULT 0,\n" +
                "\tRP5 INTEGER DEFAULT 0,\n" +
                "\tRP6 INTEGER DEFAULT 0,\n" +
                "\tRP7 INTEGER DEFAULT 0,\n" +
                "\tRP8 INTEGER DEFAULT 0,\n" +
                "\tRP9 INTEGER DEFAULT 0,\n" +
                "\tRP10 INTEGER DEFAULT 0,\n" +
                "\tRP11 INTEGER DEFAULT 0,\n" +
                "\tRP12 INTEGER DEFAULT 0,\n" +
                "\tRP13 INTEGER DEFAULT 0,\n" +
                "\tRP14 INTEGER DEFAULT 0,\n" +
                "\tRP15 INTEGER DEFAULT 0,\n" +
                "\tRP16 INTEGER DEFAULT 0,\n" +
                "\tRP17 INTEGER DEFAULT 0,\n" +
                "\tRP18 INTEGER DEFAULT 0,\n" +
                "\tRP19 INTEGER DEFAULT 0,\n" +
                "\tRP20 INTEGER DEFAULT 0,\n" +
                "\tRP21 INTEGER DEFAULT 0,\n" +
                "\tRP22 INTEGER DEFAULT 0,\n" +
                "\tRP23 INTEGER DEFAULT 0,\n" +
                "\tRP24 INTEGER DEFAULT 0,\n" +
                "\tRP25 INTEGER DEFAULT 0,\n" +
                "\tRP26 INTEGER DEFAULT 0,\n" +
                "\tRP27 INTEGER DEFAULT 0,\n" +
                "\tRP28 INTEGER DEFAULT 0,\n" +
                "\tRP29 INTEGER DEFAULT 0,\n" +
                "\tRP30 INTEGER DEFAULT 0,\n" +
                "\tRP31 INTEGER DEFAULT 0,\n" +
                "\tRM1 INTEGER DEFAULT 0,\n" +
                "\tRM2 INTEGER DEFAULT 0,\n" +
                "\tRM3 INTEGER DEFAULT 0,\n" +
                "\tRM4 INTEGER DEFAULT 0,\n" +
                "\tRM5 INTEGER DEFAULT 0,\n" +
                "\tRM6 INTEGER DEFAULT 0,\n" +
                "\tRM7 INTEGER DEFAULT 0,\n" +
                "\tRM8 INTEGER DEFAULT 0,\n" +
                "\tRM9 INTEGER DEFAULT 0,\n" +
                "\tRM10 INTEGER DEFAULT 0,\n" +
                "\tRM11 INTEGER DEFAULT 0,\n" +
                "\tRM12 INTEGER DEFAULT 0,\n" +
                "\tRM13 INTEGER DEFAULT 0,\n" +
                "\tRM14 INTEGER DEFAULT 0,\n" +
                "\tRM15 INTEGER DEFAULT 0,\n" +
                "\tRM16 INTEGER DEFAULT 0,\n" +
                "\tRM17 INTEGER DEFAULT 0,\n" +
                "\tRM18 INTEGER DEFAULT 0,\n" +
                "\tRM19 INTEGER DEFAULT 0,\n" +
                "\tRM20 INTEGER DEFAULT 0,\n" +
                "\tRM21 INTEGER DEFAULT 0,\n" +
                "\tRM22 INTEGER DEFAULT 0,\n" +
                "\tRM23 INTEGER DEFAULT 0,\n" +
                "\tRM24 INTEGER DEFAULT 0,\n" +
                "\tRM25 INTEGER DEFAULT 0,\n" +
                "\tRM26 INTEGER DEFAULT 0,\n" +
                "\tRM27 INTEGER DEFAULT 0,\n" +
                "\tRM28 INTEGER DEFAULT 0,\n" +
                "\tRM29 INTEGER DEFAULT 0,\n" +
                "\tRM30 INTEGER DEFAULT 0,\n" +
                "\tRM31 INTEGER DEFAULT 0,\n" +
                "\tRM32 INTEGER DEFAULT 0,\n" +
                "\tRM36 INTEGER DEFAULT 0,\n" +
                "\tRM37 INTEGER DEFAULT 0,\n" +
                "\tRM38 INTEGER DEFAULT 0,\n" +
                "\tRM39 INTEGER DEFAULT 0,\n" +
                "\tRM40 INTEGER DEFAULT 0,\n" +
                "\tRM41 INTEGER DEFAULT 0,\n" +
                "\tRM42 INTEGER DEFAULT 0,\n" +
                "\tRM44 INTEGER DEFAULT 0,\n" +
                "\tRM45 INTEGER DEFAULT 0,\n" +
                "\tRM46 INTEGER DEFAULT 0,\n" +
                "\tRM47 INTEGER DEFAULT 0,\n" +
                "\tRM49 INTEGER DEFAULT 0,\n" +
                "\tRM50 INTEGER DEFAULT 0,\n" +
                "\tRM51 INTEGER DEFAULT 0,\n" +
                "\tRM52 INTEGER DEFAULT 0,\n" +
                "\tRM53 INTEGER DEFAULT 0,\n" +
                "\tRM54 INTEGER DEFAULT 0,\n" +
                "\tRM55 INTEGER DEFAULT 0,\n" +
                "\tRM56 INTEGER DEFAULT 0,\n" +
                "\tRM59 INTEGER DEFAULT 0,\n" +
                "\tRM60 INTEGER DEFAULT 0,\n" +
                "\tRM61 INTEGER DEFAULT 0,\n" +
                "\tRM62 INTEGER DEFAULT 0,\n" +
                "\tRM64 INTEGER DEFAULT 0,\n" +
                "\tRM65 INTEGER DEFAULT 0,\n" +
                "\tRM66 INTEGER DEFAULT 0,\n" +
                "\tRM67 INTEGER DEFAULT 0,\n" +
                "\tRM69 INTEGER DEFAULT 0,\n" +
                "\tRM70 INTEGER DEFAULT 0,\n" +
                "\tRM71 INTEGER DEFAULT 0,\n" +
                "\tRM73 INTEGER DEFAULT 0,\n" +
                "\tRM74 INTEGER DEFAULT 0,\n" +
                "\tRM75 INTEGER DEFAULT 0,\n" +
                "\tRM76 INTEGER DEFAULT 0,\n" +
                "\tRM77 INTEGER DEFAULT 0,\n" +
                "\tRM79 INTEGER DEFAULT 0,\n" +
                "\tRM80 INTEGER DEFAULT 0,\n" +
                "\tRM81 INTEGER DEFAULT 0,\n" +
                "\tRM82 INTEGER DEFAULT 0,\n" +
                "\tRM83 INTEGER DEFAULT 0,\n" +
                "\tRM84 INTEGER DEFAULT 0,\n" +
                "\tRM85 INTEGER DEFAULT 0,\n" +
                "\tRM86 INTEGER DEFAULT 0,\n" +
                "\tRM87 INTEGER DEFAULT 0,\n" +
                "\tRM89 INTEGER DEFAULT 0,\n" +
                "\tRM90 INTEGER DEFAULT 0,\n" +
                "\tRM91 INTEGER DEFAULT 0,\n" +
                "\tRM93 INTEGER DEFAULT 0,\n" +
                "\tRM94 INTEGER DEFAULT 0,\n" +
                "\tRM95 INTEGER DEFAULT 0,\n" +
                "\tRM96 INTEGER DEFAULT 0,\n" +
                "\tRM97 INTEGER DEFAULT 0,\n" +
                "\tRM99 INTEGER DEFAULT 0,\n" +
                "\tRM100 INTEGER DEFAULT 0,\n" +
                "\tRM102 INTEGER DEFAULT 0,\n" +
                "\tRM103 INTEGER DEFAULT 0,\n" +
                "\tRM104 INTEGER DEFAULT 0,\n" +
                "\tRM105 INTEGER DEFAULT 0,\n" +
                "\tRM107 INTEGER DEFAULT 0,\n" +
                "\tRM109 INTEGER DEFAULT 0,\n" +
                "\tRM111 INTEGER DEFAULT 0,\n" +
                "\tRM112 INTEGER DEFAULT 0,\n" +
                "\tRM113 INTEGER DEFAULT 0,\n" +
                "\tRM114 INTEGER DEFAULT 0,\n" +
                "\tRM115 INTEGER DEFAULT 0,\n" +
                "\tRM116 INTEGER DEFAULT 0,\n" +
                "\tRM117 INTEGER DEFAULT 0,\n" +
                "\tRM119 INTEGER DEFAULT 0,\n" +
                "\tRM121 INTEGER DEFAULT 0,\n" +
                "\tRM122 INTEGER DEFAULT 0,\n" +
                "\tRM123 INTEGER DEFAULT 0,\n" +
                "\tRM124 INTEGER DEFAULT 0,\n" +
                "\tRM125 INTEGER DEFAULT 0,\n" +
                "\tRM126 INTEGER DEFAULT 0,\n" +
                "\tRM127 INTEGER DEFAULT 0,\n" +
                "\tRM129 INTEGER DEFAULT 0,\n" +
                "\tRM130 INTEGER DEFAULT 0,\n" +
                "\tRM131 INTEGER DEFAULT 0,\n" +
                "\tRM132 INTEGER DEFAULT 0,\n" +
                "\tRM133 INTEGER DEFAULT 0,\n" +
                "\tRM134 INTEGER DEFAULT 0,\n" +
                "\tRM135 INTEGER DEFAULT 0,\n" +
                "\tRM136 INTEGER DEFAULT 0,\n" +
                "\tRM137 INTEGER DEFAULT 0,\n" +
                "\tRM138 INTEGER DEFAULT 0,\n" +
                "\tRM139 INTEGER DEFAULT 0,\n" +
                "\tRM141 INTEGER DEFAULT 0,\n" +
                "\tRM142 INTEGER DEFAULT 0,\n" +
                "\tRM143 INTEGER DEFAULT 0,\n" +
                "\tRM144 INTEGER DEFAULT 0,\n" +
                "\tRMA01 INTEGER DEFAULT 0,\n" +
                "\tRMA02 INTEGER DEFAULT 0,\n" +
                "\tRMA04 INTEGER DEFAULT 0,\n" +
                "\tRMA07 INTEGER DEFAULT 0,\n" +
                "\tRMA08 INTEGER DEFAULT 0,\n" +
                "\tRMA09 INTEGER DEFAULT 0,\n" +
                "\tRMA10 INTEGER DEFAULT 0,\n" +
                "\tRMA12 INTEGER DEFAULT 0,\n" +
                "\tRMA13 INTEGER DEFAULT 0,\n" +
                "\tRMA14 INTEGER DEFAULT 0,\n" +
                "\tRMA15 INTEGER DEFAULT 0,\n" +
                "\tRMA16 INTEGER DEFAULT 0,\n" +
                "\tRMA17 INTEGER DEFAULT 0,\n" +
                "\tRMA18 INTEGER DEFAULT 0,\n" +
                "\tRMA19 INTEGER DEFAULT 0,\n" +
                "\tRMA20 INTEGER DEFAULT 0,\n" +
                "\tRMA21 INTEGER DEFAULT 0,\n" +
                "\tRMA22 INTEGER DEFAULT 0,\n" +
                "\tRMA23 INTEGER DEFAULT 0,\n" +
                "\tRMA24 INTEGER DEFAULT 0,\n" +
                "\tRMA25 INTEGER DEFAULT 0,\n" +
                "\tRMA26 INTEGER DEFAULT 0,\n" +
                "\tRMA27 INTEGER DEFAULT 0,\n" +
                "\tRMA28 INTEGER DEFAULT 0,\n" +
                "\tRMA30 INTEGER DEFAULT 0,\n" +
                "\tRMA31 INTEGER DEFAULT 0,\n" +
                "\tRMA32 INTEGER DEFAULT 0,\n" +
                "\tRMA33 INTEGER DEFAULT 0,\n" +
                "\tRMA34 INTEGER DEFAULT 0,\n" +
                "\tRMA35 INTEGER DEFAULT 0,\n" +
                "\tRMA36 INTEGER DEFAULT 0,\n" +
                "\tRMA37 INTEGER DEFAULT 0,\n" +
                "\tRMA39 INTEGER DEFAULT 0,\n" +
                "\tRMA40 INTEGER DEFAULT 0,\n" +
                "\tRMA41 INTEGER DEFAULT 0,\n" +
                "\tRMA42 INTEGER DEFAULT 0,\n" +
                "\tRMA43 INTEGER DEFAULT 0,\n" +
                "\tRMA44 INTEGER DEFAULT 0,\n" +
                "\tRMA45 INTEGER DEFAULT 0,\n" +
                "\tRMA46 INTEGER DEFAULT 0,\n" +
                "\tRMA47 INTEGER DEFAULT 0,\n" +
                "\tRMA48 INTEGER DEFAULT 0,\n" +
                "\tRMA50 INTEGER DEFAULT 0,\n" +
                "\tRMA53 INTEGER DEFAULT 0,\n" +
                "\tRMA54 INTEGER DEFAULT 0,\n" +
                "\tRMA55 INTEGER DEFAULT 0,\n" +
                "\tRMA56 INTEGER DEFAULT 0,\n" +
                "\tRMA57 INTEGER DEFAULT 0,\n" +
                "\tRMA58 INTEGER DEFAULT 0,\n" +
                "\tRMA59 INTEGER DEFAULT 0,\n" +
                "\tRMA60 INTEGER DEFAULT 0,\n" +
                "\tRMA61 INTEGER DEFAULT 0,\n" +
                "\tRMA62 INTEGER DEFAULT 0,\n" +
                "\tRMA63 INTEGER DEFAULT 0,\n" +
                "\tRMA64 INTEGER DEFAULT 0,\n" +
                "\tRMA65 INTEGER DEFAULT 0,\n" +
                "\tRMA66 INTEGER DEFAULT 0,\n" +
                "\tRMA67 INTEGER DEFAULT 0,\n" +
                "\tRMA68 INTEGER DEFAULT 0,\n" +
                "\tRMA69 INTEGER DEFAULT 0,\n" +
                "\tRMA70 INTEGER DEFAULT 0,\n" +
                "\tRMA71 INTEGER DEFAULT 0,\n" +
                "\tRMA72 INTEGER DEFAULT 0,\n" +
                "\tRMA73 INTEGER DEFAULT 0,\n" +
                "\tRMA74 INTEGER DEFAULT 0,\n" +
                "\tRMA75 INTEGER DEFAULT 0,\n" +
                "\tRMA76 INTEGER DEFAULT 0,\n" +
                "\tRMA77 INTEGER DEFAULT 0,\n" +
                "\tRMA79 INTEGER DEFAULT 0,\n" +
                "\tRMA80 INTEGER DEFAULT 0,\n" +
                "\tRMA81 INTEGER DEFAULT 0,\n" +
                "\tRMA83 INTEGER DEFAULT 0,\n" +
                "\tRMA84 INTEGER DEFAULT 0,\n" +
                "\tRMA88 INTEGER DEFAULT 0,\n" +
                "\tRMA89 INTEGER DEFAULT 0,\n" +
                "\tRMA90 INTEGER DEFAULT 0,\n" +
                "\tRMA91 INTEGER DEFAULT 0,\n" +
                "\tRMA92 INTEGER DEFAULT 0,\n" +
                "\tRMA93 INTEGER DEFAULT 0,\n" +
                "\tRMA94 INTEGER DEFAULT 0,\n" +
                "\tRMA96 INTEGER DEFAULT 0,\n" +
                "\tRMA97 INTEGER DEFAULT 0,\n" +
                "\tRMA99 INTEGER DEFAULT 0,\n" +
                "\tRMA100 INTEGER DEFAULT 0,\n" +
                "\tRMA103 INTEGER DEFAULT 0,\n" +
                "\tRMA106 INTEGER DEFAULT 0,\n" +
                "\tRMA107 INTEGER DEFAULT 0,\n" +
                "\tRMA108 INTEGER DEFAULT 0,\n" +
                "\tRMA109 INTEGER DEFAULT 0,\n" +
                "\tRMA110 INTEGER DEFAULT 0,\n" +
                "\tRMA111 INTEGER DEFAULT 0,\n" +
                "\tRMA112 INTEGER DEFAULT 0,\n" +
                "\tRMA113 INTEGER DEFAULT 0,\n" +
                "\tRMA114 INTEGER DEFAULT 0,\n" +
                "\tRMA115 INTEGER DEFAULT 0,\n" +
                "\tRMA116 INTEGER DEFAULT 0,\n" +
                "\tRMA117 INTEGER DEFAULT 0,\n" +
                "\tRMA118 INTEGER DEFAULT 0,\n" +
                "\tRMA120 INTEGER DEFAULT 0,\n" +
                "\tRMA121 INTEGER DEFAULT 0,\n" +
                "\tRMA122 INTEGER DEFAULT 0,\n" +
                "\tRMA123 INTEGER DEFAULT 0,\n" +
                "\tRMA125 INTEGER DEFAULT 0,\n" +
                "\tRMA126 INTEGER DEFAULT 0,\n" +
                "\tRMA128 INTEGER DEFAULT 0,\n" +
                "\tRMA130 INTEGER DEFAULT 0,\n" +
                "\tRMA131 INTEGER DEFAULT 0,\n" +
                "\tRMA132 INTEGER DEFAULT 0,\n" +
                "\tRMA133 INTEGER DEFAULT 0,\n" +
                "\tRMA134 INTEGER DEFAULT 0,\n" +
                "\tRN1 INTEGER DEFAULT 0,\n" +
                "\tRN2 INTEGER DEFAULT 0,\n" +
                "\tRN3 INTEGER DEFAULT 0,\n" +
                "\tRN4 INTEGER DEFAULT 0,\n" +
                "\tRN5 INTEGER DEFAULT 0,\n" +
                "\tRN6 INTEGER DEFAULT 0,\n" +
                "\tRN7 INTEGER DEFAULT 0,\n" +
                "\tRN8 INTEGER DEFAULT 0,\n" +
                "\tRN9 INTEGER DEFAULT 0,\n" +
                "\tRN10 INTEGER DEFAULT 0,\n" +
                "\tRN11 INTEGER DEFAULT 0,\n" +
                "\tRN12 INTEGER DEFAULT 0,\n" +
                "\tRN13 INTEGER DEFAULT 0,\n" +
                "\tRN15 INTEGER DEFAULT 0,\n" +
                "\tRN16 INTEGER DEFAULT 0,\n" +
                "\tRN19 INTEGER DEFAULT 0,\n" +
                "\tRN20 INTEGER DEFAULT 0,\n" +
                "\tRN21 INTEGER DEFAULT 0,\n" +
                "\tRN22 INTEGER DEFAULT 0,\n" +
                "\tRN23 INTEGER DEFAULT 0,\n" +
                "\tRN24 INTEGER DEFAULT 0,\n" +
                "\tRN25 INTEGER DEFAULT 0,\n" +
                "\tRN26 INTEGER DEFAULT 0,\n" +
                "\tRN27 INTEGER DEFAULT 0,\n" +
                "\tRN28 INTEGER DEFAULT 0,\n" +
                "\tRN30 INTEGER DEFAULT 0,\n" +
                "\tRN31 INTEGER DEFAULT 0,\n" +
                "\tRN32 INTEGER DEFAULT 0,\n" +
                "\tRN33 INTEGER DEFAULT 0,\n" +
                "\tRN34 INTEGER DEFAULT 0,\n" +
                "\tRN35 INTEGER DEFAULT 0,\n" +
                "\tRN36 INTEGER DEFAULT 0,\n" +
                "\tRN37 INTEGER DEFAULT 0,\n" +
                "\tRN39 INTEGER DEFAULT 0,\n" +
                "\tRN40 INTEGER DEFAULT 0,\n" +
                "\tRN42 INTEGER DEFAULT 0,\n" +
                "\tRN43 INTEGER DEFAULT 0,\n" +
                "\tRN45 INTEGER DEFAULT 0,\n" +
                "\tRN46 INTEGER DEFAULT 0,\n" +
                "\tRN47 INTEGER DEFAULT 0,\n" +
                "\tRN48 INTEGER DEFAULT 0,\n" +
                "\tRN49 INTEGER DEFAULT 0,\n" +
                "\tRN50 INTEGER DEFAULT 0,\n" +
                "\tRN51 INTEGER DEFAULT 0,\n" +
                "\tRN52 INTEGER DEFAULT 0,\n" +
                "\tRN53 INTEGER DEFAULT 0,\n" +
                "\tRN54 INTEGER DEFAULT 0,\n" +
                "\tRN55 INTEGER DEFAULT 0,\n" +
                "\tRN56 INTEGER DEFAULT 0,\n" +
                "\tRN57 INTEGER DEFAULT 0,\n" +
                "\tRN58 INTEGER DEFAULT 0,\n" +
                "\tRN59 INTEGER DEFAULT 0,\n" +
                "\tRN61 INTEGER DEFAULT 0,\n" +
                "\tRN62 INTEGER DEFAULT 0,\n" +
                "\tRN63 INTEGER DEFAULT 0,\n" +
                "\tRN64 INTEGER DEFAULT 0,\n" +
                "\tRN65 INTEGER DEFAULT 0,\n" +
                "\tRN67 INTEGER DEFAULT 0,\n" +
                "\tRN68 INTEGER DEFAULT 0,\n" +
                "\tRN69 INTEGER DEFAULT 0,\n" +
                "\tRN70 INTEGER DEFAULT 0,\n" +
                "\tRN71 INTEGER DEFAULT 0,\n" +
                "\tRN72 INTEGER DEFAULT 0,\n" +
                "\tRN74 INTEGER DEFAULT 0,\n" +
                "\tRN75 INTEGER DEFAULT 0,\n" +
                "\tRN76 INTEGER DEFAULT 0,\n" +
                "\tRN77 INTEGER DEFAULT 0,\n" +
                "\tRN78 INTEGER DEFAULT 0,\n" +
                "\tRN79 INTEGER DEFAULT 0,\n" +
                "\tRN80 INTEGER DEFAULT 0,\n" +
                "\tRN81 INTEGER DEFAULT 0,\n" +
                "\tRN82 INTEGER DEFAULT 0,\n" +
                "\tRN83 INTEGER DEFAULT 0,\n" +
                "\tRN84 INTEGER DEFAULT 0,\n" +
                "\tRN85 INTEGER DEFAULT 0,\n" +
                "\tRN86 INTEGER DEFAULT 0,\n" +
                "\tRN87 INTEGER DEFAULT 0,\n" +
                "\tRN88 INTEGER DEFAULT 0,\n" +
                "\tRN89 INTEGER DEFAULT 0,\n" +
                "\tRN90 INTEGER DEFAULT 0,\n" +
                "\tRN91 INTEGER DEFAULT 0,\n" +
                "\tRN92 INTEGER DEFAULT 0,\n" +
                "\tRN93 INTEGER DEFAULT 0,\n" +
                "\tRN94 INTEGER DEFAULT 0,\n" +
                "\tRN95 INTEGER DEFAULT 0,\n" +
                "\tRN96 INTEGER DEFAULT 0,\n" +
                "\tRN97 INTEGER DEFAULT 0,\n" +
                "\tRN98 INTEGER DEFAULT 0,\n" +
                "\tRN99 INTEGER DEFAULT 0,\n" +
                "\tRN103 INTEGER DEFAULT 0,\n" +
                "\tRN104 INTEGER DEFAULT 0,\n" +
                "\tRN105 INTEGER DEFAULT 0,\n" +
                "\tRN106 INTEGER DEFAULT 0,\n" +
                "\tRN107 INTEGER DEFAULT 0,\n" +
                "\tRN108 INTEGER DEFAULT 0,\n" +
                "\tRN109 INTEGER DEFAULT 0,\n" +
                "\tRN110 INTEGER DEFAULT 0,\n" +
                "\tRN111 INTEGER DEFAULT 0,\n" +
                "\tRN112 INTEGER DEFAULT 0,\n" +
                "\tRN113 INTEGER DEFAULT 0,\n" +
                "\tRN114 INTEGER DEFAULT 0,\n" +
                "\tRN115 INTEGER DEFAULT 0,\n" +
                "\tRN116 INTEGER DEFAULT 0,\n" +
                "\tRN117 INTEGER DEFAULT 0,\n" +
                "\tRN118 INTEGER DEFAULT 0,\n" +
                "\tRN119 INTEGER DEFAULT 0,\n" +
                "\tRN120 INTEGER DEFAULT 0,\n" +
                "\tRN121 INTEGER DEFAULT 0,\n" +
                "\tRN122 INTEGER DEFAULT 0,\n" +
                "\tRN123 INTEGER DEFAULT 0,\n" +
                "\tRN124 INTEGER DEFAULT 0,\n" +
                "\tRN125 INTEGER DEFAULT 0,\n" +
                "\tRN126 INTEGER DEFAULT 0,\n" +
                "\tRN127 INTEGER DEFAULT 0,\n" +
                "\tRN128 INTEGER DEFAULT 0,\n" +
                "\tRN129 INTEGER DEFAULT 0,\n" +
                "\tRN130 INTEGER DEFAULT 0,\n" +
                "\tRN131 INTEGER DEFAULT 0,\n" +
                "\tRN132 INTEGER DEFAULT 0,\n" +
                "\tRN133 INTEGER DEFAULT 0,\n" +
                "\tRN134 INTEGER DEFAULT 0,\n" +
                "\tRN135 INTEGER DEFAULT 0,\n" +
                "\tRN136 INTEGER DEFAULT 0,\n" +
                "\tRN137 INTEGER DEFAULT 0,\n" +
                "\tRN138 INTEGER DEFAULT 0,\n" +
                "\tRN139 INTEGER DEFAULT 0,\n" +
                "\tRN140 INTEGER DEFAULT 0,\n" +
                "\tRN141 INTEGER DEFAULT 0,\n" +
                "\tRN142 INTEGER DEFAULT 0,\n" +
                "\tRN143 INTEGER DEFAULT 0,\n" +
                "\tRN144 INTEGER DEFAULT 0,\n" +
                "\tRN146 INTEGER DEFAULT 0,\n" +
                "\tRN147 INTEGER DEFAULT 0,\n" +
                "\tRN148 INTEGER DEFAULT 0,\n" +
                "\tRN149 INTEGER DEFAULT 0,\n" +
                "\tRN150 INTEGER DEFAULT 0,\n" +
                "\tRN151 INTEGER DEFAULT 0,\n" +
                "\tRN152 INTEGER DEFAULT 0,\n" +
                "\tRN153 INTEGER DEFAULT 0,\n" +
                "\tRN154 INTEGER DEFAULT 0,\n" +
                "\tRN155 INTEGER DEFAULT 0,\n" +
                "\tRN156 INTEGER DEFAULT 0,\n" +
                "\tRN157 INTEGER DEFAULT 0,\n" +
                "\tRN158 INTEGER DEFAULT 0,\n" +
                "\tRN159 INTEGER DEFAULT 0,\n" +
                "\tRN160 INTEGER DEFAULT 0,\n" +
                "\tRN161 INTEGER DEFAULT 0,\n" +
                "\tRN162 INTEGER DEFAULT 0,\n" +
                "\tRN163 INTEGER DEFAULT 0,\n" +
                "\tRN167 INTEGER DEFAULT 0,\n" +
                "\tRN170 INTEGER DEFAULT 0,\n" +
                "\tRN171 INTEGER DEFAULT 0,\n" +
                "\tRN172 INTEGER DEFAULT 0,\n" +
                "\tRN173 INTEGER DEFAULT 0,\n" +
                "\tRN174 INTEGER DEFAULT 0,\n" +
                "\tRN175 INTEGER DEFAULT 0,\n" +
                "\tRN176 INTEGER DEFAULT 0,\n" +
                "\tRN177 INTEGER DEFAULT 0,\n" +
                "\tRN178 INTEGER DEFAULT 0,\n" +
                "\tRN179 INTEGER DEFAULT 0,\n" +
                "\tRN180 INTEGER DEFAULT 0,\n" +
                "\tRN181 INTEGER DEFAULT 0,\n" +
                "\tRN182 INTEGER DEFAULT 0,\n" +
                "\tRN183 INTEGER DEFAULT 0,\n" +
                "\tRN184 INTEGER DEFAULT 0,\n" +
                "\tRN185 INTEGER DEFAULT 0,\n" +
                "\tRN186 INTEGER DEFAULT 0,\n" +
                "\tRN187 INTEGER DEFAULT 0,\n" +
                "\tRN188 INTEGER DEFAULT 0,\n" +
                "\tRN189 INTEGER DEFAULT 0,\n" +
                "\tRN190 INTEGER DEFAULT 0,\n" +
                "\tRN191 INTEGER DEFAULT 0,\n" +
                "\tRN192 INTEGER DEFAULT 0,\n" +
                "\tRN193 INTEGER DEFAULT 0,\n" +
                "\tRN194 INTEGER DEFAULT 0,\n" +
                "\tRN195 INTEGER DEFAULT 0,\n" +
                "\tRN196 INTEGER DEFAULT 0,\n" +
                "\tRN197 INTEGER DEFAULT 0,\n" +
                "\tRN198 INTEGER DEFAULT 0,\n" +
                "\tRN199 INTEGER DEFAULT 0,\n" +
                "\tRN200 INTEGER DEFAULT 0,\n" +
                "\tRN202 INTEGER DEFAULT 0,\n" +
                "\tRN203 INTEGER DEFAULT 0,\n" +
                "\tRN204 INTEGER DEFAULT 0,\n" +
                "\tRN205 INTEGER DEFAULT 0,\n" +
                "\tRN206 INTEGER DEFAULT 0,\n" +
                "\tRN207 INTEGER DEFAULT 0,\n" +
                "\tRN208 INTEGER DEFAULT 0,\n" +
                "\tRN209 INTEGER DEFAULT 0,\n" +
                "\tRN210 INTEGER DEFAULT 0,\n" +
                "\tRN211 INTEGER DEFAULT 0,\n" +
                "\tRN212 INTEGER DEFAULT 0,\n" +
                "\tRN213 INTEGER DEFAULT 0,\n" +
                "\tRN214 INTEGER DEFAULT 0,\n" +
                "\tRN215 INTEGER DEFAULT 0,\n" +
                "\tRN216 INTEGER DEFAULT 0,\n" +
                "\tRN217 INTEGER DEFAULT 0,\n" +
                "\tRN218 INTEGER DEFAULT 0,\n" +
                "\tRN219 INTEGER DEFAULT 0,\n" +
                "\tRN220 INTEGER DEFAULT 0,\n" +
                "\tRN221 INTEGER DEFAULT 0,\n" +
                "\tRN222 INTEGER DEFAULT 0,\n" +
                "\tRN223 INTEGER DEFAULT 0,\n" +
                "\tRN224 INTEGER DEFAULT 0,\n" +
                "\tRN226 INTEGER DEFAULT 0,\n" +
                "\tRN227 INTEGER DEFAULT 0,\n" +
                "\tRN228 INTEGER DEFAULT 0,\n" +
                "\tRN229 INTEGER DEFAULT 0,\n" +
                "\tRN230 INTEGER DEFAULT 0,\n" +
                "\tRN231 INTEGER DEFAULT 0,\n" +
                "\tRN232 INTEGER DEFAULT 0,\n" +
                "\tRN233 INTEGER DEFAULT 0,\n" +
                "\tRN234 INTEGER DEFAULT 0,\n" +
                "\tRN235 INTEGER DEFAULT 0,\n" +
                "\tRN236 INTEGER DEFAULT 0,\n" +
                "\tRN237 INTEGER DEFAULT 0,\n" +
                "\tRN238 INTEGER DEFAULT 0,\n" +
                "\tRN239 INTEGER DEFAULT 0,\n" +
                "\tRN240 INTEGER DEFAULT 0,\n" +
                "\tRN241 INTEGER DEFAULT 0,\n" +
                "\tRN242 INTEGER DEFAULT 0,\n" +
                "\tRN243 INTEGER DEFAULT 0,\n" +
                "\tRN244 INTEGER DEFAULT 0,\n" +
                "\tRN245 INTEGER DEFAULT 0,\n" +
                "\tRN246 INTEGER DEFAULT 0,\n" +
                "\tRN247 INTEGER DEFAULT 0,\n" +
                "\tRN248 INTEGER DEFAULT 0,\n" +
                "\tRN249 INTEGER DEFAULT 0,\n" +
                "\tRN250 INTEGER DEFAULT 0,\n" +
                "\tRN251 INTEGER DEFAULT 0,\n" +
                "\tRN252 INTEGER DEFAULT 0,\n" +
                "\tRN253 INTEGER DEFAULT 0,\n" +
                "\tRN254 INTEGER DEFAULT 0,\n" +
                "\tRN255 INTEGER DEFAULT 0,\n" +
                "\tRN256 INTEGER DEFAULT 0,\n" +
                "\tRN257 INTEGER DEFAULT 0,\n" +
                "\tRN258 INTEGER DEFAULT 0,\n" +
                "\tRN259 INTEGER DEFAULT 0,\n" +
                "\tRN260 INTEGER DEFAULT 0,\n" +
                "\tRN261 INTEGER DEFAULT 0,\n" +
                "\tRN262 INTEGER DEFAULT 0,\n" +
                "\tRN263 INTEGER DEFAULT 0,\n" +
                "\tRN264 INTEGER DEFAULT 0,\n" +
                "\tRN265 INTEGER DEFAULT 0,\n" +
                "\tRN266 INTEGER DEFAULT 0,\n" +
                "\tRN267 INTEGER DEFAULT 0,\n" +
                "\tRN268 INTEGER DEFAULT 0,\n" +
                "\tRN269 INTEGER DEFAULT 0,\n" +
                "\tRN270 INTEGER DEFAULT 0,\n" +
                "\tRN271 INTEGER DEFAULT 0,\n" +
                "\tRN272 INTEGER DEFAULT 0,\n" +
                "\tRN273 INTEGER DEFAULT 0,\n" +
                "\tRN274 INTEGER DEFAULT 0,\n" +
                "\tRN275 INTEGER DEFAULT 0,\n" +
                "\tRN276 INTEGER DEFAULT 0,\n" +
                "\tRN277 INTEGER DEFAULT 0,\n" +
                "\tRN278 INTEGER DEFAULT 0,\n" +
                "\tRN279 INTEGER DEFAULT 0,\n" +
                "\tRN280 INTEGER DEFAULT 0,\n" +
                "\tRN281 INTEGER DEFAULT 0,\n" +
                "\tRN282 INTEGER DEFAULT 0,\n" +
                "\tRN283 INTEGER DEFAULT 0,\n" +
                "\tRN284 INTEGER DEFAULT 0,\n" +
                "\tRN285 INTEGER DEFAULT 0,\n" +
                "\tRN286 INTEGER DEFAULT 0,\n" +
                "\tRN287 INTEGER DEFAULT 0,\n" +
                "\tRN288 INTEGER DEFAULT 0,\n" +
                "\tRN289 INTEGER DEFAULT 0,\n" +
                "\tRN290 INTEGER DEFAULT 0,\n" +
                "\tRN291 INTEGER DEFAULT 0,\n" +
                "\tRN292 INTEGER DEFAULT 0,\n" +
                "\tRN293 INTEGER DEFAULT 0,\n" +
                "\tRN294 INTEGER DEFAULT 0,\n" +
                "\tRN295 INTEGER DEFAULT 0,\n" +
                "\tRN296 INTEGER DEFAULT 0,\n" +
                "\tRN297 INTEGER DEFAULT 0,\n" +
                "\tRN298 INTEGER DEFAULT 0,\n" +
                "\tRN299 INTEGER DEFAULT 0,\n" +
                "\tRN300 INTEGER DEFAULT 0,\n" +
                "\tRN301 INTEGER DEFAULT 0,\n" +
                "\tRN302 INTEGER DEFAULT 0,\n" +
                "\tRN303 INTEGER DEFAULT 0,\n" +
                "\tRN304 INTEGER DEFAULT 0,\n" +
                "\tRN305 INTEGER DEFAULT 0,\n" +
                "\tRN306 INTEGER DEFAULT 0,\n" +
                "\tRN307 INTEGER DEFAULT 0,\n" +
                "\tRN308 INTEGER DEFAULT 0,\n" +
                "\tRN309 INTEGER DEFAULT 0,\n" +
                "\tRN310 INTEGER DEFAULT 0,\n" +
                "\tRN311 INTEGER DEFAULT 0,\n" +
                "\tRN312 INTEGER DEFAULT 0,\n" +
                "\tRN313 INTEGER DEFAULT 0,\n" +
                "\tRN314 INTEGER DEFAULT 0,\n" +
                "\tRN315 INTEGER DEFAULT 0,\n" +
                "\tRN316 INTEGER DEFAULT 0,\n" +
                "\tRN317 INTEGER DEFAULT 0,\n" +
                "\tRN318 INTEGER DEFAULT 0,\n" +
                "\tRN319 INTEGER DEFAULT 0,\n" +
                "\tRN320 INTEGER DEFAULT 0,\n" +
                "\tRN321 INTEGER DEFAULT 0,\n" +
                "\tRN322 INTEGER DEFAULT 0,\n" +
                "\tRN323 INTEGER DEFAULT 0,\n" +
                "\tRN324 INTEGER DEFAULT 0,\n" +
                "\tRN325 INTEGER DEFAULT 0,\n" +
                "\tRN326 INTEGER DEFAULT 0,\n" +
                "\tRN327 INTEGER DEFAULT 0,\n" +
                "\tRN328 INTEGER DEFAULT 0,\n" +
                "\tRN329 INTEGER DEFAULT 0,\n" +
                "\tRN330 INTEGER DEFAULT 0,\n" +
                "\tRN331 INTEGER DEFAULT 0,\n" +
                "\tRN332 INTEGER DEFAULT 0,\n" +
                "\tRN333 INTEGER DEFAULT 0,\n" +
                "\tRN334 INTEGER DEFAULT 0,\n" +
                "\tRN335 INTEGER DEFAULT 0,\n" +
                "\tRN336 INTEGER DEFAULT 0,\n" +
                "\tRN337 INTEGER DEFAULT 0,\n" +
                "\tRN338 INTEGER DEFAULT 0,\n" +
                "\tRN339 INTEGER DEFAULT 0,\n" +
                "\tRN340 INTEGER DEFAULT 0,\n" +
                "\tRN341 INTEGER DEFAULT 0,\n" +
                "\tRN342 INTEGER DEFAULT 0,\n" +
                "\tRN343 INTEGER DEFAULT 0,\n" +
                "\tRN344 INTEGER DEFAULT 0,\n" +
                "\tRN345 INTEGER DEFAULT 0,\n" +
                "\tRN346 INTEGER DEFAULT 0,\n" +
                "\tRN347 INTEGER DEFAULT 0,\n" +
                "\tRN348 INTEGER DEFAULT 0,\n" +
                "\tRN349 INTEGER DEFAULT 0,\n" +
                "\tRN350 INTEGER DEFAULT 0,\n" +
                "\tRN351 INTEGER DEFAULT 0,\n" +
                "\tRN352 INTEGER DEFAULT 0,\n" +
                "\tRN353 INTEGER DEFAULT 0,\n" +
                "\tRN354 INTEGER DEFAULT 0,\n" +
                "\tRN355 INTEGER DEFAULT 0,\n" +
                "\tRN356 INTEGER DEFAULT 0,\n" +
                "\tRN357 INTEGER DEFAULT 0,\n" +
                "\tRN358 INTEGER DEFAULT 0,\n" +
                "\tRN359 INTEGER DEFAULT 0,\n" +
                "\tRN360 INTEGER DEFAULT 0,\n" +
                "\tRN362 INTEGER DEFAULT 0,\n" +
                "\tRN363 INTEGER DEFAULT 0,\n" +
                "\tRN364 INTEGER DEFAULT 0,\n" +
                "\tRN366 INTEGER DEFAULT 0,\n" +
                "\tRN367 INTEGER DEFAULT 0,\n" +
                "\tRN368 INTEGER DEFAULT 0,\n" +
                "\tRN369 INTEGER DEFAULT 0,\n" +
                "\tRN370 INTEGER DEFAULT 0,\n" +
                "\tRN371 INTEGER DEFAULT 0,\n" +
                "\tRN372 INTEGER DEFAULT 0,\n" +
                "\tRN373 INTEGER DEFAULT 0,\n" +
                "\tRN374 INTEGER DEFAULT 0,\n" +
                "\tRN375 INTEGER DEFAULT 0,\n" +
                "\tRN376 INTEGER DEFAULT 0,\n" +
                "\tRN377 INTEGER DEFAULT 0,\n" +
                "\tRN378 INTEGER DEFAULT 0,\n" +
                "\tRN379 INTEGER DEFAULT 0,\n" +
                "\tRN380 INTEGER DEFAULT 0,\n" +
                "\tRN381 INTEGER DEFAULT 0,\n" +
                "\tRN382 INTEGER DEFAULT 0,\n" +
                "\tRN383 INTEGER DEFAULT 0,\n" +
                "\tRN384 INTEGER DEFAULT 0,\n" +
                "\tRN385 INTEGER DEFAULT 0,\n" +
                "\tRN386 INTEGER DEFAULT 0,\n" +
                "\tRN387 INTEGER DEFAULT 0,\n" +
                "\tRN388 INTEGER DEFAULT 0,\n" +
                "\tRN389 INTEGER DEFAULT 0,\n" +
                "\tRN390 INTEGER DEFAULT 0,\n" +
                "\tRN391 INTEGER DEFAULT 0,\n" +
                "\tRN392 INTEGER DEFAULT 0,\n" +
                "\tRN393 INTEGER DEFAULT 0,\n" +
                "\tRN394 INTEGER DEFAULT 0,\n" +
                "\tRN395 INTEGER DEFAULT 0,\n" +
                "\tRN396 INTEGER DEFAULT 0,\n" +
                "\tRN397 INTEGER DEFAULT 0,\n" +
                "\tRN398 INTEGER DEFAULT 0,\n" +
                "\tRN399 INTEGER DEFAULT 0,\n" +
                "\tRN400 INTEGER DEFAULT 0,\n" +
                "\tRN401 INTEGER DEFAULT 0,\n" +
                "\tRN402 INTEGER DEFAULT 0,\n" +
                "\tRN403 INTEGER DEFAULT 0,\n" +
                "\tRN404 INTEGER DEFAULT 0,\n" +
                "\tRN405 INTEGER DEFAULT 0,\n" +
                "\tRN406 INTEGER DEFAULT 0,\n" +
                "\tRN407 INTEGER DEFAULT 0,\n" +
                "\tRN408 INTEGER DEFAULT 0,\n" +
                "\tRN409 INTEGER DEFAULT 0,\n" +
                "\tRN410 INTEGER DEFAULT 0,\n" +
                "\tRN411 INTEGER DEFAULT 0,\n" +
                "\tRN412 INTEGER DEFAULT 0,\n" +
                "\tRN413 INTEGER DEFAULT 0,\n" +
                "\tRN414 INTEGER DEFAULT 0,\n" +
                "\tRN415 INTEGER DEFAULT 0,\n" +
                "\tRN416 INTEGER DEFAULT 0,\n" +
                "\tRN417 INTEGER DEFAULT 0,\n" +
                "\tRN418 INTEGER DEFAULT 0,\n" +
                "\tRN419 INTEGER DEFAULT 0,\n" +
                "\tRN420 INTEGER DEFAULT 0,\n" +
                "\tRN421 INTEGER DEFAULT 0,\n" +
                "\tRN422 INTEGER DEFAULT 0,\n" +
                "\tRN423 INTEGER DEFAULT 0,\n" +
                "\tRN424 INTEGER DEFAULT 0,\n" +
                "\tRN425 INTEGER DEFAULT 0,\n" +
                "\tRN426 INTEGER DEFAULT 0,\n" +
                "\tRN427 INTEGER DEFAULT 0,\n" +
                "\tRN428 INTEGER DEFAULT 0,\n" +
                "\tRN429 INTEGER DEFAULT 0,\n" +
                "\tRN430 INTEGER DEFAULT 0,\n" +
                "\tRN431 INTEGER DEFAULT 0,\n" +
                "\tRN432 INTEGER DEFAULT 0,\n" +
                "\tRN433 INTEGER DEFAULT 0,\n" +
                "\tRN434 INTEGER DEFAULT 0,\n" +
                "\tRN435 INTEGER DEFAULT 0,\n" +
                "\tRN436 INTEGER DEFAULT 0,\n" +
                "\tRN437 INTEGER DEFAULT 0,\n" +
                "\tRN438 INTEGER DEFAULT 0,\n" +
                "\tRN439 INTEGER DEFAULT 0,\n" +
                "\tRN440 INTEGER DEFAULT 0,\n" +
                "\tRN441 INTEGER DEFAULT 0,\n" +
                "\tRN442 INTEGER DEFAULT 0,\n" +
                "\tRN443 INTEGER DEFAULT 0,\n" +
                "\tRN444 INTEGER DEFAULT 0,\n" +
                "\tRN445 INTEGER DEFAULT 0,\n" +
                "\tRN446 INTEGER DEFAULT 0,\n" +
                "\tRN447 INTEGER DEFAULT 0,\n" +
                "\tRN448 INTEGER DEFAULT 0,\n" +
                "\tRN449 INTEGER DEFAULT 0,\n" +
                "\tRN450 INTEGER DEFAULT 0,\n" +
                "\tRN451 INTEGER DEFAULT 0,\n" +
                "\tRN452 INTEGER DEFAULT 0,\n" +
                "\tRN453 INTEGER DEFAULT 0,\n" +
                "\tRN454 INTEGER DEFAULT 0,\n" +
                "\tRN455 INTEGER DEFAULT 0,\n" +
                "\tRN456 INTEGER DEFAULT 0,\n" +
                "\tRN457 INTEGER DEFAULT 0,\n" +
                "\tRN458 INTEGER DEFAULT 0,\n" +
                "\tRN459 INTEGER DEFAULT 0,\n" +
                "\tRN460 INTEGER DEFAULT 0,\n" +
                "\tRN461 INTEGER DEFAULT 0,\n" +
                "\tRN462 INTEGER DEFAULT 0,\n" +
                "\tRN463 INTEGER DEFAULT 0,\n" +
                "\tRN464 INTEGER DEFAULT 0,\n" +
                "\tRN465 INTEGER DEFAULT 0,\n" +
                "\tRN466 INTEGER DEFAULT 0,\n" +
                "\tRN467 INTEGER DEFAULT 0,\n" +
                "\tRN468 INTEGER DEFAULT 0,\n" +
                "\tRN469 INTEGER DEFAULT 0,\n" +
                "\tRN470 INTEGER DEFAULT 0,\n" +
                "\tRN471 INTEGER DEFAULT 0,\n" +
                "\tRN472 INTEGER DEFAULT 0,\n" +
                "\tRN473 INTEGER DEFAULT 0,\n" +
                "\tRN474 INTEGER DEFAULT 0,\n" +
                "\tRN475 INTEGER DEFAULT 0,\n" +
                "\tRN476 INTEGER DEFAULT 0,\n" +
                "\tRN477 INTEGER DEFAULT 0,\n" +
                "\tRN478 INTEGER DEFAULT 0,\n" +
                "\tRN479 INTEGER DEFAULT 0,\n" +
                "\tRN480 INTEGER DEFAULT 0,\n" +
                "\tRN481 INTEGER DEFAULT 0,\n" +
                "\tRN482 INTEGER DEFAULT 0,\n" +
                "\tRN485 INTEGER DEFAULT 0,\n" +
                "\tRN486 INTEGER DEFAULT 0,\n" +
                "\tRN487 INTEGER DEFAULT 0,\n" +
                "\tRN488 INTEGER DEFAULT 0,\n" +
                "\tRN489 INTEGER DEFAULT 0,\n" +
                "\tRN490 INTEGER DEFAULT 0,\n" +
                "\tRN491 INTEGER DEFAULT 0,\n" +
                "\tRN492 INTEGER DEFAULT 0,\n" +
                "\tRN493 INTEGER DEFAULT 0,\n" +
                "\tRN494 INTEGER DEFAULT 0,\n" +
                "\tRN495 INTEGER DEFAULT 0,\n" +
                "\tRN496 INTEGER DEFAULT 0,\n" +
                "\tRN497 INTEGER DEFAULT 0,\n" +
                "\tRN498 INTEGER DEFAULT 0,\n" +
                "\tRN499 INTEGER DEFAULT 0,\n" +
                "\tRN500 INTEGER DEFAULT 0,\n" +
                "\tRN501 INTEGER DEFAULT 0,\n" +
                "\tRN502 INTEGER DEFAULT 0,\n" +
                "\tRN503 INTEGER DEFAULT 0,\n" +
                "\tRN504 INTEGER DEFAULT 0,\n" +
                "\tRN505 INTEGER DEFAULT 0,\n" +
                "\tRN506 INTEGER DEFAULT 0,\n" +
                "\tRN507 INTEGER DEFAULT 0,\n" +
                "\tRN508 INTEGER DEFAULT 0,\n" +
                "\tRN509 INTEGER DEFAULT 0,\n" +
                "\tRN510 INTEGER DEFAULT 0,\n" +
                "\tRN511 INTEGER DEFAULT 0,\n" +
                "\tRN512 INTEGER DEFAULT 0,\n" +
                "\tRN513 INTEGER DEFAULT 0,\n" +
                "\tRN516 INTEGER DEFAULT 0,\n" +
                "\tRN517 INTEGER DEFAULT 0,\n" +
                "\tRN518 INTEGER DEFAULT 0,\n" +
                "\tRN519 INTEGER DEFAULT 0,\n" +
                "\tRN520 INTEGER DEFAULT 0,\n" +
                "\tRN521 INTEGER DEFAULT 0,\n" +
                "\tRN522 INTEGER DEFAULT 0,\n" +
                "\tRN523 INTEGER DEFAULT 0,\n" +
                "\tRN524 INTEGER DEFAULT 0,\n" +
                "\tRN525 INTEGER DEFAULT 0,\n" +
                "\tRN526 INTEGER DEFAULT 0,\n" +
                "\tRN527 INTEGER DEFAULT 0,\n" +
                "\tRN528 INTEGER DEFAULT 0,\n" +
                "\tRN529 INTEGER DEFAULT 0,\n" +
                "\tRN530 INTEGER DEFAULT 0,\n" +
                "\tRN531 INTEGER DEFAULT 0,\n" +
                "\tRN532 INTEGER DEFAULT 0,\n" +
                "\tRN533 INTEGER DEFAULT 0,\n" +
                "\tRN534 INTEGER DEFAULT 0,\n" +
                "\tRN535 INTEGER DEFAULT 0,\n" +
                "\tRN536 INTEGER DEFAULT 0,\n" +
                "\tRN537 INTEGER DEFAULT 0,\n" +
                "\tRN538 INTEGER DEFAULT 0,\n" +
                "\tRN539 INTEGER DEFAULT 0,\n" +
                "\tRN540 INTEGER DEFAULT 0,\n" +
                "\tRN541 INTEGER DEFAULT 0,\n" +
                "\tRN542 INTEGER DEFAULT 0,\n" +
                "\tRN543 INTEGER DEFAULT 0,\n" +
                "\tRN544 INTEGER DEFAULT 0,\n" +
                "\tRN545 INTEGER DEFAULT 0,\n" +
                "\tRN546 INTEGER DEFAULT 0,\n" +
                "\tRN547 INTEGER DEFAULT 0,\n" +
                "\tRN548 INTEGER DEFAULT 0,\n" +
                "\tRN549 INTEGER DEFAULT 0,\n" +
                "\tRN550 INTEGER DEFAULT 0,\n" +
                "\tRN551 INTEGER DEFAULT 0,\n" +
                "\tRN552 INTEGER DEFAULT 0,\n" +
                "\tRN554 INTEGER DEFAULT 0,\n" +
                "\tRN555 INTEGER DEFAULT 0,\n" +
                "\tRN556 INTEGER DEFAULT 0,\n" +
                "\tRN557 INTEGER DEFAULT 0,\n" +
                "\tRN558 INTEGER DEFAULT 0,\n" +
                "\tRN559 INTEGER DEFAULT 0,\n" +
                "\tRN560 INTEGER DEFAULT 0,\n" +
                "\tRN561 INTEGER DEFAULT 0,\n" +
                "\tRN562 INTEGER DEFAULT 0,\n" +
                "\tRN563 INTEGER DEFAULT 0,\n" +
                "\tRN564 INTEGER DEFAULT 0,\n" +
                "\tRN565 INTEGER DEFAULT 0,\n" +
                "\tRN566 INTEGER DEFAULT 0,\n" +
                "\tRN567 INTEGER DEFAULT 0,\n" +
                "\tRN568 INTEGER DEFAULT 0,\n" +
                "\tRN569 INTEGER DEFAULT 0,\n" +
                "\tRN570 INTEGER DEFAULT 0,\n" +
                "\tRN571 INTEGER DEFAULT 0,\n" +
                "\tRN572 INTEGER DEFAULT 0,\n" +
                "\tRN573 INTEGER DEFAULT 0,\n" +
                "\tRN574 INTEGER DEFAULT 0,\n" +
                "\tRN575 INTEGER DEFAULT 0,\n" +
                "\tRN576 INTEGER DEFAULT 0,\n" +
                "\tRN577 INTEGER DEFAULT 0,\n" +
                "\tRN578 INTEGER DEFAULT 0,\n" +
                "\tRN579 INTEGER DEFAULT 0,\n" +
                "\tRN580 INTEGER DEFAULT 0,\n" +
                "\tRN581 INTEGER DEFAULT 0,\n" +
                "\tRN582 INTEGER DEFAULT 0,\n" +
                "\tRN583 INTEGER DEFAULT 0,\n" +
                "\tRN584 INTEGER DEFAULT 0,\n" +
                "\tRN585 INTEGER DEFAULT 0,\n" +
                "\tRN586 INTEGER DEFAULT 0,\n" +
                "\tRN587 INTEGER DEFAULT 0,\n" +
                "\tRN588 INTEGER DEFAULT 0,\n" +
                "\tRN589 INTEGER DEFAULT 0,\n" +
                "\tRN590 INTEGER DEFAULT 0,\n" +
                "\tRN591 INTEGER DEFAULT 0,\n" +
                "\tRN592 INTEGER DEFAULT 0,\n" +
                "\tRN593 INTEGER DEFAULT 0,\n" +
                "\tRN594 INTEGER DEFAULT 0,\n" +
                "\tRN595 INTEGER DEFAULT 0,\n" +
                "\tRN596 INTEGER DEFAULT 0,\n" +
                "\tRN597 INTEGER DEFAULT 0,\n" +
                "\tRN598 INTEGER DEFAULT 0,\n" +
                "\tRN599 INTEGER DEFAULT 0,\n" +
                "\tRN600 INTEGER DEFAULT 0,\n" +
                "\tRN601 INTEGER DEFAULT 0,\n" +
                "\tRN602 INTEGER DEFAULT 0,\n" +
                "\tRN603 INTEGER DEFAULT 0,\n" +
                "\tRN604 INTEGER DEFAULT 0,\n" +
                "\tRN605 INTEGER DEFAULT 0,\n" +
                "\tRN606 INTEGER DEFAULT 0,\n" +
                "\tRN607 INTEGER DEFAULT 0,\n" +
                "\tRN608 INTEGER DEFAULT 0,\n" +
                "\tRN609 INTEGER DEFAULT 0,\n" +
                "\tRN610 INTEGER DEFAULT 0,\n" +
                "\tRN611 INTEGER DEFAULT 0,\n" +
                "\tRN612 INTEGER DEFAULT 0,\n" +
                "\tRN613 INTEGER DEFAULT 0,\n" +
                "\tRN614 INTEGER DEFAULT 0,\n" +
                "\tRN615 INTEGER DEFAULT 0,\n" +
                "\tRN616 INTEGER DEFAULT 0,\n" +
                "\tRN617 INTEGER DEFAULT 0,\n" +
                "\tRN618 INTEGER DEFAULT 0,\n" +
                "\tRN619 INTEGER DEFAULT 0,\n" +
                "\tRN620 INTEGER DEFAULT 0,\n" +
                "\tRN621 INTEGER DEFAULT 0,\n" +
                "\tRN622 INTEGER DEFAULT 0,\n" +
                "\tRN623 INTEGER DEFAULT 0,\n" +
                "\tRN624 INTEGER DEFAULT 0,\n" +
                "\tRN625 INTEGER DEFAULT 0,\n" +
                "\tRN626 INTEGER DEFAULT 0,\n" +
                "\tRN628 INTEGER DEFAULT 0,\n" +
                "\tRN629 INTEGER DEFAULT 0,\n" +
                "\tRN630 INTEGER DEFAULT 0,\n" +
                "\tRN631 INTEGER DEFAULT 0,\n" +
                "\tRN633 INTEGER DEFAULT 0,\n" +
                "\tRN634 INTEGER DEFAULT 0,\n" +
                "\tRN635 INTEGER DEFAULT 0,\n" +
                "\tRN636 INTEGER DEFAULT 0,\n" +
                "\tRN637 INTEGER DEFAULT 0,\n" +
                "\tRN638 INTEGER DEFAULT 0,\n" +
                "\tRN639 INTEGER DEFAULT 0,\n" +
                "\tRN640 INTEGER DEFAULT 0,\n" +
                "\tRN641 INTEGER DEFAULT 0,\n" +
                "\tRN642 INTEGER DEFAULT 0,\n" +
                "\tRN643 INTEGER DEFAULT 0,\n" +
                "\tRN644 INTEGER DEFAULT 0,\n" +
                "\tRN645 INTEGER DEFAULT 0,\n" +
                "\tRN646 INTEGER DEFAULT 0,\n" +
                "\tRN647 INTEGER DEFAULT 0,\n" +
                "\tRN648 INTEGER DEFAULT 0,\n" +
                "\tRN649 INTEGER DEFAULT 0,\n" +
                "\tRN650 INTEGER DEFAULT 0,\n" +
                "\tRN651 INTEGER DEFAULT 0,\n" +
                "\tRN652 INTEGER DEFAULT 0,\n" +
                "\tRN653 INTEGER DEFAULT 0,\n" +
                "\tRN654 INTEGER DEFAULT 0,\n" +
                "\tRN655 INTEGER DEFAULT 0,\n" +
                "\tRN656 INTEGER DEFAULT 0,\n" +
                "\tRN657 INTEGER DEFAULT 0,\n" +
                "\tRN658 INTEGER DEFAULT 0,\n" +
                "\tRN659 INTEGER DEFAULT 0,\n" +
                "\tRN660 INTEGER DEFAULT 0,\n" +
                "\tRN661 INTEGER DEFAULT 0,\n" +
                "\tRN662 INTEGER DEFAULT 0,\n" +
                "\tRN663 INTEGER DEFAULT 0,\n" +
                "\tRN664 INTEGER DEFAULT 0,\n" +
                "\tRN665 INTEGER DEFAULT 0,\n" +
                "\tRN666 INTEGER DEFAULT 0,\n" +
                "\tRN667 INTEGER DEFAULT 0,\n" +
                "\tRN668 INTEGER DEFAULT 0,\n" +
                "\tRN669 INTEGER DEFAULT 0,\n" +
                "\tRN670 INTEGER DEFAULT 0,\n" +
                "\tRN671 INTEGER DEFAULT 0,\n" +
                "\tRN672 INTEGER DEFAULT 0,\n" +
                "\tRN673 INTEGER DEFAULT 0,\n" +
                "\tRN674 INTEGER DEFAULT 0,\n" +
                "\tRN675 INTEGER DEFAULT 0,\n" +
                "\tRN676 INTEGER DEFAULT 0,\n" +
                "\tRN677 INTEGER DEFAULT 0,\n" +
                "\tRN678 INTEGER DEFAULT 0,\n" +
                "\tRN679 INTEGER DEFAULT 0,\n" +
                "\tRN680 INTEGER DEFAULT 0,\n" +
                "\tRN681 INTEGER DEFAULT 0,\n" +
                "\tRN682 INTEGER DEFAULT 0,\n" +
                "\tRN683 INTEGER DEFAULT 0,\n" +
                "\tRN684 INTEGER DEFAULT 0,\n" +
                "\tRN685 INTEGER DEFAULT 0,\n" +
                "\tRN686 INTEGER DEFAULT 0,\n" +
                "\tRN687 INTEGER DEFAULT 0,\n" +
                "\tRN688 INTEGER DEFAULT 0,\n" +
                "\tRN689 INTEGER DEFAULT 0,\n" +
                "\tRN690 INTEGER DEFAULT 0,\n" +
                "\tRN691 INTEGER DEFAULT 0,\n" +
                "\tRN692 INTEGER DEFAULT 0,\n" +
                "\tRN693 INTEGER DEFAULT 0,\n" +
                "\tRN694 INTEGER DEFAULT 0,\n" +
                "\tRN695 INTEGER DEFAULT 0,\n" +
                "\tRN696 INTEGER DEFAULT 0,\n" +
                "\tRN697 INTEGER DEFAULT 0,\n" +
                "\tRN698 INTEGER DEFAULT 0,\n" +
                "\tRN699 INTEGER DEFAULT 0,\n" +
                "\tRN700 INTEGER DEFAULT 0,\n" +
                "\tRN701 INTEGER DEFAULT 0,\n" +
                "\tRN702 INTEGER DEFAULT 0,\n" +
                "\tRN703 INTEGER DEFAULT 0,\n" +
                "\tRN704 INTEGER DEFAULT 0,\n" +
                "\tRN705 INTEGER DEFAULT 0,\n" +
                "\tRN706 INTEGER DEFAULT 0,\n" +
                "\tRN707 INTEGER DEFAULT 0,\n" +
                "\tRN708 INTEGER DEFAULT 0,\n" +
                "\tRN709 INTEGER DEFAULT 0,\n" +
                "\tRN710 INTEGER DEFAULT 0,\n" +
                "\tRN711 INTEGER DEFAULT 0,\n" +
                "\tRN712 INTEGER DEFAULT 0,\n" +
                "\tRN713 INTEGER DEFAULT 0,\n" +
                "\tRN714 INTEGER DEFAULT 0,\n" +
                "\tRN715 INTEGER DEFAULT 0,\n" +
                "\tRN716 INTEGER DEFAULT 0,\n" +
                "\tRN717 INTEGER DEFAULT 0,\n" +
                "\tRN718 INTEGER DEFAULT 0,\n" +
                "\tRN719 INTEGER DEFAULT 0,\n" +
                "\tRN720 INTEGER DEFAULT 0,\n" +
                "\tRN721 INTEGER DEFAULT 0,\n" +
                "\tRN722 INTEGER DEFAULT 0,\n" +
                "\tRN723 INTEGER DEFAULT 0,\n" +
                "\tRN724 INTEGER DEFAULT 0,\n" +
                "\tRN725 INTEGER DEFAULT 0,\n" +
                "\tRN726 INTEGER DEFAULT 0,\n" +
                "\tRN727 INTEGER DEFAULT 0,\n" +
                "\tRN728 INTEGER DEFAULT 0,\n" +
                "\tRN729 INTEGER DEFAULT 0,\n" +
                "\tRN730 INTEGER DEFAULT 0,\n" +
                "\tRN731 INTEGER DEFAULT 0,\n" +
                "\tRN732 INTEGER DEFAULT 0,\n" +
                "\tRN733 INTEGER DEFAULT 0,\n" +
                "\tRN734 INTEGER DEFAULT 0,\n" +
                "\tRN736 INTEGER DEFAULT 0,\n" +
                "\tRN737 INTEGER DEFAULT 0,\n" +
                "\tRN738 INTEGER DEFAULT 0,\n" +
                "\tRN739 INTEGER DEFAULT 0,\n" +
                "\tRN740 INTEGER DEFAULT 0,\n" +
                "\tRN741 INTEGER DEFAULT 0,\n" +
                "\tRN743 INTEGER DEFAULT 0,\n" +
                "\tRN744 INTEGER DEFAULT 0,\n" +
                "\tRN745 INTEGER DEFAULT 0,\n" +
                "\tRN746 INTEGER DEFAULT 0,\n" +
                "\tRN747 INTEGER DEFAULT 0,\n" +
                "\tRN748 INTEGER DEFAULT 0,\n" +
                "\tRN749 INTEGER DEFAULT 0,\n" +
                "\tRN750 INTEGER DEFAULT 0,\n" +
                "\tRN751 INTEGER DEFAULT 0,\n" +
                "\tRN752 INTEGER DEFAULT 0,\n" +
                "\tRN753 INTEGER DEFAULT 0,\n" +
                "\tRN754 INTEGER DEFAULT 0,\n" +
                "\tRN755 INTEGER DEFAULT 0,\n" +
                "\tRN756 INTEGER DEFAULT 0,\n" +
                "\tRN757 INTEGER DEFAULT 0,\n" +
                "\tRN758 INTEGER DEFAULT 0,\n" +
                "\tRN759 INTEGER DEFAULT 0,\n" +
                "\tRN760 INTEGER DEFAULT 0,\n" +
                "\tRN761 INTEGER DEFAULT 0,\n" +
                "\tRN762 INTEGER DEFAULT 0,\n" +
                "\tRN763 INTEGER DEFAULT 0,\n" +
                "\tRN764 INTEGER DEFAULT 0,\n" +
                "\tRN765 INTEGER DEFAULT 0,\n" +
                "\tRN766 INTEGER DEFAULT 0,\n" +
                "\tRN767 INTEGER DEFAULT 0,\n" +
                "\tRN768 INTEGER DEFAULT 0,\n" +
                "\tRN769 INTEGER DEFAULT 0,\n" +
                "\tRN770 INTEGER DEFAULT 0,\n" +
                "\tRN771 INTEGER DEFAULT 0,\n" +
                "\tRN772 INTEGER DEFAULT 0,\n" +
                "\tRN773 INTEGER DEFAULT 0,\n" +
                "\tRN774 INTEGER DEFAULT 0,\n" +
                "\tRN775 INTEGER DEFAULT 0,\n" +
                "\tRN776 INTEGER DEFAULT 0,\n" +
                "\tRN777 INTEGER DEFAULT 0,\n" +
                "\tRN778 INTEGER DEFAULT 0,\n" +
                "\tRN779 INTEGER DEFAULT 0,\n" +
                "\tRN780 INTEGER DEFAULT 0,\n" +
                "\tRN781 INTEGER DEFAULT 0,\n" +
                "\tRN782 INTEGER DEFAULT 0,\n" +
                "\tRN783 INTEGER DEFAULT 0,\n" +
                "\tRN784 INTEGER DEFAULT 0,\n" +
                "\tRN785 INTEGER DEFAULT 0,\n" +
                "\tRN786 INTEGER DEFAULT 0,\n" +
                "\tRN787 INTEGER DEFAULT 0,\n" +
                "\tRN788 INTEGER DEFAULT 0,\n" +
                "\tRN789 INTEGER DEFAULT 0,\n" +
                "\tRN790 INTEGER DEFAULT 0,\n" +
                "\tRN791 INTEGER DEFAULT 0,\n" +
                "\tRN792 INTEGER DEFAULT 0,\n" +
                "\tRN794 INTEGER DEFAULT 0,\n" +
                "\tRN795 INTEGER DEFAULT 0,\n" +
                "\tRN796 INTEGER DEFAULT 0,\n" +
                "\tRN797 INTEGER DEFAULT 0,\n" +
                "\tRN798 INTEGER DEFAULT 0,\n" +
                "\tRN799 INTEGER DEFAULT 0,\n" +
                "\tRN800 INTEGER DEFAULT 0,\n" +
                "\tRN801 INTEGER DEFAULT 0,\n" +
                "\tRN802 INTEGER DEFAULT 0,\n" +
                "\tRN803 INTEGER DEFAULT 0,\n" +
                "\tRN804 INTEGER DEFAULT 0,\n" +
                "\tRN805 INTEGER DEFAULT 0,\n" +
                "\tRN806 INTEGER DEFAULT 0,\n" +
                "\tRN807 INTEGER DEFAULT 0,\n" +
                "\tRN808 INTEGER DEFAULT 0,\n" +
                "\tRN809 INTEGER DEFAULT 0,\n" +
                "\tRN810 INTEGER DEFAULT 0,\n" +
                "\tRN811 INTEGER DEFAULT 0,\n" +
                "\tRN812 INTEGER DEFAULT 0,\n" +
                "\tRN815 INTEGER DEFAULT 0,\n" +
                "\tRN816 INTEGER DEFAULT 0,\n" +
                "\tRN817 INTEGER DEFAULT 0,\n" +
                "\tRN818 INTEGER DEFAULT 0,\n" +
                "\tRN819 INTEGER DEFAULT 0,\n" +
                "\tRN820 INTEGER DEFAULT 0,\n" +
                "\tRN821 INTEGER DEFAULT 0,\n" +
                "\tRN822 INTEGER DEFAULT 0,\n" +
                "\tRN823 INTEGER DEFAULT 0,\n" +
                "\tRN824 INTEGER DEFAULT 0,\n" +
                "\tRN825 INTEGER DEFAULT 0,\n" +
                "\tRN826 INTEGER DEFAULT 0,\n" +
                "\tRN827 INTEGER DEFAULT 0,\n" +
                "\tRN828 INTEGER DEFAULT 0,\n" +
                "\tRN829 INTEGER DEFAULT 0,\n" +
                "\tRN830 INTEGER DEFAULT 0,\n" +
                "\tRN831 INTEGER DEFAULT 0,\n" +
                "\tRN832 INTEGER DEFAULT 0,\n" +
                "\tRN833 INTEGER DEFAULT 0,\n" +
                "\tRN834 INTEGER DEFAULT 0,\n" +
                "\tRN835 INTEGER DEFAULT 0,\n" +
                "\tRN836 INTEGER DEFAULT 0,\n" +
                "\tRN837 INTEGER DEFAULT 0,\n" +
                "\tRN838 INTEGER DEFAULT 0,\n" +
                "\tRN839 INTEGER DEFAULT 0,\n" +
                "\tRN840 INTEGER DEFAULT 0,\n" +
                "\tRN841 INTEGER DEFAULT 0,\n" +
                "\tRN842 INTEGER DEFAULT 0,\n" +
                "\tRN843 INTEGER DEFAULT 0,\n" +
                "\tRN844 INTEGER DEFAULT 0,\n" +
                "\tRN845 INTEGER DEFAULT 0,\n" +
                "\tRN846 INTEGER DEFAULT 0,\n" +
                "\tRN847 INTEGER DEFAULT 0,\n" +
                "\tRN848 INTEGER DEFAULT 0,\n" +
                "\tRN849 INTEGER DEFAULT 0,\n" +
                "\tRN850 INTEGER DEFAULT 0,\n" +
                "\tRN851 INTEGER DEFAULT 0,\n" +
                "\tRN852 INTEGER DEFAULT 0,\n" +
                "\tRN853 INTEGER DEFAULT 0,\n" +
                "\tRN854 INTEGER DEFAULT 0,\n" +
                "\tRN855 INTEGER DEFAULT 0,\n" +
                "\tRN856 INTEGER DEFAULT 0,\n" +
                "\tRN857 INTEGER DEFAULT 0,\n" +
                "\tRN858 INTEGER DEFAULT 0,\n" +
                "\tRN859 INTEGER DEFAULT 0,\n" +
                "\tRN860 INTEGER DEFAULT 0,\n" +
                "\tRN861 INTEGER DEFAULT 0,\n" +
                "\tRN862 INTEGER DEFAULT 0,\n" +
                "\tRN863 INTEGER DEFAULT 0,\n" +
                "\tRN864 INTEGER DEFAULT 0,\n" +
                "\tRN865 INTEGER DEFAULT 0,\n" +
                "\tRN866 INTEGER DEFAULT 0,\n" +
                "\tRN867 INTEGER DEFAULT 0,\n" +
                "\tRN868 INTEGER DEFAULT 0,\n" +
                "\tRN869 INTEGER DEFAULT 0,\n" +
                "\tRN870 INTEGER DEFAULT 0,\n" +
                "\tRN871 INTEGER DEFAULT 0,\n" +
                "\tRN872 INTEGER DEFAULT 0,\n" +
                "\tRN873 INTEGER DEFAULT 0,\n" +
                "\tRN874 INTEGER DEFAULT 0,\n" +
                "\tRN875 INTEGER DEFAULT 0,\n" +
                "\tRN876 INTEGER DEFAULT 0,\n" +
                "\tRN877 INTEGER DEFAULT 0,\n" +
                "\tRN878 INTEGER DEFAULT 0,\n" +
                "\tRN879 INTEGER DEFAULT 0,\n" +
                "\tFOREIGN KEY (RemarkCode) REFERENCES RemarkCode (RemarkCode)\n" +
                "\tON DELETE CASCADE \n" +
                "\tON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (CarcRarcID) REFERENCES CarcOrRarc(ID)\n" +
                "\tON DELETE CASCADE \n" +
                "\tON UPDATE CASCADE\n" +
                ");";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method inserts test data into the database for this table.
     * @throws SQLException
     */
    public static void insertTestData() throws SQLException {
        String queryPreface = "INSERT INTO " + tableName + " (RemarkCode, CarcRarcID, Description)\n" +
                "VALUES\n";
        String query = queryPreface +
                "('1', 1, 'Deductible Amount'),\n" +
                "('2', 1, 'Coinsurance Amount'),\n" +
                "('3', 1, 'Co-payment Amount'),\n" +
                "('4', 1, 'The procedure code is inconsistent with the modifier used. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('5', 1, 'The procedure code/type of bill is inconsistent with the place of service. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('6', 1, 'The procedure/revenue code is inconsistent with the patient''s age. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('7', 1, 'The procedure/revenue code is inconsistent with the patient''s gender. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('8', 1, 'The procedure code is inconsistent with the provider type/specialty (taxonomy). Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('9', 1, 'The diagnosis is inconsistent with the patient''s age. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('10', 1, 'The diagnosis is inconsistent with the patient''s gender. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('11', 1, 'The diagnosis is inconsistent with the procedure. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('12', 1, 'The diagnosis is inconsistent with the provider type. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('13', 1, 'The date of death precedes the date of service.'),\n" +
                "('14', 1, 'The date of birth follows the date of service.'),\n" +
                "('16', 1, 'Claim/service lacks information or has submission/billing error(s). Usage: Do not use this code for claims attachment(s)/other documentation. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.) Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('18', 1, 'Exact duplicate claim/service (Use only with Group Code OA except where state workers'' compensation regulations requires CO)'),\n" +
                "('19', 1, 'This is a work-related injury/illness and thus the liability of the Worker''s Compensation Carrier.'),\n" +
                "('20', 1, 'This injury/illness is covered by the liability carrier.'),\n" +
                "('21', 1, 'This injury/illness is the liability of the no-fault carrier.'),\n" +
                "('22', 1, 'This care may be covered by another payer per coordination of benefits.'),\n" +
                "('23', 1, 'The impact of prior payer(s) adjudication including payments and/or adjustments. (Use only with Group Code OA)'),\n" +
                "('24', 1, 'Charges are covered under a capitation agreement/managed care plan.'),\n" +
                "('26', 1, 'Expenses incurred prior to coverage.'),\n" +
                "('27', 1, 'Expenses incurred after coverage terminated.'),\n" +
                "('29', 1, 'The time limit for filing has expired.'),\n" +
                "('31', 1, 'Patient cannot be identified as our insured.'),\n" +
                "('32', 1, 'Our records indicate the patient is not an eligible dependent.'),\n" +
                "('33', 1, 'Insured has no dependent coverage.'),\n" +
                "('34', 1, 'Insured has no coverage for newborns.'),\n" +
                "('35', 1, 'Lifetime benefit maximum has been reached.'),\n" +
                "('39', 1, 'Services denied at the time authorization/pre-certification was requested.'),\n" +
                "('40', 1, 'Charges do not meet qualifications for emergent/urgent care. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('44', 1, 'Prompt-pay discount.'),\n" +
                "('45', 1, 'Charge exceeds fee schedule/maximum allowable or contracted/legislated fee arrangement. Usage: This adjustment amount cannot equal the total service or claim charge amount; and must not duplicate provider adjustment amounts (payments and contractual reductions) that have resulted from prior payer(s) adjudication. (Use only with Group Codes PR or CO depending upon liability)'),\n" +
                "('49', 1, 'This is a non-covered service because it is a routine/preventive exam or a diagnostic/screening procedure done in conjunction with a routine/preventive exam. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('50', 1, 'These are non-covered services because this is not deemed a ''medical necessity'' by the payer. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('51', 1, 'These are non-covered services because this is a pre-existing condition. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('53', 1, 'Services by an immediate relative or a member of the same household are not covered.'),\n" +
                "('54', 1, 'Multiple physicians/assistants are not covered in this case. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('55', 1, 'Procedure/treatment/drug is deemed experimental/investigational by the payer. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('56', 1, 'Procedure/treatment has not been deemed ''proven to be effective'' by the payer. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('58', 1, 'Treatment was deemed by the payer to have been rendered in an inappropriate or invalid place of service. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('59', 1, 'Processed based on multiple or concurrent procedure rules. (For example multiple surgery or diagnostic imaging, concurrent anesthesia.) Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('60', 1, 'Charges for outpatient services are not covered when performed within a period of time prior to or after inpatient services.'),\n" +
                "('61', 1, 'Adjusted for failure to obtain second surgical opinion'),\n" +
                "('66', 1, 'Blood Deductible.'),\n" +
                "('69', 1, 'Day outlier amount.'),\n" +
                "('70', 1, 'Cost outlier - Adjustment to compensate for additional costs.'),\n" +
                "('74', 1, 'Indirect Medical Education Adjustment.'),\n" +
                "('75', 1, 'Direct Medical Education Adjustment.'),\n" +
                "('76', 1, 'Disproportionate Share Adjustment.'),\n" +
                "('78', 1, 'Non-Covered days/Room charge adjustment.'),\n" +
                "('85', 1, 'Patient Interest Adjustment (Use Only Group code PR)'),\n" +
                "('89', 1, 'Professional fees removed from charges.'),\n" +
                "('90', 1, 'Ingredient cost adjustment. Usage: To be used for pharmaceuticals only.'),\n" +
                "('91', 1, 'Dispensing fee adjustment.'),\n" +
                "('94', 1, 'Processed in Excess of charges.'),\n" +
                "('95', 1, 'Plan procedures not followed.'),\n" +
                "('96', 1, 'Non-covered charge(s). At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.) Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('97', 1, 'The benefit for this service is included in the payment/allowance for another service/procedure that has already been adjudicated. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('100', 1, 'Payment made to patient/insured/responsible party.'),\n" +
                "('101', 1, 'Predetermination: anticipated payment upon completion of services or claim adjudication.'),\n" +
                "('102', 1, 'Major Medical Adjustment.'),\n" +
                "('103', 1, 'Provider promotional discount (e.g., Senior citizen discount).'),\n" +
                "('104', 1, 'Managed care withholding.'),\n" +
                "('105', 1, 'Tax withholding.'),\n" +
                "('106', 1, 'Patient payment option/election not in effect.'),\n" +
                "('107', 1, 'The related or qualifying claim/service was not identified on this claim. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('108', 1, 'Rent/purchase guidelines were not met. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('109', 1, 'Claim/service not covered by this payer/contractor. You must send the claim/service to the correct payer/contractor.'),\n" +
                "('110', 1, 'Billing date predates service date.'),\n" +
                "('111', 1, 'Not covered unless the provider accepts assignment.'),\n" +
                "('112', 1, 'Service not furnished directly to the patient and/or not documented.'),\n" +
                "('114', 1, 'Procedure/product not approved by the Food and Drug Administration.'),\n" +
                "('115', 1, 'Procedure postponed, canceled, or delayed.'),\n" +
                "('116', 1, 'The advance indemnification notice signed by the patient did not comply with requirements.'),\n" +
                "('117', 1, 'Transportation is only covered to the closest facility that can provide the necessary care.'),\n" +
                "('118', 1, 'ESRD network support adjustment.'),\n" +
                "('119', 1, 'Benefit maximum for this time period or occurrence has been reached.'),\n" +
                "('121', 1, 'Indemnification adjustment - compensation for outstanding member responsibility.'),\n" +
                "('122', 1, 'Psychiatric reduction.'),\n" +
                "('128', 1, 'Newborn''s services are covered in the mother''s Allowance.'),\n" +
                "('129', 1, 'Prior processing information appears incorrect. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)'),\n" +
                "('130', 1, 'Claim submission fee.'),\n" +
                "('131', 1, 'Claim specific negotiated discount.'),\n" +
                "('132', 1, 'Prearranged demonstration project adjustment.'),\n" +
                "('133', 1, 'The disposition of this service line is pending further review. (Use only with Group Code OA). Usage: Use of this code requires a reversal and correction when the service line is finalized (use only in Loop 2110 CAS segment of the 835 or Loop 2430 of the 837).'),\n" +
                "('134', 1, 'Technical fees removed from charges.'),\n" +
                "('135', 1, 'Interim bills cannot be processed.'),\n" +
                "('136', 1, 'Failure to follow prior payer''s coverage rules. (Use only with Group Code OA)'),\n" +
                "('137', 1, 'Regulatory Surcharges, Assessments, Allowances or Health Related Taxes.'),\n" +
                "('139', 1, 'Contracted funding agreement - Subscriber is employed by the provider of services. Use only with Group Code CO.'),\n" +
                "('140', 1, 'Patient/Insured health identification number and name do not match.'),\n" +
                "('142', 1, 'Monthly Medicaid patient liability amount.'),\n" +
                "('143', 1, 'Portion of payment deferred.'),\n" +
                "('144', 1, 'Incentive adjustment, e.g. preferred product/service.'),\n" +
                "('146', 1, 'Diagnosis was invalid for the date(s) of service reported.'),\n" +
                "('147', 1, 'Provider contracted/negotiated rate expired or not on file.'),\n" +
                "('148', 1, 'Information from another provider was not provided or was insufficient/incomplete. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)'),\n" +
                "('149', 1, 'Lifetime benefit maximum has been reached for this service/benefit category.'),\n" +
                "('150', 1, 'Payer deems the information submitted does not support this level of service.'),\n" +
                "('151', 1, 'Payment adjusted because the payer deems the information submitted does not support this many/frequency of services.'),\n" +
                "('152', 1, 'Payer deems the information submitted does not support this length of service. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('153', 1, 'Payer deems the information submitted does not support this dosage.'),\n" +
                "('154', 1, 'Payer deems the information submitted does not support this day''s supply.'),\n" +
                "('155', 1, 'Patient refused the service/procedure.'),\n" +
                "('157', 1, 'Service/procedure was provided as a result of an act of war.'),\n" +
                "('158', 1, 'Service/procedure was provided outside of the United States.'),\n" +
                "('159', 1, 'Service/procedure was provided as a result of terrorism.'),\n" +
                "('160', 1, 'Injury/illness was the result of an activity that is a benefit exclusion.'),\n" +
                "('161', 1, 'Provider performance bonus'),\n" +
                "('163', 1, 'Attachment/other documentation referenced on the claim was not received.'),\n" +
                "('164', 1, 'Attachment/other documentation referenced on the claim was not received in a timely fashion.'),\n" +
                "('166', 1, 'These services were submitted after this payers responsibility for processing claims under this plan ended.'),\n" +
                "('167', 1, 'This (these) diagnosis(es) is (are) not covered. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('169', 1, 'Alternate benefit has been provided.'),\n" +
                "('170', 1, 'Payment is denied when performed/billed by this type of provider. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('171', 1, 'Payment is denied when performed/billed by this type of provider in this type of facility. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('172', 1, 'Payment is adjusted when performed/billed by a provider of this specialty. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('173', 1, 'Service/equipment was not prescribed by a physician.'),\n" +
                "('174', 1, 'Service was not prescribed prior to delivery.'),\n" +
                "('175', 1, 'Prescription is incomplete.'),\n" +
                "('176', 1, 'Prescription is not current.'),\n" +
                "('177', 1, 'Patient has not met the required eligibility requirements.'),\n" +
                "('178', 1, 'Patient has not met the required spend down requirements.'),\n" +
                "('179', 1, 'Patient has not met the required waiting requirements. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('180', 1, 'Patient has not met the required residency requirements.'),\n" +
                "('181', 1, 'Procedure code was invalid on the date of service.'),\n" +
                "('182', 1, 'Procedure modifier was invalid on the date of service.'),\n" +
                "('183', 1, 'The referring provider is not eligible to refer the service billed. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('184', 1, 'The prescribing/ordering provider is not eligible to prescribe/order the service billed. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('185', 1, 'The rendering provider is not eligible to perform the service billed. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('186', 1, 'Level of care change adjustment.'),\n" +
                "('187', 1, 'Consumer Spending Account payments (includes but is not limited to Flexible Spending Account, Health Savings Account, Health Reimbursement Account, etc.)'),\n" +
                "('188', 1, 'This product/procedure is only covered when used according to FDA recommendations.'),\n" +
                "('189', 1, '''Not otherwise classified'' or ''unlisted'' procedure code (CPT/HCPCS) was billed when there is a specific procedure code for this procedure/service'),\n" +
                "('190', 1, 'Payment is included in the allowance for a Skilled Nursing Facility (SNF) qualified stay.'),\n" +
                "('192', 1, 'Non standard adjustment code from paper remittance. Usage: This code is to be used by providers/payers providing Coordination of Benefits information to another payer in the 837 transaction only. This code is only used when the non-standard code cannot be reasonably mapped to an existing Claims Adjustment Reason Code, specifically Deductible, Coinsurance and Co-payment.'),\n" +
                "('193', 1, 'Original payment decision is being maintained. Upon review, it was determined that this claim was processed properly.'),\n" +
                "('194', 1, 'Anesthesia performed by the operating physician, the assistant surgeon or the attending physician.'),\n" +
                "('195', 1, 'Refund issued to an erroneous priority payer for this claim/service.'),\n" +
                "('197', 1, 'Precertification/authorization/notification/pre-treatment absent.'),\n" +
                "('198', 1, 'Precertification/notification/authorization/pre-treatment exceeded.'),\n" +
                "('199', 1, 'Revenue code and Procedure code do not match.'),\n" +
                "('200', 1, 'Expenses incurred during lapse in coverage'),\n" +
                "('201', 1, 'Patient is responsible for amount of this claim/service through ''set aside arrangement'' or other agreement. (Use only with Group Code PR) At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)'),\n" +
                "('202', 1, 'Non-covered personal comfort or convenience services.'),\n" +
                "('203', 1, 'Discontinued or reduced service.'),\n" +
                "('204', 1, 'This service/equipment/drug is not covered under the patient''s current benefit plan'),\n" +
                "('205', 1, 'Pharmacy discount card processing fee'),\n" +
                "('206', 1, 'National Provider Identifier - missing.'),\n" +
                "('207', 1, 'National Provider identifier - Invalid format'),\n" +
                "('208', 1, 'National Provider Identifier - Not matched.'),\n" +
                "('209', 1, 'Per regulatory or other agreement. The provider cannot collect this amount from the patient. However, this amount may be billed to subsequent payer. Refund to patient if collected. (Use only with Group code OA)'),\n" +
                "('210', 1, 'Payment adjusted because pre-certification/authorization not received in a timely fashion'),\n" +
                "('211', 1, 'National Drug Codes (NDC) not eligible for rebate, are not covered.'),\n" +
                "('212', 1, 'Administrative surcharges are not covered'),\n" +
                "('213', 1, 'Non-compliance with the physician self referral prohibition legislation or payer policy.'),\n" +
                "('215', 1, 'Based on subrogation of a third party settlement'),\n" +
                "('216', 1, 'Based on the findings of a review organization'),\n" +
                "('219', 1, 'Based on extent of injury. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier ''IG'') for the jurisdictional regulation. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF).'),\n" +
                "('222', 1, 'Exceeds the contracted maximum number of hours/days/units by this provider for this period. This is not patient specific. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('223', 1, 'Adjustment code for mandated federal, state or local law/regulation that is not already covered by another code and is mandated before a new code can be created.'),\n" +
                "('224', 1, 'Patient identification compromised by identity theft. Identity verification required for processing this and future claims.'),\n" +
                "('225', 1, 'Penalty or Interest Payment by Payer (Only used for plan to plan encounter reporting within the 837)'),\n" +
                "('226', 1, 'Information requested from the Billing/Rendering Provider was not provided or not provided timely or was insufficient/incomplete. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)'),\n" +
                "('227', 1, 'Information requested from the patient/insured/responsible party was not provided or was insufficient/incomplete. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)'),\n" +
                "('228', 1, 'Denied for failure of this provider, another provider or the subscriber to supply requested information to a previous payer for their adjudication'),\n" +
                "('229', 1, 'Partial charge amount not considered by Medicare due to the initial claim Type of Bill being 12X. Usage: This code can only be used in the 837 transaction to convey Coordination of Benefits information when the secondary payer''s cost avoidance policy allows providers to bypass claim submission to a prior payer. (Use only with Group Code PR)'),\n" +
                "('231', 1, 'Mutually exclusive procedures cannot be done in the same day/setting. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('232', 1, 'Institutional Transfer Amount. Usage: Applies to institutional claims only and explains the DRG amount difference when the patient care crosses multiple institutions.'),\n" +
                "('233', 1, 'Services/charges related to the treatment of a hospital-acquired condition or preventable medical error.'),\n" +
                "('234', 1, 'This procedure is not paid separately. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)'),\n" +
                "('235', 1, 'Sales Tax'),\n" +
                "('236', 1, 'This procedure or procedure/modifier combination is not compatible with another procedure or procedure/modifier combination provided on the same day according to the National Correct Coding Initiative or workers compensation state regulations/ fee schedule requirements.'),\n" +
                "('237', 1, 'Legislated/Regulatory Penalty. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)'),\n" +
                "('238', 1, 'Claim spans eligible and ineligible periods of coverage, this is the reduction for the ineligible period. (Use only with Group Code PR)'),\n" +
                "('239', 1, 'Claim spans eligible and ineligible periods of coverage. Rebill separate claims.'),\n" +
                "('240', 1, 'The diagnosis is inconsistent with the patient''s birth weight. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('241', 1, 'Low Income Subsidy (LIS) Co-payment Amount'),\n" +
                "('242', 1, 'Services not provided by network/primary care providers.'),\n" +
                "('243', 1, 'Services not authorized by network/primary care providers.'),\n" +
                "('245', 1, 'Provider performance program withhold.'),\n" +
                "('246', 1, 'This non-payable code is for required reporting only.'),\n" +
                "('247', 1, 'Deductible for Professional service rendered in an Institutional setting and billed on an Institutional claim.'),\n" +
                "('248', 1, 'Coinsurance for Professional service rendered in an Institutional setting and billed on an Institutional claim.'),\n" +
                "('249', 1, 'This claim has been identified as a readmission. (Use only with Group Code CO)'),\n" +
                "('250', 1, 'The attachment/other documentation that was received was the incorrect attachment/document. The expected attachment/document is still missing. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT).'),\n" +
                "('251', 1, 'The attachment/other documentation that was received was incomplete or deficient. The necessary information is still needed to process the claim. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT).'),\n" +
                "('252', 1, 'An attachment/other documentation is required to adjudicate this claim/service. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT).'),\n" +
                "('253', 1, 'Sequestration - reduction in federal payment'),\n" +
                "('254', 1, 'Claim received by the dental plan, but benefits not available under this plan. Submit these services to the patient''s medical plan for further consideration.'),\n" +
                "('256', 1, 'Service not payable per managed care contract.'),\n" +
                "('257', 1, 'The disposition of the claim/service is undetermined during the premium payment grace period, per Health Insurance Exchange requirements. This claim/service will be reversed and corrected when the grace period ends (due to premium payment or lack of premium payment). (Use only with Group Code OA)'),\n" +
                "('258', 1, 'Claim/service not covered when patient is in custody/incarcerated. Applicable federal, state or local authority may cover the claim/service.'),\n" +
                "('259', 1, 'Additional payment for Dental/Vision service utilization.'),\n" +
                "('260', 1, 'Processed under Medicaid ACA Enhanced Fee Schedule'),\n" +
                "('261', 1, 'The procedure or service is inconsistent with the patient''s history.'),\n" +
                "('262', 1, 'Adjustment for delivery cost. Usage: To be used for pharmaceuticals only.'),\n" +
                "('263', 1, 'Adjustment for shipping cost. Usage: To be used for pharmaceuticals only.'),\n" +
                "('264', 1, 'Adjustment for postage cost. Usage: To be used for pharmaceuticals only.'),\n" +
                "('265', 1, 'Adjustment for administrative cost. Usage: To be used for pharmaceuticals only.'),\n" +
                "('266', 1, 'Adjustment for compound preparation cost. Usage: To be used for pharmaceuticals only.'),\n" +
                "('267', 1, 'Claim/service spans multiple months. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)'),\n" +
                "('268', 1, 'The Claim spans two calendar years. Please resubmit one claim per calendar year.'),\n" +
                "('269', 1, 'Anesthesia not covered for this service/procedure. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('270', 1, 'Claim received by the medical plan, but benefits not available under this plan. Submit these services to the patient''s dental plan for further consideration.'),\n" +
                "('271', 1, 'Prior contractual reductions related to a current periodic payment as part of a contractual payment schedule when deferred amounts have been previously reported. (Use only with Group Code OA)'),\n" +
                "('272', 1, 'Coverage/program guidelines were not met.'),\n" +
                "('273', 1, 'Coverage/program guidelines were exceeded.'),\n" +
                "('274', 1, 'Fee/Service not payable per patient Care Coordination arrangement.'),\n" +
                "('275', 1, 'Prior payer''s (or payers'') patient responsibility (deductible, coinsurance, co-payment) not covered. (Use only with Group Code PR)'),\n" +
                "('276', 1, 'Services denied by the prior payer(s) are not covered by this payer.'),\n" +
                "('277', 1, 'The disposition of the claim/service is undetermined during the premium payment grace period, per Health Insurance SHOP Exchange requirements. This claim/service will be reversed and corrected when the grace period ends (due to premium payment or lack of premium payment). (Use only with Group Code OA)'),\n" +
                "('278', 1, 'Performance program proficiency requirements not met. (Use only with Group Codes CO or PI) Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('279', 1, 'Services not provided by Preferred network providers. Usage: Use this code when there are member network limitations. For example, using contracted providers not in the member''s ''narrow'' network.'),\n" +
                "('280', 1, 'Claim received by the medical plan, but benefits not available under this plan. Submit these services to the patient''s Pharmacy plan for further consideration.'),\n" +
                "('281', 1, 'Deductible waived per contractual agreement. Use only with Group Code CO.'),\n" +
                "('282', 1, 'The procedure/revenue code is inconsistent with the type of bill. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('283', 1, 'Attending provider is not eligible to provide direction of care.'),\n" +
                "('284', 1, 'Precertification/authorization/notification/pre-treatment number may be valid but does not apply to the billed services.'),\n" +
                "('285', 1, 'Appeal procedures not followed'),\n" +
                "('286', 1, 'Appeal time limits not met'),\n" +
                "('287', 1, 'Referral exceeded'),\n" +
                "('288', 1, 'Referral absent'),\n" +
                "('289', 1, 'Services considered under the dental and medical plans, benefits not available.'),\n" +
                "('290', 1, 'Claim received by the dental plan, but benefits not available under this plan. Claim has been forwarded to the patient''s medical plan for further consideration.'),\n" +
                "('291', 1, 'Claim received by the medical plan, but benefits not available under this plan. Claim has been forwarded to the patient''s dental plan for further consideration.'),\n" +
                "('292', 1, 'Claim received by the medical plan, but benefits not available under this plan. Claim has been forwarded to the patient''s pharmacy plan for further consideration.'),\n" +
                "('293', 1, 'Payment made to employer.'),\n" +
                "('294', 1, 'Payment made to attorney.'),\n" +
                "('295', 1, 'Pharmacy Direct/Indirect Remuneration (DIR)'),\n" +
                "('296', 1, 'Precertification/authorization/notification/pre-treatment number may be valid but does not apply to the provider.'),\n" +
                "('297', 1, 'Claim received by the medical plan, but benefits not available under this plan. Submit these services to the patient''s vision plan for further consideration.'),\n" +
                "('298', 1, 'Claim received by the medical plan, but benefits not available under this plan. Claim has been forwarded to the patient''s vision plan for further consideration.'),\n" +
                "('299', 1, 'The billing provider is not eligible to receive payment for the service billed.'),\n" +
                "('300', 1, 'Claim received by the Medical Plan, but benefits not available under this plan. Claim has been forwarded to the patient''s Behavioral Health Plan for further consideration.'),\n" +
                "('301', 1, 'Claim received by the Medical Plan, but benefits not available under this plan. Submit these services to the patient''s Behavioral Health Plan for further consideration.'),\n" +
                "('302', 1, 'Precertification/notification/authorization/pre-treatment time limit has expired.'),\n" +
                "('303', 1, 'Prior payer''s (or payers'') patient responsibility (deductible, coinsurance, co-payment) not covered for Qualified Medicare and Medicaid Beneficiaries. (Use only with Group Code CO)'),\n" +
                "('304', 1, 'Claim received by the medical plan, but benefits not available under this plan. Submit these services to the patient''s hearing plan for further consideration.'),\n" +
                "('305', 1, 'Claim received by the medical plan, but benefits not available under this plan. Claim has been forwarded to the patient''s hearing plan for further consideration.'),\n" +
                "('A0', 1, 'Patient refund amount.'),\n" +
                "('A1', 1, 'Claim/Service denied. At least one Remark Code must be provided (may be comprised of either the NCPDP Reject Reason Code, or Remittance Advice Remark Code that is not an ALERT.)'),\n" +
                "('A5', 1, 'Medicare Claim PPS Capital Cost Outlier Amount.'),\n" +
                "('A6', 1, 'Prior hospitalization or 30 day transfer requirement not met.'),\n" +
                "('A8', 1, 'Ungroupable DRG.'),\n" +
                "('B1', 1, 'Non-covered visits.'),\n" +
                "('B4', 1, 'Late filing penalty.'),\n" +
                "('B7', 1, 'This provider was not certified/eligible to be paid for this procedure/service on this date of service. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('B8', 1, 'Alternative services were available, and should have been utilized. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('B9', 1, 'Patient is enrolled in a Hospice.'),\n" +
                "('B10', 1, 'Allowed amount has been reduced because a component of the basic procedure/test was paid. The beneficiary is not liable for more than the charge limit for the basic procedure/test.'),\n" +
                "('B11', 1, 'The claim/service has been transferred to the proper payer/processor for processing. Claim/service not covered by this payer/processor.'),\n" +
                "('B12', 1, 'Services not documented in patient''s medical records.'),\n" +
                "('B13', 1, 'Previously paid. Payment for this claim/service may have been provided in a previous payment.'),\n" +
                "('B14', 1, 'Only one visit or consultation per physician per day is covered.'),\n" +
                "('B15', 1, 'This service/procedure requires that a qualifying service/procedure be received and covered. The qualifying other service/procedure has not been received/adjudicated. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present.'),\n" +
                "('B16', 1, '''New Patient'' qualifications were not met.'),\n" +
                "('B20', 1, 'Procedure/service was partially or fully furnished by another provider.'),\n" +
                "('B22', 1, 'This payment is adjusted based on the diagnosis.'),\n" +
                "('B23', 1, 'Procedure billed is not authorized per your Clinical Laboratory Improvement Amendment (CLIA) proficiency test.'),\n" +
                "('P1', 1, 'State-mandated Requirement for Property and Casualty, see Claim Payment Remarks Code for specific explanation. To be used for Property and Casualty only.'),\n" +
                "('P2', 1, 'Not a work related injury/illness and thus not the liability of the workers'' compensation carrier Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier ''IG'') for the jurisdictional regulation. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF). To be used for Workers'' Compensation only.'),\n" +
                "('P3', 1, 'Workers'' Compensation case settled. Patient is responsible for amount of this claim/service through WC ''Medicare set aside arrangement'' or other agreement. To be used for Workers'' Compensation only. (Use only with Group Code PR)'),\n" +
                "('P4', 1, 'Workers'' Compensation claim adjudicated as non-compensable. This Payer not liable for claim or service/treatment. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier ''IG'') for the jurisdictional regulation. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF). To be used for Workers'' Compensation only'),\n" +
                "('P5', 1, 'Based on payer reasonable and customary fees. No maximum allowable defined by legislated fee arrangement. To be used for Property and Casualty only.'),\n" +
                "('P6', 1, 'Based on entitlement to benefits. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier ''IG'') for the jurisdictional regulation. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF). To be used for Property and Casualty only.'),\n" +
                "('P7', 1, 'The applicable fee schedule/fee database does not contain the billed code. Please resubmit a bill with the appropriate fee schedule/fee database code(s) that best describe the service(s) provided and supporting documentation if required. To be used for Property and Casualty only.'),\n" +
                "('P8', 1, 'Claim is under investigation. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier ''IG'') for the jurisdictional regulation. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF). To be used for Property and Casualty only.'),\n" +
                "('P9', 1, 'No available or correlating CPT/HCPCS code to describe this service. To be used for Property and Casualty only.'),\n" +
                "('P10', 1, 'Payment reduced to zero due to litigation. Additional information will be sent following the conclusion of litigation. To be used for Property and Casualty only.'),\n" +
                "('P11', 1, 'The disposition of the related Property & Casualty claim (injury or illness) is pending due to litigation. To be used for Property and Casualty only. (Use only with Group Code OA)'),\n" +
                "('P12', 1, 'Workers'' compensation jurisdictional fee schedule adjustment. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Class of Contract Code Identification Segment (Loop 2100 Other Claim Related Information REF). If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Workers'' Compensation only.'),\n" +
                "('P13', 1, 'Payment reduced or denied based on workers'' compensation jurisdictional regulations or payment policies, use only if no other code is applicable. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier ''IG'') if the jurisdictional regulation applies. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Workers'' Compensation only.'),\n" +
                "('P14', 1, 'The Benefit for this Service is included in the payment/allowance for another service/procedure that has been performed on the same day. Usage: Refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information REF), if present. To be used for Property and Casualty only.'),\n" +
                "('P15', 1, 'Workers'' Compensation Medical Treatment Guideline Adjustment. To be used for Workers'' Compensation only.'),\n" +
                "('P16', 1, 'Medical provider not authorized/certified to provide treatment to injured workers in this jurisdiction. To be used for Workers'' Compensation only. (Use with Group Code CO or OA)'),\n" +
                "('P17', 1, 'Referral not authorized by attending physician per regulatory requirement. To be used for Property and Casualty only.'),\n" +
                "('P18', 1, 'Procedure is not listed in the jurisdiction fee schedule. An allowance has been made for a comparable service. To be used for Property and Casualty only.'),\n" +
                "('P19', 1, 'Procedure has a relative value of zero in the jurisdiction fee schedule, therefore no payment is due. To be used for Property and Casualty only.'),\n" +
                "('P20', 1, 'Service not paid under jurisdiction allowed outpatient facility fee schedule. To be used for Property and Casualty only.'),\n" +
                "('P21', 1, 'Payment denied based on the Medical Payments Coverage (MPC) and/or Personal Injury Protection (PIP) Benefits jurisdictional regulations, or payment policies. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier ''IG'') if the jurisdictional regulation applies. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty Auto only.'),\n" +
                "('P22', 1, 'Payment adjusted based on the Medical Payments Coverage (MPC) and/or Personal Injury Protection (PIP) Benefits jurisdictional regulations, or payment policies. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier ''IG'') if the jurisdictional regulation applies. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty Auto only.'),\n" +
                "('P23', 1, 'Medical Payments Coverage (MPC) or Personal Injury Protection (PIP) Benefits jurisdictional fee schedule adjustment. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Class of Contract Code Identification Segment (Loop 2100 Other Claim Related Information REF). If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty Auto only.'),\n" +
                "('P24', 1, 'Payment adjusted based on Preferred Provider Organization (PPO). Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Class of Contract Code Identification Segment (Loop 2100 Other Claim Related Information REF). If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty only. Use only with Group Code CO.'),\n" +
                "('P25', 1, 'Payment adjusted based on Medical Provider Network (MPN). Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Class of Contract Code Identification Segment (Loop 2100 Other Claim Related Information REF). If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty only. (Use only with Group Code CO).'),\n" +
                "('P26', 1, 'Payment adjusted based on Voluntary Provider network (VPN). Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Class of Contract Code Identification Segment (Loop 2100 Other Claim Related Information REF). If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty only. (Use only with Group Code CO).'),\n" +
                "('P27', 1, 'Payment denied based on the Liability Coverage Benefits jurisdictional regulations and/or payment policies. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier ''IG'') if the jurisdictional regulation applies. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty Auto only.'),\n" +
                "('P28', 1, 'Payment adjusted based on the Liability Coverage Benefits jurisdictional regulations and/or payment policies. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Insurance Policy Number Segment (Loop 2100 Other Claim Related Information REF qualifier ''IG'') if the jurisdictional regulation applies. If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty Auto only.'),\n" +
                "('P29', 1, 'Liability Benefits jurisdictional fee schedule adjustment. Usage: If adjustment is at the Claim Level, the payer must send and the provider should refer to the 835 Class of Contract Code Identification Segment (Loop 2100 Other Claim Related Information REF). If adjustment is at the Line Level, the payer must send and the provider should refer to the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment information REF) if the regulations apply. To be used for Property and Casualty Auto only.'),\n" +
                "('P30', 1, 'Payment denied for exacerbation when supporting documentation was not complete. To be used for Property and Casualty only.'),\n" +
                "('P31', 1, 'Payment denied for exacerbation when treatment exceeds time allowed. To be used for Property and Casualty only.');";
        SQLGeneral.executeUpdate(query);
        query = queryPreface +
                "('M1', 2, 'X-ray not taken within the past 12 months or near enough to the start of treatment.'),\n" +
                "('M2', 2, 'Not paid separately when the patient is an inpatient.'),\n" +
                "('M3', 2, 'Equipment is the same or similar to equipment already being used.'),\n" +
                "('M4', 2, 'Alert: This is the last monthly installment payment for this durable medical equipment.'),\n" +
                "('M5', 2, 'Monthly rental payments can continue until the earlier of the 15th month from the first rental month, or the month when the equipment is no longer needed.'),\n" +
                "('M6', 2, 'Alert: You must furnish and service this item for any period of medical need for the remainder of the reasonable useful lifetime of the equipment.'),\n" +
                "('M7', 2, 'No rental payments after the item is purchased, returned or after the total of issued rental payments equals the purchase price.'),\n" +
                "('M8', 2, 'We do not accept blood gas tests results when the test was conducted by a medical supplier or taken while the patient is on oxygen.'),\n" +
                "('M9', 2, 'Alert: This is the tenth rental month. You must offer the patient the choice of changing the rental to a purchase agreement.'),\n" +
                "('M10', 2, 'Equipment purchases are limited to the first or the tenth month of medical necessity.'),\n" +
                "('M11', 2, 'DME, orthotics and prosthetics must be billed to the DME carrier who services the patient''s zip code.'),\n" +
                "('M12', 2, 'Diagnostic tests performed by a physician must indicate whether purchased services are included on the claim.'),\n" +
                "('M13', 2, 'Only one initial visit is covered per specialty per medical group.'),\n" +
                "('M14', 2, 'No separate payment for an injection administered during an office visit, and no payment for a full office visit if the patient only received an injection.'),\n" +
                "('M15', 2, 'Separately billed services/tests have been bundled as they are considered components of the same procedure. Separate payment is not allowed.'),\n" +
                "('M16', 2, 'Alert: Please see our web site, mailings, or bulletins for more details concerning this policy/procedure/decision.'),\n" +
                "('M17', 2, 'Alert: Payment approved as you did not know, and could not reasonably have been expected to know, that this would not normally have been covered for this patient. In the future, you will be liable for charges for the same service(s) under the same or similar conditions.'),\n" +
                "('M18', 2, 'Certain services may be approved for home use. Neither a hospital nor a Skilled Nursing Facility (SNF) is considered to be a patient''s home.'),\n" +
                "('M19', 2, 'Missing oxygen certification/re-certification.'),\n" +
                "('M20', 2, 'Missing/incomplete/invalid HCPCS.'),\n" +
                "('M21', 2, 'Missing/incomplete/invalid place of residence for this service/item provided in a home.'),\n" +
                "('M22', 2, 'Missing/incomplete/invalid number of miles traveled.'),\n" +
                "('M23', 2, 'Missing invoice.'),\n" +
                "('M24', 2, 'Missing/incomplete/invalid number of doses per vial.'),\n" +
                "('M25', 2, 'The information furnished does not substantiate the need for this level of service. If you believe the service should have been fully covered as billed, or if you did not know and could not reasonably have been expected to know that we would not pay for this level of service, or if you notified the patient in writing in advance that we would not pay for this level of service and he/she agreed in writing to pay, ask us to review your claim within 120 days of the date of this notice. If you do not request an appeal, we will, upon application from the patient, reimburse him/her for the amount you have collected from him/her in excess of any deductible and coinsurance amounts. We will recover the reimbursement from you as an overpayment.'),\n" +
                "('M26', 2, 'The information furnished does not substantiate the need for this level of service. If you have collected any amount from the patient for this level of service/any amount that exceeds the limiting charge for the less extensive service, the law requires you to refund that amount to the patient within 30 days of receiving this notice.'),\n" +
                "('M27', 2, 'Alert: The patient has been relieved of liability of payment of these items and services under the limitation of liability provision of the law. The provider is ultimately liable for the patient''s waived charges, including any charges for coinsurance, since the items or services were not reasonable and necessary or constituted custodial care, and you knew or could reasonably have been expected to know, that they were not covered. You may appeal this determination. You may ask for an appeal regarding both the coverage determination and the issue of whether you exercised due care. The appeal request must be filed within 120 days of the date you receive this notice. You must make the request through this office.'),\n" +
                "('M28', 2, 'This does not qualify for payment under Part B when Part A coverage is exhausted or not otherwise available.'),\n" +
                "('M29', 2, 'Missing operative note/report.'),\n" +
                "('M30', 2, 'Missing pathology report.'),\n" +
                "('M31', 2, 'Missing radiology report.'),\n" +
                "('M32', 2, 'Alert: This is a conditional payment made pending a decision on this service by the patient''s primary payer. This payment may be subject to refund upon your receipt of any additional payment for this service from another payer. You must contact this office immediately upon receipt of an additional payment for this service.'),\n" +
                "('M36', 2, 'This is the 11th rental month. We cannot pay for this until you indicate that the patient has been given the option of changing the rental to a purchase.'),\n" +
                "('M37', 2, 'Not covered when the patient is under age 35.'),\n" +
                "('M38', 2, 'Alert: The patient is liable for the charges for this service as they were informed in writing before the service was furnished that we would not pay for it and the patient agreed to be responsible for the charges.'),\n" +
                "('M39', 2, 'Alert: The patient is not liable for payment of this service as the advance notice of non-coverage you provided the patient did not comply with program requirements.'),\n" +
                "('M40', 2, 'Claim must be assigned and must be filed by the practitioner''s employer.'),\n" +
                "('M41', 2, 'We do not pay for this as the patient has no legal obligation to pay for this.'),\n" +
                "('M42', 2, 'The medical necessity form must be personally signed by the attending physician.'),\n" +
                "('M44', 2, 'Missing/incomplete/invalid condition code.'),\n" +
                "('M45', 2, 'Missing/incomplete/invalid occurrence code(s).'),\n" +
                "('M46', 2, 'Missing/incomplete/invalid occurrence span code(s).'),\n" +
                "('M47', 2, 'Missing/incomplete/invalid Payer Claim Control Number. Other terms exist for this element including, but not limited to, Internal Control Number (ICN), Claim Control Number (CCN), Document Control Number (DCN).'),\n" +
                "('M49', 2, 'Missing/incomplete/invalid value code(s) or amount(s).'),\n" +
                "('M50', 2, 'Missing/incomplete/invalid revenue code(s).'),\n" +
                "('M51', 2, 'Missing/incomplete/invalid procedure code(s).'),\n" +
                "('M52', 2, 'Missing/incomplete/invalid ''from'' date(s) of service.'),\n" +
                "('M53', 2, 'Missing/incomplete/invalid days or units of service.'),\n" +
                "('M54', 2, 'Missing/incomplete/invalid total charges.'),\n" +
                "('M55', 2, 'We do not pay for self-administered anti-emetic drugs that are not administered with a covered oral anti-cancer drug.'),\n" +
                "('M56', 2, 'Missing/incomplete/invalid payer identifier.'),\n" +
                "('M59', 2, 'Missing/incomplete/invalid ''to'' date(s) of service.'),\n" +
                "('M60', 2, 'Missing Certificate of Medical Necessity.'),\n" +
                "('M61', 2, 'We cannot pay for this as the approval period for the FDA clinical trial has expired.'),\n" +
                "('M62', 2, 'Missing/incomplete/invalid treatment authorization code.'),\n" +
                "('M64', 2, 'Missing/incomplete/invalid other diagnosis.'),\n" +
                "('M65', 2, 'One interpreting physician charge can be submitted per claim when a purchased diagnostic test is indicated. Please submit a separate claim for each interpreting physician.'),\n" +
                "('M66', 2, 'Our records indicate that you billed diagnostic tests subject to price limitations and the procedure code submitted includes a professional component. Only the technical component is subject to price limitations. Please submit the technical and professional components of this service as separate line items.'),\n" +
                "('M67', 2, 'Missing/incomplete/invalid other procedure code(s).'),\n" +
                "('M69', 2, 'Paid at the regular rate as you did not submit documentation to justify the modified procedure code.'),\n" +
                "('M70', 2, 'Alert: The NDC code submitted for this service was translated to a HCPCS code for processing, but please continue to submit the NDC on future claims for this item.'),\n" +
                "('M71', 2, 'Total payment reduced due to overlap of tests billed.'),\n" +
                "('M73', 2, 'The HPSA/Physician Scarcity bonus can only be paid on the professional component of this service. Rebill as separate professional and technical components.'),\n" +
                "('M74', 2, 'This service does not qualify for a HPSA/Physician Scarcity bonus payment.'),\n" +
                "('M75', 2, 'Multiple automated multichannel tests performed on the same day combined for payment.'),\n" +
                "('M76', 2, 'Missing/incomplete/invalid diagnosis or condition.'),\n" +
                "('M77', 2, 'Missing/incomplete/invalid/inappropriate place of service.'),\n" +
                "('M79', 2, 'Missing/incomplete/invalid charge.'),\n" +
                "('M80', 2, 'Not covered when performed during the same session/date as a previously processed service for the patient.'),\n" +
                "('M81', 2, 'You are required to code to the highest level of specificity.'),\n" +
                "('M82', 2, 'Service is not covered when patient is under age 50.'),\n" +
                "('M83', 2, 'Service is not covered unless the patient is classified as at high risk.'),\n" +
                "('M84', 2, 'Medical code sets used must be the codes in effect at the time of service.'),\n" +
                "('M85', 2, 'Subjected to review of physician evaluation and management services.'),\n" +
                "('M86', 2, 'Service denied because payment already made for same/similar procedure within set time frame.'),\n" +
                "('M87', 2, 'Claim/service(s) subjected to CFO-CAP prepayment review.'),\n" +
                "('M89', 2, 'Not covered more than once under age 40.'),\n" +
                "('M90', 2, 'Not covered more than once in a 12 month period.'),\n" +
                "('M91', 2, 'Lab procedures with different CLIA certification numbers must be billed on separate claims.'),\n" +
                "('M93', 2, 'Information supplied supports a break in therapy. A new capped rental period began with delivery of this equipment.'),\n" +
                "('M94', 2, 'Information supplied does not support a break in therapy. A new capped rental period will not begin.'),\n" +
                "('M95', 2, 'Services subjected to Home Health Initiative medical review/cost report audit.'),\n" +
                "('M96', 2, 'The technical component of a service furnished to an inpatient may only be billed by that inpatient facility. You must contact the inpatient facility for technical component reimbursement. If not already billed, you should bill us for the professional component only.'),\n" +
                "('M97', 2, 'Not paid to practitioner when provided to patient in this place of service. Payment included in the reimbursement issued the facility.'),\n" +
                "('M99', 2, 'Missing/incomplete/invalid Universal Product Number/Serial Number.'),\n" +
                "('M100', 2, 'We do not pay for an oral anti-emetic drug that is not administered for use immediately before, at, or within 48 hours of administration of a covered chemotherapy drug.'),\n" +
                "('M102', 2, 'Service not performed on equipment approved by the FDA for this purpose.'),\n" +
                "('M103', 2, 'Information supplied supports a break in therapy. However, the medical information we have for this patient does not support the need for this item as billed. We have approved payment for this item at a reduced level, and a new capped rental period will begin with the delivery of this equipment.'),\n" +
                "('M104', 2, 'Information supplied supports a break in therapy. A new capped rental period will begin with delivery of the equipment. This is the maximum approved under the fee schedule for this item or service.'),\n" +
                "('M105', 2, 'Information supplied does not support a break in therapy. The medical information we have for this patient does not support the need for this item as billed. We have approved payment for this item at a reduced level, and a new capped rental period will not begin.'),\n" +
                "('M107', 2, 'Payment reduced as 90-day rolling average hematocrit for ESRD patient exceeded 36.5%.'),\n" +
                "('M109', 2, 'We have provided you with a bundled payment for a teleconsultation. You must send 25 percent of the teleconsultation payment to the referring practitioner.'),\n" +
                "('M111', 2, 'We do not pay for chiropractic manipulative treatment when the patient refuses to have an x-ray taken.'),\n" +
                "('M112', 2, 'Reimbursement for this item is based on the single payment amount required under the DMEPOS Competitive Bidding Program for the area where the patient resides.'),\n" +
                "('M113', 2, 'Our records indicate that this patient began using this item/service prior to the current contract period for the DMEPOS Competitive Bidding Program.'),\n" +
                "('M114', 2, 'This service was processed in accordance with rules and guidelines under the DMEPOS Competitive Bidding Program or a Demonstration Project. For more information regarding these projects, contact your local contractor.'),\n" +
                "('M115', 2, 'This item is denied when provided to this patient by a non-contract or non-demonstration supplier.'),\n" +
                "('M116', 2, 'Processed under a demonstration project or program. Project or program is ending and additional services may not be paid under this project or program.'),\n" +
                "('M117', 2, 'Not covered unless submitted via electronic claim.'),\n" +
                "('M119', 2, 'Missing/incomplete/invalid/ deactivated/withdrawn National Drug Code (NDC).'),\n" +
                "('M121', 2, 'We pay for this service only when performed with a covered cryosurgical ablation.'),\n" +
                "('M122', 2, 'Missing/incomplete/invalid level of subluxation.'),\n" +
                "('M123', 2, 'Missing/incomplete/invalid name, strength, or dosage of the drug furnished.'),\n" +
                "('M124', 2, 'Missing indication of whether the patient owns the equipment that requires the part or supply.'),\n" +
                "('M125', 2, 'Missing/incomplete/invalid information on the period of time for which the service/supply/equipment will be needed.'),\n" +
                "('M126', 2, 'Missing/incomplete/invalid individual lab codes included in the test.'),\n" +
                "('M127', 2, 'Missing patient medical record for this service.'),\n" +
                "('M129', 2, 'Missing/incomplete/invalid indicator of x-ray availability for review.'),\n" +
                "('M130', 2, 'Missing invoice or statement certifying the actual cost of the lens, less discounts, and/or the type of intraocular lens used.'),\n" +
                "('M131', 2, 'Missing physician financial relationship form.'),\n" +
                "('M132', 2, 'Missing pacemaker registration form.'),\n" +
                "('M133', 2, 'Claim did not identify who performed the purchased diagnostic test or the amount you were charged for the test.'),\n" +
                "('M134', 2, 'Performed by a facility/supplier in which the provider has a financial interest.'),\n" +
                "('M135', 2, 'Missing/incomplete/invalid plan of treatment.'),\n" +
                "('M136', 2, 'Missing/incomplete/invalid indication that the service was supervised or evaluated by a physician.'),\n" +
                "('M137', 2, 'Part B coinsurance under a demonstration project or pilot program.'),\n" +
                "('M138', 2, 'Patient identified as a demonstration participant but the patient was not enrolled in the demonstration at the time services were rendered. Coverage is limited to demonstration participants.'),\n" +
                "('M139', 2, 'Denied services exceed the coverage limit for the demonstration.'),\n" +
                "('M141', 2, 'Missing physician certified plan of care.'),\n" +
                "('M142', 2, 'Missing American Diabetes Association Certificate of Recognition.'),\n" +
                "('M143', 2, 'The provider must update license information with the payer.'),\n" +
                "('M144', 2, 'Pre-/post-operative care payment is included in the allowance for the surgery/procedure.'),\n" +
                "('MA01', 2, 'Alert: If you do not agree with what we approved for these services, you may appeal our decision. To make sure that we are fair to you, we require another individual that did not process your initial claim to conduct the appeal. However, in order to be eligible for an appeal, you must write to us within 120 days of the date you received this notice, unless you have a good reason for being late.'),\n" +
                "('MA02', 2, 'Alert: If you do not agree with this determination, you have the right to appeal. You must file a written request for an appeal within 180 days of the date you receive this notice.'),\n" +
                "('MA04', 2, 'Secondary payment cannot be considered without the identity of or payment information from the primary payer. The information was either not reported or was illegible.'),\n" +
                "('MA07', 2, 'Alert: The claim information has also been forwarded to Medicaid for review.'),\n" +
                "('MA08', 2, 'Alert: Claim information was not forwarded because the supplemental coverage is not with a Medigap plan, or you do not participate in Medicare.'),\n" +
                "('MA09', 2, 'Alert: Claim submitted as unassigned but processed as assigned in accordance with our current assignment/participation agreement.'),\n" +
                "('MA10', 2, 'Alert: The patient''s payment was in excess of the amount owed. You must refund the overpayment to the patient.'),\n" +
                "('MA12', 2, 'You have not established that you have the right under the law to bill for services furnished by the person(s) that furnished this (these) service(s).'),\n" +
                "('MA13', 2, 'Alert: You may be subject to penalties if you bill the patient for amounts not reported with the PR (patient responsibility) group code.'),\n" +
                "('MA14', 2, 'Alert: The patient is a member of an employer-sponsored prepaid health plan. Services from outside that health plan are not covered. However, as you were not previously notified of this, we are paying this time. In the future, we will not pay you for non-plan services.'),\n" +
                "('MA15', 2, 'Alert: Your claim has been separated to expedite handling. You will receive a separate notice for the other services reported.'),\n" +
                "('MA16', 2, 'The patient is covered by the Black Lung Program. Send this claim to the Department of Labor, Federal Black Lung Program, P.O. Box 828, Lanham-Seabrook MD 20703.'),\n" +
                "('MA17', 2, 'We are the primary payer and have paid at the primary rate. You must contact the patient''s other insurer to refund any excess it may have paid due to its erroneous primary payment.'),\n" +
                "('MA18', 2, 'Alert: The claim information is also being forwarded to the patient''s supplemental insurer. Send any questions regarding supplemental benefits to them.'),\n" +
                "('MA19', 2, 'Alert: Information was not sent to the Medigap insurer due to incorrect/invalid information you submitted concerning that insurer. Please verify your information and submit your secondary claim directly to that insurer.'),\n" +
                "('MA20', 2, 'Skilled Nursing Facility (SNF) stay not covered when care is primarily related to the use of an urethral catheter for convenience or the control of incontinence.'),\n" +
                "('MA21', 2, 'SSA records indicate mismatch with name and sex.'),\n" +
                "('MA22', 2, 'Payment of less than $1.00 suppressed.'),\n" +
                "('MA23', 2, 'Demand bill approved as result of medical review.'),\n" +
                "('MA24', 2, 'Christian Science Sanitarium/ Skilled Nursing Facility (SNF) bill in the same benefit period.'),\n" +
                "('MA25', 2, 'A patient may not elect to change a hospice provider more than once in a benefit period.'),\n" +
                "('MA26', 2, 'Alert: Our records indicate that you were previously informed of this rule.'),\n" +
                "('MA27', 2, 'Missing/incomplete/invalid entitlement number or name shown on the claim.'),\n" +
                "('MA28', 2, 'Alert: Receipt of this notice by a physician or supplier who did not accept assignment is for information only and does not make the physician or supplier a party to the determination. No additional rights to appeal this decision, above those rights already provided for by regulation/instruction, are conferred by receipt of this notice.'),\n" +
                "('MA30', 2, 'Missing/incomplete/invalid type of bill.'),\n" +
                "('MA31', 2, 'Missing/incomplete/invalid beginning and ending dates of the period billed.'),\n" +
                "('MA32', 2, 'Missing/incomplete/invalid number of covered days during the billing period.'),\n" +
                "('MA33', 2, 'Missing/incomplete/invalid non-covered days during the billing period.'),\n" +
                "('MA34', 2, 'Missing/incomplete/invalid number of coinsurance days during the billing period.'),\n" +
                "('MA35', 2, 'Missing/incomplete/invalid number of lifetime reserve days.'),\n" +
                "('MA36', 2, 'Missing/incomplete/invalid patient name.'),\n" +
                "('MA37', 2, 'Missing/incomplete/invalid patient''s address.'),\n" +
                "('MA39', 2, 'Missing/incomplete/invalid gender.'),\n" +
                "('MA40', 2, 'Missing/incomplete/invalid admission date.'),\n" +
                "('MA41', 2, 'Missing/incomplete/invalid admission type.'),\n" +
                "('MA42', 2, 'Missing/incomplete/invalid admission source.'),\n" +
                "('MA43', 2, 'Missing/incomplete/invalid patient status.'),\n" +
                "('MA44', 2, 'Alert: No appeal rights. Adjudicative decision based on law.'),\n" +
                "('MA45', 2, 'Alert: As previously advised, a portion or all of your payment is being held in a special account.'),\n" +
                "('MA46', 2, 'Alert: The new information was considered but additional payment will not be issued.'),\n" +
                "('MA47', 2, 'Our records show you have opted out of Medicare, agreeing with the patient not to bill Medicare for services/tests/supplies furnished. As result, we cannot pay this claim. The patient is responsible for payment.'),\n" +
                "('MA48', 2, 'Missing/incomplete/invalid name or address of responsible party or primary payer.'),\n" +
                "('MA50', 2, 'Missing/incomplete/invalid Investigational Device Exemption number or Clinical Trial number.'),\n" +
                "('MA53', 2, 'Missing/incomplete/invalid Competitive Bidding Demonstration Project identification.'),\n" +
                "('MA54', 2, 'Physician certification or election consent for hospice care not received timely.'),\n" +
                "('MA55', 2, 'Not covered as patient received medical health care services, automatically revoking his/her election to receive religious non-medical health care services.'),\n" +
                "('MA56', 2, 'Our records show you have opted out of Medicare, agreeing with the patient not to bill Medicare for services/tests/supplies furnished. As result, we cannot pay this claim. The patient is responsible for payment, but under Federal law, you cannot charge the patient more than the limiting charge amount.'),\n" +
                "('MA57', 2, 'Patient submitted written request to revoke his/her election for religious non-medical health care services.'),\n" +
                "('MA58', 2, 'Missing/incomplete/invalid release of information indicator.'),\n" +
                "('MA59', 2, 'Alert: The patient overpaid you for these services. You must issue the patient a refund within 30 days for the difference between his/her payment and the total amount shown as patient responsibility on this notice.'),\n" +
                "('MA60', 2, 'Missing/incomplete/invalid patient relationship to insured.'),\n" +
                "('MA61', 2, 'Missing/incomplete/invalid social security number.'),\n" +
                "('MA62', 2, 'Alert: This is a telephone review decision.'),\n" +
                "('MA63', 2, 'Missing/incomplete/invalid principal diagnosis.'),\n" +
                "('MA64', 2, 'Our records indicate that we should be the third payer for this claim. We cannot process this claim until we have received payment information from the primary and secondary payers.'),\n" +
                "('MA65', 2, 'Missing/incomplete/invalid admitting diagnosis.'),\n" +
                "('MA66', 2, 'Missing/incomplete/invalid principal procedure code.'),\n" +
                "('MA67', 2, 'Alert: Correction to a prior claim.'),\n" +
                "('MA68', 2, 'Alert: We did not crossover this claim because the secondary insurance information on the claim was incomplete. Please supply complete information or use the PLANID of the insurer to assure correct and timely routing of the claim.'),\n" +
                "('MA69', 2, 'Missing/incomplete/invalid remarks.'),\n" +
                "('MA70', 2, 'Missing/incomplete/invalid provider representative signature.'),\n" +
                "('MA71', 2, 'Missing/incomplete/invalid provider representative signature date.'),\n" +
                "('MA72', 2, 'Alert: The patient overpaid you for these assigned services. You must issue the patient a refund within 30 days for the difference between his/her payment to you and the total of the amount shown as patient responsibility and as paid to the patient on this notice.'),\n" +
                "('MA73', 2, 'Informational remittance associated with a Medicare demonstration. No payment issued under fee-for-service Medicare as patient has elected managed care.'),\n" +
                "('MA74', 2, 'Alert: This payment replaces an earlier payment for this claim that was either lost, damaged or returned.'),\n" +
                "('MA75', 2, 'Missing/incomplete/invalid patient or authorized representative signature.'),\n" +
                "('MA76', 2, 'Missing/incomplete/invalid provider identifier for home health agency or hospice when physician is performing care plan oversight services.'),\n" +
                "('MA77', 2, 'Alert: The patient overpaid you. You must issue the patient a refund within 30 days for the difference between the patient''s payment less the total of our and other payer payments and the amount shown as patient responsibility on this notice.'),\n" +
                "('MA79', 2, 'Billed in excess of interim rate.'),\n" +
                "('MA80', 2, 'Informational notice. No payment issued for this claim with this notice. Payment issued to the hospital by its intermediary for all services for this encounter under a demonstration project.'),\n" +
                "('MA81', 2, 'Missing/incomplete/invalid provider/supplier signature.'),\n" +
                "('MA83', 2, 'Did not indicate whether we are the primary or secondary payer.'),\n" +
                "('MA84', 2, 'Patient identified as participating in the National Emphysema Treatment Trial but our records indicate that this patient is either not a participant, or has not yet been approved for this phase of the study. Contact Johns Hopkins University, the study coordinator, to resolve if there was a discrepancy.'),\n" +
                "('MA88', 2, 'Missing/incomplete/invalid insured''s address and/or telephone number for the primary payer.'),\n" +
                "('MA89', 2, 'Missing/incomplete/invalid patient''s relationship to the insured for the primary payer.'),\n" +
                "('MA90', 2, 'Missing/incomplete/invalid employment status code for the primary insured.'),\n" +
                "('MA91', 2, 'Alert: This determination is the result of the appeal you filed.'),\n" +
                "('MA92', 2, 'Missing plan information for other insurance.'),\n" +
                "('MA93', 2, 'Non-PIP (Periodic Interim Payment) claim.'),\n" +
                "('MA94', 2, 'Did not enter the statement ''Attending physician not hospice employee'' on the claim form to certify that the rendering physician is not an employee of the hospice.'),\n" +
                "('MA96', 2, 'Claim rejected. Coded as a Medicare Managed Care Demonstration but patient is not enrolled in a Medicare managed care plan.'),\n" +
                "('MA97', 2, 'Missing/incomplete/invalid Medicare Managed Care Demonstration contract number or clinical trial registry number.'),\n" +
                "('MA99', 2, 'Missing/incomplete/invalid Medigap information.'),\n" +
                "('MA100', 2, 'Missing/incomplete/invalid date of current illness or symptoms.'),\n" +
                "('MA103', 2, 'Hemophilia Add On.'),\n" +
                "('MA106', 2, 'PIP (Periodic Interim Payment) claim.'),\n" +
                "('MA107', 2, 'Paper claim contains more than three separate data items in field 19.'),\n" +
                "('MA108', 2, 'Paper claim contains more than one data item in field 23.'),\n" +
                "('MA109', 2, 'Claim processed in accordance with ambulatory surgical guidelines.'),\n" +
                "('MA110', 2, 'Missing/incomplete/invalid information on whether the diagnostic test(s) were performed by an outside entity or if no purchased tests are included on the claim.'),\n" +
                "('MA111', 2, 'Missing/incomplete/invalid purchase price of the test(s) and/or the performing laboratory''s name and address.'),\n" +
                "('MA112', 2, 'Missing/incomplete/invalid group practice information.'),\n" +
                "('MA113', 2, 'Incomplete/invalid taxpayer identification number (TIN) submitted by you per the Internal Revenue Service. Your claims cannot be processed without your correct TIN, and you may not bill the patient pending correction of your TIN. There are no appeal rights for unprocessable claims, but you may resubmit this claim after you have notified this office of your correct TIN.'),\n" +
                "('MA114', 2, 'Missing/incomplete/invalid information on where the services were furnished.'),\n" +
                "('MA115', 2, 'Missing/incomplete/invalid physical location (name and address, or PIN) where the service(s) were rendered in a Health Professional Shortage Area (HPSA).'),\n" +
                "('MA116', 2, 'Did not complete the statement ''Homebound'' on the claim to validate whether laboratory services were performed at home or in an institution.'),\n" +
                "('MA117', 2, 'This claim has been assessed a $1.00 user fee.'),\n" +
                "('MA118', 2, 'Alert: No Medicare payment issued for this claim for services or supplies furnished to a Medicare-eligible veteran through a facility of the Department of Veterans Affairs. Coinsurance and/or deductible are applicable.'),\n" +
                "('MA120', 2, 'Missing/incomplete/invalid CLIA certification number.'),\n" +
                "('MA121', 2, 'Missing/incomplete/invalid x-ray date.'),\n" +
                "('MA122', 2, 'Missing/incomplete/invalid initial treatment date.'),\n" +
                "('MA123', 2, 'Your center was not selected to participate in this study, therefore, we cannot pay for these services.'),\n" +
                "('MA125', 2, 'Per legislation governing this program, payment constitutes payment in full.'),\n" +
                "('MA126', 2, 'Pancreas transplant not covered unless kidney transplant performed.'),\n" +
                "('MA128', 2, 'Missing/incomplete/invalid FDA approval number.'),\n" +
                "('MA130', 2, 'Your claim contains incomplete and/or invalid information, and no appeal rights are afforded because the claim is unprocessable. Please submit a new claim with the complete/correct information.'),\n" +
                "('MA131', 2, 'Physician already paid for services in conjunction with this demonstration claim. You must have the physician withdraw that claim and refund the payment before we can process your claim.'),\n" +
                "('MA132', 2, 'Adjustment to the pre-demonstration rate.'),\n" +
                "('MA133', 2, 'Claim overlaps inpatient stay. Rebill only those services rendered outside the inpatient stay.'),\n" +
                "('MA134', 2, 'Missing/incomplete/invalid provider number of the facility where the patient resides.'),\n" +
                "('N1', 2, 'Alert: You may appeal this decision in writing within the required time limits following receipt of this notice by following the instructions included in your contract, plan benefit documents or jurisdiction statutes. Refer to the URL provided in the ERA for the payer website to access the appeals process guidelines.'),\n" +
                "('N2', 2, 'This allowance has been made in accordance with the most appropriate course of treatment provision of the plan.'),\n" +
                "('N3', 2, 'Missing consent form.'),\n" +
                "('N4', 2, 'Missing/Incomplete/Invalid prior Insurance Carrier(s) EOB.'),\n" +
                "('N5', 2, 'EOB received from previous payer. Claim not on file.'),\n" +
                "('N6', 2, 'Under FEHB law (U.S.C. 8904(b)), we cannot pay more for covered care than the amount Medicare would have allowed if the patient were enrolled in Medicare Part A and/or Medicare Part B.'),\n" +
                "('N7', 2, 'Alert: Processing of this claim/service has included consideration under Major Medical provisions.'),\n" +
                "('N8', 2, 'Crossover claim denied by previous payer and complete claim data not forwarded. Resubmit this claim to this payer to provide adequate data for adjudication.'),\n" +
                "('N9', 2, 'Adjustment represents the estimated amount a previous payer may pay.'),\n" +
                "('N10', 2, 'Adjustment based on the findings of a review organization/professional consult/manual adjudication/medical advisor/dental advisor/peer review.'),\n" +
                "('N11', 2, 'Denial reversed because of medical review.'),\n" +
                "('N12', 2, 'Policy provides coverage supplemental to Medicare. As the member does not appear to be enrolled in the applicable part of Medicare, the member is responsible for payment of the portion of the charge that would have been covered by Medicare.'),\n" +
                "('N13', 2, 'Payment based on professional/technical component modifier(s).'),\n" +
                "('N15', 2, 'Services for a newborn must be billed separately.'),\n" +
                "('N16', 2, 'Family/member Out-of-Pocket maximum has been met. Payment based on a higher percentage.'),\n" +
                "('N19', 2, 'Procedure code incidental to primary procedure.'),\n" +
                "('N20', 2, 'Service not payable with other service rendered on the same date.'),\n" +
                "('N21', 2, 'Alert: Your line item has been separated into multiple lines to expedite handling.'),\n" +
                "('N22', 2, 'Alert: This procedure code was added/changed because it more accurately describes the services rendered.'),\n" +
                "('N23', 2, 'Alert: Patient liability may be affected due to coordination of benefits with other carriers and/or maximum benefit provisions.'),\n" +
                "('N24', 2, 'Missing/incomplete/invalid Electronic Funds Transfer (EFT) banking information.'),\n" +
                "('N25', 2, 'This company has been contracted by your benefit plan to provide administrative claims payment services only. This company does not assume financial risk or obligation with respect to claims processed on behalf of your benefit plan.'),\n" +
                "('N26', 2, 'Missing itemized bill/statement.'),\n" +
                "('N27', 2, 'Missing/incomplete/invalid treatment number.'),\n" +
                "('N28', 2, 'Consent form requirements not fulfilled.'),\n" +
                "('N30', 2, 'Patient ineligible for this service.'),\n" +
                "('N31', 2, 'Missing/incomplete/invalid prescribing provider identifier.'),\n" +
                "('N32', 2, 'Claim must be submitted by the provider who rendered the service.'),\n" +
                "('N33', 2, 'No record of health check prior to initiation of treatment.'),\n" +
                "('N34', 2, 'Incorrect claim form/format for this service.'),\n" +
                "('N35', 2, 'Program integrity/utilization review decision.'),\n" +
                "('N36', 2, 'Claim must meet primary payer''s processing requirements before we can consider payment.'),\n" +
                "('N37', 2, 'Missing/incomplete/invalid tooth number/letter.'),\n" +
                "('N39', 2, 'Procedure code is not compatible with tooth number/letter.'),\n" +
                "('N40', 2, 'Missing radiology film(s)/image(s).'),\n" +
                "('N42', 2, 'Missing mental health assessment.'),\n" +
                "('N43', 2, 'Bed hold or leave days exceeded.'),\n" +
                "('N45', 2, 'Payment based on authorized amount.'),\n" +
                "('N46', 2, 'Missing/incomplete/invalid admission hour.'),\n" +
                "('N47', 2, 'Claim conflicts with another inpatient stay.'),\n" +
                "('N48', 2, 'Claim information does not agree with information received from other insurance carrier.'),\n" +
                "('N49', 2, 'Court ordered coverage information needs validation.'),\n" +
                "('N50', 2, 'Missing/incomplete/invalid discharge information.'),\n" +
                "('N51', 2, 'Electronic interchange agreement not on file for provider/submitter.'),\n" +
                "('N52', 2, 'Patient not enrolled in the billing provider''s managed care plan on the date of service.'),\n" +
                "('N53', 2, 'Missing/incomplete/invalid point of pick-up address.'),\n" +
                "('N54', 2, 'Claim information is inconsistent with pre-certified/authorized services.'),\n" +
                "('N55', 2, 'Procedures for billing with group/referring/performing providers were not followed.'),\n" +
                "('N56', 2, 'Procedure code billed is not correct/valid for the services billed or the date of service billed.'),\n" +
                "('N57', 2, 'Missing/incomplete/invalid prescribing date.'),\n" +
                "('N58', 2, 'Missing/incomplete/invalid patient liability amount.'),\n" +
                "('N59', 2, 'Alert: Please refer to your provider manual for additional program and provider information.'),\n" +
                "('N61', 2, 'Rebill services on separate claims.'),\n" +
                "('N62', 2, 'Dates of service span multiple rate periods. Resubmit separate claims.'),\n" +
                "('N63', 2, 'Rebill services on separate claim lines.'),\n" +
                "('N64', 2, 'The ''from'' and ''to'' dates must be different.'),\n" +
                "('N65', 2, 'Procedure code or procedure rate count cannot be determined, or was not on file, for the date of service/provider.'),\n" +
                "('N67', 2, 'Professional provider services not paid separately. Included in facility payment under a demonstration project. Apply to that facility for payment, or resubmit your claim if: the facility notifies you the patient was excluded from this demonstration; or if you furnished these services in another location on the date of the patient''s admission or discharge from a demonstration hospital. If services were furnished in a facility not involved in the demonstration on the same date the patient was discharged from or admitted to a demonstration facility, you must report the provider ID number for the non-demonstration facility on the new claim.'),\n" +
                "('N68', 2, 'Prior payment being cancelled as we were subsequently notified this patient was covered by a demonstration project in this site of service. Professional services were included in the payment made to the facility. You must contact the facility for your payment. Prior payment made to you by the patient or another insurer for this claim must be refunded to the payer within 30 days.'),\n" +
                "('N69', 2, 'Alert: PPS (Prospective Payment System) code changed by claims processing system.'),\n" +
                "('N70', 2, 'Consolidated billing and payment applies.'),\n" +
                "('N71', 2, 'Your unassigned claim for a drug or biological, clinical diagnostic laboratory services or ambulance service was processed as an assigned claim. You are required by law to accept assignment for these types of claims.'),\n" +
                "('N72', 2, 'PPS (Prospective Payment System) code changed by medical reviewers. Not supported by clinical records.'),\n" +
                "('N74', 2, 'Resubmit with multiple claims, each claim covering services provided in only one calendar month.'),\n" +
                "('N75', 2, 'Missing/incomplete/invalid tooth surface information.'),\n" +
                "('N76', 2, 'Missing/incomplete/invalid number of riders.'),\n" +
                "('N77', 2, 'Missing/incomplete/invalid designated provider number.'),\n" +
                "('N78', 2, 'The necessary components of the child and teen checkup (EPSDT) were not completed.'),\n" +
                "('N79', 2, 'Service billed is not compatible with patient location information.'),\n" +
                "('N80', 2, 'Missing/incomplete/invalid prenatal screening information.'),\n" +
                "('N81', 2, 'Procedure billed is not compatible with tooth surface code.'),\n" +
                "('N82', 2, 'Provider must accept insurance payment as payment in full when a third party payer contract specifies full reimbursement.'),\n" +
                "('N83', 2, 'No appeal rights. Adjudicative decision based on the provisions of a demonstration project.'),\n" +
                "('N84', 2, 'Alert: Further installment payments are forthcoming.'),\n" +
                "('N85', 2, 'Alert: This is the final installment payment.'),\n" +
                "('N86', 2, 'A failed trial of pelvic muscle exercise training is required in order for biofeedback training for the treatment of urinary incontinence to be covered.'),\n" +
                "('N87', 2, 'Home use of biofeedback therapy is not covered.'),\n" +
                "('N88', 2, 'Alert: This payment is being made conditionally. An HHA episode of care notice has been filed for this patient. When a patient is treated under a HHA episode of care, consolidated billing requires that certain therapy services and supplies, such as this, be included in the HHA''s payment. This payment will need to be recouped from you if we establish that the patient is concurrently receiving treatment under a HHA episode of care.'),\n" +
                "('N89', 2, 'Alert: Payment information for this claim has been forwarded to more than one other payer, but format limitations permit only one of the secondary payers to be identified in this remittance advice.'),\n" +
                "('N90', 2, 'Covered only when performed by the attending physician.'),\n" +
                "('N91', 2, 'Services not included in the appeal review.');";
        SQLGeneral.executeUpdate(query);
        query = queryPreface +
                "('N92', 2, 'This facility is not certified for digital mammography.'),\n" +
                "('N93', 2, 'A separate claim must be submitted for each place of service. Services furnished at multiple sites may not be billed in the same claim.'),\n" +
                "('N94', 2, 'Claim/Service denied because a more specific taxonomy code is required for adjudication.'),\n" +
                "('N95', 2, 'This provider type/provider specialty may not bill this service.'),\n" +
                "('N96', 2, 'Patient must be refractory to conventional therapy (documented behavioral, pharmacologic and/or surgical corrective therapy) and be an appropriate surgical candidate such that implantation with anesthesia can occur.'),\n" +
                "('N97', 2, 'Patients with stress incontinence, urinary obstruction, and specific neurologic diseases (e.g., diabetes with peripheral nerve involvement) which are associated with secondary manifestations of the above three indications are excluded.'),\n" +
                "('N98', 2, 'Patient must have had a successful test stimulation in order to support subsequent implantation. Before a patient is eligible for permanent implantation, he/she must demonstrate a 50 percent or greater improvement through test stimulation. Improvement is measured through voiding diaries.'),\n" +
                "('N99', 2, 'Patient must be able to demonstrate adequate ability to record voiding diary data such that clinical results of the implant procedure can be properly evaluated.'),\n" +
                "('N103', 2, 'Records indicate this patient was a prisoner or in custody of a Federal, State, or local authority when the service was rendered. This payer does not cover items and services furnished to an individual while he or she is in custody under a penal statute or rule, unless under State or local law, the individual is personally liable for the cost of his or her health care while in custody and the State or local government pursues the collection of such debt in the same way and with the same vigor as the collection of its other debts. The provider can collect from the Federal/State/ Local Authority as appropriate.'),\n" +
                "('N104', 2, 'This claim/service is not payable under our claims jurisdiction area. You can identify the correct Medicare contractor to process this claim/service through the CMS website at www.cms.gov.'),\n" +
                "('N105', 2, 'This is a misdirected claim/service for an RRB beneficiary. Submit paper claims to the RRB carrier: Palmetto GBA, P.O. Box 10066, Augusta, GA 30999. Call 888-355-9165 for RRB EDI information for electronic claims processing.'),\n" +
                "('N106', 2, 'Payment for services furnished to Skilled Nursing Facility (SNF) inpatients (except for excluded services) can only be made to the SNF. You must request payment from the SNF rather than the patient for this service.'),\n" +
                "('N107', 2, 'Services furnished to Skilled Nursing Facility (SNF) inpatients must be billed on the inpatient claim. They cannot be billed separately as outpatient services.'),\n" +
                "('N108', 2, 'Missing/incomplete/invalid upgrade information.'),\n" +
                "('N109', 2, 'Alert: This claim/service was chosen for complex review.'),\n" +
                "('N110', 2, 'This facility is not certified for film mammography.'),\n" +
                "('N111', 2, 'No appeal right except duplicate claim/service issue. This service was included in a claim that has been previously billed and adjudicated.'),\n" +
                "('N112', 2, 'This claim is excluded from your electronic remittance advice.'),\n" +
                "('N113', 2, 'Only one initial visit is covered per physician, group practice or provider.'),\n" +
                "('N114', 2, 'During the transition to the Ambulance Fee Schedule, payment is based on the lesser of a blended amount calculated using a percentage of the reasonable charge/cost and fee schedule amounts, or the submitted charge for the service. You will be notified yearly what the percentages for the blended payment calculation will be.'),\n" +
                "('N115', 2, 'This decision was based on a Local Coverage Determination (LCD). An LCD provides a guide to assist in determining whether a particular item or service is covered. A copy of this policy is available at www.cms.gov/mcd, or if you do not have web access, you may contact the contractor to request a copy of the LCD.'),\n" +
                "('N116', 2, 'Alert: This payment is being made conditionally because the service was provided in the home, and it is possible that the patient is under a home health episode of care. When a patient is treated under a home health episode of care, consolidated billing requires that certain therapy services and supplies, such as this, be included in the home health agency''s (HHA''s) payment. This payment will need to be recouped from you if we establish that the patient is concurrently receiving treatment under an HHA episode of care.'),\n" +
                "('N117', 2, 'This service is paid only once in a patient''s lifetime.'),\n" +
                "('N118', 2, 'This service is not paid if billed more than once every 28 days.'),\n" +
                "('N119', 2, 'This service is not paid if billed once every 28 days, and the patient has spent 5 or more consecutive days in any inpatient or Skilled /nursing Facility (SNF) within those 28 days.'),\n" +
                "('N120', 2, 'Payment is subject to home health prospective payment system partial episode payment adjustment. Patient was transferred/discharged/readmitted during payment episode.'),\n" +
                "('N121', 2, 'Medicare Part B does not pay for items or services provided by this type of practitioner for beneficiaries in a Medicare Part A covered Skilled Nursing Facility (SNF) stay.'),\n" +
                "('N122', 2, 'Add-on code cannot be billed by itself.'),\n" +
                "('N123', 2, 'Alert: This is a split service and represents a portion of the units from the originally submitted service.'),\n" +
                "('N124', 2, 'Payment has been denied for the/made only for a less extensive service/item because the information furnished does not substantiate the need for the (more extensive) service/item. The patient is liable for the charges for this service/item as you informed the patient in writing before the service/item was furnished that we would not pay for it, and the patient agreed to pay.'),\n" +
                "('N125', 2, 'Payment has been (denied for the/made only for a less extensive) service/item because the information furnished does not substantiate the need for the (more extensive) service/item. If you have collected any amount from the patient, you must refund that amount to the patient within 30 days of receiving this notice.'),\n" +
                "('N126', 2, 'Social Security Records indicate that this individual has been deported. This payer does not cover items and services furnished to individuals who have been deported.'),\n" +
                "('N127', 2, 'This is a misdirected claim/service for a United Mine Workers of America (UMWA) beneficiary. Please submit claims to them.'),\n" +
                "('N128', 2, 'This amount represents the prior to coverage portion of the allowance.'),\n" +
                "('N129', 2, 'Not eligible due to the patient''s age.'),\n" +
                "('N130', 2, 'Consult plan benefit documents/guidelines for information about restrictions for this service.'),\n" +
                "('N131', 2, 'Total payments under multiple contracts cannot exceed the allowance for this service.'),\n" +
                "('N132', 2, 'Alert: Payments will cease for services rendered by this US Government debarred or excluded provider after the 30 day grace period as previously notified.'),\n" +
                "('N133', 2, 'Alert: Services for predetermination and services requesting payment are being processed separately.'),\n" +
                "('N134', 2, 'Alert: This represents your scheduled payment for this service. If treatment has been discontinued, please contact Customer Service.'),\n" +
                "('N135', 2, 'Record fees are the patient''s responsibility and limited to the specified co-payment.'),\n" +
                "('N136', 2, 'Alert: To obtain information on the process to file an appeal in Arizona, call the Department''s Consumer Assistance Office at (602) 912-8444 or (800) 325-2548.'),\n" +
                "('N137', 2, 'Alert: The provider acting on the Member''s behalf, may file an appeal with the Payer. The provider, acting on the Member''s behalf, may file a complaint with the State Insurance Regulatory Authority without first filing an appeal, if the coverage decision involves an urgent condition for which care has not been rendered. The address may be obtained from the State Insurance Regulatory Authority.'),\n" +
                "('N138', 2, 'Alert: In the event you disagree with the Dental Advisor''s opinion and have additional information relative to the case, you may submit radiographs to the Dental Advisor Unit at the subscriber''s dental insurance carrier for a second Independent Dental Advisor Review.'),\n" +
                "('N139', 2, 'Alert: Under 32 CFR 199.13, a non-participating provider is not an appropriate appealing party. Therefore, if you disagree with the Dental Advisor''s opinion, you may appeal the determination if appointed in writing, by the beneficiary, to act as his/her representative. Should you be appointed as a representative, submit a copy of this letter, a signed statement explaining the matter in which you disagree, and any radiographs and relevant information to the subscriber''s Dental insurance carrier within 90 days from the date of this letter.'),\n" +
                "('N140', 2, 'Alert: You have not been designated as an authorized OCONUS provider therefore are not considered an appropriate appealing party. If the beneficiary has appointed you, in writing, to act as his/her representative and you disagree with the Dental Advisor''s opinion, you may appeal by submitting a copy of this letter, a signed statement explaining the matter in which you disagree, and any relevant information to the subscriber''s Dental insurance carrier within 90 days from the date of this letter.'),\n" +
                "('N141', 2, 'The patient was not residing in a long-term care facility during all or part of the service dates billed.'),\n" +
                "('N142', 2, 'The original claim was denied. Resubmit a new claim, not a replacement claim.'),\n" +
                "('N143', 2, 'The patient was not in a hospice program during all or part of the service dates billed.'),\n" +
                "('N144', 2, 'The rate changed during the dates of service billed.'),\n" +
                "('N146', 2, 'Missing screening document.'),\n" +
                "('N147', 2, 'Long term care case mix or per diem rate cannot be determined because the patient ID number is missing, incomplete, or invalid on the assignment request.'),\n" +
                "('N148', 2, 'Missing/incomplete/invalid date of last menstrual period.'),\n" +
                "('N149', 2, 'Rebill all applicable services on a single claim.'),\n" +
                "('N150', 2, 'Missing/incomplete/invalid model number.'),\n" +
                "('N151', 2, 'Telephone contact services will not be paid until the face-to-face contact requirement has been met.'),\n" +
                "('N152', 2, 'Missing/incomplete/invalid replacement claim information.'),\n" +
                "('N153', 2, 'Missing/incomplete/invalid room and board rate.'),\n" +
                "('N154', 2, 'Alert: This payment was delayed for correction of provider''s mailing address.'),\n" +
                "('N155', 2, 'Alert: Our records do not indicate that other insurance is on file. Please submit other insurance information for our records.'),\n" +
                "('N156', 2, 'Alert: The patient is responsible for the difference between the approved treatment and the elective treatment.'),\n" +
                "('N157', 2, 'Transportation to/from this destination is not covered.'),\n" +
                "('N158', 2, 'Transportation in a vehicle other than an ambulance is not covered.'),\n" +
                "('N159', 2, 'Payment denied/reduced because mileage is not covered when the patient is not in the ambulance.'),\n" +
                "('N160', 2, 'The patient must choose an option before a payment can be made for this procedure/ equipment/ supply/ service.'),\n" +
                "('N161', 2, 'This drug/service/supply is covered only when the associated service is covered.'),\n" +
                "('N162', 2, 'Alert: Although your claim was paid, you have billed for a test/specialty not included in your Laboratory Certification. Your failure to correct the laboratory certification information will result in a denial of payment in the near future.'),\n" +
                "('N163', 2, 'Medical record does not support code billed per the code definition.'),\n" +
                "('N167', 2, 'Charges exceed the post-transplant coverage limit.'),\n" +
                "('N170', 2, 'A new/revised/renewed certificate of medical necessity is needed.'),\n" +
                "('N171', 2, 'Payment for repair or replacement is not covered or has exceeded the purchase price.'),\n" +
                "('N172', 2, 'The patient is not liable for the denied/adjusted charge(s) for receiving any updated service/item.'),\n" +
                "('N173', 2, 'No qualifying hospital stay dates were provided for this episode of care.'),\n" +
                "('N174', 2, 'This is not a covered service/procedure/ equipment/bed, however patient liability is limited to amounts shown in the adjustments under group ''PR''.'),\n" +
                "('N175', 2, 'Missing review organization approval.'),\n" +
                "('N176', 2, 'Services provided aboard a ship are covered only when the ship is of United States registry and is in United States waters. In addition, a doctor licensed to practice in the United States must provide the service.'),\n" +
                "('N177', 2, 'Alert: We did not send this claim to patient''s other insurer. They have indicated no additional payment can be made.'),\n" +
                "('N178', 2, 'Missing pre-operative images/visual field results.'),\n" +
                "('N179', 2, 'Additional information has been requested from the member. The charges will be reconsidered upon receipt of that information.'),\n" +
                "('N180', 2, 'This item or service does not meet the criteria for the category under which it was billed.'),\n" +
                "('N181', 2, 'Additional information is required from another provider involved in this service.'),\n" +
                "('N182', 2, 'This claim/service must be billed according to the schedule for this plan.'),\n" +
                "('N183', 2, 'Alert: This is a predetermination advisory message, when this service is submitted for payment additional documentation as specified in plan documents will be required to process benefits.'),\n" +
                "('N184', 2, 'Rebill technical and professional components separately.'),\n" +
                "('N185', 2, 'Alert: Do not resubmit this claim/service.'),\n" +
                "('N186', 2, 'Non-Availability Statement (NAS) required for this service. Contact the nearest Military Treatment Facility (MTF) for assistance.'),\n" +
                "('N187', 2, 'Alert: You may request a review in writing within the required time limits following receipt of this notice by following the instructions included in your contract or plan benefit documents.'),\n" +
                "('N188', 2, 'The approved level of care does not match the procedure code submitted.'),\n" +
                "('N189', 2, 'Alert: This service has been paid as a one-time exception to the plan''s benefit restrictions.'),\n" +
                "('N190', 2, 'Missing contract indicator.'),\n" +
                "('N191', 2, 'The provider must update insurance information directly with payer.'),\n" +
                "('N192', 2, 'Alert: Patient is a Medicaid/Qualified Medicare Beneficiary.'),\n" +
                "('N193', 2, 'Alert: Specific federal/state/local program may cover this service through another payer.'),\n" +
                "('N194', 2, 'Technical component not paid if provider does not own the equipment used.'),\n" +
                "('N195', 2, 'The technical component must be billed separately.'),\n" +
                "('N196', 2, 'Alert: Patient eligible to apply for other coverage which may be primary.'),\n" +
                "('N197', 2, 'The subscriber must update insurance information directly with payer.'),\n" +
                "('N198', 2, 'Rendering provider must be affiliated with the pay-to provider.'),\n" +
                "('N199', 2, 'Additional payment/recoupment approved based on payer-initiated review/audit.'),\n" +
                "('N200', 2, 'The professional component must be billed separately.'),\n" +
                "('N202', 2, 'Alert: Additional information/explanation will be sent separately.'),\n" +
                "('N203', 2, 'Missing/incomplete/invalid anesthesia time/units.'),\n" +
                "('N204', 2, 'Services under review for possible pre-existing condition. Send medical records for prior 12 months'),\n" +
                "('N205', 2, 'Information provided was illegible.'),\n" +
                "('N206', 2, 'The supporting documentation does not match the information sent on the claim.'),\n" +
                "('N207', 2, 'Missing/incomplete/invalid weight.'),\n" +
                "('N208', 2, 'Missing/incomplete/invalid DRG code.'),\n" +
                "('N209', 2, 'Missing/incomplete/invalid taxpayer identification number (TIN).'),\n" +
                "('N210', 2, 'Alert: You may appeal this decision.'),\n" +
                "('N211', 2, 'Alert: You may not appeal this decision.'),\n" +
                "('N212', 2, 'Charges processed under a Point of Service benefit.'),\n" +
                "('N213', 2, 'Missing/incomplete/invalid facility/discrete unit DRG/DRG exempt status information.'),\n" +
                "('N214', 2, 'Missing/incomplete/invalid history of the related initial surgical procedure(s).'),\n" +
                "('N215', 2, 'Alert: A payer providing supplemental or secondary coverage shall not require a claims determination for this service from a primary payer as a condition of making its own claims determination.'),\n" +
                "('N216', 2, 'We do not offer coverage for this type of service or the patient is not enrolled in this portion of our benefit package.'),\n" +
                "('N217', 2, 'We pay only one site of service per provider per claim.'),\n" +
                "('N218', 2, 'You must furnish and service this item for as long as the patient continues to need it. We can pay for maintenance and/or servicing for the time period specified in the contract or coverage manual.'),\n" +
                "('N219', 2, 'Payment based on previous payer''s allowed amount.'),\n" +
                "('N220', 2, 'Alert: See the payer''s web site or contact the payer''s Customer Service department to obtain forms and instructions for filing a provider dispute.'),\n" +
                "('N221', 2, 'Missing Admitting History and Physical report.'),\n" +
                "('N222', 2, 'Incomplete/invalid Admitting History and Physical report.'),\n" +
                "('N223', 2, 'Missing documentation of benefit to the patient during initial treatment period.'),\n" +
                "('N224', 2, 'Incomplete/invalid documentation of benefit to the patient during initial treatment period.'),\n" +
                "('N226', 2, 'Incomplete/invalid American Diabetes Association Certificate of Recognition.'),\n" +
                "('N227', 2, 'Incomplete/invalid Certificate of Medical Necessity.'),\n" +
                "('N228', 2, 'Incomplete/invalid consent form.'),\n" +
                "('N229', 2, 'Incomplete/invalid contract indicator.'),\n" +
                "('N230', 2, 'Incomplete/invalid indication of whether the patient owns the equipment that requires the part or supply.'),\n" +
                "('N231', 2, 'Incomplete/invalid invoice or statement certifying the actual cost of the lens, less discounts, and/or the type of intraocular lens used.'),\n" +
                "('N232', 2, 'Incomplete/invalid itemized bill/statement.'),\n" +
                "('N233', 2, 'Incomplete/invalid operative note/report.'),\n" +
                "('N234', 2, 'Incomplete/invalid oxygen certification/re-certification.'),\n" +
                "('N235', 2, 'Incomplete/invalid pacemaker registration form.'),\n" +
                "('N236', 2, 'Incomplete/invalid pathology report.'),\n" +
                "('N237', 2, 'Incomplete/invalid patient medical record for this service.'),\n" +
                "('N238', 2, 'Incomplete/invalid physician certified plan of care.'),\n" +
                "('N239', 2, 'Incomplete/invalid physician financial relationship form.'),\n" +
                "('N240', 2, 'Incomplete/invalid radiology report.'),\n" +
                "('N241', 2, 'Incomplete/invalid review organization approval.'),\n" +
                "('N242', 2, 'Incomplete/invalid radiology film(s)/image(s).'),\n" +
                "('N243', 2, 'Incomplete/invalid/not approved screening document.'),\n" +
                "('N244', 2, 'Incomplete/Invalid pre-operative images/visual field results.'),\n" +
                "('N245', 2, 'Incomplete/invalid plan information for other insurance.'),\n" +
                "('N246', 2, 'State regulated patient payment limitations apply to this service.'),\n" +
                "('N247', 2, 'Missing/incomplete/invalid assistant surgeon taxonomy.'),\n" +
                "('N248', 2, 'Missing/incomplete/invalid assistant surgeon name.'),\n" +
                "('N249', 2, 'Missing/incomplete/invalid assistant surgeon primary identifier.'),\n" +
                "('N250', 2, 'Missing/incomplete/invalid assistant surgeon secondary identifier.'),\n" +
                "('N251', 2, 'Missing/incomplete/invalid attending provider taxonomy.'),\n" +
                "('N252', 2, 'Missing/incomplete/invalid attending provider name.'),\n" +
                "('N253', 2, 'Missing/incomplete/invalid attending provider primary identifier.'),\n" +
                "('N254', 2, 'Missing/incomplete/invalid attending provider secondary identifier.'),\n" +
                "('N255', 2, 'Missing/incomplete/invalid billing provider taxonomy.'),\n" +
                "('N256', 2, 'Missing/incomplete/invalid billing provider/supplier name.'),\n" +
                "('N257', 2, 'Missing/incomplete/invalid billing provider/supplier primary identifier.'),\n" +
                "('N258', 2, 'Missing/incomplete/invalid billing provider/supplier address.'),\n" +
                "('N259', 2, 'Missing/incomplete/invalid billing provider/supplier secondary identifier.'),\n" +
                "('N260', 2, 'Missing/incomplete/invalid billing provider/supplier contact information.'),\n" +
                "('N261', 2, 'Missing/incomplete/invalid operating provider name.'),\n" +
                "('N262', 2, 'Missing/incomplete/invalid operating provider primary identifier.'),\n" +
                "('N263', 2, 'Missing/incomplete/invalid operating provider secondary identifier.'),\n" +
                "('N264', 2, 'Missing/incomplete/invalid ordering provider name.'),\n" +
                "('N265', 2, 'Missing/incomplete/invalid ordering provider primary identifier.'),\n" +
                "('N266', 2, 'Missing/incomplete/invalid ordering provider address.'),\n" +
                "('N267', 2, 'Missing/incomplete/invalid ordering provider secondary identifier.'),\n" +
                "('N268', 2, 'Missing/incomplete/invalid ordering provider contact information.'),\n" +
                "('N269', 2, 'Missing/incomplete/invalid other provider name.'),\n" +
                "('N270', 2, 'Missing/incomplete/invalid other provider primary identifier.'),\n" +
                "('N271', 2, 'Missing/incomplete/invalid other provider secondary identifier.'),\n" +
                "('N272', 2, 'Missing/incomplete/invalid other payer attending provider identifier.'),\n" +
                "('N273', 2, 'Missing/incomplete/invalid other payer operating provider identifier.'),\n" +
                "('N274', 2, 'Missing/incomplete/invalid other payer other provider identifier.'),\n" +
                "('N275', 2, 'Missing/incomplete/invalid other payer purchased service provider identifier.'),\n" +
                "('N276', 2, 'Missing/incomplete/invalid other payer referring provider identifier.'),\n" +
                "('N277', 2, 'Missing/incomplete/invalid other payer rendering provider identifier.'),\n" +
                "('N278', 2, 'Missing/incomplete/invalid other payer service facility provider identifier.'),\n" +
                "('N279', 2, 'Missing/incomplete/invalid pay-to provider name.'),\n" +
                "('N280', 2, 'Missing/incomplete/invalid pay-to provider primary identifier.'),\n" +
                "('N281', 2, 'Missing/incomplete/invalid pay-to provider address.'),\n" +
                "('N282', 2, 'Missing/incomplete/invalid pay-to provider secondary identifier.'),\n" +
                "('N283', 2, 'Missing/incomplete/invalid purchased service provider identifier.'),\n" +
                "('N284', 2, 'Missing/incomplete/invalid referring provider taxonomy.'),\n" +
                "('N285', 2, 'Missing/incomplete/invalid referring provider name.'),\n" +
                "('N286', 2, 'Missing/incomplete/invalid referring provider primary identifier.'),\n" +
                "('N287', 2, 'Missing/incomplete/invalid referring provider secondary identifier.'),\n" +
                "('N288', 2, 'Missing/incomplete/invalid rendering provider taxonomy.'),\n" +
                "('N289', 2, 'Missing/incomplete/invalid rendering provider name.'),\n" +
                "('N290', 2, 'Missing/incomplete/invalid rendering provider primary identifier.'),\n" +
                "('N291', 2, 'Missing/incomplete/invalid rendering provider secondary identifier.'),\n" +
                "('N292', 2, 'Missing/incomplete/invalid service facility name.'),\n" +
                "('N293', 2, 'Missing/incomplete/invalid service facility primary identifier.'),\n" +
                "('N294', 2, 'Missing/incomplete/invalid service facility primary address.'),\n" +
                "('N295', 2, 'Missing/incomplete/invalid service facility secondary identifier.'),\n" +
                "('N296', 2, 'Missing/incomplete/invalid supervising provider name.'),\n" +
                "('N297', 2, 'Missing/incomplete/invalid supervising provider primary identifier.'),\n" +
                "('N298', 2, 'Missing/incomplete/invalid supervising provider secondary identifier.'),\n" +
                "('N299', 2, 'Missing/incomplete/invalid occurrence date(s).'),\n" +
                "('N300', 2, 'Missing/incomplete/invalid occurrence span date(s).'),\n" +
                "('N301', 2, 'Missing/incomplete/invalid procedure date(s).'),\n" +
                "('N302', 2, 'Missing/incomplete/invalid other procedure date(s).'),\n" +
                "('N303', 2, 'Missing/incomplete/invalid principal procedure date.'),\n" +
                "('N304', 2, 'Missing/incomplete/invalid dispensed date.'),\n" +
                "('N305', 2, 'Missing/incomplete/invalid injury/accident date.'),\n" +
                "('N306', 2, 'Missing/incomplete/invalid acute manifestation date.'),\n" +
                "('N307', 2, 'Missing/incomplete/invalid adjudication or payment date.'),\n" +
                "('N308', 2, 'Missing/incomplete/invalid appliance placement date.'),\n" +
                "('N309', 2, 'Missing/incomplete/invalid assessment date.'),\n" +
                "('N310', 2, 'Missing/incomplete/invalid assumed or relinquished care date.'),\n" +
                "('N311', 2, 'Missing/incomplete/invalid authorized to return to work date.'),\n" +
                "('N312', 2, 'Missing/incomplete/invalid begin therapy date.'),\n" +
                "('N313', 2, 'Missing/incomplete/invalid certification revision date.'),\n" +
                "('N314', 2, 'Missing/incomplete/invalid diagnosis date.'),\n" +
                "('N315', 2, 'Missing/incomplete/invalid disability from date.'),\n" +
                "('N316', 2, 'Missing/incomplete/invalid disability to date.'),\n" +
                "('N317', 2, 'Missing/incomplete/invalid discharge hour.'),\n" +
                "('N318', 2, 'Missing/incomplete/invalid discharge or end of care date.'),\n" +
                "('N319', 2, 'Missing/incomplete/invalid hearing or vision prescription date.'),\n" +
                "('N320', 2, 'Missing/incomplete/invalid Home Health Certification Period.'),\n" +
                "('N321', 2, 'Missing/incomplete/invalid last admission period.'),\n" +
                "('N322', 2, 'Missing/incomplete/invalid last certification date.'),\n" +
                "('N323', 2, 'Missing/incomplete/invalid last contact date.'),\n" +
                "('N324', 2, 'Missing/incomplete/invalid last seen/visit date.'),\n" +
                "('N325', 2, 'Missing/incomplete/invalid last worked date.'),\n" +
                "('N326', 2, 'Missing/incomplete/invalid last x-ray date.'),\n" +
                "('N327', 2, 'Missing/incomplete/invalid other insured birth date.'),\n" +
                "('N328', 2, 'Missing/incomplete/invalid Oxygen Saturation Test date.'),\n" +
                "('N329', 2, 'Missing/incomplete/invalid patient birth date.'),\n" +
                "('N330', 2, 'Missing/incomplete/invalid patient death date.'),\n" +
                "('N331', 2, 'Missing/incomplete/invalid physician order date.'),\n" +
                "('N332', 2, 'Missing/incomplete/invalid prior hospital discharge date.'),\n" +
                "('N333', 2, 'Missing/incomplete/invalid prior placement date.'),\n" +
                "('N334', 2, 'Missing/incomplete/invalid re-evaluation date.'),\n" +
                "('N335', 2, 'Missing/incomplete/invalid referral date.'),\n" +
                "('N336', 2, 'Missing/incomplete/invalid replacement date.'),\n" +
                "('N337', 2, 'Missing/incomplete/invalid secondary diagnosis date.'),\n" +
                "('N338', 2, 'Missing/incomplete/invalid shipped date.'),\n" +
                "('N339', 2, 'Missing/incomplete/invalid similar illness or symptom date.'),\n" +
                "('N340', 2, 'Missing/incomplete/invalid subscriber birth date.'),\n" +
                "('N341', 2, 'Missing/incomplete/invalid surgery date.'),\n" +
                "('N342', 2, 'Missing/incomplete/invalid test performed date.'),\n" +
                "('N343', 2, 'Missing/incomplete/invalid Transcutaneous Electrical Nerve Stimulator (TENS) trial start date.'),\n" +
                "('N344', 2, 'Missing/incomplete/invalid Transcutaneous Electrical Nerve Stimulator (TENS) trial end date.'),\n" +
                "('N345', 2, 'Date range not valid with units submitted.'),\n" +
                "('N346', 2, 'Missing/incomplete/invalid oral cavity designation code.'),\n" +
                "('N347', 2, 'Your claim for a referred or purchased service cannot be paid because payment has already been made for this same service to another provider by a payment contractor representing the payer.'),\n" +
                "('N348', 2, 'You chose that this service/supply/drug would be rendered/supplied and billed by a different practitioner/supplier.'),\n" +
                "('N349', 2, 'The administration method and drug must be reported to adjudicate this service.'),\n" +
                "('N350', 2, 'Missing/incomplete/invalid description of service for a Not Otherwise Classified (NOC) code or for an Unlisted/By Report procedure.'),\n" +
                "('N351', 2, 'Service date outside of the approved treatment plan service dates.'),\n" +
                "('N352', 2, 'Alert: There are no scheduled payments for this service. Submit a claim for each patient visit.'),\n" +
                "('N353', 2, 'Alert: Benefits have been estimated, when the actual services have been rendered, additional payment will be considered based on the submitted claim.'),\n" +
                "('N354', 2, 'Incomplete/invalid invoice.'),\n" +
                "('N355', 2, 'Alert: The law permits exceptions to the refund requirement in two cases: - If you did not know, and could not have reasonably been expected to know, that we would not pay for this service; or - If you notified the patient in writing before providing the service that you believed that we were likely to deny the service, and the patient signed a statement agreeing to pay for the service.'),\n" +
                "('N356', 2, 'Not covered when performed with, or subsequent to, a non-covered service.'),\n" +
                "('N357', 2, 'Time frame requirements between this service/procedure/supply and a related service/procedure/supply have not been met.'),\n" +
                "('N358', 2, 'Alert: This decision may be reviewed if additional documentation as described in the contract or plan benefit documents is submitted.'),\n" +
                "('N359', 2, 'Missing/incomplete/invalid height.'),\n" +
                "('N360', 2, 'Alert: Coordination of benefits has not been calculated when estimating benefits for this pre-determination. Submit payment information from the primary payer with the secondary claim.'),\n" +
                "('N362', 2, 'The number of Days or Units of Service exceeds our acceptable maximum.'),\n" +
                "('N363', 2, 'Alert: in the near future we are implementing new policies/procedures that would affect this determination.'),\n" +
                "('N364', 2, 'Alert: According to our agreement, you must waive the deductible and/or coinsurance amounts.'),\n" +
                "('N366', 2, 'Requested information not provided. The claim will be reopened if the information previously requested is submitted within one year after the date of this denial notice.'),\n" +
                "('N367', 2, 'Alert: The claim information has been forwarded to a Consumer Spending Account processor for review; for example, flexible spending account or health savings account.'),\n" +
                "('N368', 2, 'You must appeal the determination of the previously adjudicated claim.'),\n" +
                "('N369', 2, 'Alert: Although this claim has been processed, it is deficient according to state legislation/regulation.'),\n" +
                "('N370', 2, 'Billing exceeds the rental months covered/approved by the payer.'),\n" +
                "('N371', 2, 'Alert: title of this equipment must be transferred to the patient.'),\n" +
                "('N372', 2, 'Only reasonable and necessary maintenance/service charges are covered.'),\n" +
                "('N373', 2, 'It has been determined that another payer paid the services as primary when they were not the primary payer. Therefore, we are refunding to the payer that paid as primary on your behalf.'),\n" +
                "('N374', 2, 'Primary Medicare Part A insurance has been exhausted and a Part B Remittance Advice is required.'),\n" +
                "('N375', 2, 'Missing/incomplete/invalid questionnaire/information required to determine dependent eligibility.'),\n" +
                "('N376', 2, 'Subscriber/patient is assigned to active military duty, therefore primary coverage may be TRICARE.'),\n" +
                "('N377', 2, 'Payment based on a processed replacement claim.'),\n" +
                "('N378', 2, 'Missing/incomplete/invalid prescription quantity.'),\n" +
                "('N379', 2, 'Claim level information does not match line level information.'),\n" +
                "('N380', 2, 'The original claim has been processed, submit a corrected claim.'),\n" +
                "('N381', 2, 'Alert: Consult our contractual agreement for restrictions/billing/payment information related to these charges.'),\n" +
                "('N382', 2, 'Missing/incomplete/invalid patient identifier.'),\n" +
                "('N383', 2, 'Not covered when deemed cosmetic.'),\n" +
                "('N384', 2, 'Records indicate that the referenced body part/tooth has been removed in a previous procedure.'),\n" +
                "('N385', 2, 'Notification of admission was not timely according to published plan procedures.'),\n" +
                "('N386', 2, 'This decision was based on a National Coverage Determination (NCD). An NCD provides a coverage determination as to whether a particular item or service is covered. A copy of this policy is available at www.cms.gov/mcd/search.asp. If you do not have web access, you may contact the contractor to request a copy of the NCD.'),\n" +
                "('N387', 2, 'Alert: Submit this claim to the patient''s other insurer for potential payment of supplemental benefits. We did not forward the claim information.'),\n" +
                "('N388', 2, 'Missing/incomplete/invalid prescription number.'),\n" +
                "('N389', 2, 'Duplicate prescription number submitted.'),\n" +
                "('N390', 2, 'This service/report cannot be billed separately.'),\n" +
                "('N391', 2, 'Missing emergency department records.'),\n" +
                "('N392', 2, 'Incomplete/invalid emergency department records.'),\n" +
                "('N393', 2, 'Missing progress notes/report.'),\n" +
                "('N394', 2, 'Incomplete/invalid progress notes/report.'),\n" +
                "('N395', 2, 'Missing laboratory report.'),\n" +
                "('N396', 2, 'Incomplete/invalid laboratory report.'),\n" +
                "('N397', 2, 'Benefits are not available for incomplete service(s)/undelivered item(s).'),\n" +
                "('N398', 2, 'Missing elective consent form.'),\n" +
                "('N399', 2, 'Incomplete/invalid elective consent form.'),\n" +
                "('N400', 2, 'Alert: Electronically enabled providers should submit claims electronically.'),\n" +
                "('N401', 2, 'Missing periodontal charting.'),\n" +
                "('N402', 2, 'Incomplete/invalid periodontal charting.'),\n" +
                "('N403', 2, 'Missing facility certification.'),\n" +
                "('N404', 2, 'Incomplete/invalid facility certification.'),\n" +
                "('N405', 2, 'This service is only covered when the donor''s insurer(s) do not provide coverage for the service.'),\n" +
                "('N406', 2, 'This service is only covered when the recipient''s insurer(s) do not provide coverage for the service.'),\n" +
                "('N407', 2, 'You are not an approved submitter for this transmission format.'),\n" +
                "('N408', 2, 'This payer does not cover deductibles assessed by a previous payer.'),\n" +
                "('N409', 2, 'This service is related to an accidental injury and is not covered unless provided within a specific time frame from the date of the accident.'),\n" +
                "('N410', 2, 'Not covered unless the prescription changes.'),\n" +
                "('N411', 2, 'This service is allowed one time in a 6-month period.'),\n" +
                "('N412', 2, 'This service is allowed 2 times in a 12-month period.'),\n" +
                "('N413', 2, 'This service is allowed 2 times in a benefit year.'),\n" +
                "('N414', 2, 'This service is allowed 4 times in a 12-month period.'),\n" +
                "('N415', 2, 'This service is allowed 1 time in an 18-month period.'),\n" +
                "('N416', 2, 'This service is allowed 1 time in a 3-year period.'),\n" +
                "('N417', 2, 'This service is allowed 1 time in a 5-year period.'),\n" +
                "('N418', 2, 'Misrouted claim. See the payer''s claim submission instructions.'),\n" +
                "('N419', 2, 'Claim payment was the result of a payer''s retroactive adjustment due to a retroactive rate change.'),\n" +
                "('N420', 2, 'Claim payment was the result of a payer''s retroactive adjustment due to a Coordination of Benefits or Third Party Liability Recovery.'),\n" +
                "('N421', 2, 'Claim payment was the result of a payer''s retroactive adjustment due to a review organization decision.'),\n" +
                "('N422', 2, 'Claim payment was the result of a payer''s retroactive adjustment due to a payer''s contract incentive program.'),\n" +
                "('N423', 2, 'Claim payment was the result of a payer''s retroactive adjustment due to a non standard program.'),\n" +
                "('N424', 2, 'Patient does not reside in the geographic area required for this type of payment.'),\n" +
                "('N425', 2, 'Statutorily excluded service(s).'),\n" +
                "('N426', 2, 'No coverage when self-administered.'),\n" +
                "('N427', 2, 'Payment for eyeglasses or contact lenses can be made only after cataract surgery.'),\n" +
                "('N428', 2, 'Not covered when performed in this place of service.'),\n" +
                "('N429', 2, 'Not covered when considered routine.'),\n" +
                "('N430', 2, 'Procedure code is inconsistent with the units billed.'),\n" +
                "('N431', 2, 'Not covered with this procedure.'),\n" +
                "('N432', 2, 'Alert: Adjustment based on a Recovery Audit.'),\n" +
                "('N433', 2, 'Resubmit this claim using only your National Provider Identifier (NPI).'),\n" +
                "('N434', 2, 'Missing/Incomplete/Invalid Present on Admission indicator.'),\n" +
                "('N435', 2, 'Exceeds number/frequency approved /allowed within time period without support documentation.'),\n" +
                "('N436', 2, 'The injury claim has not been accepted and a mandatory medical reimbursement has been made.'),\n" +
                "('N437', 2, 'Alert: If the injury claim is accepted, these charges will be reconsidered.'),\n" +
                "('N438', 2, 'This jurisdiction only accepts paper claims.'),\n" +
                "('N439', 2, 'Missing anesthesia physical status report/indicators.'),\n" +
                "('N440', 2, 'Incomplete/invalid anesthesia physical status report/indicators.'),\n" +
                "('N441', 2, 'This missed/cancelled appointment is not covered.'),\n" +
                "('N442', 2, 'Payment based on an alternate fee schedule.'),\n" +
                "('N443', 2, 'Missing/incomplete/invalid total time or begin/end time.'),\n" +
                "('N444', 2, 'Alert: This facility has not filed the Election for High Cost Outlier form with the Division of Workers'' Compensation.'),\n" +
                "('N445', 2, 'Missing document for actual cost or paid amount.'),\n" +
                "('N446', 2, 'Incomplete/invalid document for actual cost or paid amount.'),\n" +
                "('N447', 2, 'Payment is based on a generic equivalent as required documentation was not provided.'),\n" +
                "('N448', 2, 'This drug/service/supply is not included in the fee schedule or contracted/legislated fee arrangement.'),\n" +
                "('N449', 2, 'Payment based on a comparable drug/service/supply.'),\n" +
                "('N450', 2, 'Covered only when performed by the primary treating physician or the designee.'),\n" +
                "('N451', 2, 'Missing Admission Summary Report.'),\n" +
                "('N452', 2, 'Incomplete/invalid Admission Summary Report.'),\n" +
                "('N453', 2, 'Missing Consultation Report.'),\n" +
                "('N454', 2, 'Incomplete/invalid Consultation Report.'),\n" +
                "('N455', 2, 'Missing Physician Order.'),\n" +
                "('N456', 2, 'Incomplete/invalid Physician Order.'),\n" +
                "('N457', 2, 'Missing Diagnostic Report.'),\n" +
                "('N458', 2, 'Incomplete/invalid Diagnostic Report.'),\n" +
                "('N459', 2, 'Missing Discharge Summary.'),\n" +
                "('N460', 2, 'Incomplete/invalid Discharge Summary.'),\n" +
                "('N461', 2, 'Missing Nursing Notes.'),\n" +
                "('N462', 2, 'Incomplete/invalid Nursing Notes.'),\n" +
                "('N463', 2, 'Missing support data for claim.'),\n" +
                "('N464', 2, 'Incomplete/invalid support data for claim.'),\n" +
                "('N465', 2, 'Missing Physical Therapy Notes/Report.'),\n" +
                "('N466', 2, 'Incomplete/invalid Physical Therapy Notes/Report.'),\n" +
                "('N467', 2, 'Missing Tests and Analysis Report.'),\n" +
                "('N468', 2, 'Incomplete/invalid Report of Tests and Analysis Report.'),\n" +
                "('N469', 2, 'Alert: Claim/Service(s) subject to appeal process, see section 935 of Medicare Prescription Drug, Improvement, and Modernization Act of 2003 (MMA).'),\n" +
                "('N470', 2, 'This payment will complete the mandatory medical reimbursement limit.'),\n" +
                "('N471', 2, 'Missing/incomplete/invalid HIPPS Rate Code.'),\n" +
                "('N472', 2, 'Payment for this service has been issued to another provider.'),\n" +
                "('N473', 2, 'Missing certification.'),\n" +
                "('N474', 2, 'Incomplete/invalid certification.'),\n" +
                "('N475', 2, 'Missing completed referral form.'),\n" +
                "('N476', 2, 'Incomplete/invalid completed referral form.'),\n" +
                "('N477', 2, 'Missing Dental Models.'),\n" +
                "('N478', 2, 'Incomplete/invalid Dental Models.'),\n" +
                "('N479', 2, 'Missing Explanation of Benefits (Coordination of Benefits or Medicare Secondary Payer).'),\n" +
                "('N480', 2, 'Incomplete/invalid Explanation of Benefits (Coordination of Benefits or Medicare Secondary Payer).'),\n" +
                "('N481', 2, 'Missing Models.'),\n" +
                "('N482', 2, 'Incomplete/invalid Models.'),\n" +
                "('N485', 2, 'Missing Physical Therapy Certification.'),\n" +
                "('N486', 2, 'Incomplete/invalid Physical Therapy Certification.'),\n" +
                "('N487', 2, 'Missing Prosthetics or Orthotics Certification.'),\n" +
                "('N488', 2, 'Incomplete/invalid Prosthetics or Orthotics Certification.'),\n" +
                "('N489', 2, 'Missing referral form.'),\n" +
                "('N490', 2, 'Incomplete/invalid referral form.'),\n" +
                "('N491', 2, 'Missing/Incomplete/Invalid Exclusionary Rider Condition.'),\n" +
                "('N492', 2, 'Alert: A network provider may bill the member for this service if the member requested the service and agreed in writing, prior to receiving the service, to be financially responsible for the billed charge.'),\n" +
                "('N493', 2, 'Missing Doctor First Report of Injury.'),\n" +
                "('N494', 2, 'Incomplete/invalid Doctor First Report of Injury.'),\n" +
                "('N495', 2, 'Missing Supplemental Medical Report.'),\n" +
                "('N496', 2, 'Incomplete/invalid Supplemental Medical Report.'),\n" +
                "('N497', 2, 'Missing Medical Permanent Impairment or Disability Report.'),\n" +
                "('N498', 2, 'Incomplete/invalid Medical Permanent Impairment or Disability Report.'),\n" +
                "('N499', 2, 'Missing Medical Legal Report.'),\n" +
                "('N500', 2, 'Incomplete/invalid Medical Legal Report.'),\n" +
                "('N501', 2, 'Missing Vocational Report.'),\n" +
                "('N502', 2, 'Incomplete/invalid Vocational Report.'),\n" +
                "('N503', 2, 'Missing Work Status Report.'),\n" +
                "('N504', 2, 'Incomplete/invalid Work Status Report.'),\n" +
                "('N505', 2, 'Alert: This response includes only services that could be estimated in real-time. No estimate will be provided for the services that could not be estimated in real-time.'),\n" +
                "('N506', 2, 'Alert: This is an estimate of the member''s liability based on the information available at the time the estimate was processed. Actual coverage and member liability amounts will be determined when the claim is processed. This is not a pre-authorization or a guarantee of payment.'),\n" +
                "('N507', 2, 'Plan distance requirements have not been met.'),\n" +
                "('N508', 2, 'Alert: This real-time claim adjudication response represents the member responsibility to the provider for services reported. The member will receive an Explanation of Benefits electronically or in the mail. Contact the insurer if there are any questions.'),\n" +
                "('N509', 2, 'Alert: A current inquiry shows the member''s Consumer Spending Account contains sufficient funds to cover the member liability for this claim/service. Actual payment from the Consumer Spending Account will depend on the availability of funds and determination of eligible services at the time of payment processing.'),\n" +
                "('N510', 2, 'Alert: A current inquiry shows the member''s Consumer Spending Account does not contain sufficient funds to cover the member''s liability for this claim/service. Actual payment from the Consumer Spending Account will depend on the availability of funds and determination of eligible services at the time of payment processing.'),\n" +
                "('N511', 2, 'Alert: Information on the availability of Consumer Spending Account funds to cover the member liability on this claim/service is not available at this time.'),\n" +
                "('N512', 2, 'Alert: This is the initial remit of a non-NCPDP claim originally submitted real-time without change to the adjudication.'),\n" +
                "('N513', 2, 'Alert: This is the initial remit of a non-NCPDP claim originally submitted real-time with a change to the adjudication.'),\n" +
                "('N516', 2, 'Records indicate a mismatch between the submitted NPI and EIN.'),\n" +
                "('N517', 2, 'Resubmit a new claim with the requested information.'),\n" +
                "('N518', 2, 'No separate payment for accessories when furnished for use with oxygen equipment.'),\n" +
                "('N519', 2, 'Invalid combination of HCPCS modifiers.'),\n" +
                "('N520', 2, 'Alert: Payment made from a Consumer Spending Account.'),\n" +
                "('N521', 2, 'Mismatch between the submitted provider information and the provider information stored in our system.'),\n" +
                "('N522', 2, 'Duplicate of a claim processed, or to be processed, as a crossover claim.'),\n" +
                "('N523', 2, 'The limitation on outlier payments defined by this payer for this service period has been met. The outlier payment otherwise applicable to this claim has not been paid.'),\n" +
                "('N524', 2, 'Based on policy this payment constitutes payment in full.'),\n" +
                "('N525', 2, 'These services are not covered when performed within the global period of another service.'),\n" +
                "('N526', 2, 'Not qualified for recovery based on employer size.'),\n" +
                "('N527', 2, 'We processed this claim as the primary payer prior to receiving the recovery demand.'),\n" +
                "('N528', 2, 'Patient is entitled to benefits for Institutional Services only.'),\n" +
                "('N529', 2, 'Patient is entitled to benefits for Professional Services only.'),\n" +
                "('N530', 2, 'Not Qualified for Recovery based on enrollment information.'),\n" +
                "('N531', 2, 'Not qualified for recovery based on direct payment of premium.'),\n" +
                "('N532', 2, 'Not qualified for recovery based on disability and working status.'),\n" +
                "('N533', 2, 'Services performed in an Indian Health Services facility under a self-insured tribal Group Health Plan.'),\n" +
                "('N534', 2, 'This is an individual policy, the employer does not participate in plan sponsorship.'),\n" +
                "('N535', 2, 'Payment is adjusted when procedure is performed in this place of service based on the submitted procedure code and place of service.'),\n" +
                "('N536', 2, 'We are not changing the prior payer''s determination of patient responsibility, which you may collect, as this service is not covered by us.'),\n" +
                "('N537', 2, 'We have examined claims history and no records of the services have been found.'),\n" +
                "('N538', 2, 'A facility is responsible for payment to outside providers who furnish these services/supplies/drugs to its patients/residents.'),\n" +
                "('N539', 2, 'Alert: We processed appeals/waiver requests on your behalf and that request has been denied.'),\n" +
                "('N540', 2, 'Payment adjusted based on the interrupted stay policy.'),\n" +
                "('N541', 2, 'Mismatch between the submitted insurance type code and the information stored in our system.'),\n" +
                "('N542', 2, 'Missing income verification.'),\n" +
                "('N543', 2, 'Incomplete/invalid income verification.'),\n" +
                "('N544', 2, 'Alert: Although this was paid, you have billed with a referring/ordering provider that does not match our system record. Unless corrected this will not be paid in the future.'),\n" +
                "('N545', 2, 'Payment reduced based on status as an unsuccessful eprescriber per the Electronic Prescribing (eRx) Incentive Program.'),\n" +
                "('N546', 2, 'Payment represents a previous reduction based on the Electronic Prescribing (eRx) Incentive Program.'),\n" +
                "('N547', 2, 'A refund request (Frequency Type Code 8) was processed previously.'),\n" +
                "('N548', 2, 'Alert: Patient''s calendar year deductible has been met.'),\n" +
                "('N549', 2, 'Alert: Patient''s calendar year out-of-pocket maximum has been met.'),\n" +
                "('N550', 2, 'Alert: You have not responded to requests to revalidate your provider/supplier enrollment information. Your failure to revalidate your enrollment information will result in a payment hold in the near future.'),\n" +
                "('N551', 2, 'Payment adjusted based on the Ambulatory Surgical Center (ASC) Quality Reporting Program.'),\n" +
                "('N552', 2, 'Payment adjusted to reverse a previous withhold/bonus amount.'),\n" +
                "('N554', 2, 'Missing/Incomplete/Invalid Family Planning Indicator.'),\n" +
                "('N555', 2, 'Missing medication list.'),\n" +
                "('N556', 2, 'Incomplete/invalid medication list.'),\n" +
                "('N557', 2, 'This claim/service is not payable under our service area. The claim must be filed to the Payer/Plan in whose service area the specimen was collected.');\n";
            SQLGeneral.executeUpdate(query);
            query = queryPreface +
                    "('N558', 2, 'This claim/service is not payable under our service area. The claim must be filed to the Payer/Plan in whose service area the equipment was received.'),\n" +
                    "('N559', 2, 'This claim/service is not payable under our service area. The claim must be filed to the Payer/Plan in whose service area the Ordering Physician is located.'),\n" +
                    "('N560', 2, 'The pilot program requires an interim or final claim within 60 days of the Notice of Admission. A claim was not received.'),\n" +
                    "('N561', 2, 'The bundled claim originally submitted for this episode of care includes related readmissions. You may resubmit the original claim to receive a corrected payment based on this readmission.'),\n" +
                    "('N562', 2, 'The provider number of your incoming claim does not match the provider number on the processed Notice of Admission (NOA) for this bundled payment.'),\n" +
                    "('N563', 2, 'Alert: Missing required provider/supplier issuance of advance patient notice of non-coverage. The patient is not liable for payment for this service.'),\n" +
                    "('N564', 2, 'Patient did not meet the inclusion criteria for the demonstration project or pilot program.'),\n" +
                    "('N565', 2, 'Alert: This non-payable reporting code requires a modifier. Future claims containing this non-payable reporting code must include an appropriate modifier for the claim to be processed.'),\n" +
                    "('N566', 2, 'Alert: This procedure code requires functional reporting. Future claims containing this procedure code must include an applicable non-payable code and appropriate modifiers for the claim to be processed.'),\n" +
                    "('N567', 2, 'Not covered when considered preventative.'),\n" +
                    "('N568', 2, 'Alert: Initial payment based on the Notice of Admission (NOA) under the Bundled Payment Model IV initiative.'),\n" +
                    "('N569', 2, 'Not covered when performed for the reported diagnosis.'),\n" +
                    "('N570', 2, 'Missing/incomplete/invalid credentialing data.'),\n" +
                    "('N571', 2, 'Alert: Payment will be issued quarterly by another payer/contractor.'),\n" +
                    "('N572', 2, 'This procedure is not payable unless appropriate non-payable reporting codes and associated modifiers are submitted.'),\n" +
                    "('N573', 2, 'Alert: You have been overpaid and must refund the overpayment. The refund will be requested separately by another payer/contractor.'),\n" +
                    "('N574', 2, 'Our records indicate the ordering/referring provider is of a type/specialty that cannot order or refer. Please verify that the claim ordering/referring provider information is accurate or contact the ordering/referring provider.'),\n" +
                    "('N575', 2, 'Mismatch between the submitted ordering/referring provider name and the ordering/referring provider name stored in our records.'),\n" +
                    "('N576', 2, 'Services not related to the specific incident/claim/accident/loss being reported.'),\n" +
                    "('N577', 2, 'Personal Injury Protection (PIP) Coverage.'),\n" +
                    "('N578', 2, 'Coverages do not apply to this loss.'),\n" +
                    "('N579', 2, 'Medical Payments Coverage (MPC).'),\n" +
                    "('N580', 2, 'Determination based on the provisions of the insurance policy.'),\n" +
                    "('N581', 2, 'Investigation of coverage eligibility is pending.'),\n" +
                    "('N582', 2, 'Benefits suspended pending the patient''s cooperation.'),\n" +
                    "('N583', 2, 'Patient was not an occupant of our insured vehicle and therefore, is not an eligible injured person.'),\n" +
                    "('N584', 2, 'Not covered based on the insured''s noncompliance with policy or statutory conditions.'),\n" +
                    "('N585', 2, 'Benefits are no longer available based on a final injury settlement.'),\n" +
                    "('N586', 2, 'The injured party does not qualify for benefits.'),\n" +
                    "('N587', 2, 'Policy benefits have been exhausted.'),\n" +
                    "('N588', 2, 'The patient has instructed that medical claims/bills are not to be paid.'),\n" +
                    "('N589', 2, 'Coverage is excluded to any person injured as a result of operating a motor vehicle while in an intoxicated condition or while the ability to operate such a vehicle is impaired by the use of a drug.'),\n" +
                    "('N590', 2, 'Missing independent medical exam detailing the cause of injuries sustained and medical necessity of services rendered.'),\n" +
                    "('N591', 2, 'Payment based on an Independent Medical Examination (IME) or Utilization Review (UR).'),\n" +
                    "('N592', 2, 'Adjusted because this is not the initial prescription or exceeds the amount allowed for the initial prescription.'),\n" +
                    "('N593', 2, 'Not covered based on failure to attend a scheduled Independent Medical Exam (IME).'),\n" +
                    "('N594', 2, 'Records reflect the injured party did not complete an Application for Benefits for this loss.'),\n" +
                    "('N595', 2, 'Records reflect the injured party did not complete an Assignment of Benefits for this loss.'),\n" +
                    "('N596', 2, 'Records reflect the injured party did not complete a Medical Authorization for this loss.'),\n" +
                    "('N597', 2, 'Adjusted based on a medical/dental provider''s apportionment of care between related injuries and other unrelated medical/dental conditions/injuries.'),\n" +
                    "('N598', 2, 'Health care policy coverage is primary.'),\n" +
                    "('N599', 2, 'Our payment for this service is based upon a reasonable amount pursuant to both the terms and conditions of the policy of insurance under which the subject claim is being made as well as the Florida No-Fault Statute, which permits, when determining a reasonable charge for a service, an insurer to consider usual and customary charges and payments accepted by the provider, reimbursement levels in the community and various federal and state fee schedules applicable to automobile and other insurance coverages, and other information relevant to the reasonableness of the reimbursement for the service. The payment for this service is based upon 200% of the Participating Level of Medicare Part B fee schedule for the locale in which the services were rendered.'),\n" +
                    "('N600', 2, 'Adjusted based on the applicable fee schedule for the region in which the service was rendered.'),\n" +
                    "('N601', 2, 'In accordance with Hawaii Administrative Rules, Title 16, Chapter 23 Motor Vehicle Insurance Law payment is recommended based on Medicare Resource Based Relative Value Scale System applicable to Hawaii.'),\n" +
                    "('N602', 2, 'Adjusted based on the Redbook maximum allowance.'),\n" +
                    "('N603', 2, 'This fee is calculated according to the New Jersey medical fee schedules for Automobile Personal Injury Protection and Motor Bus Medical Expense Insurance Coverage.'),\n" +
                    "('N604', 2, 'In accordance with New York No-Fault Law, Regulation 68, this base fee was calculated according to the New York Workers'' Compensation Board Schedule of Medical Fees, pursuant to Regulation 83 and / or Appendix 17-C of 11 NYCRR.'),\n" +
                    "('N605', 2, 'This fee was calculated based upon New York All Patients Refined Diagnosis Related Groups (APR-DRG), pursuant to Regulation 68.'),\n" +
                    "('N606', 2, 'The Oregon allowed amount for this procedure is based upon the Workers Compensation Fee Schedule (OAR 436-009). The allowed amount has been calculated in accordance with Section 4 of ORS 742.524.'),\n" +
                    "('N607', 2, 'Service provided for non-compensable condition(s).'),\n" +
                    "('N608', 2, 'The fee schedule amount allowed is calculated at 110% of the Medicare Fee Schedule for this region, specialty and type of service. This fee is calculated in compliance with Act 6.'),\n" +
                    "('N609', 2, '80% of the provider''s billed amount is being recommended for payment according to Act 6.'),\n" +
                    "('N610', 2, 'Alert: Payment based on an appropriate level of care.'),\n" +
                    "('N611', 2, 'Claim in litigation. Contact insurer for more information.'),\n" +
                    "('N612', 2, 'Medical provider not authorized/certified to provide treatment to injured workers in this jurisdiction.'),\n" +
                    "('N613', 2, 'Alert: Although this was paid, you have billed with an ordering provider that needs to update their enrollment record. Please verify that the ordering provider information you submitted on the claim is accurate and if it is, contact the ordering provider instructing them to update their enrollment record. Unless corrected, a claim with this ordering provider will not be paid in the future.'),\n" +
                    "('N614', 2, 'Alert: Additional information is included in the 835 Healthcare Policy Identification Segment (loop 2110 Service Payment Information).'),\n" +
                    "('N615', 2, 'Alert: This enrollee receiving advance payments of the premium tax credit is in the grace period of three consecutive months for non-payment of premium. Under 45 CFR 156.270, a Qualified Health Plan issuer must pay all appropriate claims for services rendered to the enrollee during the first month of the grace period and may pend claims for services rendered to the enrollee in the second and third months of the grace period.'),\n" +
                    "('N616', 2, 'Alert: This enrollee is in the first month of the advance premium tax credit grace period.'),\n" +
                    "('N617', 2, 'This enrollee is in the second or third month of the advance premium tax credit grace period.'),\n" +
                    "('N618', 2, 'Alert: This claim will automatically be reprocessed if the enrollee pays their premiums.'),\n" +
                    "('N619', 2, 'Coverage terminated for non-payment of premium.'),\n" +
                    "('N620', 2, 'Alert: This procedure code is for quality reporting/informational purposes only.'),\n" +
                    "('N621', 2, 'Charges for Jurisdiction required forms, reports, or chart notes are not payable.'),\n" +
                    "('N622', 2, 'Not covered based on the date of injury/accident.'),\n" +
                    "('N623', 2, 'Not covered when deemed unscientific/unproven/outmoded/experimental/excessive/inappropriate.'),\n" +
                    "('N624', 2, 'The associated Workers'' Compensation claim has been withdrawn.'),\n" +
                    "('N625', 2, 'Missing/Incomplete/Invalid Workers'' Compensation Claim Number.'),\n" +
                    "('N626', 2, 'New or established patient E/M codes are not payable with chiropractic care codes.'),\n" +
                    "('N628', 2, 'Out-patient follow up visits on the same date of service as a scheduled test or treatment is disallowed.'),\n" +
                    "('N629', 2, 'Reviews/documentation/notes/summaries/reports/charts not requested.'),\n" +
                    "('N630', 2, 'Referral not authorized by attending physician.'),\n" +
                    "('N631', 2, 'Medical Fee Schedule does not list this code. An allowance was made for a comparable service.'),\n" +
                    "('N633', 2, 'Additional anesthesia time units are not allowed.'),\n" +
                    "('N634', 2, 'The allowance is calculated based on anesthesia time units.'),\n" +
                    "('N635', 2, 'The Allowance is calculated based on the anesthesia base units plus time.'),\n" +
                    "('N636', 2, 'Adjusted because this is reimbursable only once per injury.'),\n" +
                    "('N637', 2, 'Consultations are not allowed once treatment has been rendered by the same provider.'),\n" +
                    "('N638', 2, 'Reimbursement has been made according to the home health fee schedule.'),\n" +
                    "('N639', 2, 'Reimbursement has been made according to the inpatient rehabilitation facilities fee schedule.'),\n" +
                    "('N640', 2, 'Exceeds number/frequency approved/allowed within time period.'),\n" +
                    "('N641', 2, 'Reimbursement has been based on the number of body areas rated.'),\n" +
                    "('N642', 2, 'Adjusted when billed as individual tests instead of as a panel.'),\n" +
                    "('N643', 2, 'The services billed are considered Not Covered or Non-Covered (NC) in the applicable state fee schedule.'),\n" +
                    "('N644', 2, 'Reimbursement has been made according to the bilateral procedure rule.'),\n" +
                    "('N645', 2, 'Mark-up allowance.'),\n" +
                    "('N646', 2, 'Reimbursement has been adjusted based on the guidelines for an assistant.'),\n" +
                    "('N647', 2, 'Adjusted based on diagnosis-related group (DRG).'),\n" +
                    "('N648', 2, 'Adjusted based on Stop Loss.'),\n" +
                    "('N649', 2, 'Payment based on invoice.'),\n" +
                    "('N650', 2, 'This policy was not in effect for this date of loss. No coverage is available.'),\n" +
                    "('N651', 2, 'No Personal Injury Protection/Medical Payments Coverage on the policy at the time of the loss.'),\n" +
                    "('N652', 2, 'The date of service is before the date of loss.'),\n" +
                    "('N653', 2, 'The date of injury does not match the reported date of loss.'),\n" +
                    "('N654', 2, 'Adjusted based on achievement of maximum medical improvement (MMI).'),\n" +
                    "('N655', 2, 'Payment based on provider''s geographic region.'),\n" +
                    "('N656', 2, 'An interest payment is being made because benefits are being paid outside the statutory requirement.'),\n" +
                    "('N657', 2, 'This should be billed with the appropriate code for these services.'),\n" +
                    "('N658', 2, 'The billed service(s) are not considered medical expenses.'),\n" +
                    "('N659', 2, 'This item is exempt from sales tax.'),\n" +
                    "('N660', 2, 'Sales tax has been included in the reimbursement.'),\n" +
                    "('N661', 2, 'Documentation does not support that the services rendered were medically necessary.'),\n" +
                    "('N662', 2, 'Alert: Consideration of payment will be made upon receipt of a final bill.'),\n" +
                    "('N663', 2, 'Adjusted based on an agreed amount.'),\n" +
                    "('N664', 2, 'Adjusted based on a legal settlement.'),\n" +
                    "('N665', 2, 'Services by an unlicensed provider are not reimbursable.'),\n" +
                    "('N666', 2, 'Only one evaluation and management code at this service level is covered during the course of care.'),\n" +
                    "('N667', 2, 'Missing prescription.'),\n" +
                    "('N668', 2, 'Incomplete/invalid prescription.'),\n" +
                    "('N669', 2, 'Adjusted based on the Medicare fee schedule.'),\n" +
                    "('N670', 2, 'This service code has been identified as the primary procedure code subject to the Medicare Multiple Procedure Payment Reduction (MPPR) rule.'),\n" +
                    "('N671', 2, 'Payment based on a jurisdiction cost-charge ratio.'),\n" +
                    "('N672', 2, 'Alert: Amount applied to Health Insurance Offset.'),\n" +
                    "('N673', 2, 'Reimbursement has been calculated based on an outpatient per diem or an outpatient factor and/or fee schedule amount.'),\n" +
                    "('N674', 2, 'Not covered unless a pre-requisite procedure/service has been provided.'),\n" +
                    "('N675', 2, 'Additional information is required from the injured party.'),\n" +
                    "('N676', 2, 'Service does not qualify for payment under the Outpatient Facility Fee Schedule.'),\n" +
                    "('N677', 2, 'Alert: Films/Images will not be returned.'),\n" +
                    "('N678', 2, 'Missing post-operative images/visual field results.'),\n" +
                    "('N679', 2, 'Incomplete/Invalid post-operative images/visual field results.'),\n" +
                    "('N680', 2, 'Missing/Incomplete/Invalid date of previous dental extractions.'),\n" +
                    "('N681', 2, 'Missing/Incomplete/Invalid full arch series.'),\n" +
                    "('N682', 2, 'Missing/Incomplete/Invalid history of prior periodontal therapy/maintenance.'),\n" +
                    "('N683', 2, 'Missing/Incomplete/Invalid prior treatment documentation.'),\n" +
                    "('N684', 2, 'Payment denied as this is a specialty claim submitted as a general claim.'),\n" +
                    "('N685', 2, 'Missing/Incomplete/Invalid Prosthesis, Crown or Inlay Code.'),\n" +
                    "('N686', 2, 'Missing/incomplete/Invalid questionnaire needed to complete payment determination.'),\n" +
                    "('N687', 2, 'Alert: This reversal is due to a retroactive disenrollment.'),\n" +
                    "('N688', 2, 'Alert: This reversal is due to a medical or utilization review decision.'),\n" +
                    "('N689', 2, 'Alert: This reversal is due to a retroactive rate change.'),\n" +
                    "('N690', 2, 'Alert: This reversal is due to a provider submitted appeal.'),\n" +
                    "('N691', 2, 'Alert: This reversal is due to a patient submitted appeal.'),\n" +
                    "('N692', 2, 'Alert: This reversal is due to an incorrect rate on the initial adjudication.'),\n" +
                    "('N693', 2, 'Alert: This reversal is due to a cancellation of the claim by the provider.'),\n" +
                    "('N694', 2, 'Alert: This reversal is due to a resubmission/change to the claim by the provider.'),\n" +
                    "('N695', 2, 'Alert: This reversal is due to incorrect patient financial responsibility information on the initial adjudication.'),\n" +
                    "('N696', 2, 'Alert: This reversal is due to a Coordination of Benefits or Third Party Liability Recovery retroactive adjustment.'),\n" +
                    "('N697', 2, 'Alert: This reversal is due to a payer''s retroactive contract incentive program adjustment.'),\n" +
                    "('N698', 2, 'Alert: This reversal is due to non-payment of the health insurance premiums (Health Insurance Exchange or other) by the end of the premium payment grace period, resulting in loss of coverage.'),\n" +
                    "('N699', 2, 'Payment adjusted based on the Physician Quality Reporting System (PQRS) Incentive Program.'),\n" +
                    "('N700', 2, 'Payment adjusted based on the Electronic Health Records (EHR) Incentive Program.'),\n" +
                    "('N701', 2, 'Payment adjusted based on the Value-based Payment Modifier.'),\n" +
                    "('N702', 2, 'Decision based on review of previously adjudicated claims or for claims in process for the same/similar type of services.'),\n" +
                    "('N703', 2, 'This service is incompatible with previously adjudicated claims or claims in process.'),\n" +
                    "('N704', 2, 'Alert: You may not appeal this decision but can resubmit this claim/service with corrected information if warranted.'),\n" +
                    "('N705', 2, 'Incomplete/invalid documentation.'),\n" +
                    "('N706', 2, 'Missing documentation.'),\n" +
                    "('N707', 2, 'Incomplete/invalid orders.'),\n" +
                    "('N708', 2, 'Missing orders.'),\n" +
                    "('N709', 2, 'Incomplete/invalid notes.'),\n" +
                    "('N710', 2, 'Missing notes.'),\n" +
                    "('N711', 2, 'Incomplete/invalid summary.'),\n" +
                    "('N712', 2, 'Missing summary.'),\n" +
                    "('N713', 2, 'Incomplete/invalid report.'),\n" +
                    "('N714', 2, 'Missing report.'),\n" +
                    "('N715', 2, 'Incomplete/invalid chart.'),\n" +
                    "('N716', 2, 'Missing chart.'),\n" +
                    "('N717', 2, 'Incomplete/Invalid documentation of face-to-face examination.'),\n" +
                    "('N718', 2, 'Missing documentation of face-to-face examination.'),\n" +
                    "('N719', 2, 'Penalty applied based on plan requirements not being met.'),\n" +
                    "('N720', 2, 'Alert: The patient overpaid you. You may need to issue the patient a refund for the difference between the patient''s payment and the amount shown as patient responsibility on this notice.'),\n" +
                    "('N721', 2, 'This service is only covered when performed as part of a clinical trial.'),\n" +
                    "('N722', 2, 'Patient must use Workers'' Compensation Set-Aside (WCSA) funds to pay for the medical service or item.'),\n" +
                    "('N723', 2, 'Patient must use Liability set-aside (LSA) funds to pay for the medical service or item.'),\n" +
                    "('N724', 2, 'Patient must use No-Fault set-aside (NFSA) funds to pay for the medical service or item.'),\n" +
                    "('N725', 2, 'A liability insurer has reported having ongoing responsibility for medical services (ORM) for this diagnosis.'),\n" +
                    "('N726', 2, 'A conditional payment is not allowed.'),\n" +
                    "('N727', 2, 'A no-fault insurer has reported having ongoing responsibility for medical services (ORM) for this diagnosis.'),\n" +
                    "('N728', 2, 'A workers'' compensation insurer has reported having ongoing responsibility for medical services (ORM) for this diagnosis.'),\n" +
                    "('N729', 2, 'Missing patient medical/dental record for this service.'),\n" +
                    "('N730', 2, 'Incomplete/invalid patient medical/dental record for this service.'),\n" +
                    "('N731', 2, 'Incomplete/Invalid mental health assessment.'),\n" +
                    "('N732', 2, 'Services performed at an unlicensed facility are not reimbursable.'),\n" +
                    "('N733', 2, 'Regulatory surcharges are paid directly to the state.'),\n" +
                    "('N734', 2, 'The patient is eligible for these medical services only when unable to work or perform normal activities due to an illness or injury.'),\n" +
                    "('N736', 2, 'Incomplete/invalid Sleep Study Report.'),\n" +
                    "('N737', 2, 'Missing Sleep Study Report.'),\n" +
                    "('N738', 2, 'Incomplete/invalid Vein Study Report.'),\n" +
                    "('N739', 2, 'Missing Vein Study Report.'),\n" +
                    "('N740', 2, 'The member''s Consumer Spending Account does not contain sufficient funds to cover the member''s liability for this claim/service.'),\n" +
                    "('N741', 2, 'This is a site neutral payment.'),\n" +
                    "('N743', 2, 'Adjusted because the services may be related to an employment accident.'),\n" +
                    "('N744', 2, 'Adjusted because the services may be related to an auto/other accident.'),\n" +
                    "('N745', 2, 'Missing Ambulance Report.'),\n" +
                    "('N746', 2, 'Incomplete/invalid Ambulance Report.'),\n" +
                    "('N747', 2, 'This is a misdirected claim/service. Submit the claim to the payer/plan where the patient resides.'),\n" +
                    "('N748', 2, 'Adjusted because the related hospital charges have not been received.'),\n" +
                    "('N749', 2, 'Missing Blood Gas Report.'),\n" +
                    "('N750', 2, 'Incomplete/invalid Blood Gas Report.'),\n" +
                    "('N751', 2, 'Adjusted because the patient is covered under a Medicare Part D plan.'),\n" +
                    "('N752', 2, 'Missing/incomplete/invalid HIPPS Treatment Authorization Code (TAC).'),\n" +
                    "('N753', 2, 'Missing/incomplete/invalid Attachment Control Number.'),\n" +
                    "('N754', 2, 'Missing/incomplete/invalid Referring Provider or Other Source Qualifier on the 1500 Claim Form.'),\n" +
                    "('N755', 2, 'Missing/incomplete/invalid ICD Indicator.'),\n" +
                    "('N756', 2, 'Missing/incomplete/invalid point of drop-off address.'),\n" +
                    "('N757', 2, 'Adjusted based on the Federal Indian Fees schedule (MLR).'),\n" +
                    "('N758', 2, 'Adjusted based on the prior authorization decision.'),\n" +
                    "('N759', 2, 'Payment adjusted based on the National Electrical Manufacturers Association (NEMA) Standard XR-29-2013.'),\n" +
                    "('N760', 2, 'This facility is not authorized to receive payment for the service(s).'),\n" +
                    "('N761', 2, 'This provider is not authorized to receive payment for the service(s).'),\n" +
                    "('N762', 2, 'This facility is not certified for Tomosynthesis (3-D) mammography.'),\n" +
                    "('N763', 2, 'The demonstration code is not appropriate for this claim; resubmit without a demonstration code.'),\n" +
                    "('N764', 2, 'Missing/incomplete/invalid Hematocrit (HCT) value.'),\n" +
                    "('N765', 2, 'This payer does not cover coinsurance assessed by a previous payer.'),\n" +
                    "('N766', 2, 'This payer does not cover co-payment assessed by a previous payer.'),\n" +
                    "('N767', 2, 'The Medicaid state requires provider to be enrolled in the member''s Medicaid state program prior to any claim benefits being processed.'),\n" +
                    "('N768', 2, 'Incomplete/invalid initial evaluation report.'),\n" +
                    "('N769', 2, 'A lateral diagnosis is required.'),\n" +
                    "('N770', 2, 'The adjustment request received from the provider has been processed. Your original claim has been adjusted based on the information received.'),\n" +
                    "('N771', 2, 'Alert: Under Federal law you cannot charge more than the limiting charge amount.'),\n" +
                    "('N772', 2, 'Alert: Rebill urgent/emergent and ancillary services separately.'),\n" +
                    "('N773', 2, 'Drug supplied not obtained from specialty vendor.'),\n" +
                    "('N774', 2, 'Alert: Refer to your Third Party Processor Agreement for specific information on fees associated with this payment type.'),\n" +
                    "('N775', 2, 'Payment adjusted based on x-ray radiograph on film.'),\n" +
                    "('N776', 2, 'This service is not a covered Telehealth service.'),\n" +
                    "('N777', 2, 'Missing Assignment of Benefits Indicator.'),\n" +
                    "('N778', 2, 'Missing Primary Care Physician Information.'),\n" +
                    "('N779', 2, 'Replacement/Void claims cannot be submitted until the original claim has finalized. Please resubmit once payment or denial is received.'),\n" +
                    "('N780', 2, 'Missing/incomplete/invalid end therapy date.'),\n" +
                    "('N781', 2, 'Alert: Patient is a Medicaid/ Qualified Medicare Beneficiary. Review your records for any wrongfully collected deductible. This amount may be billed to a subsequent payer.'),\n" +
                    "('N782', 2, 'Alert: Patient is a Medicaid/ Qualified Medicare Beneficiary. Review your records for any wrongfully collected coinsurance. This amount may be billed to a subsequent payer.'),\n" +
                    "('N783', 2, 'Alert: Patient is a Medicaid/ Qualified Medicare Beneficiary. Review your records for any wrongfully collected copayment. This amount may be billed to a subsequent payer.'),\n" +
                    "('N784', 2, 'Missing comprehensive procedure code.'),\n" +
                    "('N785', 2, 'Missing current radiology film/images.'),\n" +
                    "('N786', 2, 'Benefit limitation for the orthodontic active and/or retention phase of treatment.'),\n" +
                    "('N787', 2, 'Alert: Under 42 CFR 410.43, an eligible Partial Hospitalization Program (PHP) patient/beneficiary requires a minimum of 20 hours of PHP services per week, as evidenced in the plan of care. PHP services must be furnished in accordance with the plan of care.'),\n" +
                    "('N788', 2, 'Alert: The third-party administrator/review organization did not receive the required information.'),\n" +
                    "('N789', 2, 'Clinical Trial is not a covered benefit.'),\n" +
                    "('N790', 2, 'Provider/supplier not accredited for product/service.'),\n" +
                    "('N791', 2, 'Missing history & physical report.'),\n" +
                    "('N792', 2, 'Incomplete/invalid history & physical report.'),\n" +
                    "('N794', 2, 'Payment adjusted based on type of technology used.'),\n" +
                    "('N795', 2, 'Item must be resubmitted as a purchase.'),\n" +
                    "('N796', 2, 'Missing/incomplete/invalid Hemoglobin (Hb or Hgb) value.'),\n" +
                    "('N797', 2, 'Missing/incomplete/invalid date qualifier.'),\n" +
                    "('N798', 2, 'Submit a void request for the original claim and resubmit a new claim.'),\n" +
                    "('N799', 2, 'Submitted identifier must be an individual identifier, not group identifier.'),\n" +
                    "('N800', 2, 'Only one service date is allowed per claim.'),\n" +
                    "('N801', 2, 'Services performed in a Medicare participating or CAH facility under a self-insured tribal Group Health Plan, in accordance with Federal Regulation 42 CFR 136.'),\n" +
                    "('N802', 2, 'This claim/service is not payable under our service area. The claim must be filed to the Payer/Plan in whose service area the Rendering Physician is located.'),\n" +
                    "('N803', 2, 'Submission of the claim for the service rendered is the responsibility of the Contracted Medical Group or Hospital.'),\n" +
                    "('N804', 2, 'Alert: The claim/service was processed through the Outpatient Code Editor (OCE).'),\n" +
                    "('N805', 2, 'Alert: The claim/service was processed through the Correct Code Editor (CCE).'),\n" +
                    "('N806', 2, 'Payment is included in the Global transplant allowance.'),\n" +
                    "('N807', 2, 'Payment adjustment based on the Merit-based Incentive Payment System (MIPS).'),\n" +
                    "('N808', 2, 'Not covered for this provider type / provider specialty.'),\n" +
                    "('N809', 2, 'Alert: The fee schedule amount for this service was adjusted based on prior competitive bidding rates. For more information, contact your local contractor.'),\n" +
                    "('N810', 2, 'Alert: Due to federal, state or local disaster declaration, this claim has been processed at the in-network level of benefit. At the conclusion or expiration of the disaster declaration, network payment rules will be reinstated.'),\n" +
                    "('N811', 2, 'Missing Federal Sequestration Reduction from Prior Payer.'),\n" +
                    "('N812', 2, 'The start service date through end service date cannot span greater than 18 months.'),\n" +
                    "('N815', 2, 'Missing/Incomplete/Invalid NDC Unit Count'),\n" +
                    "('N816', 2, 'Missing/Incomplete/Invalid NDC Unit of Measure'),\n" +
                    "('N817', 2, 'Alert: Applicable laboratories are required to collect and report private payor data and report that data to CMS between January 1, 2020 - March 31, 2020.'),\n" +
                    "('N818', 2, 'Claims Dates of Service do not match Electronic Visit Verification System.'),\n" +
                    "('N819', 2, 'Patient not enrolled in Electronic Visit Verification System.'),\n" +
                    "('N820', 2, 'Electronic Visit Verification System units do not meet requirements of visit.'),\n" +
                    "('N821', 2, 'Electronic Visit Verification System visit not found.'),\n" +
                    "('N822', 2, 'Missing procedure modifier(s).'),\n" +
                    "('N823', 2, 'Incomplete/Invalid procedure modifier(s).'),\n" +
                    "('N824', 2, 'Electronic Visit Verification (EVV) data must be submitted through EVV Vendor.'),\n" +
                    "('N825', 2, 'Early intervention guidelines were not met.'),\n" +
                    "('N826', 2, 'Patient did not meet the inclusion criteria for the Medicare Shared Savings Program.'),\n" +
                    "('N827', 2, 'Missing/Incomplete/Invalid Federal Information Processing Standard (FIPS) Code.'),\n" +
                    "('N828', 2, 'Alert: Payment is suppressed due to a contracted funding.'),\n" +
                    "('N829', 2, 'Missing/incomplete/invalid Diagnostics Exchange Z-Code Identifier.'),\n" +
                    "('N830', 2, 'Alert: The charge[s] for this service was processed in accordance with Federal/ State, Balance Billing/ No Surprise Billing regulations. As such, any amount identified with OA, CO, or PI cannot be collected from the member and may be considered provider liability or be billable to a subsequent payer. Any amount the provider collected over the identified PR amount must be refunded to the patient within applicable Federal/State timeframes. Payment amounts are eligible for dispute pursuant to any Federal/State documented appeal/grievance process(es).'),\n" +
                    "('N831', 2, 'You have not responded to requests to revalidate your provider/supplier enrollment information.'),\n" +
                    "('N832', 2, 'Duplicate occurrence code/occurrence span code.'),\n" +
                    "('N833', 2, 'Patient share of cost waived.'),\n" +
                    "('N834', 2, 'Jurisdiction exempt from sales and health tax charges.'),\n" +
                    "('N835', 2, 'Unrelated Service/procedure/treatment is reduced. The balance of this charge is the patient''s responsibility.'),\n" +
                    "('N836', 2, 'Provider W9 or Payee Registration not on file.'),\n" +
                    "('N837', 2, 'Alert: Missing modifier was added.'),\n" +
                    "('N838', 2, 'Alert: Service/procedure postponed due to a federal, state, or local mandate/disaster declaration. Any amounts applied to deductible or member liability will be applied to the prior plan year from which the procedure was cancelled.'),\n" +
                    "('N839', 2, 'The procedure code was added/changed because the level of service exceeds the compensable condition(s).'),\n" +
                    "('N840', 2, 'Worker''s compensation claim filed with a different state.'),\n" +
                    "('N841', 2, 'Alert: North Dakota Administrative Rule 92-01-02-50.3.'),\n" +
                    "('N842', 2, 'Alert: Patient cannot be billed for charges.'),\n" +
                    "('N843', 2, 'Missing/incomplete/invalid Core-Based Statistical Area (CBSA) code.'),\n" +
                    "('N844', 2, 'This claim, or a portion of this claim, was processed in accordance with the Nebraska Legislative LB997 July 24, 2020 - Out of Network Emergency Medical Care Act.'),\n" +
                    "('N845', 2, 'Alert: Nebraska Legislative LB997 July 24, 2020 - Out of Network Emergency Medical Care Act.'),\n" +
                    "('N846', 2, 'National Drug Code (NDC) supplied does not correspond to the HCPCs/CPT billed.'),\n" +
                    "('N847', 2, 'National Drug Code (NDC) billed is obsolete.'),\n" +
                    "('N848', 2, 'National Drug Code (NDC) billed cannot be associated with a product.'),\n" +
                    "('N849', 2, 'Missing Tooth Clause: Tooth missing prior to the member effective date.'),\n" +
                    "('N850', 2, 'Missing/incomplete/invalid narrative explaining/describing this service/treatment.'),\n" +
                    "('N851', 2, 'Payment reduced because services were furnished by a therapy assistant.'),\n" +
                    "('N852', 2, 'The pay-to and rendering provider tax identification numbers (TINs) do not match'),\n" +
                    "('N853', 2, 'The number of modalities performed per session exceeds our acceptable maximum.'),\n" +
                    "('N854', 2, 'Alert: If you have primary other health insurance (OHI) coverage that has denied services, you must exhaust all appeal levels with your primary OHI before we can consider your claim for reimbursement.'),\n" +
                    "('N855', 2, 'This coverage is subject to the exclusive jurisdiction of ERISA (1974), U.S.C. SEC 1001.'),\n" +
                    "('N856', 2, 'This coverage is not subject to the exclusive jurisdiction of ERISA (1974), U.S.C. SEC 1001.'),\n" +
                    "('N857', 2, 'This claim has been adjusted/reversed. Refund any collected copayment to the member.'),\n" +
                    "('N858', 2, 'Alert: State regulations relating to an Out of Network Medical Emergency Care Act were applied to the processing of this claim. Payment amounts are eligible for dispute following the state''s documented appeal/ grievance/ arbitration process.'),\n" +
                    "('N859', 2, 'Alert: The Federal No Surprise Billing Act was applied to the processing of this claim. Payment amounts are eligible for dispute pursuant to any Federal documented appeal/ grievance/ dispute resolution process(es).'),\n" +
                    "('N860', 2, 'Alert: The Federal No Surprise Billing Act Qualified Payment Amount (QPA) was used to calculate the member cost share(s).'),\n" +
                    "('N861', 2, 'Alert: Mismatch between the submitted Patient Liability/Share of Cost and the amount on record for this recipient.'),\n" +
                    "('N862', 2, 'Alert: Member cost share is in compliance with the No Surprises Act, and is calculated using the lesser of the QPA or billed charge.'),\n" +
                    "('N863', 2, 'Alert: This claim is subject to the No Surprises Act (NSA). The amount paid is the final out-of-network rate and was calculated based on an All Payer Model Agreement, in accordance with the NSA.'),\n" +
                    "('N864', 2, 'Alert: This claim is subject to the No Surprises Act provisions that apply to emergency services.'),\n" +
                    "('N865', 2, 'Alert: This claim is subject to the No Surprises Act provisions that apply to nonemergency services furnished by nonparticipating providers during a patient visit to a participating facility.'),\n" +
                    "('N866', 2, 'Alert: This claim is subject to the No Surprises Act provisions that apply to services furnished by nonparticipating providers of air ambulance services.'),\n" +
                    "('N867', 2, 'Alert: Cost sharing was calculated based on a specified state law, in accordance with the No Surprises Act.'),\n" +
                    "('N868', 2, 'Alert: Cost sharing was calculated based on an All-Payer Model Agreement, in accordance with the No Surprises Act.'),\n" +
                    "('N869', 2, 'Alert: Cost sharing was calculated based on the qualifying payment amount, in accordance with the No Surprises Act.'),\n" +
                    "('N870', 2, 'Alert: In accordance with the No Surprises Act, cost sharing was based on the billed amount because the billed amount was lower than the qualifying payment amount.'),\n" +
                    "('N871', 2, 'Alert: This initial payment was calculated based on a specified state law, in accordance with the No Surprises Act.'),\n" +
                    "('N872', 2, 'Alert: This final payment was calculated based on a specified state law, in accordance with the No Surprises Act.'),\n" +
                    "('N873', 2, 'Alert: This final payment was calculated based on an All-Payer Model Agreement, in accordance with the No Surprises Act.'),\n" +
                    "('N874', 2, 'Alert: This final payment was determined through open negotiation, in accordance with the No Surprises Act.'),\n" +
                    "('N875', 2, 'Alert: This final payment equals the amount selected as the out-of-network rate by a Federal Independent Dispute Resolution Entity, in accordance with the No Surprises Act.'),\n" +
                    "('N876', 2, 'Alert: This item or service is covered under the plan. This is a notice of denial of payment provided in accordance with the No Surprises Act. The provider or facility may initiate open negotiation if they desire to negotiate a higher out-of-network rate than the amount paid by the patient in cost sharing.'),\n" +
                    "('N877', 2, 'Alert: This initial payment is provided in accordance with the No Surprises Act. The provider or facility may initiate open negotiation if they desire to negotiate a higher out-of-network rate.'),\n" +
                    "('N878', 2, 'Alert: The provider or facility specified that notice was provided and consent to balance bill obtained, but notice and consent was not provided and obtained in a manner consistent with applicable Federal law. Thus, cost sharing and the total amount paid have been calculated based on the requirements under the No Surprises Act, and balance billing is prohibited.'),\n" +
                    "('N879', 2, 'Alert: The notice and consent to balance bill, and to be charged out-of-network cost sharing, that was obtained from the patient with regard to the billed services, is not permitted for these services. Thus, cost sharing and the total amount paid have been calculated based on the requirements under the No Surprises Act, and balance billing is prohibited.');";
            SQLGeneral.executeUpdate(query);
    }

}
