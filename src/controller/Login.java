package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import utilities.Alerts;
import utilities.UserQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login implements Initializable {

    public Text locationText;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    Stage stage;

    public void onActionLogin(ActionEvent actionEvent) {
        for (User user : UserQuery.users()) {
            if (usernameTextField.getText().equals(user.getUserID()) && passwordTextField.getText().equals(user.getPassword())) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
                    stage.setTitle("Customer Relationship Manager");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alerts.login();
            }
        }
    }

    public void onActionExit(ActionEvent actionEvent) {
        Alerts.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //locationText.setText();
    }
}
