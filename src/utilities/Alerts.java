package utilities;

import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {

    public static void selectCustomer() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR, "Please select a customer from the table.");
        alert.showAndWait();
    }

    public static void selectAppointment() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR, "Please select an appointment from the table.");
        alert.showAndWait();
    }

    public static void exit() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            System.exit(0);
        }
    }
}
