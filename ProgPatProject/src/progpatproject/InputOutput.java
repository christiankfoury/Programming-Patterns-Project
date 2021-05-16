package progpatproject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author Christian
 */
public class InputOutput {

    private final Connection connection = SingleConnection.getInstance();

    // the type of the user
    // 1 for manager, 2 for client 
    private static int userType;
    private static int languageChoice;
    private final static String country = "CA";
    protected static Locale locale;
    protected static ResourceBundle res;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        promptUserLanguage();
        promptUserType();
        if (userType == 1) {
            int choice = -1;

            while (!new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7)).contains(choice)) {
                printChosenLanguage("wrongInputMessage");
                printManagerChoice();
                try {
                    Scanner scanner = new Scanner(System.in);
                    if (scanner.hasNextInt()) {
                        choice = scanner.nextInt();
                    } else {
                        System.out.println("Your input is wrong");
                    }
                } catch (NumberFormatException exception) {
                }
            }
            switch (choice) {
                case 1:
                    // Works
                    Controller controller = new Controller(promptAddFlight(), null);
                    controller.addFlight();
                    break;
                case 2:
                    // Works
                    controller = new Controller(getFlight(), null);
                    controller.removeFlight(promptRemoveFlight());
                    break;
                case 3:
                    // Works
                    List<String> list = promptUpdateFlight();
                    controller = new Controller(getFlight(), null);
                    controller.updateFlightData(list.get(0), list.get(1), list.get(2));
                    break;
                case 4:
                    // Works
                    List<Object> issueTicketList = promptIssueTicket();
                    controller = new Controller(getFlight(), null);
                    Client client = controller.getClient((int) issueTicketList.get(1));
                    controller.issueTicket((String) issueTicketList.get(0), client);
                    break;
                case 5:
                    // Can only check once IssueTicket is done.
                    List<Integer> cancelFlightList = promptCancelFlight();
                    controller = new Controller(getFlight(), null);
                    controller.cancelFlight(cancelFlightList.get(0), cancelFlightList.get(1));
                    break;
                case 6:
                    // Works
                    controller = new Controller(getFlight(), null);
                    controller.viewBoard();
                    break;
                case 7:
                    // Works
                    controller = new Controller(getFlight(), null);
                    controller.viewBookedFlights();
                    break;
            }
        } else if (userType == 2) {
            Client client = null;
            client = promptClientInfoInput();
            while (client == null) {
                System.out.println("This client does not exist");
                client = promptClientInfoInput();
            }
            int choice = 0;
            Controller controller = new Controller(getFlight(), client);
            boolean correctInput = false;
            while (!correctInput) {
                printClientChoice();
                Scanner input = new Scanner(System.in);
                if (input.hasNextInt()) {
                    choice += input.nextInt();
                }
                switch (choice) {
                    case 1:
                        // Works
                        controller.bookASeat(promptBookASeat());
                        correctInput = true;
                        break;
                    case 2:
                        // Works.
                        controller.cancelReservation(promptCancelReservation());
                        correctInput = true;
                        break;
                    case 3:
                        // Works.
                        controller.searchFlightByDestination(promptSearchFlightsByDestination());
                        correctInput = true;
                        break;
                    case 4:
                        //Works
                        controller.searchFlightByOrigin(promptSearchFlightsByOrigin());
                        correctInput = true;
                        break;
                    case 5:
                        //Works
                        controller.viewFlightBoard();
                        correctInput = true;
                        break;
                    default:
                        printChosenLanguage("switchDefault");
                        System.out.println("Please select a valid option!");
                        correctInput = false;
                        break;
                }
            }
        }
    }
    
    public static void promptUserLanguage(){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("What language do you prefer?");
            System.out.println("Enter 1 for English");
            System.out.println("Enter 2 for French");
            
            String line = scanner.nextLine();
            
            while (!line.equals("1") && !line.equals("2")) {
                System.out.println("Your input is wrong");
                System.out.println("What language do you prefer?");
                System.out.println("Enter 1 for English");
                System.out.println("Enter 2 for French");
                line = scanner.nextLine();
            }
            languageChoice = Integer.parseInt(line);
            if (line.equals("1")) {
                locale = new Locale("en", country);
                res = ResourceBundle.getBundle("progpatproject/OutputBundle", locale);
            }
            else {
                locale = new Locale("fr", country);
                res = ResourceBundle.getBundle("progpatproject/OutputBundle", locale);
            }
        }
        catch(Exception e){
            
        }
    }
    
    public static void printChosenLanguage(String key){        
        System.out.println(res.getString(key));
    }

    public static Flight getFlight() {
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

    public static void printManagerChoice() {
        printChosenLanguage("managerChoice1");
        printChosenLanguage("managerChoice2");
        printChosenLanguage("managerChoice3");
        printChosenLanguage("managerChoice4");
        printChosenLanguage("managerChoice5");
        printChosenLanguage("managerChoice6");
        printChosenLanguage("managerChoice7");
    }

    public static Flight promptAddFlight() {
            String flightN, name, origin, dest;
    //        flightN = name = origin = dest = "";
            int duration, seats;
            double amount;
            
            Scanner scanner= new Scanner(System.in);

            printChosenLanguage("flightNumberInput");
            flightN = scanner.nextLine();
            while (flightN.trim().isEmpty()) {
                printChosenLanguage("wrongInputMessage");
                printChosenLanguage("flightNumberInput");
                flightN = scanner.nextLine();
            }

            printChosenLanguage("aircraftNameInput");
            name = scanner.nextLine();
            while (name.trim().isEmpty()) {
                printChosenLanguage("wrongInputMessage");
                printChosenLanguage("aircraftNameInput");
                name = scanner.nextLine();
            }

            printChosenLanguage("flightOriginInput");
            origin = scanner.nextLine();
            while (origin.trim().isEmpty()) {
                printChosenLanguage("wrongInputMessage");
                printChosenLanguage("flightOriginInput");
                origin = scanner.nextLine();
            }

            printChosenLanguage("flightDestinationInput");
            dest = scanner.nextLine();
            while (dest.trim().isEmpty()) {
                printChosenLanguage("wrongInputMessage");
                printChosenLanguage("flightDestinationInput");
                dest = scanner.nextLine();
            }

            printChosenLanguage("flightDurationInput");
            String input = scanner.nextLine();
            while (!isInteger(input)) {
                printChosenLanguage("wrongInputMessage");
                printChosenLanguage("flightDurationInput");
                input = scanner.nextLine();
            }
            duration = Integer.parseInt(input);

            printChosenLanguage("flightSeatsInput");
            input = scanner.nextLine();
            while (!isInteger(input)) {
                printChosenLanguage("wrongInputMessage");
                printChosenLanguage("flightSeatsInput");
                input = scanner.nextLine();
            }
            seats = Integer.parseInt(input);
            // no prompt for the available amount of seats since
            // the flight has just been addec

            printChosenLanguage("flightPriceInput");
            input = scanner.nextLine();
            while (!isDouble(input)) {
                printChosenLanguage("wrongInputMessage");
                printChosenLanguage("flightPriceInput");
                input = scanner.nextLine();
            }
            amount = Double.parseDouble(input);

        return new Flight(flightN, name, origin, dest, duration, seats, seats, amount);
    }

    public static String promptRemoveFlight() {
        String flightNumber = "";
        
        printChosenLanguage("flightNumberInput");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the flight number: ");
        flightNumber = scanner.nextLine();
        while (flightNumber.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("flightNumberInput");
            flightNumber = scanner.nextLine();
        }

        return flightNumber;
    }

    public static List<String> promptUpdateFlight() {
        String flightNumber = "";
        String field = "";
        String newValue = "";
        List<String> list = new ArrayList<String>();

        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("flightNumberInput");
        flightNumber = scanner.nextLine();

        while (flightNumber.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("flightNumberInput");
            flightNumber = scanner.nextLine();
        }
        list.add(flightNumber);

        printChosenLanguage("fieldToChangeInput");
        field = scanner.nextLine();

        while (!new ArrayList<>(Arrays.asList("origin", "dest", "duration")).contains(field.toLowerCase())) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("fieldToChangeInput");
            field += scanner.nextLine();
        }

        list.add(field);

        if (field.equalsIgnoreCase("duration")) {
            printChosenLanguage("newValueInput");
            newValue = scanner.nextLine();

            while (!isInteger(newValue)) {
                printChosenLanguage("wrongInputMessage");
                printChosenLanguage("newValueInput");
                newValue = scanner.nextLine();
            }
        } else {
            printChosenLanguage("newValueInput");
            newValue = scanner.nextLine();

            while (newValue.trim().isEmpty()) {
                printChosenLanguage("wrongInputMessage");
                printChosenLanguage("newValueInput");
                newValue = scanner.nextLine();
            }
        }

        list.add(newValue);

        return list;
    }

    public static List<Object> promptIssueTicket() {
        List<Object> list = new ArrayList<>(1);
        String flightNumber = "";

        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("flightNumberInput");
        flightNumber = scanner.nextLine();

        while (flightNumber.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("flightNumberInput");
            flightNumber = scanner.nextLine();
        }
        list.add(flightNumber);
        // PASSPORT NUMBER CAN BE 0 NO?
        printChosenLanguage("passportNumberInput");
        String input = scanner.nextLine();

        while (!isInteger(input) || input.startsWith("0")) {
            printChosenLanguage("incorrectPassportNumberInput");
            printChosenLanguage("passportNumberInput");
            input = scanner.nextLine();
        }
        if (checkClient(Integer.parseInt(input)) == null) {
            System.out.println("The client does not exist. Let's restart the process");
            return promptIssueTicket();
        }

        list.add(Integer.parseInt(input));

        return list;
    }

    public static List<Integer> promptCancelFlight() {
        List<Integer> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("ticketNumberInput");
        String input = scanner.nextLine();

        while (!isInteger(input) || input.startsWith("0")) {
            printChosenLanguage("incorrectTicketNumberInput");
            printChosenLanguage("ticketNumberInput");
            input = scanner.nextLine();
        }
        list.add(Integer.parseInt(input));

        printChosenLanguage("passportNumberInput");
        input = scanner.nextLine();

        while (!isInteger(input) || input.startsWith("0")) {
            printChosenLanguage("incorrectPassportNumberInput");
            printChosenLanguage("passportNumberInput");
            input = scanner.nextLine();
        }
        list.add(Integer.parseInt(input));

        return list;

    }


    public static Client promptClientInfoInput() {
        Scanner scanner = new Scanner(System.in);
        printChosenLanguage("passportNumberInput");

        String input = scanner.nextLine();
        while (!isInteger(input) || input.startsWith("0")) {
            printChosenLanguage("incorrectPassportNumberInput");
            printChosenLanguage("passportNumberInput");
            input = scanner.nextLine();
        }

        return checkClient(Integer.parseInt(input));
    }

    public static Client checkClient(int passNumber) {
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

    public static void printClientChoice() {
        printChosenLanguage("clientChoice1");
        printChosenLanguage("clientChoice2");
        printChosenLanguage("clientChoice3");
        printChosenLanguage("clientChoice4");
        printChosenLanguage("clientChoice5");
    }

    public static String promptBookASeat() {
        String flightN = "";
        Scanner input = new Scanner(System.in);

        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("flightNumberInput");
        flightN = scanner.nextLine();
        while (flightN.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("flightNumberInput");
            flightN = scanner.nextLine();
        }

        return flightN;
    }

    public static int promptCancelReservation() {
        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("ticketNumberInput");
        String input = scanner.nextLine();

        while (!isInteger(input) || input.startsWith("0")) {
            printChosenLanguage("incorrectTicketNumberInput");
            printChosenLanguage("ticketNumberInput");
            input = scanner.nextLine();
        }

        return Integer.parseInt(input);
    }

    public static String promptSearchFlightsByDestination() {
        String dest = "";

        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("promptSearchFlightByDestination");
        dest = scanner.nextLine();
        while (dest.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("promptSearchFlightByDestination");
            dest = scanner.nextLine();
        }

        return dest;
    }

    public static String promptSearchFlightsByOrigin() {
        String origin = "";
        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("promptSearchFlightByOrigin");
        origin = scanner.nextLine();
        while (origin.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("promptSearchFlightByOrigin");
            origin = scanner.nextLine();
        }

        return origin;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    // We can try to use this method because this method is strategy?
    public static int isDataExists(String queryTable) {
        Connection connection = SingleConnection.getInstance();
        int count = -1;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryTable);

            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return count;
    }
}

//if (InputOutput.isDataExists(String.format("SELECT COUNT(*) FROM Flights where FlightN = '%s'", flight)) == 0) {
//    return false;
//}
//if (InputOutput.isDataExists(String.format("SELECT COUNT(*) FROM Clients where PassNum = %d", client.getPassNumber())) == 0) {
//    return false;
//}
