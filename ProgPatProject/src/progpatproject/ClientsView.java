package progpatproject;

import java.sql.*;
import java.util.List;

/**
 *
 * @author Christian
 */
public class ClientsView {
    private Connection connection;

    public ClientsView(Connection connection) {
        this.connection = connection;
    }
    
    public void printData(List<Flight> flights) {
        for(Flight flight: flights){
            System.out.println(String.format("Flight Number: %s, Aircraft Name: %s, "
                    + "Origin: %s, Destination: %s, Duration: %d, Seats: %d, "
                    + "Available: %d, Amount: %f",flight.getFlightN(),flight.getName(),
                    flight.getOrigin(),flight.getDestination(),flight.getDuration(),
                    flight.getSeats(),flight.getAvailableSeats(),flight.getAmount()));
        }
    }
}
