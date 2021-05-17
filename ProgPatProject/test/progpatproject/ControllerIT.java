/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progpatproject;

import java.sql.*;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Christian
 */
public class ControllerIT {
    
    public ControllerIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addFlight method, of class Controller.
     */
    @Test
    public void testAddFlight() {
        System.out.println("addFlight");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        instance.addFlight();
        int count = countData(String.format("SELECT COUNT(*) FROM Flights WHERE FlightN = 'F100'"));
        assertEquals(1, count);
        assertEquals(instance.getBoard().containsKey(res.getString("flightN") + " F100"), true);
        System.out.println("");
    }

    /**
     * Test of removeFlight method, of class Controller.
     */
    @Test
    public void testRemoveFlight() {
        System.out.println("removeFlight");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        instance.removeFlight("F100");
        int count = countData(String.format("SELECT COUNT(*) FROM Flights WHERE FlightN = 'F100'"));
        assertEquals(0,count);
        assertEquals(instance.getBoard().containsKey(res.getString("flightN") + "F100"), false );
        System.out.println("");
    }

    /**
     * Test of updateFlightData method, of class Controller.
     */
    @Test
    public void testUpdateFlightData() {
        
        // Need to fix.
        System.out.println("removeFlight");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        instance.addFlight();
        instance.updateFlightData("F100","Dest","Jamaica");
        int count = countData(String.format("SELECT COUNT(*) FROM Flights WHERE FlightN = 'F100' and Dest = 'California';"));
        assertEquals(0, count);
        System.out.println("");
    }

    /**
     * Test of issueTicket method, of class Controller.
     */
    @Test
    public void testIssueTicket() {
        System.out.println("removeFlight");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        instance.issueTicket("F100", new Client("Christian Kfoury", 2234, "2223334444"));
        int count = countData(String.format("SELECT COUNT(*) FROM ReservedFlights WHERE FlightN = 'F100' and PassNum = 2234;"));
        assertEquals(1, count);
        System.out.println("");
    }

    /**
     * Test of cancelFlight method, of class Controller.
     */
    @Test
    public void testCancelFlight() {
        System.out.println("cancelFlight");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        instance.cancelFlight(1,2234);
        int count = countData(String.format("SELECT COUNT(*) FROM ReservedFlights WHERE FlightN = 'F100' and PassNum = 2234;"));
        assertEquals(0, count);
        System.out.println("");
    }

    /**
     * Test of getBoard method, of class Controller.
     */
    @Test
    public void testGetBoard() {
        System.out.println("cancelFlight");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        Map<String,String> map = instance.getBoard();
        int boardSize = map.size();
        int count = countData(String.format("SELECT COUNT(*) FROM Flights;"));
        assertEquals(count, boardSize);
        System.out.println("");
    }

    /**
     * Test of getBookedFlights method, of class Controller.
     */
    @Test
    public void testGetBookedFlights() {
        System.out.println("getBookedFlights");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        Map<String,String> map = instance.getBookedFlights();
        int bookedFlightsSize = map.size();
        int count = countData(String.format("SELECT COUNT(*) FROM ReservedFlights;"));
        assertEquals(count, bookedFlightsSize);
        System.out.println("");
    }

    /**
     * Test of updateViewFlight method, of class Controller.
     */
    @Test
    public void testUpdateViewFlight() {
        System.out.println("updateViewFlight");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        instance.updateViewFlight(instance.getBoard());
    }

    /**
     * Test of bookASeat method, of class Controller.
     */
    @Test
    public void testBookASeat() throws Exception {
        System.out.println("getBookedFlights");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        instance.bookASeat("AC145");
        int count = countData("SELECT COUNT(*) FROM ReservedFlights WHERE FlightN = 'AC145' and PassNum = 2234;");
        assertEquals(1, count);
    }

    /**
     * Test of cancelReservation method, of class Controller.
     */
    @Test
    public void testCancelReservation() throws Exception {
        System.out.println("cancelReservation");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        instance.cancelReservation(1);
        int count = countData("SELECT COUNT(*) FROM ReservedFlights WHERE FlightN = 'AC145' and PassNum = 2234;");
        assertEquals(0, count);
    }

    /**
     * Test of getSearchFlightByDestination method, of class Controller.
     */
    @Test
    public void testGetSearchFlightByDestination() {
        System.out.println("getSearchFlightByDestination");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        List<Flight> list = instance.getSearchFlightByDestination("Alaska");
        int count = countData("Select Count(*) FROM Flights WHERE Dest = 'Alaska';");
        assertEquals(count, list.size());
    }

    /**
     * Test of getSearchFlightByDuration method, of class Controller.
     */
    @Test
    public void testGetSearchFlightByDuration() {
        System.out.println("getSearchFlightByDuration");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        List<Flight> list = instance.getSearchFlightByDuration(100);
        int count = countData("Select Count(*) FROM Flights WHERE Duration = 100;");
        assertEquals(count, list.size());
    }

    /**
     * Test of getSearchFlightByOrigin method, of class Controller.
     */
    @Test
    public void testGetSearchFlightByOrigin() {
        System.out.println("getSearchFlightByOrigin");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        List<Flight> list = instance.getSearchFlightByOrigin("Canada");
        int count = countData("Select Count(*) FROM Flights WHERE Origin = 'Canada';");
        assertEquals(count, list.size());
    }

    /**
     * Test of updateViewClient method, of class Controller.
     */
    @Test
    public void testUpdateViewClient() {
        System.out.println("updateViewClient");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        instance.updateViewClient(instance.getSearchFlightByDestination("Alaska"));
    }

    /**
     * Test of viewFlightBoard method, of class Controller.
     */
    @Test
    public void testViewFlightBoard() throws Exception {
        System.out.println("viewFlightBoard");
        Controller instance = new Controller(new Flight("F100", "American Airlines", "Laval", "California", 10, 10, 10, 10), new Client("Christian Kfoury", 2234, "2223334444"));
        ResourceBundle res = instance.getRessourceBundle();
        instance.viewFlightBoard();
    }
    
    public static int countData(String queryTable) {
        Connection connection = SingleConnection.getInstance();
        int count = -1;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryTable);

            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return count;
    } 
}
