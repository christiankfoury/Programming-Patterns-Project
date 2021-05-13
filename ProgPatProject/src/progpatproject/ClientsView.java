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
        System.out.println(flights);
    }
}
