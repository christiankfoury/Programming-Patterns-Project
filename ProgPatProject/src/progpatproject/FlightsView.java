package progpatproject;

import java.sql.*;
import java.util.Map;
/**
 *
 * @author Christian
 */
public class FlightsView {
    
    /**
     * Prints the value of a map containing data of flights
     * @param map the map to be printed
     */
    public void printData(Map<String,String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
        System.out.println(entry.getKey() + " ->" + entry.getValue().toString());
        }
    }
}
