package embeddeddata;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class EmbeddedConnection {
    /**Database Protocol
     *
     */
    private static final String protocol = "jdbc";
    /**
     * Database vendor
     */
    private static final String vendor = ":sqlite:";
    /**
     * Database location
     */
    private static final String location = "C:/Databases/InformationSuiteDB/InformationSuiteTestDB";
    /**
     * Database URl
     */
    private static final String jdbcUrl = protocol + vendor + location;
    /**Connection object
     *
     */
    public static Connection connection;

    /**Opens the connections to the database
     *
     */

    public static void openConnection()
    {
        try {
            connection = DriverManager.getConnection(jdbcUrl);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            //System.out.println("Error:" + e.getMessage());
        }
    }

    /**Returns a connection object
     *
     * @return connection object
     */
    public static Connection getConnection(){
        return connection;
    }

    /**
     * Closes the connection
     */
    public static void closeConnection() {
        try {
            connection.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //System.out.println("Error:" + e.getMessage());
        }
    }
}

