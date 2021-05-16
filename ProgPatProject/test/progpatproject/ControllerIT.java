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
    }

    /**
     * Test of removeFlight method, of class Controller.
     */
    @Test
    public void testRemoveFlight() {
        System.out.println("removeFlight");
        String flightNumber = "";
        Controller instance = null;
        instance.removeFlight(flightNumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateFlightData method, of class Controller.
     */
    @Test
    public void testUpdateFlightData() {
        System.out.println("updateFlightData");
        String flightNumber = "";
        String field = "";
        String newValue = "";
        Controller instance = null;
        instance.updateFlightData(flightNumber, field, newValue);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of issueTicket method, of class Controller.
     */
    @Test
    public void testIssueTicket() {
        System.out.println("issueTicket");
        String flight = "";
        Client client = null;
        Controller instance = null;
        instance.issueTicket(flight, client);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cancelFlight method, of class Controller.
     */
    @Test
    public void testCancelFlight() {
        System.out.println("cancelFlight");
        int ticket = 0;
        int passNumber = 0;
        Controller instance = null;
        instance.cancelFlight(ticket, passNumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoard method, of class Controller.
     */
    @Test
    public void testGetBoard() {
        System.out.println("getBoard");
        Controller instance = null;
        Map<String, String> expResult = null;
        Map<String, String> result = instance.getBoard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBookedFlights method, of class Controller.
     */
    @Test
    public void testGetBookedFlights() {
        System.out.println("getBookedFlights");
        Controller instance = null;
        Map<String, String> expResult = null;
        Map<String, String> result = instance.getBookedFlights();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateViewFlight method, of class Controller.
     */
    @Test
    public void testUpdateViewFlight() {
        System.out.println("updateViewFlight");
        Map<String, String> board = null;
        Controller instance = null;
        instance.updateViewFlight(board);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of bookASeat method, of class Controller.
     */
    @Test
    public void testBookASeat() throws Exception {
        System.out.println("bookASeat");
        String flightNumber = "";
        Controller instance = null;
        instance.bookASeat(flightNumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cancelReservation method, of class Controller.
     */
    @Test
    public void testCancelReservation() throws Exception {
        System.out.println("cancelReservation");
        int ticket = 0;
        Controller instance = null;
        instance.cancelReservation(ticket);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSearchFlightByDestination method, of class Controller.
     */
    @Test
    public void testGetSearchFlightByDestination() {
        System.out.println("getSearchFlightByDestination");
        String destination = "";
        Controller instance = null;
        List<Flight> expResult = null;
        List<Flight> result = instance.getSearchFlightByDestination(destination);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSearchFlightByDuration method, of class Controller.
     */
    @Test
    public void testGetSearchFlightByDuration() {
        System.out.println("getSearchFlightByDuration");
        int duration = 0;
        Controller instance = null;
        List<Flight> expResult = null;
        List<Flight> result = instance.getSearchFlightByDuration(duration);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSearchFlightByOrigin method, of class Controller.
     */
    @Test
    public void testGetSearchFlightByOrigin() {
        System.out.println("getSearchFlightByOrigin");
        String origin = "";
        Controller instance = null;
        List<Flight> expResult = null;
        List<Flight> result = instance.getSearchFlightByOrigin(origin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateViewClient method, of class Controller.
     */
    @Test
    public void testUpdateViewClient() {
        System.out.println("updateViewClient");
        List<Flight> flights = null;
        Controller instance = null;
        instance.updateViewClient(flights);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of viewFlightBoard method, of class Controller.
     */
    @Test
    public void testViewFlightBoard() throws Exception {
        System.out.println("viewFlightBoard");
        Controller instance = null;
        instance.viewFlightBoard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLocale method, of class Controller.
     */
    @Test
    public void testGetLocale() {
        System.out.println("getLocale");
        Controller instance = null;
        Locale expResult = null;
        Locale result = instance.getLocale();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRessourceBundle method, of class Controller.
     */
    @Test
    public void testGetRessourceBundle() {
        System.out.println("getRessourceBundle");
        Controller instance = null;
        ResourceBundle expResult = null;
        ResourceBundle result = instance.getRessourceBundle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
