package progpatproject;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Christian
 */
public class Client {
    private Connection connection = SingleConnection.getInstance();
    private String fullName;
    private int passNumber;
    private String contact;

    public Client(String fullName, int passNumber, String contact) {
        this.fullName = fullName;
        this.passNumber = passNumber;
        this.contact = contact;
    }
    
    public boolean bookASeat(String flightNumber){
        try{
            Statement stmt = connection.createStatement();
            Flight flight = null;
            String flNb = "'" + flightNumber + "'";
            String getFlight = "SELECT * FROM Flights WHERE flightN = " + flNb + ";";
            ResultSet rs = stmt.executeQuery(getFlight);
            while(rs.next()){
                String name = rs.getString("Name");
                String origin = rs.getString("Origin");
                String dest = rs.getString("Dest");
                int duration = rs.getInt("Duration");
                int totalSeats = rs.getInt("Seats");
                int availSeats = rs.getInt("Available");
                double price = rs.getDouble("Amount");

                flight = new Flight(flightNumber,name,origin,dest,duration,totalSeats,availSeats,price);
            }
            try{
                if (flight.getAvailableSeats() <= 0){
                    return false;
                }
                else{
                    stmt = connection.createStatement();
                    int availSeats = flight.getAvailableSeats() - 1;
                    String remove1FromAvailSeats = "UPDATE Flights SET Available = " + availSeats + " WHERE flightN = " + flNb + ";";
                    stmt.executeUpdate(remove1FromAvailSeats);

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-mm-dd");
                    LocalDateTime now = LocalDateTime.now();


                    String addEntry = String.format("INSERT INTO ReservedFlights VALUES('%s',%d,'%s',"
                            + "'%s','%s',%f);", flightNumber, getPassNumber(),getFullName(),
                            "" + dtf.format(now),getContact(),flight.getAmount());
                    stmt.executeUpdate(addEntry);
                    return true;
                }
            }            
            catch(SQLException e){
                System.err.println("Error: " + e + " , Inserting in ReservedFlights Table.");
                return false;
            }  
        }
        catch (SQLException e){
            System.err.println("Error: " + e + " Seat could not be added.");
            return false;
        }
    }
    
    public boolean cancelReservation(int ticket) throws SQLException {
        
        if (ticket <= 0){
            return false;
        }
        
        Statement stmt = connection.createStatement();
        
        String getReservedFlight = "SELECT * FROM ReservedFlights WHERE ticketN = " + ticket + ";";
        ResultSet rs = stmt.executeQuery(getReservedFlight);
        String flightNumber = null;
        if(rs == null){
            return false;
        }
        else{
            while (rs.next()){
                flightNumber = rs.getString("flightN");
            }
            stmt = connection.createStatement();
            String removeReservedFlight = "DELETE FROM ReservedFlights WHERE ticketN = " + ticket + ";";
            stmt.executeUpdate(removeReservedFlight);
            
            stmt = connection.createStatement();
            String getCurrentFlightAvail = "SELECT * FROM Flights WHERE flightN = " + "'" + flightNumber + "'" + ";";
            rs = stmt.executeQuery(getCurrentFlightAvail);
            int currentAvail = 0;
            while(rs.next()){
                currentAvail = rs.getInt("Available");
            }
            currentAvail++;
            
            stmt = connection.createStatement();
            String add1Availability = "UPDATE Flights SET Available = "
                    + "" + currentAvail + " WHERE flightN = " + "'" + flightNumber + "';";
            stmt.executeUpdate(add1Availability);
           
            return true; 
        }
    }
     
