package progpatproject;

import java.sql.*;
/**
 *
 * @author Christian
 */
public class FlightsView {
    private Connection connection;

    public FlightsView(Connection connection) {
        this.connection = connection;
    }
    
    public void printFlightsDetails() {
        try {
            Statement statement = connection.createStatement();
            String queryTable = "select * from Flights;";
            ResultSet resultSet = statement.executeQuery(queryTable);

            while (resultSet.next()) {
                System.out.println("FLIGHTSN = " + resultSet.getString("FlightsN"));
                System.out.println("NAME = " + resultSet.getString("Name"));
                System.out.println("ORIGIN = " + resultSet.getString("Origin"));
                System.out.println("DESTINATION = " + resultSet.getString("Destination"));
                System.out.println("DURATION = " + resultSet.getString("Duration"));
                System.out.println("SEATS = " + resultSet.getString("Seats"));
                System.out.println("AVAILABLE SEATS = " + resultSet.getString("AvailableSeats"));
                System.out.println("AMOUNT = " + resultSet.getString("Amount" + "\n"));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
