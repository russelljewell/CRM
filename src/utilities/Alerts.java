package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            JDBC.closeConnection();
            System.exit(0);
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
        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid credentials.");
        alert.showAndWait();
    }
}
