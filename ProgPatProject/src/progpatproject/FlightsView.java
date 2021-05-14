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
    
    public void printData(Map<String,String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
        System.out.println(entry.getKey() + " ->" + entry.getValue().toString());
        }
    }
}
