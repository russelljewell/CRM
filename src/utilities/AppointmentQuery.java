package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;

public abstract class AppointmentQuery {

    public static int insert(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) throws SQLException {
        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int appointmentID, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
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
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int appointmentID) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int deleteAssociated(int customerID) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

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
}


