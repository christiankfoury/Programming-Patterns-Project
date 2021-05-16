package progpatproject;

import java.util.*;
import java.sql.*;

/**
 *
 * @author Christian
 */
public class Test {

    /**
     * Application. Main method
     *
     * @param args the command line arguments
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        InputOutputUser.promptUserLanguage();
        InputOutputUser.promptUserType();
        // if the user is a manager
        if (InputOutputUser.userType == 1) {
            InputOutputManager inputOutputManager = new InputOutputManager();
            String continueChoice;
            do {
                int choice = -1;
                while (!new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7)).contains(choice)) {
                    inputOutputManager.printChoice();
                    try {
                        Scanner scanner = new Scanner(System.in);
                        if (scanner.hasNextInt()) {
                            choice = scanner.nextInt();
                        } else {
                            InputOutputManager.printChosenLanguage("wrongInputMessage");
                        }
                    } catch (NumberFormatException exception) {
                    }
                }
                switch (choice) {
                    case 1:
                        // Works
                        Controller controller = new Controller(inputOutputManager.promptAddFlight(), null);
                        controller.addFlight();
                        break;
                    case 2:
                        // Works
                        controller = new Controller(inputOutputManager.getFlight(), null);
                        controller.removeFlight(inputOutputManager.promptRemoveFlight());
                        break;
                    case 3:
                        // Works
                        List<String> list = inputOutputManager.promptUpdateFlight();
                        controller = new Controller(inputOutputManager.getFlight(), null);
                        controller.updateFlightData(list.get(0), list.get(1), list.get(2));
                        break;
                    case 4:
                        // Works
                        List<Object> issueTicketList = inputOutputManager.promptIssueTicket();
                        controller = new Controller(inputOutputManager.getFlight(), null);
                        Client client = inputOutputManager.getClient((int) issueTicketList.get(1));
                        controller.issueTicket((String) issueTicketList.get(0), client);
                        break;
                    case 5:
                        // Can only check once IssueTicket is done.
                        List<Integer> cancelFlightList = inputOutputManager.promptCancelFlight();
                        controller = new Controller(inputOutputManager.getFlight(), null);
                        controller.cancelFlight(cancelFlightList.get(0), cancelFlightList.get(1));
                        break;
                    case 6:
                        // Works
                        controller = new Controller(inputOutputManager.getFlight(), null);
                        controller.updateViewFlight(controller.getBoard());
                        break;
                    case 7:
                        // Works
                        controller = new Controller(inputOutputManager.getFlight(), null);
                        controller.updateViewFlight(controller.getBookedFlights());
                        break;
                }
                // if the user wants to continue
                InputOutputManager.printChosenLanguage("doContinue");
                InputOutputManager.printChosenLanguage("yesNo");
                Scanner scanner = new Scanner(System.in);
                continueChoice = scanner.nextLine();
            } while (continueChoice.equals("1"));

            // If the user is a client
        } else if (InputOutputUser.userType == 2) {
            InputOutputClient inputOutputClient = new InputOutputClient();
            Client client = null;
            client = inputOutputClient.promptClientInfoInput();
            while (client == null) {
                InputOutputClient.printChosenLanguage("clientDoesNotExist");
                client = inputOutputClient.promptClientInfoInput();
            }
            String continueChoice;
            do {
                int choice = 0;
                Controller controller = new Controller(inputOutputClient.getFlight(), client);
                boolean correctInput = false;
                while (!correctInput) {
                    inputOutputClient.printChoice();
                    Scanner input = new Scanner(System.in);
                    if (input.hasNextInt()) {
                        choice += input.nextInt();
                    }
                    switch (choice) {
                        case 1:
                            // Works
                            controller.bookASeat(inputOutputClient.promptBookASeat());
                            correctInput = true;
                            break;
                        case 2:
                            // Works.
                            controller.cancelReservation(inputOutputClient.promptCancelReservation());
                            correctInput = true;
                            break;
                        case 3:
                            // Works.
                            controller.updateViewClient(controller.getSearchFlightByDestination(inputOutputClient.promptSearchFlightsByDestination()));
                            correctInput = true;
                            break;
                        case 4:
                            //Works
//                            controller.searchFlightByOrigin(inputOutputClient.promptSearchFlightsByOrigin());
                            controller.updateViewClient(controller.getSearchFlightByDuration(inputOutputClient.promptSearchFlightsByDuration()));
                            correctInput = true;
                            break;
                        case 5:
                            //Works
//                            controller.searchFlightByOrigin(inputOutputClient.promptSearchFlightsByOrigin());
                            controller.updateViewClient(controller.getSearchFlightByOrigin(inputOutputClient.promptSearchFlightsByOrigin()));
                            correctInput = true;
                            break;
                        case 6:
                            //Works
                            controller.viewFlightBoard();
                            correctInput = true;
                            break;
                        default:
                            InputOutputClient.printChosenLanguage("switchDefault");
                            correctInput = false;
                            break;
                    }
                }
                InputOutputClient.printChosenLanguage("doContinue");
                InputOutputClient.printChosenLanguage("yesNo");
                Scanner scanner = new Scanner(System.in);
                continueChoice = scanner.nextLine();
            } while (continueChoice.equals("1"));
        }
    }
}
