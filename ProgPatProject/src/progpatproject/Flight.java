package progpatproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Christian
 */
public class Flight {

    private Connection connection = SingleConnection.getInstance();
    private String flightN;
    private String name;
    private String origin;
    private String destination;
    private int duration;
    private int seats;
    private int availableSeats;
    private double amount;

    public Flight(String flightN, String name, String origin, String destination,
            int duration, int seats, int availableSeats, double amount) {
        this.flightN = flightN;
        this.name = name;
        this.origin = origin;
        this.destination = destination;
        this.duration = duration;
        this.seats = seats;
        this.availableSeats = availableSeats;
        this.amount = amount;
    }

    public boolean addFlight(Flight flight) {
        try {
            // Checking if flight already exists
            Statement statement = connection.createStatement();
            String queryTable = String.format("SELECT COUNT(*) FROM Flights WHERE "
                    + "FlightN = '%s';", flight.getFlightN());
            ResultSet resultSet = statement.executeQuery(queryTable);

            int count = -1;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            if (count == 1) {
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        // if flight does not already exist in the database insert a new row
        try {
            Statement statement = connection.createStatement();
            String insertInTable = String.format("INSERT INTO Flights (FlightN, Name, Origin,"
                    + "Dest, Duration, Seats, Available, Amount) "
                    + "VALUES ('%s', '%s', '%s', '%s', %d, %d, %d, %f);", flight.getFlightN(),
                    flight.getName(), flight.getOrigin(), flight.getDestination(),
                    flight.getDuration(), flight.getSeats(), flight.getAvailableSeats(), flight.getAmount());
            statement.executeUpdate(insertInTable);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }

    public boolean removeFlight(String flightNumber) {
        try {
            // Checking if flight does not exist
            Statement statement = connection.createStatement();
            String queryTable = String.format("SELECT COUNT(*) FROM Flights WHERE "
                    + "FlightN = '%s';", flightNumber);
            ResultSet resultSet = statement.executeQuery(queryTable);

            int count = -1;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            if (count == 0) {
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        // if if flight exists, remove it
        try {
            Statement statement = connection.createStatement();
            String deleteInTable = String.format("DELETE FROM Flights WHERE FlightN = '%s'", flightNumber);
            statement.executeUpdate(deleteInTable);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }

    public boolean updateFlightData(String flightNumber, String field, String newValue) {
        // SHOULD PUT THIS IN THE ACTUAL APPLICATION
//        ArrayList<String> fields = new ArrayList<>(Arrays.asList("flightn", "name", "origin", "dest",
//                "duration", "seats", "available", "amount"));
//
//        if (!fields.contains(field.toLowerCase())) {
//            return false;
//        }

        try {
            // check if flight does not exist
            Statement statement = connection.createStatement();
            String queryTable = String.format("SELECT COUNT(*) FROM Flights WHERE "
                    + "FlightN = '%s';", flightNumber);
            ResultSet resultSet = statement.executeQuery(queryTable);

            int count = -1;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            if (count == 0) {
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        try {
            Statement statement = connection.createStatement();
            String updateInTable;

            // depdending if the value is string or int/double, there should be
            // quotations surronding the value
            if (new ArrayList<>(Arrays.asList("origin", "dest")).contains(field.toLowerCase())) {
                updateInTable = String.format("UPDATE Flights"
                        + "SET '%s' = '%s'"
                        + "WHERE '%s' = '%s'", field, newValue, field, flightNumber);
            } // else duration
            else {
                updateInTable = String.format("UPDATE Flights"
                        + "SET '%s' = %s"
                        + "WHERE '%s' = '%s'", field, newValue, field, flightNumber);
            }
            statement.executeUpdate(updateInTable);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }

    public boolean issueTicket(String flight, Client client) {
        try {
            Statement statement = connection.createStatement();
            // checking if there is available and selecting amount if there is available
            // in one shot
            String queryTable = String.format("SELECT Available, Amount FROM Flights WHERE "
                    + "FlightN = '%s';", flight);
            ResultSet resultSet = statement.executeQuery(queryTable);

            int available = -1;
            double amountToPay = -1;
            while (resultSet.next()) {
                available = resultSet.getInt(1);
                amountToPay = resultSet.getInt(2);
            }
            if (available == 0) {
                return false;
            }

            statement = connection.createStatement();
            String updateTable = String.format("UPDATE TABLE"
                    + "SET Available = %d"
                    + "WHERE FlightN = '%s'", available - 1, flight);
            statement.executeUpdate(updateTable);

            statement = connection.createStatement();
            //                                                                                                                                  AUTO-INCREMENT
            java.util.Date date = new java.util.Date();

            String insertTable = String.format("INSERT INTO ReservedFlights (FlightN, PassNum, FlName, IssueDate, Contact, Amount)"
                    + "VALUES ('%s', '%s', '%s', '%s', '%s', '%f')", flight, client.getPassNumber(), client.getFullName(), date, client.getContact(), amountToPay);
            statement.executeUpdate(insertTable);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }

    public boolean cancelFlight(int ticket, int passNumber) {
        try {
            Statement statement = connection.createStatement();
            // check if ticket already exists
            String queryTable = String.format("SELECT COUNT(*) FROM ReservedFlights WHERE "
                    + "TicketN = %d AND PassNum = %d;", ticket, passNumber);
            ResultSet resultSet = statement.executeQuery(queryTable);

            int count = -1;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            // if the ticket does not exist
            if (count == 0) {
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        try {
            Statement statement = connection.createStatement();
            String updateInTable = String.format("DELETE FROM ReservedFlights WHERE "
                    + "TicketN = %d AND PassNum = %d;", ticket, passNumber);
            statement.executeUpdate(updateInTable);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }

    public static Map<String, String> viewBoard() {
        TreeMap<String, String> map = new TreeMap<>();
        Connection connection = SingleConnection.getInstance();
        try {
            Statement statement = connection.createStatement();
            String queryTable = String.format("SELECT * FROM Flights ORDER BY FlightN;");
            ResultSet resultSet = statement.executeQuery(queryTable);

            while (resultSet.next()) {
                map.put("FlightN: " + resultSet.getString("FlightN"), " Name: " + resultSet.getString("Name")
                        + ", Origin: " + resultSet.getString("Origin") + ", Dest: " + resultSet.getString("Dest")
                        + ", Duration: " + resultSet.getString("Duration") + ", Seats: " + resultSet.getString("Seats")
                        + ", Available: " + resultSet.getString("Available") + ", Amount: " + resultSet.getString("Amount"));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return map;
    }

    public static Map<String, String> viewBookedFlights() {
        TreeMap<String, String> map = new TreeMap<>();
        Connection connection = SingleConnection.getInstance();
        try {
            Statement statement = connection.createStatement();
            String queryTable = String.format("SELECT * FROM ReservedFlights ORDER BY FlightN;");
            ResultSet resultSet = statement.executeQuery(queryTable);

            while (resultSet.next()) {
                map.put("FlightN" + resultSet.getString("FlightN"), " TicketN" + resultSet.getString("TicketN")
                        + ", PassNum" + resultSet.getString("PassNum") + ", FLName" + resultSet.getString("FLName")
                        + ", IssueDate" + resultSet.getString("IssueDate") + ", Contact" + resultSet.getString("Contact")
                        + ", Amount" + resultSet.getString("Amount"));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return map;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getFlightN() {
        return flightN;
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getDuration() {
        return duration;
    }

    public int getSeats() {
        return seats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getAmount() {
        return amount;
    }
}
