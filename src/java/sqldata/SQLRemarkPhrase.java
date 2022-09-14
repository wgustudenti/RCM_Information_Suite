package sqldata;

import masterdata.SQLGeneral;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import static sqldata.SQLRemarkCode.getJustRemarkCodes;
import static sqldata.SQLRemarkCode.getNewJustRemarkCodes;

/**
 * This class is not meant to be used directly, but to be a superclass for classes SQL driven databases
 * for specific vendors.
 *
 * The subclasses to this class interact with a database to retrieve information from the table specified
 * by the tableName variable (and the name of this specific .java file).
 */
public abstract class SQLRemarkPhrase {

    /**
     * This variable represents the name of the database table from which this class will grab information.
     *
     * This is necessary to declare as it affects the methods in this class and its superclass.
     */
    private static final String tableName = "RemarkPhrase";

    /**
     * This variable represents the name of the column containing the primary key for the database table represented
     * by the "String tableName" variable.
     */
    private static final String primaryKeyColName = "ID";


    /**
     * This method inserts a new row into the table.
     *
     * @param phrase
     * @throws SQLException
     */
    public static void insert(String phrase) throws SQLException {
        phrase = SQLGeneral.escapeApostrophes(phrase);
        String query = "INSERT INTO " + tableName + " (Phrase) VALUES ('" + phrase + "');";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates a row in the table.
     *
     * @param phrase
     * @param primaryKey
     * @throws SQLException
     */
    public static void update(String phrase, int primaryKey) throws SQLException {
        phrase = SQLGeneral.escapeApostrophes(phrase);
        String query = "UPDATE " + tableName + " SET Phrase='" + phrase + "' WHERE "
                + primaryKeyColName + " = " + primaryKey + ";";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method takes the parameters and updates the use information in the RemarkPhrase table.
     * If no "numberToAdd" (third parameter) is inserted, the method will add increment the use data by one.
     * @param primaryKey
     * @param remarkCode
     * @throws SQLException
     */
    public static void updateRemarkPhraseUseData(String remarkCode, int primaryKey) throws SQLException {
        int numberToAdd = 1;
        remarkCode = SQLGeneral.escapeApostrophes(remarkCode);
        String remarkCodeColName = 'R' + remarkCode;
        String query = "UPDATE " + tableName + " SET " + remarkCodeColName + "=" + remarkCodeColName + " + " + numberToAdd
                + " WHERE " + primaryKeyColName + " = " + primaryKey + ";";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates the "most commonly used with" information in the database.
     * @param remarkCode
     * @param numberToAdd
     * @param primaryKey
     * @throws SQLException
     */
    public static void updateRemarkPhraseUseData(String remarkCode, int numberToAdd, int primaryKey) throws SQLException {
        remarkCode = SQLGeneral.escapeApostrophes(remarkCode);
        String columnName = 'R' + remarkCode;
        String query = "UPDATE " + tableName + " SET " + columnName + "=" + columnName + " + " + numberToAdd
                + " WHERE " + primaryKeyColName + " = " + primaryKey + ";";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates a single row in the database which matches the search criteria and has the least number of
     * letters.  If more than one is found, the query affects the row with the lower primary key.
     *
     * If no rows are affected, a new row is inserted with the search phrase and the update query is rerun.
     *
     * @param searchPhrase
     * @param remarkCodeString
     * @throws SQLException
     */
    public static void updateRemarkPhraseUseDataFromSearch(String searchPhrase, String remarkCodeString) throws SQLException {
        String remarkCodeColumnName = 'R' + remarkCodeString;
        String query = "WITH ShortestMatchingPhrases AS (SELECT " + primaryKeyColName + ", MIN(LENGTH(Phrase)) FROM "
                + tableName + " WHERE Phrase LIKE '%" + searchPhrase + "%')\n" +
                "UPDATE " + tableName + " SET " + remarkCodeColumnName + "=" + remarkCodeColumnName + " + 1 " +
                "WHERE ID = (SELECT MIN(" + primaryKeyColName + ") FROM ShortestMatchingPhrases)";
        int rowsMatched = SQLGeneral.executeUpdate(query);
        if(rowsMatched == 0){
            insert(searchPhrase);
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
     * @param primaryKey
     * @throws SQLException
     */
    public static void delete(int primaryKey) throws SQLException {
        SQLGeneral.deleteFromTable(tableName, primaryKey, primaryKeyColName);
    }

    /**
     * This method inserts test data into the database for this table.
     * @throws SQLException
     */
    public static void insertTestData() throws SQLException {
        insert("Insert");
        insert("Update");
        insert("Delete");
    }

    /**
     * This method alters the RemarkPhrase table by adding a column to it.
     * This is done when a new remark code is added to the database for the phrase-used-with data.
     * @param remarkCode
     * @throws SQLException
     */
    public static void addRemarkPhraseColumn(String remarkCode) throws SQLException {
        String columnName = 'R' + remarkCode;
        String query = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " INTEGER DEFAULT 0;";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method alters the RemarkPhrase table by removing a column from it.
     * This is done when a new remark code is added to the database for the phrase-used-with data.
     * @param remarkCode
     * @throws SQLException
     */
    public static void removeRemarkPhraseColumn(String remarkCode) throws SQLException {
        String columnName = 'R' + remarkCode;
        String query = "ALTER TABLE " + tableName + " DROP COLUMN " + columnName + ";";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method inserts random use data into the RemarkPhrase table in the database.
     *
     * Could've technically been faster but this method won't be used enough to make it worth the coding.
     * Never used by normal users.
     *
     * @throws SQLException
     */
    public static void insertRandomRemarkUseData() throws SQLException {
        ResultSet wholeTable = getAll();
        ArrayList<String> justRemarkCodes = getNewJustRemarkCodes();
        int numberOfRemarkCodes = justRemarkCodes.size();
        Random random = new Random();
        while (wholeTable.next()){
            int phraseRow = wholeTable.getInt("ID");
            int numberOfColumnsToUpdate = random.nextInt(12);
            for(int i = 0; i < numberOfColumnsToUpdate; i++){
                int randomIndex = random.nextInt(numberOfRemarkCodes);
                int randomNumberToAdd = random.nextInt(25);
                String randomRemarkCode = justRemarkCodes.get(randomIndex);
                updateRemarkPhraseUseData(randomRemarkCode, randomNumberToAdd, phraseRow);
            }
        }
    }

    /**
     * This method returns the row for a given remark phrase from the RemarkPhrase table.
     * @param remarkPhrase
     * @return ResultSet containing the row info for a given RemarkPhrase.
     * @throws SQLException
     */
    public static ResultSet getSingleRowInfo(String remarkPhrase) throws SQLException {
        String query = "SELECT *, MIN(LENGTH(Phrase)) FROM " + tableName +  " WHERE Phrase LIKE '"
                + remarkPhrase + "';";
        ResultSet singleRowInfo = SQLGeneral.executeQuery(query);
        return singleRowInfo;
    }

    /**
     * This method will create a table (the table and columns depend on the class
     * which implements this method
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        String query = "CREATE TABLE " + tableName + " (\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\tPhrase varchar[500] NOT NULL,\n" +
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
                "\tRN879 INTEGER DEFAULT 0\n" +
                ");";
        SQLGeneral.executeUpdate(query);
    }

}
