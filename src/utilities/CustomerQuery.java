package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class contains database interface methods. This class queries data pertaining to Customer objects. */
public abstract class CustomerQuery {

    /** This method inserts customer data into a database.
     * @param customerName Customer name.
     * @param address Customer address.
     * @param postalCode Customer postal code.
     * @param phoneNumber Customer phone number.
     * @param divisionID Customer division ID.
     * @return rowsAffected The number of rows affected by the INSERT statement.
     * */
    public static int insert(String customerName, String address, String postalCode, String phoneNumber, int divisionID) {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        int rowsAffected = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setInt(5, divisionID);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowsAffected;
    }

    /** This method updates customer data in the database for a specific customer ID
     * @param customerID The ID of the customer being updated.
     * @param customerName Customer name.
     * @param address Customer address.
     * @param postalCode Customer postal code.
     * @param phoneNumber Customer phone number.
     * @param divisionID Customer division ID.
     * @return rowsAffected The number of rows affected by the UPDATE statement.
     * */
    public static int update(int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID) {
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        int rowsAffected = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setInt(5, divisionID);
            ps.setInt(6, customerID);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowsAffected;
    }

    /** This method deletes a customer of a specific customer ID from the database.
     * @param customerID The ID of the customer being deleted.
     * @return rowsAffected The number of rows affected by the DELETE statement.
     * */
    public static int delete(int customerID) {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        int rowsAffected = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowsAffected;
    }

    /** This method selects all customers from a database into an Observable List.
     * @return allCustomers The Observable List containing all customers.
     * */
    public static ObservableList<Customer> select() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT CUSTOMERS.Customer_ID, Customer_Name, Address, Postal_Code, Phone, CUSTOMERS.Division_ID, FIRST_LEVEL_DIVISIONS.Division, FIRST_LEVEL_DIVISIONS.Country_ID, COUNTRIES.Country FROM CUSTOMERS, FIRST_LEVEL_DIVISIONS, COUNTRIES WHERE FIRST_LEVEL_DIVISIONS.Country_ID = COUNTRIES.Country_ID AND CUSTOMERS.Division_ID = FIRST_LEVEL_DIVISIONS.Division_ID";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                int countryID = rs.getInt("Country_ID");
                String divisionName = rs.getString("Division");
                String countryName = rs.getString("Country");
                Customer customer = new Customer(customerID, customerName, address, postalCode, phoneNumber, divisionID, countryID, divisionName, countryName);
                allCustomers.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCustomers;
    }
}
