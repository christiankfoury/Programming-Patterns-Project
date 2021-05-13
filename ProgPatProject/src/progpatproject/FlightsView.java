package progpatproject;

import java.sql.*;
import java.util.Map;
/**
 *
 * @author Christian
 */
public class FlightsView {
    private Connection connection;

    public FlightsView(Connection connection) {
        this.connection = connection;
    }
    
    public void printData(Map map) {
        System.out.println(map);
    }
}
