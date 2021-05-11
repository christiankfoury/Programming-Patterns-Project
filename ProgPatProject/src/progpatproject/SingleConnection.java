package progpatproject;

import java.sql.*;

/**
 *
 * @author Christian
 */
public class SingleConnection {
    private static Connection clientsConnection;
    private static Connection flightsConnection;
    private static Connection reservedFlightsConnection;
    
    public static Connection getClientsInstance() {
        if (clientsConnection == null) {
            clientsConnection = createConnection("Clients.db");
        }
        return clientsConnection;
    }
    
    public static Connection getFlightsInstance() {
        if (flightsConnection == null) {
            flightsConnection = createConnection("Flights.db");
        }
        return flightsConnection;
    }
    
    public static Connection getReservedFlightsInstance() {
        if (reservedFlightsConnection == null) {
            reservedFlightsConnection = createConnection("ReservedFlights.db");
        }
        return reservedFlightsConnection;
    }
    
    private static Connection createConnection(String databaseName){
        String databaseUrl = "jdbc:sqlite:C:\\SQlite\\db\\Project\\" + databaseName;
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseUrl);
            System.out.println("Opened database successfully");

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return connection;
    }    
}
