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

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
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
                    // Works
                    Controller controller = new Controller(promptAddFlight(), null);
                    controller.addFlight();
                    break;
                case 2:
                    // Works
                    controller = new Controller(getFlight(),null);
                    controller.removeFlight(promptRemoveFlight());
                    break;
                case 3:
                    // Need to fix "[SQLITE_ERROR] SQL error or missing database (near "'Dest'": syntax error)".
                    List<String> list = promptUpdateFlight();
                    controller = new Controller(getFlight(),null);
                    controller.updateFlightData(list.get(0),list.get(1),list.get(2));
                    break;
                case 4:
                    // Need to fix "[SQLITE_ERROR] SQL error or missing database (near "Available": syntax error)" error.
                    List<Object> issueTicketList = promptIssueTicket();
                    controller = new Controller(getFlight(),null);
                    Client client = controller.getClient((int)issueTicketList.get(1));
                    controller.issueTicket((String)issueTicketList.get(0),client);
                    break;
                case 5:
                    // Can only check once IssueTicket is done.
                    List<Integer> cancelFlightList = promptCancelFlight();
                    controller = new Controller(getFlight(),null);
                    controller.cancelFlight(cancelFlightList.get(0), cancelFlightList.get(1));
                    break;
                case 6:
                    // Works
                    controller = new Controller(getFlight(),null);
                    controller.viewBoard();
                    break;
                case 7:
                    // Works
                    controller = new Controller(getFlight(),null);
                    controller.viewBookedFlights();
                    break;
            }
        }
        else if (userType == 2){
            Client client = null;
            while(client == null){
                client = promptClientInfoInput();
            }
            int choice = 0;
            Controller controller = new Controller(getFlight(), client);
            boolean correctInput = false;
            while (!correctInput)
            {
                printClientChoice();
                Scanner input = new Scanner(System.in);
                if(input.hasNextInt()){
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
                    System.err.println("Please select a valid option!");
                    correctInput = false;
                    break;
                }
            }  
        }
    }

    public static Flight getFlight(){
        String flightNumber = "";
        String name = "";
        String origin = "";
        String destination = "";
        int duration = 0;
        int seats = 0;
        int availableSeats = 0;
        double amount = 0.0;
        
        return new Flight(flightNumber,name,origin,destination,duration,seats,availableSeats,amount);
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
    
    public static String promptRemoveFlight(){
        String flightNumber = "";
        
        try{
            Scanner input = new Scanner(System.in);
            
            System.out.println("Please enter the flight number: ");
            if(input.hasNextLine()){
                flightNumber += input.nextLine();
            }
            if(flightNumber.isEmpty()){
                throw new InputMismatchException();
            }
            
            return flightNumber;
            
        }
        catch(InputMismatchException e){
            System.err.println("Error: " + e + ", The Flight could not be removed.");
            return null;
        }
    }
    
    public static List<String> promptUpdateFlight(){
        String flightNumber = "";
        String field = "";
        String newValue = "";
        List<String> list = new ArrayList<String>();
        
        try{
            Scanner input = new Scanner(System.in);
            
            System.out.println("Please enter the flight number: ");
            if(input.hasNextLine()){
                flightNumber += input.nextLine();
                
                if(flightNumber.isEmpty()){
                throw new InputMismatchException();
                }
                
                list.add(flightNumber);
            }
            System.out.println("Please enter the field that you would like to update: ");
            if(input.hasNextLine()){
                field += input.nextLine();
                
                if(field.isEmpty()){
                throw new InputMismatchException();
                }
                
                list.add(field);                
            }
            
            System.out.println("Please enter the new value: ");
            if(input.hasNextLine()){
                newValue += input.nextLine();
                
                if(newValue.isEmpty()){
                throw new InputMismatchException();
                }
                
                list.add(newValue);
            }
            
            if(list.isEmpty()){
                throw new InputMismatchException();
            }
            else{
                return list;
            }
            
        }
        catch(InputMismatchException e){
            System.err.println("Error: " + e + ", Please verify your input!");
            return null;
        }
    }
    
    public static List<Object> promptIssueTicket(){
        List<Object> list = new ArrayList<>(1);
        String flightNumber = "";

        int passNum = 0;
        
        try{
            Scanner input = new Scanner(System.in);
            
            System.out.println("Please enter the flight number: ");
            if(input.hasNextLine()){
                flightNumber += input.nextLine();
                
                if (flightNumber.isEmpty()){
                throw new InputMismatchException();
                }
                
                list.add(flightNumber);
            }

            System.out.println("Please enter the client's passport number: ");
            if(input.hasNextInt()){
                passNum += input.nextInt();
            
                if(passNum == 0){
                throw new InputMismatchException();
                }
                list.add(passNum);
            }
            
            
            if(list.isEmpty()){
                throw new InputMismatchException();
            }
            
            return list;  
        }
        catch(InputMismatchException e){
            System.err.println("Error: " + e + ", Please verify your input!");
            return null;
        }
    }
    
    public static List<Integer> promptCancelFlight(){
        List<Integer> list = null;
        int ticketNumber = 0;
        int passNumber = 0;
        
        try{
            Scanner input = new Scanner(System.in);
            
            System.out.println("Please enter the ticket number: ");
            if(input.hasNextInt()){
                ticketNumber += input.nextInt();
            }
            if(ticketNumber==0){
                throw new InputMismatchException();
            }
            
            System.out.println("Please enter the passport number: ");
            if(input.hasNextInt()){
                passNumber += input.nextInt();
            }
            if(passNumber==0){
                throw new InputMismatchException();
            }
            
            list.add(ticketNumber);
            list.add(passNumber);
            
            if(list.isEmpty()){
                throw new InputMismatchException();
            }
            
            return list;
        }
        catch(InputMismatchException e){
            System.err.println("Error: " + e + ", Please verify your input!");
            return null;
        }
    }
    
        public static Client promptClientInfoInput(){
        int passNum = 0;
        
        try{
            Scanner input = new Scanner(System.in);
            
            System.out.println("Please enter you passport number: ");
            if(input.hasNextInt()){
                passNum += input.nextInt();
            }
            if(passNum == 0){
                throw new InputMismatchException();
            }
            return checkClient(passNum);
        }
        catch(Exception e){
            System.err.println("Error: " + e + ", please check your input.");
            return null;
        }
    }
    
    public static Client checkClient(int passNumber){
        
        try{
            Connection connection = SingleConnection.getInstance();
            Statement stmt = connection.createStatement();
            
            String getClient = String.format("SELECT * FROM Clients WHERE PassNum = %d", passNumber);
            ResultSet rs = stmt.executeQuery(getClient);
            Client client = null;
            while(rs.next()){
                String fullName = rs.getString("FlName");
                String contact = rs.getString("Contact");
                client = new Client(fullName,passNumber,contact);
            }
            if(client == null){
                throw new SQLException();
            }
            return client;
        }
        catch(Exception e){
            System.err.println("Error: " + e + ", Client does not exist.");
            return null;
        }
    }
    
    public static void printClientChoice(){
        System.out.println("Press 1 to book a seat");
        System.out.println("Press 2 to cancel a reservation");
        System.out.println("Press 3 to search flights by destination");
        System.out.println("Press 4 to search flights by origin");
        System.out.println("Press 5 to view the flights board");
    }
        
    public static String promptBookASeat(){
        String flightNumber = "";
        
        try{
            Scanner input = new Scanner(System.in);
            
            System.out.println("Please enter the flight number: ");
            if(input.hasNextLine()){
                flightNumber += input.nextLine();
            }
            if(flightNumber.isEmpty()){
                throw new InputMismatchException();
            }
            
            return flightNumber;
        }
        catch(InputMismatchException e){
            System.err.println("Error: " + e + ", Please verify your input!");
            return null;
        }
    }
    
    public static int promptCancelReservation(){
        int ticketNum = 0;
        
        try{
            Scanner input = new Scanner(System.in);
            
            System.out.println("Please enter the ticket number: ");
            if(input.hasNextInt()){
                ticketNum += input.nextInt();
            }
            if(ticketNum == 0){
                throw new InputMismatchException();
            }
            
            return ticketNum;
        }
        catch(InputMismatchException e){
            System.err.println("Error: " + e + ", Please verify your input!");
            return 0;
        }
    }
    
    public static String promptSearchFlightsByDestination(){
        String destination = "";
        
        try{
            Scanner input = new Scanner(System.in);
            
            System.out.println("Please enter a destination: ");
            if(input.hasNextLine()){
                destination += input.nextLine();
                
                if(destination.isEmpty()){
                throw new InputMismatchException();
                }
            }
            
            return destination;
        }
        catch(InputMismatchException e){
            System.err.println("Error: " + e + ", Please verify your input!");
            return null;
        }
    }
    
    public static String promptSearchFlightsByOrigin(){
        String origin = "";
        
        try{
            Scanner input = new Scanner(System.in);
            
            System.out.println("Please enter an origin: ");
            if(input.hasNextLine()){
                origin += input.nextLine();
            }
            if(origin.isEmpty()){
                throw new InputMismatchException();
            }
            return origin;
        }
        catch(InputMismatchException e){
            System.err.println("Error: " + e + ", Please verify your input!");
            return null;
        }
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
