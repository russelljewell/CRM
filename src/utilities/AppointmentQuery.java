/**
 * @author Russell Jewell
 * Customer Relationshop Manager
 * Appointment DAO
 * */

package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;

/** This class contains database interface methods. This class manipulates data pertaining to Appointment objects.  */
public abstract class AppointmentQuery {

    /** This method inserts appointment data into a database.
     * @param title Appointment title.
     * @param description Appointment description.
     * @param location Appointment location.
     * @param type Appointment type.
     * @param start Appointment start date and time.
     * @param end Appointment end date and time.
     * @param customerID Customer's ID.
     * @param userID User's ID.
     * @param contactID Contact's ID.
     * @return rowsAffected The number of rows affected by the INSERT statement.
     * */
    public static int insert(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) {
        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int rowsAffected = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowsAffected;
    }

    /** This method updates appointment data in the database for a specific appointment ID.
     * @param appointmentID The ID of the appointment being updated.
     * @param title Appointment title.
     * @param description Appointment description.
     * @param location Appointment location.
     * @param type Appointment type.
     * @param start Appointment start date and time.
     * @param end Appointment end date and time.
     * @param customerID Customer's ID.
     * @param userID User's ID.
     * @param contactID Contact's ID.
     * @return rowsAffected The number of rows affected by the UPDATE statement.
     * */
    public static int update(int appointmentID, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) {
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        int rowsAffected = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            ps.setInt(10, appointmentID);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowsAffected;
    }

    /** This method deletes appointments of a specific appointment ID from the database.
     * @param appointmentID The ID of the appointment being deleted.
     * @return rowsAffected The number of rows affected by the DELETE statement.
     * */
    public static int delete(int appointmentID) {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        int rowsAffected = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointmentID);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowsAffected;
    }

    /** This method deletes all appointments associated with a specific customer ID from the database.
     * @param customerID The customer ID whose appointments will be deleted.
     * @return rowsAffected The number of rows affected by the DELETE statement.
     * */
    public static int deleteAssociated(int customerID) {
        String sql = "DELETE FROM APPOINTMENTS WHERE Customer_ID = ?";
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

    /** This method selects all appointments from a database into an Observable List.
     * @return allAppointments The Observable List containing all appointments.
     * */
    public static ObservableList<Appointment> select() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end, customerID, userID, contactID);
                allAppointments.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allAppointments;
    }

    /** This method selects all appointments associated with a customer ID into an Observable List.
     * @param selectedCustomerID The ID of the customer selected in the customer table.
     * @return allAssociated The Observable List containing all associated appointments.
     * */
    public static ObservableList<Appointment> selectAssociated(int selectedCustomerID) {
        ObservableList<Appointment> allAssociated = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, selectedCustomerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end, customerID, userID, contactID);
                allAssociated.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allAssociated;
    }

    /** This method returns the total number of appointments of a given month and year This method is used in the bar-chart.
     * @param month The specified month to be selected from.
     * @param year The specified year to be selected from.
     * @return total The total number of appointments.
     * */
    public static int monthlyTotal(int month, int year) {
        String sql = "SELECT COUNT(*) AS total FROM APPOINTMENTS WHERE MONTH(Start) = ? AND YEAR(Start) = ?";
        int total = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return total;
    }

    /** This method returns an Observable List of appointments associated with a specific contact ID This method is used in the Schedule report..
     * @param contactID Contact's ID.
     * @return allAssociated The Observable List containing all associated appointments.
     * */
    public static ObservableList<Appointment> report(int contactID) {
        ObservableList<Appointment> allAssociated = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE Contact_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end, customerID, userID, contactID);
                allAssociated.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allAssociated;
    }

    /** This method returns an Observable List of all appointment types from the database. This method is used in the Month/Type report's combo-box.
     * @return allTypes An Observable List of all appointment types.
     * */
    public static ObservableList<Appointment> type() {
        ObservableList allTypes = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Type");
                allTypes.add(type);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allTypes;
    }

    /** This method returns the total number of appointments of a given type and month. This method is used in the Month/Type report.
     * @param type Appointment type.
     * @param month The month to be selected from.
     * @return total The total number of appointments.
     * */
    public static int total(String type, int month) {
        String sql = "SELECT COUNT(*) AS total FROM APPOINTMENTS WHERE Type = ? AND MONTH(Start) = ? ";
        int total = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, type);
            ps.setInt(2, month);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return total;
    }
}




