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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    public Text locationText;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    Stage stage;
    boolean pass = false;

    public void onActionLogin(ActionEvent actionEvent) throws IOException {
        for (User user : UserQuery.users()) {
            if (usernameTextField.getText().equals(user.getUserName()) && passwordTextField.getText().equals(user.getPassword())) {
                pass = true;
            }
        }
        if (pass == true) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            Alerts.login();
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
