package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import utilities.AppointmentQuery;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Appointments implements Initializable {

    public ComboBox<Appointment> monthComboBox;
    public ComboBox<Appointment> typeComboBox;
    public Button cancelButton;
    public TableView<Appointment> appointmentsTable;
    public TableColumn monthCol;
    public TableColumn typeCol;
    public TableColumn totalCol;
    Stage stage;

    public void onActionFilterMonth(ActionEvent actionEvent) {
    }

    public void OnActionFilterType(ActionEvent actionEvent) {
    }

    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/reports.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentsTable.setItems(AppointmentQuery.select());
        monthCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));


    }
}
