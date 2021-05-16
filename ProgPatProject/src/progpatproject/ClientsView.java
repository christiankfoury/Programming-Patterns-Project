package progpatproject;

import java.sql.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Christian
 */
public class ClientsView {

    /**
     * To print the data of a list of flights
     *
     * @param flights the list of flights to be printed
     */
    public void printData(List<Flight> flights) {
        Locale locale = InputOutputUser.locale;
        if (locale == null) {
            locale = new Locale("en", "CA");
        }
        ResourceBundle res = InputOutputUser.res;
        if (res == null) {
            res = ResourceBundle.getBundle("progpatproject/OutputBundle", locale);
        }
        
        for (Flight flight : flights) {
            System.out.println(String.format("%s %s, %s %s, "
                    + "%s %s, %s %s, %s %d, %s %d, "
                    + "%s %d, %s %f", 
                    res.getString("flightN"), flight.getFlightN(), 
                    res.getString("name"), flight.getName(),
                    res.getString("origin"), flight.getOrigin(), 
                    res.getString("dest"), flight.getDestination(), 
                    res.getString("duration"), flight.getDuration(),
                    res.getString("seats"), flight.getSeats(), 
                    res.getString("available"), flight.getAvailableSeats(), 
                    res.getString("amount"), flight.getAmount()));
        }
    }
}
