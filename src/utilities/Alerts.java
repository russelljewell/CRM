package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is a custom alerts class. This class contains custom alerts to facilitate more readable code in the controllers. */
public class Alerts {

    /** This method informs the user that no customer is selected. This method is called when the user attempts to modify customer information before indicating which customer should be modified. */
    public static void selectCustomer() {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR, "Please select a customer from the table.");
        alert.showAndWait();
    }

    /** This method informs the user that no appointment is selected. This method is called when the user attempts to modify appointment information before indicating which appointment should be modified. */
    public static void selectAppointment() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment from the table.");
        alert.showAndWait();
    }

    /** This method confirms whether the user wishes to terminate the program. This method is called when the Exit button is pressed. */
    public static void exit() {
        ResourceBundle rb = ResourceBundle.getBundle("resourceBundle", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("Prompt"), ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                JDBC.closeConnection();
                System.exit(0);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                JDBC.closeConnection();
                System.exit(0);
            }
        }
    }

    /** This method informs the user of blank text fields. This method is called when the user attempts to save changes made to a new or existing appointment or customer. */
    public static void invalid() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Text fields cannot be blank.");
        alert.showAndWait();
    }

    /** This method informs the user of invalid time input. This method is called when a user attempts to create an appointment in which the start-time is later than the end-time.*/
    public static void invalidTime() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Start time cannot be later than end time.");
        alert.showAndWait();
    }

    /** This method informs the user of invalid login credentials. */
    public static void login() {
        ResourceBundle rb = ResourceBundle.getBundle("resourceBundle", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("Invalid"));
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid credentials.");
            alert.showAndWait();
        }
    }

    /** This method informs the user of invalid time input. This method is called when a user attempts to schedule an appointment outside of business hours.*/
    public static void businessHours() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Appointments must be scheduled within business hours.");
        alert.showAndWait();
    }

    /** This method informs the user of an upcoming appointment. This method is called when an appointment will begin within 15 minutes of the user's login.
     * @param id Scheduled customer's Customer ID.
     * @param date Date of appointment.
     * @param time Start time of appointment.
     * */
    public static void upcoming(int id, LocalDate date, LocalTime time) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Upcoming Appointment \n\n" + "Customer ID: " + id + "\nDate: " + date + "\nTime: " + time);
        alert.showAndWait();
    }

    /** This method informs the user that there are no upcoming appointments. This method is called when no appointments begin within 15 minutes of the user's login. */
    public static void noUpcoming() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have no upcoming appointments.");
        alert.showAndWait();
    }

    /** This method informs the user of overlapping appointments. This method is called when the user attempts to create an appointment which conflicts with an existing appointment. */
    public static void overlap() {
            Alert alert = new Alert(Alert.AlertType.ERROR, "This customer has an existing appointment which overlaps with the times provided.");
            alert.showAndWait();
    }
}

