package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import utilities.Alerts;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    public Text locationText;

    public void onActionLogin(ActionEvent actionEvent) {
    }

    public void onActionExit(ActionEvent actionEvent) {
        Alerts.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //locationText.setText();
    }
}
