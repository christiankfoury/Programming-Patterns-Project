package progpatproject;

import java.sql.*;

/**
 *
 * @author Christian
 */
public class SingleConnection {
    private static Connection clientsConnection;
    
    /**
     * Singleton for getting the connection
     * @return the connection of the database
     */
    public static Connection getInstance() {
        if (clientsConnection == null) {
            clientsConnection = createConnection();
        }
        return clientsConnection;
    }
    
    private static Connection createConnection(){
        String databaseUrl = "jdbc:sqlite:AirLineDB.db";
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseUrl);

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return connection;
    }    
}
