/**
 * @author Russell Jewell
 * Customer Relationshop Manager
 * Reports Controller
 * */

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
import java.time.Month;
import java.util.ResourceBundle;

/** This class defines the functionality of the reports screen. */
public class Reports implements Initializable {

    public ComboBox<Contact> contactsComboBox;
    public TextField yearTF;
    public BarChart barChart;
    public ComboBox<Appointment> typeComboBox;
    public ComboBox<Month> monthComboBox;
    public Text totalText;
    public TableView<Appointment> scheduleTable;
    public TableColumn appointmentIdCol;
    public TableColumn customerIdCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;

    /** This method displays contact-specific appointments in a table view. The user selects a contact from a combo-box which populates the table view with their associated appointments.
     * @param actionEvent The selection of a contact in a combo-box.
     * */
    public void onActionGenerateSchedule(ActionEvent actionEvent) {
        if (contactsComboBox.getValue() != null) {
            for (Contact contact: contactsComboBox.getItems()) {
                if (contact.getContactID() == contactsComboBox.getValue().getContactID()) {
                    scheduleTable.setItems(AppointmentQuery.report(contactsComboBox.getValue().getContactID()));
                }
            }
        }
    }

    /** This method displays the total appointments for each month in a bar chart. The user inputs a year into a text-field. which populates the chart with the totals for each month in that year.
     * @param actionEvent Pressing the submit button after entering a year into the text-field.
     * */
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

    /** This method displays the total of appointments for a given month and type.
     * @param actionEvent Pressing the submit button after a selection is made in the type and month combo-boxes.
     * */
    public void onActionGenerateType(ActionEvent actionEvent) {
        String type = String.valueOf(typeComboBox.getValue());
        int month = monthComboBox.getSelectionModel().getSelectedIndex() + 1;
        int total = AppointmentQuery.total(type, month);
        totalText.setText(String.valueOf(total));
    }

    /** This method loads the dashboard screen.
     * @param actionEvent The return button is pressed.
     * */
    public void onActionReturn(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    /** This method terminates the application.
     * @param actionEvent The exit button is pressed.
     * */
    public void onActionExit(ActionEvent actionEvent) {
        Alerts.exit();
    }

    /** This method initializes the reports screen. This method defines the contents of table columns and combo-boxes.
     * @param url Location of root-object.
     * @param resourceBundle  Root-object localization resources.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        totalText.setText("");
        ObservableList months = FXCollections.observableArrayList();
        months.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        monthComboBox.setItems(months);
        contactsComboBox.setItems(ContactQuery.contacts());
        typeComboBox.setItems(AppointmentQuery.type());

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("end"));


    }
}
