package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {

    public static void selectCustomer() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer from the table.");
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
            System.exit(0);
        }
    }
}
