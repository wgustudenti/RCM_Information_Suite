package sqldata;

import masterdata.SQLGeneral;
import model.Client;
import model.Payer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is not meant to be used directly, but to be a superclass for classes SQL driven databases
 * for specific vendors.
 *
 * The subclasses to this class interact with a database to retrieve information from the table specified
 * by the tableName variable (and the name of this specific .java file).
 */
public abstract class SQLPayer {

    /**
     * This variable represents the name of the database table from which this class will grab information.
     *
     * This is necessary to declare as it affects the methods in this class and its superclass.
     */
    private static final String tableName = "Payer";

    /**
     * This variable represents the name of the column containing the primary key for the database table represented
     * by the "String tableName" variable.
     */
    private static final String primaryKeyColName = "ID";

    /**
     * This method will create a table (the table and columns depend on the class
     * which implements this method
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        String query = "CREATE TABLE " + tableName + " (\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\tClientID INTEGER NOT NULL,\n" +
                "\tNameOnDocument VARCHAR[50] NOT NULL,\n" +
                "\tNameToEnter VARCHAR[50] NOT NULL,\n" +
                "\tFOREIGN KEY (ClientID) REFERENCES Client(ID)\n" +
                "\tON DELETE CASCADE \n" +
                "\tON UPDATE CASCADE\n" +
                ");";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method inserts a new row into the table.
     *
     * @param nameOnDocument
     * @param nameToEnter
     * @param clientID
     * @throws SQLException
     */
    public static void insert(String nameOnDocument, String nameToEnter, int clientID) throws SQLException {
        nameOnDocument = SQLGeneral.escapeApostrophes(nameOnDocument);
        nameToEnter = SQLGeneral.escapeApostrophes(nameToEnter);
        String query = "INSERT INTO " + tableName + " (NameOnDocument, NameToEnter, ClientID) VALUES ('"
                + nameOnDocument + "', '" + nameToEnter + "', " + clientID + ");";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method will insert a payer for all clients.
     * If this method is called and the payer combo already exists for a given client, this method will skip
     * that client.
     *
     * @param nameOnDocument
     * @param nameToEnter
     * @throws SQLException
     */
    public static void insertForAllClients(String nameOnDocument, String nameToEnter) throws SQLException{
        String query = "INSERT INTO " + tableName + " (NameOnDocument, NameToEnter, ClientID) VALUES ";
        for(Client client : Client.getNewMapOfAll().values()){
            Boolean alreadyInList = false;
            for(Payer payer : Payer.getNewMapOfAll().values()){
                if(payer.getNameOnDocument().equalsIgnoreCase(nameOnDocument)){
                    if(payer.getNameToEnter().equalsIgnoreCase(nameToEnter)){
                        if(payer.getClientID() == client.getPrimaryKey()){
                            alreadyInList = true;
                        }
                    }
                }
            }
            if(!alreadyInList) {
                nameOnDocument = SQLGeneral.escapeApostrophes(nameOnDocument);
                nameToEnter = SQLGeneral.escapeApostrophes(nameToEnter);
                query = query + "('" + nameOnDocument + "', '" + nameToEnter + "', " + client.getPrimaryKey() + "),";
            }
        }
        int indexOfComma = query.length() - 1;
        query = query.substring(0, indexOfComma);
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method will update payers with similar nameOnDocument attributes for all clients.
     *
     * @param oldNameOnDocument
     * @param updatedNameOnDocument
     * @param updatedNameToEnter
     * @throws SQLException
     */
    public static void updateForAllClients(String oldNameOnDocument, String updatedNameOnDocument, String updatedNameToEnter) throws SQLException {
        oldNameOnDocument = SQLGeneral.escapeApostrophes(oldNameOnDocument);
        updatedNameOnDocument = SQLGeneral.escapeApostrophes(updatedNameOnDocument);
        updatedNameToEnter = SQLGeneral.escapeApostrophes(updatedNameToEnter);
        String query = "UPDATE " + tableName + " SET NameOnDocument='" + updatedNameOnDocument + "', NameToEnter='"
                + updatedNameToEnter + "' WHERE NameOnDocument = " + oldNameOnDocument + ";";
        SQLGeneral.executeUpdate(query);
    }

    /**
     * This method updates a row in the table.
     *
     * @param nameOnDocument
     * @param nameToEnter
     * @param clientID
     * @param primaryKey
     * @throws SQLException
     */
    public static void update(String nameOnDocument, String nameToEnter, int clientID, int primaryKey) throws SQLException {
        nameOnDocument = SQLGeneral.escapeApostrophes(nameOnDocument);
        nameToEnter = SQLGeneral.escapeApostrophes(nameToEnter);
        String query = "UPDATE " + tableName + " SET NameOnDocument='" + nameOnDocument + "', NameToEnter='"
                + nameToEnter + "', ClientID=" + clientID + " WHERE " + primaryKeyColName + " = " + primaryKey + ";";
        SQLGeneral.executeUpdate(query);
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
        insert("Best Payer", "Good Payer", 1);
        insert("Regular Payer", "Normal Payer", 1);
        insert("Rich Payer", "Wealthy Payer", 1);
        insert("Poor Payer", "Indigent Payer", 2);
        insert("Okay Payer", "RunOfMill Payer", 2);
        insert("Best Payer", "Extraordinary Payer", 2);
        insert("Same Old Payer", "Ancient Payer", 3);
        insert("Redacted Payer", "CIA", 3);
    }
}
