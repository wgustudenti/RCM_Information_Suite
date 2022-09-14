package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sqldata.SQLRemarkCode;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Subclass used to populate tables with the variables which are unique to these objects
 * (as opposed to their superclass).
 */
public class RemarkCodeMostUsedWith extends RemarkCode{

    /**
     * int representing the number of times this remark code was used with another, primary remarkcode.
     */
    int timesUsedWithPrimaryCode;

    /**
     * Constructor for this class
     * @param remarkCode
     * @param description
     * @param notes
     * @param carcRarcID
     * @param totalTimesUsed
     * @param timesUsedWithRemarkCode
     * @param timesUsedWithPrimaryCode
     */
    public RemarkCodeMostUsedWith(String remarkCode, String description, String notes, int carcRarcID, int totalTimesUsed,
                                  HashMap<String, Integer> timesUsedWithRemarkCode, int timesUsedWithPrimaryCode) {
        super(remarkCode, description, notes, carcRarcID, totalTimesUsed, timesUsedWithRemarkCode);
        this.timesUsedWithPrimaryCode = timesUsedWithPrimaryCode;
    }

    public int getTimesUsedWithPrimaryCode() {
        return timesUsedWithPrimaryCode;
    }

    public void setTimesUsedWithPrimaryCode(int timesUsedWithPrimaryCode) {
        this.timesUsedWithPrimaryCode = timesUsedWithPrimaryCode;
    }

    /**
     * This method returns a list of RemarkCodesMostUsed that have been used with the primaryRemarkCode parameter.
     * @param primaryRemarkCode
     * @return ObservableList of RemarkCodesMostUsed that have been used with the primaryRemarkCode parameter
     * @throws SQLException
     */
    public static ObservableList<RemarkCodeMostUsedWith> getRemarkCodesUsedWithPrimary(RemarkCode primaryRemarkCode,
                                                                                       HashMap<String, RemarkCode> mapToSearch) {
        ObservableList<RemarkCodeMostUsedWith> remarkCodesUsedWithPrimary = FXCollections.observableArrayList();
        for(RemarkCode remarkCode : mapToSearch.values()){
            Integer timesUsedWithPrimary = primaryRemarkCode.getTimesUsedWithRemarkCodeMap().get(remarkCode.getRemarkCode());
            if(timesUsedWithPrimary != null && timesUsedWithPrimary != 0){
                String remarkCodeID = remarkCode.getRemarkCode();
                String description = remarkCode.getDescription();
                String notes = remarkCode.getNotes();
                int carcRarcID = remarkCode.getCarcRarcID();
                int totalTimesUsed = remarkCode.getTotalTimesUsed();
                HashMap<String, Integer> timesUsedWithRemarkCodeMap = remarkCode.getTimesUsedWithRemarkCodeMap();
                RemarkCodeMostUsedWith remarkCodeMostUsedWith = new RemarkCodeMostUsedWith(remarkCodeID, description,
                        notes, carcRarcID, totalTimesUsed, timesUsedWithRemarkCodeMap, timesUsedWithPrimary);

                remarkCodesUsedWithPrimary.add(remarkCodeMostUsedWith);
            }
        }
        return  remarkCodesUsedWithPrimary;
    }
}
