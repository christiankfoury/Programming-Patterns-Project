package progpatproject;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Christian
 */
public class Client {
    private String fullName;
    private int passNumber;
    private String contact;

    public Client(String fullName, int passNumber, String contact) {
        this.fullName = fullName;
        this.passNumber = passNumber;
        this.contact = contact;
    }
    
//    public boolean bookASeat(String flightNumber){
//        
//    }
    
//    public boolean cancelReservation(int ticket) {
//        
//    }
    
//    public List<Flight> searchFlightByDestination(String destination) {
//        
//    }
    
//    public List<Flight> serarchFlightByDuration(int duration) {
//        
//    }
    
//    public List<Flight> searchFlightByOrigin(String origin) {
//        
//    }
    
//    public Map<String, String> viewFlightBoard() {
//        
//    }

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
