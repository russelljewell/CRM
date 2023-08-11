package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import utilities.Alerts;
import utilities.AppointmentQuery;
import utilities.ContactQuery;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Reports implements Initializable {

    public ComboBox<Contact> contactsComboBox;
    public TextField yearTF;
    public BarChart barChart;
    public ComboBox<Appointment> typeComboBox;
    public ComboBox<Appointment> monthComboBox;
    public Text totalText;
    public TableView<Appointment> scheduleTable;
    public TableColumn appointmentIdCol;
    public TableColumn customerIdCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;

    public void onActionGenerateSchedule(ActionEvent actionEvent) {
        if (contactsComboBox.getValue() != null) {
            for (Contact contact: contactsComboBox.getItems()) {
                if (contact.getContactID() == contactsComboBox.getValue().getContactID()) {
                    scheduleTable.setItems(AppointmentQuery.report(contactsComboBox.getValue().getContactID()));
                }
            }
        }
    }

    public void onActionGenerateTotals(ActionEvent actionEvent) {
        if (yearTF.getText().isBlank()) {
            Alerts.invalid();
        } else {
            XYChart.Series data = new XYChart.Series();
            data.getData().add(new XYChart.Data("January", AppointmentQuery.monthlyTotal(1, Integer.parseInt(yearTF.getText()))));
            data.getData().add(new XYChart.Data("February", AppointmentQuery.monthlyTotal(2, Integer.parseInt(yearTF.getText()))));
            data.getData().add(new XYChart.Data("March", AppointmentQuery.monthlyTotal(3, Integer.parseInt(yearTF.getText()))));
            data.getData().add(new XYChart.Data("April", AppointmentQuery.monthlyTotal(4, Integer.parseInt(yearTF.getText()))));
            data.getData().add(new XYChart.Data("May", AppointmentQuery.monthlyTotal(5, Integer.parseInt(yearTF.getText()))));
            data.getData().add(new XYChart.Data("June", AppointmentQuery.monthlyTotal(6, Integer.parseInt(yearTF.getText()))));
            data.getData().add(new XYChart.Data("July", AppointmentQuery.monthlyTotal(7, Integer.parseInt(yearTF.getText()))));
            data.getData().add(new XYChart.Data("August", AppointmentQuery.monthlyTotal(8, Integer.parseInt(yearTF.getText()))));
            data.getData().add(new XYChart.Data("September", AppointmentQuery.monthlyTotal(9, Integer.parseInt(yearTF.getText()))));
            data.getData().add(new XYChart.Data("October", AppointmentQuery.monthlyTotal(10, Integer.parseInt(yearTF.getText()))));
            data.getData().add(new XYChart.Data("November", AppointmentQuery.monthlyTotal(11, Integer.parseInt(yearTF.getText()))));
            data.getData().add(new XYChart.Data("December", AppointmentQuery.monthlyTotal(12, Integer.parseInt(yearTF.getText()))));
            barChart.getData().addAll(data);
        }
    }

    public void onActionGenerateType(ActionEvent actionEvent) {
        if (typeComboBox.getValue() != null && monthComboBox.getValue() != null) {
            for (Appointment type : typeComboBox.getItems()) {
                if (type.getType() == typeComboBox.getValue().getType()) {
                    String selectedType = typeComboBox.getValue().getType();
                }
            }
        }


    }

    public void onActionReturn(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void onActionExit(ActionEvent actionEvent) {
        Alerts.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        contactsComboBox.setItems(ContactQuery.contacts());
        typeComboBox.setItems(AppointmentQuery.type());
        monthComboBox.setItems();


        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("end"));


    }
}
