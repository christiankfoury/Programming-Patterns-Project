package progpatproject;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * @author Christian
 */
public class Controller {

    private Flight model;
    private FlightsView flightsView = new FlightsView();
    private Client client;
    private ClientsView clientsView = new ClientsView();

    /**
     * Constructor for controller
     *
     * @param flight the model
     * @param client the client
     */
    public Controller(Flight flight, Client client) {
        this.model = flight;
        this.client = client;
    }

    /*
    Fix error print messages.
     */
    /**
     * To add a flight
     */
    public void addFlight() {
        ResourceBundle res = getRessourceBundle();
        if (model.addFlight(model)) {
            System.out.println(res.getString("successfullyAddedFlight"));
        } else {
            System.out.println(res.getString("flightAlreadyExists"));
        }
    }

    /**
     * To remove a flight
     *
     * @param flightNumber the flight number
     */
    public void removeFlight(String flightNumber) {
        ResourceBundle res = getRessourceBundle();
        if (model.removeFlight(flightNumber)) {
            System.out.println(res.getString("successfullyRemovedFlight"));
        } else {
            System.out.println(res.getString("flightDoesNotExist"));
        }
    }

    /**
     * The update flight data
     *
     * @param flightNumber the flight number
     * @param field the field to update
     * @param newValue the new value
     */
    public void updateFlightData(String flightNumber, String field, String newValue) {
        ResourceBundle res = getRessourceBundle();
        if (model.updateFlightData(flightNumber, field, newValue)) {
            System.out.println(res.getString("flightInformationUpdated"));
        } else {
            System.out.println(res.getString("flightDoesNotExist"));
        }
    }

    /**
     * To issue a ticket
     *
     * @param flight the flight of the reservation
     * @param client the client of the reservation
     */
    public void issueTicket(String flight, Client client) {
        ResourceBundle res = getRessourceBundle();
        if (model.issueTicket(flight, client)) {
            System.out.println(res.getString("ticketIssued"));
        } else {
            System.out.println(res.getString("noMoreSeats"));
        }
    }

    /**
     * To cancel a flight
     *
     * @param ticket the ticket number
     * @param passNumber the passport number
     */
    public void cancelFlight(int ticket, int passNumber) {
        ResourceBundle res = getRessourceBundle();
        if (model.cancelFlight(ticket, passNumber)) {
            System.out.println(res.getString("flightCanceled"));
        } else {
            System.out.println(res.getString("flightCanceledError"));
        }
    }

    /**
     * To get a map the flights table board
     *
     * @return map of the flights table
     */
    public Map<String, String> getBoard() {
        return Flight.viewBoard();
    }

    /**
     * To get a map the ReservedFlights table board
     *
     * @return map of the ReservedFlights table board
     */
    public Map<String, String> getBookedFlights() {
        return Flight.viewBookedFlights();
    }

    /**
     * To print the data of the map
     *
     * @param board the board to be printed
     */
    public void updateViewFlight(Map<String, String> board) {
        flightsView.printData(board);
    }

    // Client methods are below.
    /**
     * To book a seat
     *
     * @param flightNumber the flight number
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void bookASeat(String flightNumber) throws ClassNotFoundException, SQLException {
        ResourceBundle res = getRessourceBundle();
        if (client.bookASeat(flightNumber)) {
            System.out.println(res.getString("seatHasBeenBooked"));
        } else {
            System.out.println(res.getString("seatHasBeenBookedError"));
        }
    }

    /**
     * To cancel a reservation
     *
     * @param ticket the ticket number of the reservation
     * @throws SQLException
     */
    public void cancelReservation(int ticket) throws SQLException {
        ResourceBundle res = getRessourceBundle();
        if (client.cancelReservation(ticket)) {
            System.out.println(res.getString("reservationCancelled"));
        } else {
            System.out.println(res.getString("reservationCancelledError"));
        }
    }

    /**
     * Return a list of flights by their destination
     *
     * @param destination the destination to be searched
     * @return a list of flights by their destination
     */
    public List<Flight> getSearchFlightByDestination(String destination) {
        return client.searchFlightByDestination(destination);
    }

    /**
     * Return a list of flights by their destination
     *
     * @param duration the destination to be searched
     * @return a list of flights by their destination
     */
    public List<Flight> getSearchFlightByDuration(int duration) {
        return client.searchFlightByDuration(duration);
    }

    /**
     * Return a list of flights by their origin
     *
     * @param origin the origin to be searched
     * @return a list of flights by their origin
     */
    public List<Flight> getSearchFlightByOrigin(String origin) {
        return client.searchFlightByOrigin(origin);
    }

    /**
     *
     * @param flights
     */
    public void updateViewClient(List<Flight> flights) {
        clientsView.printData(flights);
    }

//    /**
//     *
//     * @param destination
//     * @throws SQLException
//     */
//    public void searchFlightByDestination(String destination) throws SQLException{
//        clientsView.printData(client.searchFlightByDestination(destination));
//    }
//    
//    /**
//     *
//     * @param origin
//     * @throws SQLException
//     */
//    public void searchFlightByOrigin(String origin) throws SQLException{
//        clientsView.printData(client.searchFlightByOrigin(origin));
//    }
    /**
     *
     * @throws SQLException
     */
    public void viewFlightBoard() throws SQLException {
        flightsView.printData(client.viewFlightBoard());
    }

    /**
     *
     * @return
     */
    public Locale getLocale() {
        Locale locale = InputOutputUser.locale;
        if (locale == null) {
            return new Locale("en", "CA");
        }
        return locale;
    }

    /**
     *
     * @return
     */
    public ResourceBundle getRessourceBundle() {
        ResourceBundle res = InputOutputUser.res;
        if (res == null) {
            return ResourceBundle.getBundle("progpatproject/OutputBundle", getLocale());
        }
        return res;
    }

//    public Client getClient(int passNum){
//        try{
//            Statement stmt = connection.createStatement();
//            String getClient = String.format("SELECT * FROM Clients WHERE PassNum = %d", passNum);
//        
//            ResultSet rs = stmt.executeQuery(getClient);
//            Client client = null;
//            while(rs.next()){
//                String fullName = rs.getString("FlName");
//                String contact = rs.getString("Contact");
//                client = new Client(fullName,passNum,contact);
//            }
//            if(client == null){
//                throw new Exception();
//            }
//            else return client;
//        }
//        catch(Exception e){
//            System.err.println("Error: " + e + ", Client does not exist.");
//            return null;
//        }
//    }    
}
