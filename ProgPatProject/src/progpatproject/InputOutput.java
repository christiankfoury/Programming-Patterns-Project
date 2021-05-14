package progpatproject;

import java.util.*;

/**
 *
 * @author Christian
 */
public class InputOutput {

    // the type of the user
    // 1 for manager, 2 for client 
    private static int userType;

    public static void main(String[] args) {
        promptUserType();
        if (userType == 1) {
            int choice = -1;

            while (!new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7)).contains(choice)) {
                System.out.println("Your input is wrong");
                printManagerChoice();
                try {
                    Scanner scanner = new Scanner(System.in);
                    if (scanner.hasNextInt()) {
                        choice = scanner.nextInt();
                    }
                } catch (NumberFormatException exception) {
                }
            }
            switch (choice) {
                case 1:
                    Controller controller = new Controller(promptAddFlight(), null);
                    controller.addFlight();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }
        }
    }

    public static void promptUserType() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("What type of user are you?");
            System.out.println("Enter 1 for manager");
            System.out.println("Enter 2 for client");

            String line = scanner.nextLine();

            while (!line.equals("1") && !line.equals("2")) {
                System.out.println("Your input is wrong");
                System.out.println("What type of user are you?");
                System.out.println("Enter 1 for manager");
                System.out.println("Enter 2 for client");
                line = scanner.nextLine();
            }
            userType = Integer.parseInt(line);
        } catch (NumberFormatException exception) {
        }
    }

    public static void printManagerChoice() {
        System.out.println("Press 1 to add a flight");
        System.out.println("Press 2 to remove a flight");
        System.out.println("Press 3 to update a flight");
        System.out.println("Press 4 to issue a ticket to a client");
        System.out.println("Press 5 to cancel a flight");
        System.out.println("Press 6 to view the flights board");
        System.out.println("Press 7 to view the booked flights");
    }

    public static Flight promptAddFlight() {
        String flightN, name, origin, dest;
        flightN = name = origin = dest = null;
        int duration = 0, seats = 0;
        double amount = 0;
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Enter the flight number");
            if (scanner.hasNextLine()) {
                flightN = scanner.nextLine(); 
            }
            
            System.out.println("Enter the name of the aircraft");
            if (scanner.hasNextLine()) {
                name = scanner.nextLine();
            }
            
            System.out.println("Enter the origin of the flight");
            if (scanner.hasNextLine()) {
                origin = scanner.nextLine();
            }
            
            System.out.println("Enter the destination of the flight");
            if (scanner.hasNextLine()) {
                dest = scanner.nextLine();
            }
            
            System.out.println("Enter the duration of the flight");
            String input = scanner.nextLine();
            while (!isInteger(input)) {
                System.out.println("Your input is wrong.");
                System.out.println("Enter the duration of the flight");
                input = scanner.nextLine();
            }
            duration = Integer.parseInt(input);
            
            System.out.println("Enter the number of seats");
            input = scanner.nextLine();
            while (!isInteger(input)) {
                System.out.println("Your input is wrong.");
                System.out.println("Enter the number of seats");
                input = scanner.nextLine();
            }
            seats = Integer.parseInt(input);
            // no prompt for the available amount of seats since
            // the flight has just been addec
            
            System.out.println("Enter the amount of flight");
            input = scanner.nextLine();
            while (!isDouble(input)) {
                System.out.println("Your input is wrong!");
                System.out.println("Enter the amount of flight");
                input = scanner.nextLine();
            }
            amount = Double.parseDouble(input);
            
        } catch (NumberFormatException exception) {
        }

        return new Flight(flightN, name, origin, dest, duration, seats, seats, amount);
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
}
