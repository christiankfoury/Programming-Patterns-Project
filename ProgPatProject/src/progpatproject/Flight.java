package progpatproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Christian
 */
public class Flight {

    private Connection connection = SingleConnection.getFlightsInstance();
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
            Statement statement = connection.createStatement();
            String queryTable = String.format("SELECT COUNT(*) FROM Flights WHERE "
                    + "FlightN = '%s;", flight.getFlightN());
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
        try {
            Statement statement = connection.createStatement();
            String insertInTable = String.format("INSERT INTO FLIGHTS (FlightsN, Name, Origin,"
                    + "Destination, Duration, Seats, Available, Amount)"
                    + "VALUES ('%s', '%s', '%s', '%s', %d, %d, %d, %f);", flight.getFlightN(),
                    flight.getName(), flight.getOrigin(), flight.getDestination(),
                    flight.getDuration(), flight.getSeats(), flight.getAvailableSeats(), flight.getAmount());
            statement.executeUpdate(insertInTable);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return false;
    }

    public boolean removeFlight(String flightNumber) {
        try {
            Statement statement = connection.createStatement();
            String queryTable = String.format("SELECT COUNT(*) FROM Flights WHERE "
                    + "FlightN = '%s;", flightNumber);
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
            String deleteInTable = String.format("DELETE FROM Flights WHERE FligtsN = %s", flightNumber);
            statement.executeUpdate(deleteInTable);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return false;
    }

    public boolean updateFlightData(String flightNumber, String field, String newValue) {
        ArrayList<String> fields = new ArrayList<>(Arrays.asList("flightn", "name", "origin", "destination", 
            "duration", "seats", "available", "amount"));
        
        if (!fields.contains(field.toLowerCase())) {
            return false;
        }
        
        try {
            Statement statement = connection.createStatement();
            String queryTable = String.format("SELECT COUNT(*) FROM Flights WHERE "
                    + "FlightN = '%s;", flightNumber);
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
            String updateInTable = String.format("UPDATE Flights"
                    + "SET '%s' = '%s'"
                    + "WHERE '%s' = '%s'", field, newValue, field, flightNumber);
            statement.executeUpdate(updateInTable);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return false;
    }
    
    public boolean issueTicket(String flight, Client client) {
        try {
            Statement statement = connection.createStatement();
            String queryTable = String.format("SELECT Available, Amount FROM Flights WHERE "
                    + "FlightN = '%s;", flight);
            ResultSet resultSet = statement.executeQuery(queryTable);

            int available = -1;
            double amount = -1;
            while (resultSet.next()) {
                available = resultSet.getInt(1);
                amount = resultSet.getInt(2);
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
                    + "VALUES ('%s', '%s', '%s', '%t', '%s', '%d')", flight, client.getPassNumber(), client.getFullName(), date, client.getContact(), amount);
            statement.executeUpdate(insertTable);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }
    
//    public boolean cancelFlight(int ticket, int passNumber) {
//        
//    }
//    public static Map<String, String> viewBoard() {
//        
//    }
//    public static Map<String, String> viewBookedFlights(){
//        
//    }
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
