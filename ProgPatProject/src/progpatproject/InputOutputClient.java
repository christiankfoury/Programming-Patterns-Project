package progpatproject;

import java.util.*;

/**
 *
 * @author Christian
 */
public class InputOutputClient extends InputOutputUser {

    /**
     * STRATEGY DESIGN PATTERN. Since the user is a client. These are the choices
     */
    @Override
    public void printChoice() {
        printChosenLanguage("clientChoice1");
        printChosenLanguage("clientChoice2");
        printChosenLanguage("clientChoice3");
        printChosenLanguage("clientChoice4");
        printChosenLanguage("clientChoice5");
        printChosenLanguage("clientChoice6");
    }
    
    /**
     * To prompt the client with book a seat (when selected)
     * @return the flight number that the client wants to book (which is inputted by the user)
     */
    public String promptBookASeat() {
        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("flightNumberInput");
        String flightN = scanner.nextLine();
        // VALIDATION
        while (flightN.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("flightNumberInput");
            flightN = scanner.nextLine();
        }

        return flightN;
    }

    /**
     * To prompt the client with cancel reservation(when selected)
     * @return the ticket number that the client wants to remove(which is inputted by the user)
     */
    public int promptCancelReservation() {
        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("ticketNumberInput");
        String input = scanner.nextLine();
        // VALIDATION
        while (!isInteger(input) || input.startsWith("0")) {
            printChosenLanguage("incorrectTicketNumberInput");
            printChosenLanguage("ticketNumberInput");
            input = scanner.nextLine();
        }

        return Integer.parseInt(input);
    }

    /**
     * To prompt the client with search flight by destination (when selected)
     * @return the destination the client wants to view (which is inputted by the user)
     */
    public String promptSearchFlightsByDestination() {
        String dest = "";

        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("promptSearchFlightByDestination");
        dest = scanner.nextLine();
        // VALIDATION
        while (dest.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("promptSearchFlightByDestination");
            dest = scanner.nextLine();
        }

        return dest;
    }
    
    /**
     * To prompt the client with search flight by destination (when selected)
     * @return the destination the client wants to view (which is inputted by the user)
     */
    public int promptSearchFlightsByDuration() {
        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("promptSearchFlightByDuration");
        String input = scanner.nextLine();
        // VALIDATION
        while (!isInteger(input) || input.startsWith("0")) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("promptSearchFlightByDuration");
            input = scanner.nextLine();
        }

        return Integer.parseInt(input);
    }
    

    /**
     * To prompt the client with search flight by origin (when selected)
     * @return the origin the client wants to view (which is inputted by the user)
     */
    public String promptSearchFlightsByOrigin() {
        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("promptSearchFlightByOrigin");
        String origin = scanner.nextLine();
        // VALIDATION
        while (origin.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("promptSearchFlightByOrigin");
            origin = scanner.nextLine();
        }

        return origin;
    }

    /**
     * To prompt the client with their information
     * @return a client object
     */
    public Client promptClientInfoInput() {
        Scanner scanner = new Scanner(System.in);
        printChosenLanguage("passportNumberInput");

        String input = scanner.nextLine();
        // VALIDATION
        while (!isInteger(input) || input.startsWith("0")) {
            printChosenLanguage("incorrectPassportNumberInput");
            printChosenLanguage("passportNumberInput");
            input = scanner.nextLine();
        }

        return getClient(Integer.parseInt(input));
    }
}
