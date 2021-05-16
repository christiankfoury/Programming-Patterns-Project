package progpatproject;

import java.util.*;

/**
 *
 * @author Christian
 */
public class InputOutputManager extends InputOutputUser {

    /**
     * STRATEGY DESIGN PATTERN. Since the user is a manager. These are the choices
     */
    @Override
    public void printChoice() {
        printChosenLanguage("managerChoice1");
        printChosenLanguage("managerChoice2");
        printChosenLanguage("managerChoice3");
        printChosenLanguage("managerChoice4");
        printChosenLanguage("managerChoice5");
        printChosenLanguage("managerChoice6");
        printChosenLanguage("managerChoice7");
    }
    
    /**
     * To prompt the manager with add a flight (when selected)
     * @return the flight that the manager wants to add (which is inputted by the user)
     */
    public Flight promptAddFlight() {
        String flightN, name, origin, dest;
        int duration, seats;
        double amount;

        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("flightNumberInput");
        flightN = scanner.nextLine();
        // validation
        while (flightN.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("flightNumberInput");
            flightN = scanner.nextLine();
        }

        printChosenLanguage("aircraftNameInput");
        name = scanner.nextLine();
        // validation
        while (name.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("aircraftNameInput");
            name = scanner.nextLine();
        }

        printChosenLanguage("flightOriginInput");
        origin = scanner.nextLine();
        // validation
        while (origin.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("flightOriginInput");
            origin = scanner.nextLine();
        }

        printChosenLanguage("flightDestinationInput");
        dest = scanner.nextLine();
        // validation
        while (dest.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("flightDestinationInput");
            dest = scanner.nextLine();
        }

        printChosenLanguage("flightDurationInput");
        String input = scanner.nextLine();
        // validation
        while (!isInteger(input)) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("flightDurationInput");
            input = scanner.nextLine();
        }
        duration = Integer.parseInt(input);

        printChosenLanguage("flightSeatsInput");
        input = scanner.nextLine();
        // validation
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
        // validation
        while (!isDouble(input)) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("flightPriceInput");
            input = scanner.nextLine();
        }
        amount = Double.parseDouble(input);

        return new Flight(flightN, name, origin, dest, duration, seats, seats, amount);
    }
    
    /**
     * To prompt the manager with remove a flight (when selected)
     * @return the flightnumber that the manager wants to remove (which is inputted by the user)
     */
    public String promptRemoveFlight() {
        printChosenLanguage("flightNumberInput");
        Scanner scanner = new Scanner(System.in);
        
        String flightNumber = scanner.nextLine();
        // validation
        while (flightNumber.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("flightNumberInput");
            flightNumber = scanner.nextLine();
        }

        return flightNumber;
    }
    
    /**
     * To prompt the manager with update a flight (when selected)
     * @return a list of string that the manager wants to update(which is inputted by the user)
     * index 0: flightnumber, index 1: field to update, index 2: the new value
     */
    public List<String> promptUpdateFlight() {
        String newValue;
        List<String> list = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("flightNumberInput");
        String flightNumber = scanner.nextLine();
        // validation
        while (flightNumber.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("flightNumberInput");
            flightNumber = scanner.nextLine();
        }
        list.add(flightNumber);

        printChosenLanguage("fieldToChangeInput");
        String field = scanner.nextLine();
        // validation
        while (!new ArrayList<>(Arrays.asList("origin", "dest", "duration")).contains(field.toLowerCase())) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("fieldToChangeInput");
            field = scanner.nextLine();
        }

        list.add(field);

        if (field.equalsIgnoreCase("duration")) {
            printChosenLanguage("newValueInput");
            newValue = scanner.nextLine();
            // validation
            while (!isInteger(newValue)) {
                printChosenLanguage("wrongInputMessage");
                printChosenLanguage("newValueInput");
                newValue = scanner.nextLine();
            }
        } else {
            printChosenLanguage("newValueInput");
            newValue = scanner.nextLine();
            // validation
            while (newValue.trim().isEmpty()) {
                printChosenLanguage("wrongInputMessage");
                printChosenLanguage("newValueInput");
                newValue = scanner.nextLine();
            }
        }

        list.add(newValue);

        return list;
    }
    
    /**
     * To prompt the manager with issue a flight (when selected)
     * @return a list that the manager wants to issue (which is inputted by the user)
     * index 0: flightnumber, index 1: passportnumber
     */
    public List<Object> promptIssueTicket() {
        List<Object> list = new ArrayList<>(1);
        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("flightNumberInput");
        String flightNumber = scanner.nextLine();
        
        // validation
        while (flightNumber.trim().isEmpty()) {
            printChosenLanguage("wrongInputMessage");
            printChosenLanguage("flightNumberInput");
            flightNumber = scanner.nextLine();
        }
        if (!isFlightExists(flightNumber)) {
            printChosenLanguage("flightDoesNotExist");
            return promptIssueTicket();
        }
        list.add(flightNumber);
        printChosenLanguage("passportNumberInput");
        String input = scanner.nextLine();

        // validation
        while (!isInteger(input) || input.startsWith("0")) {
            printChosenLanguage("incorrectPassportNumberInput");
            printChosenLanguage("passportNumberInput");
            input = scanner.nextLine();
        }
        if (getClient(Integer.parseInt(input)) == null) {
            printChosenLanguage("clientDoesNotExist");
            return promptIssueTicket();
        }
        list.add(Integer.parseInt(input));

        return list;
    }
    
    /**
     * To prompt the manager with cancel a flight (when selected)
     * @return a list of the flight details that the manager cancel(which is inputted by the user)
     * index 0: ticketnumber, index 1: passportnumber
     */
    public List<Integer> promptCancelFlight() {
        List<Integer> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        printChosenLanguage("ticketNumberInput");
        String input = scanner.nextLine();

        // validation
        while (!isInteger(input) || input.startsWith("0")) {
            printChosenLanguage("incorrectTicketNumberInput");
            printChosenLanguage("ticketNumberInput");
            input = scanner.nextLine();
        }
        list.add(Integer.parseInt(input));

        printChosenLanguage("passportNumberInput");
        input = scanner.nextLine();

        // validation
        while (!isInteger(input) || input.startsWith("0")) {
            printChosenLanguage("incorrectPassportNumberInput");
            printChosenLanguage("passportNumberInput");
            input = scanner.nextLine();
        }
        list.add(Integer.parseInt(input));

        return list;

    }
}
