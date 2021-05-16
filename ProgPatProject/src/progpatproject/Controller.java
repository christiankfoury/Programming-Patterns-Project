package progpatproject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Christian
 */
public class Controller {
    private final Connection connection = SingleConnection.getInstance();
    private Flight model;
    private FlightsView flightsView = new FlightsView(connection);
    private Client client;
    private ClientsView clientsView = new ClientsView(connection);

    public Controller(Flight flight, Client client) {
        this.model = flight;
        this.client = client;
    }
    
    /*
    Fix error print messages.
    */

    public void addFlight(){
        if(model.addFlight(model)){
            InputOutput.printChosenLanguage("successfullyAddedFlight");
        }
        else{
            InputOutput.printChosenLanguage("flightAlreadyExists");
        }
    }
    
    public void removeFlight(String flightNumber){
        if(model.removeFlight(flightNumber)){
            InputOutput.printChosenLanguage("successfullyRemovedFlight");
        }
        else{
            InputOutput.printChosenLanguage("flightDoesNotExist");
        }
    }
    
    public void updateFlightData(String flightNumber, String field, String newValue){
        if(model.updateFlightData(flightNumber, field, newValue)){
            InputOutput.printChosenLanguage("flightInformationUpdated");
        }
        else{
            InputOutput.printChosenLanguage("flightDoesNotExist");
        }
    }
    
    public void issueTicket(String flight, Client client){
        if(model.issueTicket(flight, client)){
            InputOutput.printChosenLanguage("ticketIssued");
        }
        else{
            InputOutput.printChosenLanguage("noMoreSeats");
        }
    }
    
    public void cancelFlight(int ticket, int passNumber){
        if(model.cancelFlight(ticket, passNumber)){
            InputOutput.printChosenLanguage("flightCanceled");
        }
        else{
            InputOutput.printChosenLanguage("flightCanceledError");
        }
    }
    
    public void viewBoard(){
        flightsView.printData(Flight.viewBoard());
    }
    
    public void viewBookedFlights(){
        flightsView.printData(Flight.viewBookedFlights());
    }
    
    
    // Client methods are below.
    
    
    public void bookASeat(String flightNumber) throws ClassNotFoundException, SQLException{
        if(client.bookASeat(flightNumber)){
            InputOutput.printChosenLanguage("seatHasBeenBooked");
        }
        else{
            InputOutput.printChosenLanguage("seatHasBeenBookedError");
        }
    }
    
    public void cancelReservation(int ticket) throws SQLException{
        if(client.cancelReservation(ticket)){
            InputOutput.printChosenLanguage("reservationCancelled");
        }
        else{
            InputOutput.printChosenLanguage("reservationCancelledError");
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
    
    public Client getClient(int passNum){
        try{
            Statement stmt = connection.createStatement();
            String getClient = String.format("SELECT * FROM Clients WHERE PassNum = %d", passNum);
        
            ResultSet rs = stmt.executeQuery(getClient);
            Client client = null;
            while(rs.next()){
                String fullName = rs.getString("FlName");
                String contact = rs.getString("Contact");
                client = new Client(fullName,passNum,contact);
            }
            if(client == null){
                throw new Exception();
            }
            else return client;
        }
        catch(Exception e){
            System.err.println("Error: " + e + ", Client does not exist.");
            return null;
        }
    }
    
}
