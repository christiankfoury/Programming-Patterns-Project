package progpatproject;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Christian
 */
public class Client {

    private Connection connection = SingleConnection.getInstance();
    private String fullName;
    private int passNumber;
    private String contact;

    /**
     * Constructor for client
     * @param fullName the full name
     * @param passNumber the passport number
     * @param contact the contact 
     */
    public Client(String fullName, int passNumber, String contact) {
        this.fullName = fullName;
        this.passNumber = passNumber;
        this.contact = contact;
    }

    /**
     * To book a seat. Adds a row in the ReservedFlights table if the the Flight
     * exist in the Flights table and if the flights have more than 0 available seats
     * @param flightNumber the flight number to be booked
     * @return true if a row has been inserted according to the mentioned constraints
     */
    public boolean bookASeat(String flightNumber) {
        try {
            String flNb = "'" + flightNumber + "'";
            Statement stmt = connection.createStatement();
            String getFlight = "SELECT COUNT(*) FROM Flights WHERE flightN = " + flNb + ";";
            ResultSet rs = stmt.executeQuery(getFlight);
            int count = -1;
            while (rs.next()) {
                count = rs.getInt(1);
            }
            
            if (count == 0) {
                return false;
            }
                        
            stmt = connection.createStatement();
            Flight flight = null;
            getFlight = "SELECT * FROM Flights WHERE flightN = " + flNb + ";";
            rs = stmt.executeQuery(getFlight);
            while (rs.next()) {
                String name = rs.getString("Name");
                String origin = rs.getString("Origin");
                String dest = rs.getString("Dest");
                int duration = rs.getInt("Duration");
                int totalSeats = rs.getInt("Seats");
                int availSeats = rs.getInt("Available");
                double price = rs.getDouble("Amount");

                flight = new Flight(flightNumber, name, origin, dest, duration, totalSeats, availSeats, price);
            }
            try {
                if (flight.getAvailableSeats() <= 0) {
                    return false;
                } else {
                    stmt = connection.createStatement();
                    int availSeats = flight.getAvailableSeats() - 1;
                    String remove1FromAvailSeats = "UPDATE Flights SET Available = " + availSeats + " WHERE flightN = " + flNb + ";";
                    stmt.executeUpdate(remove1FromAvailSeats);

                    stmt = connection.createStatement();
                    String getAllTicketNumbers = "SELECT * FROM ReservedFlights ORDER BY TicketN;";
                    List<Integer> list = new ArrayList<>();
                    ResultSet resultSet = stmt.executeQuery(getAllTicketNumbers);
                    while (resultSet.next()) {
                        list.add(resultSet.getInt("TicketN"));
                    }

                    int ticketN = 1;

                    if (!list.isEmpty()) {
                        ticketN = list.get(list.get(list.size() - 1)) + 1;
                    }

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-mm-dd");
                    LocalDateTime now = LocalDateTime.now();

                    String addEntry = String.format("INSERT INTO ReservedFlights VALUES('%d','%s',%d,'%s',"
                            + "'%s','%s',%f);", ticketN, flightNumber, getPassNumber(), getFullName(),
                            "" + dtf.format(now), getContact(), flight.getAmount());
                    stmt.executeUpdate(addEntry);
                    return true;
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e + " , Inserting in ReservedFlights Table.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e + " Seat could not be added.");
            return false;
        }
    }

    /**
     * To cancel a reservation (or remove a row from the reservedFlights table).
     * A row is removed if a row in the ReservedFlights table has a row with the ticket and corresponding
     * passport number
     * @param ticket the ticket number
     * @return true if a row has been removed
     */
    public boolean cancelReservation(int ticket) {
        try {
            Statement stmt = connection.createStatement();

            String getReservedFlight = String.format("SELECT COUNT(*) FROM ReservedFlights WHERE ticketN = %d AND PassNum = %d;", ticket, passNumber);
            ResultSet resultSet = stmt.executeQuery(getReservedFlight);

            int count = -1;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            // if the ticket does not exist
            if (count == 0) {
                return false;
            }

            stmt = connection.createStatement();

            getReservedFlight = String.format("SELECT FlightN FROM ReservedFlights WHERE ticketN = %d AND PassNum = %d;", ticket, passNumber);
            resultSet = stmt.executeQuery(getReservedFlight);

            String flightNumber = null;

            while (resultSet.next()) {
                flightNumber = resultSet.getString("FlightN");
            }
            stmt = connection.createStatement();
            String removeReservedFlight = String.format("DELETE FROM ReservedFlights WHERE ticketN = %d AND PassNum = %d;", ticket, passNumber);
            stmt.executeUpdate(removeReservedFlight);

            stmt = connection.createStatement();
            String getCurrentFlightAvail = String.format("SELECT Available FROM Flights WHERE FlightN = '%s';", flightNumber);
            resultSet = stmt.executeQuery(getCurrentFlightAvail);
            int currentAvail = 0;
            while (resultSet.next()) {
                currentAvail = resultSet.getInt("Available");
            }
            currentAvail++;

            stmt = connection.createStatement();
            String add1Availability = "UPDATE Flights SET Available = "
                    + "" + currentAvail + " WHERE flightN = " + "'" + flightNumber + "';";
            stmt.executeUpdate(add1Availability);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return true;
    }

    /**
     * To get all flights in a list with a corresponding destination
     * @param destination the destination to be searched
     * @return a list containing all the flights with the destination
     */
    public List<Flight> searchFlightByDestination(String destination) {
        try {
            Statement stmt = connection.createStatement();
            String destinationString = "'" + destination + "'";
            String getFlight = "SELECT * FROM Flights WHERE Dest = " + destinationString + " ORDER BY flightN;";
            Flight flight = null;
            List<Flight> list = new ArrayList<>();

            ResultSet rs = stmt.executeQuery(getFlight);
            while (rs.next()) {
                String number = rs.getString("flightN");
                String name = rs.getString("Name");
                String origin = rs.getString("Origin");
                int duration = rs.getInt("Duration");
                int totalSeats = rs.getInt("Seats");
                int availSeats = rs.getInt("Available");
                double price = rs.getDouble("Amount");

                flight = new Flight(number, name, origin, destination, duration, totalSeats, availSeats, price);
                list.add(flight);
            }
            return list;
        } catch (SQLException e) {
            System.err.println("Error: " + e + " , Flights could not be pulled.");
            return null;
        }
    }

    /**
     * To get all flights in a list with a corresponding duration
     * @param duration the duration to be searched
     * @return a list containing all the flights with the duration
     */
    public List<Flight> searchFlightByDuration(int duration) {
        try {
            Statement stmt = connection.createStatement();
            String getFlight = "SELECT * FROM Flights WHERE duration = " + duration + " ORDER BY flightN;";
            Flight flight = null;
            List<Flight> list = new ArrayList<>();

            ResultSet rs = stmt.executeQuery(getFlight);
            while (rs.next()) {
                String number = rs.getString("flightN");
                String name = rs.getString("Name");
                String origin = rs.getString("Origin");
                String destination = rs.getString("Dest");
                int totalSeats = rs.getInt("Seats");
                int availSeats = rs.getInt("Available");
                double price = rs.getDouble("Amount");

                flight = new Flight(number, name, origin, destination, duration, totalSeats, availSeats, price);
                list.add(flight);
            }
            return list;
        } catch (SQLException e) {
            System.err.println("Error: " + e + " , Flights could not be pulled.");
            return null;
        }
    }

    /**
     * To get all flights in a list with a corresponding origin
     * @param origin the origin to be searched
     * @return a list containing all the flights with the origin
     */
    public List<Flight> searchFlightByOrigin(String origin) {
        try {
            Statement stmt = connection.createStatement();
            String flightOrigin = "'" + origin + "'";
            String getFlight = "SELECT * FROM Flights WHERE Origin = " + flightOrigin + " ORDER BY flightN;";
            Flight flight = null;
            List<Flight> list = new ArrayList<>();

            ResultSet rs = stmt.executeQuery(getFlight);
            while (rs.next()) {
                String number = rs.getString("flightN");
                String name = rs.getString("Name");
                int duration = rs.getInt("Duration");
                String destination = rs.getString("Dest");
                int totalSeats = rs.getInt("Seats");
                int availSeats = rs.getInt("Available");
                double price = rs.getDouble("Amount");

                flight = new Flight(number, name, origin, destination, duration, totalSeats, availSeats, price);
                list.add(flight);
            }
            return list;
        } catch (SQLException e) {
            System.err.println("Error: " + e + " , Flights could not be pulled.");
            return null;
        }
    }

    /**
     * Displays only flights with available seats.
     * @return a map of flights with available seats
     */
    public Map<String, String> viewFlightBoard() {
        try {
            Locale locale = InputOutputUser.locale;
            if (locale == null) {
                locale = new Locale("en", "CA");
            }
            ResourceBundle res = InputOutputUser.res;
            if (res == null) {
                res = ResourceBundle.getBundle("progpatproject/OutputBundle", locale);
            }
            Statement stmt = connection.createStatement();
            String getFlight = "SELECT * FROM Flights WHERE Available > 0 ORDER BY flightN;";
            TreeMap<String, String> map = new TreeMap();

            ResultSet rs = stmt.executeQuery(getFlight);
            while (rs.next()) {
                String number = rs.getString("flightN");
                String name = rs.getString("Name");
                String origin = rs.getString("Origin");
                int duration = rs.getInt("Duration");
                String destination = rs.getString("Dest");
                int totalSeats = rs.getInt("Seats");
                int availSeats = rs.getInt("Available");
                double price = rs.getDouble("Amount");

                map.put(res.getString("flightN") + " " + number, String.format("{" + res.getString("name") + "  %s, " + res.getString("origin") + " %s, " + res.getString("dest") + " %s,"
                        + " " + res.getString("duration") + " %d, " + res.getString("seats") + " %d, " + res.getString("available") + " %d, " + res.getString("amount") + " %f}",
                        name, origin, destination, duration, totalSeats, availSeats, price));
            }
            return map;
        } catch (SQLException e) {
            System.err.println("Error: " + e + " , Flights board could not be pulled.");
            return null;
        }

    }

    /**
     * Get full name 
     * @return full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Get passport number
     * @return passport number
     */
    public int getPassNumber() {
        return passNumber;
    }

    /**
     * Get contact 
     * @return contact
     */
    public String getContact() {
        return contact;
    }

}
