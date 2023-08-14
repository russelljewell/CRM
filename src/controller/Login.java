package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import utilities.Alerts;
import utilities.UserQuery;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {

    public Text locationText;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Button loginButton;
    public Button exitButton;
    boolean pass = false;


    public void onActionLogin(ActionEvent actionEvent) throws IOException {
        FileWriter fw = new FileWriter("login_activity.txt", true);
        PrintWriter pw = new PrintWriter(fw);
        for (User user : UserQuery.users()) {
            if (usernameTextField.getText().equals(user.getUserName()) && passwordTextField.getText().equals(user.getPassword())) {
                pass = true;
            }
        }
        if (pass == true) {
            pw.println("Successful Log-In: " + usernameTextField.getText() + " (" + LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")) + " UTC)");
            pw.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } else {
            pw.println("Failed Log-In: " + usernameTextField.getText() + " (" + LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")) + " UTC)");
            pw.close();
            Alerts.login();
        }
    }

    public void onActionExit(ActionEvent actionEvent) {
        Alerts.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Locale.getDefault().getLanguage().equals("fr")) {
            ResourceBundle rb = ResourceBundle.getBundle("resourceBundle", Locale.getDefault());
            usernameTextField.setPromptText(rb.getString("Username"));
            passwordTextField.setPromptText(rb.getString("Password"));
            loginButton.setText(rb.getString("Login"));
            exitButton.setText(rb.getString("Exit"));
        }

        locationText.setText(ZoneId.systemDefault().getId());

    }
}
