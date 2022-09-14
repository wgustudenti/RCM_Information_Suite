package model;

import databasevendor.VendorPayerSuggestion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;

/**
 * This class deals with creating and manipulating Payer Business Objects
 * Payer business objects represent actions that an administrator can take which can be
 * logged by the system.
 */
public class PayerSuggestion extends Payer implements RCMBusinessObject {

    /**
     * Represents the corresponding attribute for the table which corresponds to this class in the database.
     */
    private Timestamp actionTime;

    /**
     * HashMap of all PayerSuggestions
     */
    private static HashMap<Integer, PayerSuggestion> mapOfAllSuggestions;

    /**
     * Constructor for this class
     * @param nameOnDocument
     * @param nameToEnter
     * @param clientID
     * @param actionTime
     * @param primaryKey
     */
    public PayerSuggestion(String nameOnDocument, String nameToEnter, int clientID, Timestamp actionTime, int primaryKey){
        super(nameOnDocument, nameToEnter, clientID, primaryKey);
        this.actionTime = actionTime;
    }

    /**
     * Constructor for this class
     * @param nameOnDocument
     * @param nameToEnter
     * @param clientID
     */
    public PayerSuggestion(String nameOnDocument, String nameToEnter, int clientID){
        super(nameOnDocument, nameToEnter, clientID, 0);
        this.actionTime = Timestamp.valueOf(LocalDateTime.now());
    }

    public Timestamp getActionTime() {
        return actionTime;
    }

    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }

    public static HashMap<Integer, PayerSuggestion> getMapOfAllSuggestions() {
        return mapOfAllSuggestions;
    }

    public static void setMapOfAllSuggestions(HashMap<Integer, PayerSuggestion> mapOfAll) {
        PayerSuggestion.mapOfAllSuggestions = mapOfAll;
    }

    /**
     * This method returns an alphabetically ordered list of all payerSuggestions.
     * @return ObservableList<PayerSuggestion></PayerSuggestion> alphabetically ordered list of all payerSuggestions.
     */
    public static ObservableList<PayerSuggestion> getObservableListOfAllSuggestions(){
        ObservableList<PayerSuggestion> listOfPayerSuggestions = FXCollections.observableArrayList();
        for(PayerSuggestion payerSuggestion : mapOfAllSuggestions.values()){
            listOfPayerSuggestions.add(payerSuggestion);
        }
        listOfPayerSuggestions.sort(Comparator.comparing(PayerSuggestion::getActionTime).reversed());
        return listOfPayerSuggestions;
    }

    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void insert() throws SQLException {
        VendorPayerSuggestion.insert(this.getNameOnDocument(), this.getNameOnDocument(), this.getClientID());
    }

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void update() throws SQLException {
        VendorPayerSuggestion.update(this.getNameOnDocument(), this.getNameToEnter(), this.getClientID(), this.getPrimaryKey());
    }

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        VendorPayerSuggestion.delete(this.getPrimaryKey());
    }

    /**
     * Interacts with database, takes the result set, and creates a HashMap of objects representing that ResultSet.
     * @return a HashMap with objects corresponding to the class within which this method is implemented.
     * @throws SQLException
     */
    public static HashMap<Integer, PayerSuggestion> getNewMapOfAllSuggestions() throws SQLException {
        ResultSet wholeTable = VendorPayerSuggestion.getAll();
        HashMap<Integer, PayerSuggestion> allPayers = new HashMap<>();
        while (wholeTable.next()){
            int primaryKey = wholeTable.getInt("ID");
            int clientID = wholeTable.getInt("ClientID");
            String nameOnDocument = wholeTable.getString("NameOnDocument");
            String nameToEnter = wholeTable.getString("NameToEnter");
            Timestamp utcActionTime = wholeTable.getTimestamp("ActionTime");
            Timestamp userActionTime = RCMBusinessObject.convertUTCtoUserTime(utcActionTime);
            PayerSuggestion payerSuggestionToAdd = new PayerSuggestion(nameOnDocument, nameToEnter, clientID, userActionTime, primaryKey);
            allPayers.put(primaryKey, payerSuggestionToAdd);
        }
        return allPayers;
    }

    /**
     * This method initializes the mapOfall variable for this class.
     * @throws SQLException
     */
    public static void initilizeStaticVariables() throws SQLException {
        mapOfAllSuggestions = getNewMapOfAllSuggestions();
    }
}
