package progpatproject;

import java.sql.*;

/**
 *
 * @author Christian
 */
public class SingleConnection {
    private static Connection clientsConnection;
    
    public static Connection getInstance() {
        if (clientsConnection == null) {
            clientsConnection = createConnection();
        }
        return clientsConnection;
    }
    
    private static Connection createConnection(){
        String databaseUrl = "jdbc:sqlite:C:\\SQlite\\db\\Project\\AirLine.db";
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
