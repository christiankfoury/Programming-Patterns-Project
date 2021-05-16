package progpatproject;

import java.sql.*;
import java.util.*;

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

    /**
     * Constructor for flight
     * @param flightN the flights number
     * @param name the name of the flight
     * @param origin the origin of the flight
     * @param destination the destination of the flight
     * @param duration the duration of the flight
     * @param seats the number of seats of the flight
     * @param availableSeats the number of available of the flight
     * @param amount the cost of the flight
     */
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

    /**
     * To add a row into the Flights table. A row is inserted if there is not a
     * flight with the same flight number
     * @param flight the flight to be inserted
     * @return true if a row has been inserted
     */
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

    /**
     * To remove a flight row from the Flights table. A row is deleted if there is
     * an instance of that flight
     * @param flightNumber the flight number of the flight to be removed
     * @return true if a row has been removed
     */
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

    /**
     * The update the data of a flight in the flights table. Returns true if there is
     * an instance of this flights and the row has been updated
     * @param flightNumber the flight number of the flight to be updated
     * @param field the field of the flight
     * @param newValue the new value to be updated
     * @return true Returns true if there is
     * an instance of this flights and the row has been updated
     */
    public boolean updateFlightData(String flightNumber, String field, String newValue) {
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
                updateInTable = String.format("UPDATE Flights "
                        + "SET '%s' = '%s' "
                        + "WHERE FlightN = '%s';", field, newValue, flightNumber);
            } // else duration
            else {
                updateInTable = String.format("UPDATE Flights "
                        + "SET '%s' = %s "
                        + "WHERE FlightN = '%s';", field, newValue, flightNumber);
            }
            statement.executeUpdate(updateInTable);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }

    /**
     * To issue a ticket to a client. Inserts a row to the reservedflights table.
     * Returns true if available is greater than 0 and a row has been inserted 
     * @param flight the flight of the reservation
     * @param client the client to be reserved
     * @return true if available is greater than 0 and a row has been inserted
     */
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
            String updateTable = String.format("UPDATE Flights "
                    + "SET Available = %d "
                    + "WHERE FlightN = '%s';", available - 1, flight);
            statement.executeUpdate(updateTable);

            // From line 77-89, we are extracting all the ticket numbers in the ReservedFlight Table and
            // inserting them in a list which then we are incrementing by one if the list is not empty
            // in order to increment ticket numbers when inserting new records in the ReservedFlights
            // table. We chose to do it this way because we did not like SQLite's method of incrementing
            // because their method is unpractical because it skips numbers when previous rows are deleted.
            statement = connection.createStatement();
            String getAllTicketNumbers = "SELECT * FROM ReservedFlights ORDER BY TicketN;";
            List<Integer> list = new ArrayList<>();
            resultSet = statement.executeQuery(getAllTicketNumbers);
            while (resultSet.next()) {
                list.add(resultSet.getInt("TicketN"));
            }

            int ticketN = 1;

            if (!list.isEmpty()) {
                ticketN = list.get(list.size() - 1) + 1;
            }
            
            java.util.Date date = new java.util.Date();

            String insertTable = String.format("INSERT INTO ReservedFlights (TicketN,FlightN, PassNum, FlName, IssueDate, Contact, Amount) "
                    + "VALUES ('%d','%s', '%s', '%s', '%s', '%s', '%f');",ticketN, flight, client.getPassNumber(), client.getFullName(), date, client.getContact(), amountToPay);
            statement.executeUpdate(insertTable);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }

    /**
     * To remove a row from the reservedflights table. Returns true if a row is removed when there is a row
     * with a corresponding ticket number and passport number and that the row
     * has been removed. Increment the available amount in the flights table
     * @param ticket the ticket number of the reservation
     * @param passNumber the passport number of the reservation
     * @return Returns true if a row is removed when there is a row
     * with a corresponding ticket number and passport number and that the row
     * has been removed 
     */
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
            // getting flight number to later increment the available
            String queryTable = String.format("SELECT FlightN FROM ReservedFlights WHERE "
                    + "TicketN = %d AND PassNum = %d;", ticket, passNumber);
            ResultSet resultSet = statement.executeQuery(queryTable);

            String flight = "";
            while (resultSet.next()) {
                flight = resultSet.getString("FlightN");
            }

            // getting the available from the flight number
            int available = -1;
            statement = connection.createStatement();
            queryTable = String.format("SELECT Available FROM Flights WHERE "
                    + "FlightN = '%s';", flight);
            resultSet = statement.executeQuery(queryTable);

            while (resultSet.next()) {
                available = resultSet.getInt("Available");
            }

            // deleting the reserved flight
            statement = connection.createStatement();
            String updateInTable = String.format("DELETE FROM ReservedFlights WHERE "
                    + "TicketN = %d AND PassNum = %d;", ticket, passNumber);
            statement.executeUpdate(updateInTable);

            // incrementing the available
            statement = connection.createStatement();
            updateInTable = String.format("UPDATE Flights SET Available = %d WHERE FlightN = '%s'", available + 1, flight);
            statement.executeUpdate(updateInTable);

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }

    /**
     * Returns a map of the flights table
     * @return a map of the flights table
     */
    public static Map<String, String> viewBoard() {
        Locale locale = InputOutputUser.locale;
        if (locale == null) {
            locale = new Locale("en", "CA");
        }
        ResourceBundle res = InputOutputUser.res;
        if (res == null) {
            res = ResourceBundle.getBundle("progpatproject/OutputBundle", locale);
        }
        TreeMap<String, String> map = new TreeMap<>();
        Connection connection = SingleConnection.getInstance();
        try {
            Statement statement = connection.createStatement();
            String queryTable = String.format("SELECT * FROM Flights ORDER BY FlightN;");
            ResultSet resultSet = statement.executeQuery(queryTable);

            while (resultSet.next()) {
                map.put(res.getString("flightN") + " " + resultSet.getString("FlightN"), " " + res.getString("name") + " " + resultSet.getString("Name")
                        + ", " + res.getString("origin") + " " + resultSet.getString("Origin") + ", " + res.getString("dest") + " " + resultSet.getString("Dest")
                        + ", " + res.getString("duration") + " " + resultSet.getString("Duration") + ", " + res.getString("seats") + " " + resultSet.getString("Seats")
                        + ", " + res.getString("available") + " " + resultSet.getString("Available") + ", " + res.getString("amount") + " " + resultSet.getString("Amount"));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return map;
    }

    /**
     * Returns a map of the ReservedFlights table
     * @return a map of the ReservedFlights table
     */
    public static Map<String, String> viewBookedFlights() {
        Locale locale = InputOutputUser.locale;
        if (locale == null) {
            locale = new Locale("en", "CA");
        }
        ResourceBundle res = InputOutputUser.res;
        if (res == null) {
            res = ResourceBundle.getBundle("progpatproject/OutputBundle", locale);
        }
                
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        Connection connection = SingleConnection.getInstance();
        try {
            Statement statement = connection.createStatement();
            String queryTable = String.format("SELECT * FROM ReservedFlights ORDER BY FlightN;");
            ResultSet resultSet = statement.executeQuery(queryTable);

            while (resultSet.next()) {
                map.put(res.getString("ticketN") + " " + resultSet.getString("ticketn"),res.getString("flightN")  + " " + resultSet.getString("flightn")
                        + ", " + res.getString("passNum") + " " + resultSet.getString("PassNum") + ", " + res.getString("flName") + " " + resultSet.getString("FLName")
                        + ", " + res.getString("issueDate") + " " + resultSet.getString("IssueDate") + ", " + res.getString("contact") + " " + resultSet.getString("Contact")
                        + ", " + res.getString("amount") + " " + resultSet.getString("Amount"));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return map;
    }

    /**
     * Getter for connection
     * @return connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Getter for flight name
     * @return flight name
     */
    public String getFlightN() {
        return flightN;
    }

    /**
     * Get the name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for origin
     * @return origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Getter for destination
     * @return destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Getter for duration
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Getter for seats
     * @return seats
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Getter for  amount of seats
     * @return amount of seats
     */
    public int getAvailableSeats() {
        return availableSeats;
    }

    /**
     * Getter for available of seats
     * @return available of seats
     */
    public double getAmount() {
        return amount;
    }
}
