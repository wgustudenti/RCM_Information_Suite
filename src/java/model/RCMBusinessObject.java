package model;

import sqldata.SQLAdminLog;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This Interface backs all RCM Business Objects.
 */
public interface RCMBusinessObject {

    /**
     * Inserts an object into the database and creates a log of the action.
     * @throws SQLException
     */
    public void insert() throws SQLException;

    /**
     * Updates an object in the database and creates a log of the action.
     * @throws SQLException
     */
    public void update() throws SQLException;

    /**
     * Deletes an object in the database and creates a log of the action.
     * @throws SQLException
     */
    public void delete() throws SQLException;

    /**
     * Takes a LocalDateTime which represents a UTC time and converts the timezone to the User's timezone.
     * @return Timestamp for converted time.
     */
    public static Timestamp convertUTCtoUserTime(Timestamp utcTime){
        LocalDateTime utcLocalDateTime = utcTime.toLocalDateTime();
        ZonedDateTime utcTimeZonedDateTime = utcLocalDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime convertedTimeZoneDateTime = ZonedDateTime.ofInstant(Instant.from(utcTimeZonedDateTime), ZoneId.systemDefault());
        LocalDateTime convertedTimeLocalDateTime = convertedTimeZoneDateTime.toLocalDateTime();
        Timestamp convertedTime = Timestamp.valueOf(convertedTimeLocalDateTime);

        return convertedTime;
    }
}