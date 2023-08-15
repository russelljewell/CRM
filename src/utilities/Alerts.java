package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class Alerts {

    public static void selectCustomer() {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR, "Please select a customer from the table.");
        alert.showAndWait();
    }

    public static void selectAppointment() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment from the table.");
        alert.showAndWait();
    }

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

    public static void invalid() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Text fields cannot be blank.");
        alert.showAndWait();
    }

    public static void invalidTime() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Start time cannot be later than end time.");
        alert.showAndWait();
    }

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
}
