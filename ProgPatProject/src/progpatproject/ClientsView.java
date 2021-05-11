package progpatproject;

import java.sql.*;

/**
 *
 * @author Christian
 */
public class ClientsView {
    private Connection connection;

    public ClientsView(Connection connection) {
        this.connection = connection;
    }
    
    public void printClientsDetails() {
        try {
            Statement statement = connection.createStatement();
            String queryTable = "select * from Clients;";
            ResultSet resultSet = statement.executeQuery(queryTable);

            while (resultSet.next()) {
                System.out.println("PASS NUM = " + resultSet.getString("PassNum"));
                System.out.println("FLNAME = " + resultSet.getString("FlName"));
                System.out.println("CONTACT = " + resultSet.getString("Contact") + "\n");
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
