package progpatproject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Christian
 */
public class Controller {
    private final Connection connection = SingleConnection.getInstance();
    private Flight model;
    private FlightsView flightsView;
    private Client client;
    private ClientsView clientsView;

    public Controller(Flight flight, Client client) {
        this.model = flight;
        this.client = client;
    }
    
    /*
    Fix error print messages.
    */

    public void addFlight(){
        if(model.addFlight(model)){
            System.out.println("Flight has been successfully added!");
        }
        else{
            System.err.println("Check your input, flight has not been added.");
        }
    }
    
    public void removeFlight(String flightNumber){
        if(model.removeFlight(flightNumber)){
            System.out.println("Flight has been successfully removed!");
        }
        else{
            System.err.println("Check your input, flight has not been removed.");
        }
    }
    
    public void updateFlightData(String flightNumber, String field, String newValue){
        if(model.updateFlightData(flightNumber, field, newValue)){
            System.out.println("Flight information has been updated.");
        }
        else{
            System.err.println("");
        }
    }
    
    public void issueTicket(String flight, Client client){
        if(model.issueTicket(flight, client)){
            System.out.println("A ticket has been issued to the client.");
        }
        else{
            System.err.println("");
        }
    }
    
    public void cancelFlight(int ticket, int passNumber){
        if(model.cancelFlight(ticket, passNumber)){
            System.out.println("The flight has been successfully canceled.");
        }
        else{
            System.err.println("");
        }
    }
    
    public void viewBoard(){
        flightsView.printData(Flight.viewBoard());
    }
    
    public void viewBookedFlights(){
        flightsView.printData(Flight.viewBoard());
    }
    
    
    // Client methods are below.
    
    
    public void bookASeat(String flightNumber) throws ClassNotFoundException, SQLException{
        if(client.bookASeat(flightNumber)){
            System.out.println("A Seat has been booked!");
        }
        else{
            System.err.println("");
        }
    }
    
    public void cancelReservation(int ticket) throws SQLException{
        if(client.cancelReservation(ticket)){
            System.out.println("A Reservation has been cancelled.");
        }
        else{
            System.err.println("");
        }
    }
    
    public void searchFlightByDestination(String destination) throws SQLException{
        clientsView.printData(client.searchFlightByDestination(destination));
    }
    
    public void searchFlightByOrigin(String origin) throws SQLException{
        clientsView.printData(client.searchFlightByOrigin(origin));
    }
    
    public void viewFlightBoard() throws SQLException{
        flightsView.printData(client.viewFlightBoard());
    }
    
}