    public List<Flight> searchFlightByDestination(String destination){
       try{
            Statement stmt = connection.createStatement();
            String destinationString = "'" + destination + "'";
            String getFlight = "SELECT * FROM Flights WHERE Dest = " + destinationString + " ORDER BY flightN;";
            Flight flight = null;
            List<Flight> list = null;

            ResultSet rs = stmt.executeQuery(getFlight);
                while(rs.next()){
                String number = rs.getString("flightN");
                String name = rs.getString("Name");
                String origin = rs.getString("Origin");
                int duration = rs.getInt("Duration");
                int totalSeats = rs.getInt("Seats");
                int availSeats = rs.getInt("Available");
                double price = rs.getDouble("Amount");

                flight = new Flight(number,name,origin,destination,duration,totalSeats,availSeats,price);
                list.add(flight);
            }
            return list;
       }
       catch(SQLException e){
           System.err.println("Error: " + e + " , Flights could not be pulled.");
           return null;
       }
    }
    
    public List<Flight> serarchFlightByDuration(int duration){
        try{
            Statement stmt = connection.createStatement();
            String getFlight = "SELECT * FROM Flights WHERE duration = " + duration + " ORDER BY flightN;";
            Flight flight = null;
            List<Flight> list = null;

            ResultSet rs = stmt.executeQuery(getFlight);
             while(rs.next()){
             String number = rs.getString("flightN");
             String name = rs.getString("Name");
             String origin = rs.getString("Origin");
             String destination = rs.getString("Dest");
             int totalSeats = rs.getInt("Seats");
             int availSeats = rs.getInt("Available");
             double price = rs.getDouble("Amount");

             flight = new Flight(number,name,origin,destination,duration,totalSeats,availSeats,price);
             list.add(flight);
             }
             return list;
        }
        catch (SQLException e){
           System.err.println("Error: " + e + " , Flights could not be pulled.");
           return null;
       }  
    }
    
    public List<Flight> searchFlightByOrigin(String origin){
        try{
            Statement stmt = connection.createStatement();
            String flightOrigin = "'" + origin + "'";
            String getFlight = "SELECT * FROM Flights WHERE Origin = " + flightOrigin + " ORDER BY flightN;";
            Flight flight = null;
            List<Flight> list = null;

            ResultSet rs = stmt.executeQuery(getFlight);
             while(rs.next()){
             String number = rs.getString("flightN");
             String name = rs.getString("Name");
             int duration = rs.getInt("Duration");
             String destination = rs.getString("Dest");
             int totalSeats = rs.getInt("Seats");
             int availSeats = rs.getInt("Available");
             double price = rs.getDouble("Amount");

             flight = new Flight(number,name,origin,destination,duration,totalSeats,availSeats,price);
             list.add(flight);
             }
             return list;
        }
        catch(SQLException e){
           System.err.println("Error: " + e + " , Flights could not be pulled.");
           return null;
        }
    }
    
    public Map<String, String> viewFlightBoard() throws SQLException {
        try{
            Statement stmt = connection.createStatement();
            String getFlight = "SELECT * FROM Flights ORDER BY flightN;";
            Flight flight = null;
            Map<String, String> map = null;


            ResultSet rs = stmt.executeQuery(getFlight);
             while(rs.next()){
             String number = rs.getString("flightN");
             String name = rs.getString("Name");
             String origin = rs.getString("Origin");
             int duration = rs.getInt("Duration");
             String destination = rs.getString("Dest");
             int totalSeats = rs.getInt("Seats");
             int availSeats = rs.getInt("Available");
             double price = rs.getDouble("Amount");

             flight = new Flight(number,name,origin,destination,duration,totalSeats,availSeats,price);
             map.put(number, String.format("{Aircraft Name: %s, Origin: %s, Destination: %s,"
                     + " Duration: %d, Total Seats: %d, Available Seats: %d, Price: %f }"
                     ,name,origin,destination,duration,totalSeats,availSeats,price));
             }
            return map;
        }
        catch(SQLException e){
            System.err.println("Error: " + e + " , Flights board could not be pulled.");
           return null;
        }
        
    }

    public String getFullName() {
        return fullName;
    }

    public int getPassNumber() {
        return passNumber;
    }

    public String getContact() {
        return contact;
    }
    
}
