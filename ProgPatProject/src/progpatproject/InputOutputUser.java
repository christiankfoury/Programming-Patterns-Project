package progpatproject;

import java.sql.*;
import java.util.*;

/**
 *
 * @author Christian
 */
public abstract class InputOutputUser implements Printable {

    /**
     * The type of user. 1 for manager, and two client
     */
    protected static int userType;
    private final static String country = "CA";

    /**
     * The locale of the IO
     */
    protected static Locale locale;

    /**
     * The resource bundle of the IO
     */
    protected static ResourceBundle res;

    /**
     * Default constructor of the Input output class
     */
    public InputOutputUser() {
    }

    /**
     * To prompt the user the language that the application will be
     */
    public static void promptUserLanguage() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("What language do you prefer? / Quel language préférez-vous");
            System.out.println("Enter 1 for English");
            System.out.println("Entrez 2 pour Français");

            String line = scanner.nextLine();

            // VALIDATION
            while (!line.equals("1") && !line.equals("2")) {
                System.out.println("Your input is wrong / Mauvaise entrée");
                System.out.println("What language do you prefer? / Quel language préférez-vous");
                System.out.println("Enter 1 for English");
                System.out.println("Entrez 2 pour Français");
                line = scanner.nextLine();
            }
            if (line.equals("1")) {
                locale = new Locale("en", country);
                res = ResourceBundle.getBundle("progpatproject/OutputBundle", locale);
            } else {
                locale = new Locale("fr", country);
                res = ResourceBundle.getBundle("progpatproject/OutputBundle", locale);
            }
        } catch (Exception e) {
        }
    }
    
    /**
     * To print strings with the chosen language
     * @param key the key to be printed
     */
    public static void printChosenLanguage(String key) {
        System.out.println(res.getString(key));
    }
    
    /**
     * To print which type of user they are
     */
    public static void promptUserType() {
        try {
            Scanner scanner = new Scanner(System.in);
            printChosenLanguage("userTypeInput");
            printChosenLanguage("managerType");
            printChosenLanguage("clientType");

            String line = scanner.nextLine();

            while (!line.equals("1") && !line.equals("2")) {
                printChosenLanguage("wrongInputMessage");
                printChosenLanguage("userTypeInput");
                printChosenLanguage("managerType");
                printChosenLanguage("clientType");
                line = scanner.nextLine();
            }
            userType = Integer.parseInt(line);
        } catch (NumberFormatException exception) {
        }
    }
    
    /**
     * To get a full client object with the corresponding passport number
     * @param passNumber the passport number to be searched
     * @return a client. Returns null if the client does not exist
     */
    public Client getClient(int passNumber) {
        Client client = null;
        try {
            Connection connection = SingleConnection.getInstance();
            Statement stmt = connection.createStatement();

            String getClient = String.format("SELECT * FROM Clients WHERE PassNum = %d", passNumber);
            ResultSet rs = stmt.executeQuery(getClient);
            while (rs.next()) {
                String fullName = rs.getString("FlName");
                String contact = rs.getString("Contact");
                client = new Client(fullName, passNumber, contact);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return client;
    }
    
    /**
     * To initialize an empty flight. This method will be used in several methods in the client class
     * because the flight object is not relevant in all cases.
     * @return a basic flight object
     */
    public Flight getFlight() {
        String flightNumber = "";
        String name = "";
        String origin = "";
        String destination = "";
        int duration = 0;
        int seats = 0;
        int availableSeats = 0;
        double amount = 0.0;

        return new Flight(flightNumber, name, origin, destination, duration, seats, availableSeats, amount);
    }
    
    /**
     * Returns true if a flight exists in the Flights table
     * @param flightN the flight number to be searched
     * @return true if a flight exists in the Flights table
     */
    public boolean isFlightExists(String flightN) {
        int count = -1;
        try {
            Connection connection = SingleConnection.getInstance();
            Statement stmt = connection.createStatement();

            String getClient = String.format("SELECT COUNT(*) FROM Flights WHERE FlightN = '%s'", flightN);
            ResultSet rs = stmt.executeQuery(getClient);
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return count > 0;
    }
    
    /**
     * For validation purposes. To validate an input is an integer
     * @param input the input to be validated
     * @return true if the input is an integer
     */
    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * For validation purposes. To validate an input is a double
     * @param input the input to be validated
     * @return true if the input is a double
     */
    public boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
