package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Customer;
import utilities.AppointmentQuery;
import utilities.CustomerQuery;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    public TableView<Customer> customerTableView;
    public TableColumn<Customer, Integer> customerIdColumn;
    public TableColumn<Customer, String> customerNameColumn;
    public TableColumn<Customer, String> customerAddressColumn;
    public TableColumn<Customer, String> customerPostalColumn;
    public TableColumn<Customer, String> customerPhoneColumn;
    public TableColumn<Customer, Integer> customerDivisionColumn;
    public TableView<Appointment> allAppointmentTable;
    public TableColumn<Appointment, Integer> allAppointmentIdColumn;
    public TableColumn<Appointment, Integer> allCustomerIdColumn;
    public TableColumn<Appointment, Integer> allUserIdColumn;
    public TableColumn<Appointment, String> allTitleColumn;
    public TableColumn<Appointment, String> allDescriptionColumn;
    public TableColumn<Appointment, String> allLocationColumn;
    public TableColumn<Appointment, Integer> allContactColumn;
    public TableColumn<Appointment, String> allTypeColumn;
    public TableColumn<Appointment, LocalDateTime> allStartColumn;
    public TableColumn<Appointment, LocalDateTime> allEndColumn;
    public TableView<Appointment> monthAppointmentTable;
    public TableColumn<Appointment, Integer> monthAppointmentIdColumn;
    public TableColumn<Appointment, Integer> monthCustomerIdColumn;
    public TableColumn<Appointment, Integer> monthUserIdColumn;
    public TableColumn<Appointment, String> monthTitleColumn;
    public TableColumn<Appointment, String> monthDescriptionColumn;
    public TableColumn<Appointment, String> monthLocationColumn;
    public TableColumn<Appointment, Integer> monthContactColumn;
    public TableColumn<Appointment, String> monthTypeColumn;
    public TableColumn<Appointment, LocalDateTime> monthStartColumn;
    public TableColumn<Appointment, LocalDateTime> monthEndColumn;
    public TableView<Appointment> weekAppointmentTable;
    public TableColumn<Appointment, Integer> weekAppointmentIdColumn;
    public TableColumn<Appointment, Integer> weekCustomerIdColumn;
    public TableColumn<Appointment, Integer> weekUserIdColumn;
    public TableColumn<Appointment, String> weekTitleColumn;
    public TableColumn<Appointment, String> weekDescriptionColumn;
    public TableColumn<Appointment, String> weekLocationColumn;
    public TableColumn<Appointment, Integer> weekContactColumn;
    public TableColumn<Appointment, String> weekTypeColumn;
    public TableColumn<Appointment, LocalDateTime> weekStartColumn;
    public TableColumn<Appointment, LocalDateTime> weekEndColumn;
    public Label detailsLabel;
    public Label customerIdLabel;
    public Label customerIdText;
    public Label appointmentIdLabel;
    public Label appointmentIdText;
    public Label customerNameLabel;
    public TextField customerNameTextField;
    public Label addressLabel;
    public TextField addressTextField;
    public Label postalLabel;
    public TextField postalTextField;
    public Label phoneLabel;
    public TextField phoneTextField;
    public Label countryLabel;
    public ComboBox countryComboBox;
    public Label divisionLabel;
    public ComboBox divisionComboBox;
    public Label dateLabel;
    public DatePicker dateDatePicker;
    public Label startLabel;
    public TextField startTextField;
    public Label endLabel;
    public TextField endTextField;

    public void onActionCreateCustomer(ActionEvent actionEvent) {
        customerDetails();
    }

    public void onActionDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        if (customerTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer from the Customers table.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {

            }
        }
    }

    public void onActionCreateAppointment(ActionEvent actionEvent) {
        if (customerTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer from the Customers table.");
            alert.showAndWait();
        } else {
            appointmentDetails();
        }
    }

    public void onActionDeleteAppointment(ActionEvent actionEvent) {
        if (customerTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer from the Customers table.");
            alert.showAndWait();
        } else if (allAppointmentTable.getSelectionModel().getSelectedItem() == null ||
                monthAppointmentTable.getSelectionModel().getSelectedItem() == null ||
                weekAppointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment from the Appointments table.");
            alert.showAndWait();
        } else {

        }
    }

    public void onActionSave(ActionEvent actionEvent) {
        if (!customerIdLabel.isVisible()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer from the Customer table.");
            alert.showAndWait();
        }
    }

    public void onActionReset(ActionEvent actionEvent) {
        if (!customerIdLabel.isVisible()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer from the Customers table.");
            alert.showAndWait();
        }
    }

    public void onActionGenerateReport(ActionEvent actionEvent) {
    }

    public void onActionExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            System.exit(0);
        }
    }

    public void customerDetails() {
        detailsLabel.setText("Create Customer");
        customerIdLabel.setVisible(true);
        customerIdText.setVisible(true);
        customerNameLabel.setVisible(true);
        customerNameLabel.setText("Customer Name");
        customerNameTextField.setVisible(true);
        addressLabel.setVisible(true);
        addressLabel.setText("Address");
        addressTextField.setVisible(true);
        postalLabel.setVisible(true);
        postalLabel.setText("Postal Code");
        postalTextField.setVisible(true);
        phoneLabel.setVisible(true);
        phoneLabel.setText("Phone Number");
        phoneTextField.setVisible(true);
        countryLabel.setVisible(true);
        countryLabel.setText("Country");
        countryComboBox.setVisible(true);
        divisionLabel.setVisible(true);
        divisionLabel.setText("Division");
        divisionComboBox.setVisible(true);
        appointmentIdLabel.setVisible(false);
        appointmentIdText.setVisible(false);
        dateLabel.setVisible(false);
        dateDatePicker.setVisible(false);
        startLabel.setVisible(false);
        startTextField.setVisible(false);
        endLabel.setVisible(false);
        endTextField.setVisible(false);
    }

    public void appointmentDetails() {
        detailsLabel.setText("Create Appointment");
        customerIdLabel.setVisible(true);
        customerIdText.setVisible(true);
        customerNameLabel.setVisible(true);
        customerNameLabel.setText("Title");
        customerNameTextField.setVisible(true);
        appointmentIdLabel.setVisible(true);
        appointmentIdText.setVisible(true);
        addressLabel.setVisible(true);
        addressLabel.setText("Description");
        addressTextField.setVisible(true);
        postalLabel.setVisible(true);
        postalLabel.setText("Location");
        postalTextField.setVisible(true);
        phoneLabel.setVisible(true);
        phoneLabel.setText("Type");
        phoneTextField.setVisible(true);
        countryLabel.setVisible(true);
        countryLabel.setText("Contact");
        countryComboBox.setVisible(true);
        divisionLabel.setVisible(true);
        divisionLabel.setText("User ID");
        divisionComboBox.setVisible(true);
        dateLabel.setVisible(true);
        dateDatePicker.setVisible(true);
        startLabel.setVisible(true);
        startTextField.setVisible(true);
        endLabel.setVisible(true);
        endTextField.setVisible(true);
    }

    public void initializeDetails() {
        detailsLabel.setText("Details");
        customerIdLabel.setVisible(false);
        customerIdText.setVisible(false);
        customerNameLabel.setVisible(false);
        customerNameTextField.setVisible(false);
        appointmentIdLabel.setVisible(false);
        appointmentIdText.setVisible(false);
        addressLabel.setVisible(false);
        addressTextField.setVisible(false);
        postalLabel.setVisible(false);
        postalTextField.setVisible(false);
        phoneLabel.setVisible(false);
        phoneTextField.setVisible(false);
        countryLabel.setVisible(false);
        countryComboBox.setVisible(false);
        divisionLabel.setVisible(false);
        divisionComboBox.setVisible(false);
        dateLabel.setVisible(false);
        dateDatePicker.setVisible(false);
        startLabel.setVisible(false);
        startTextField.setVisible(false);
        endLabel.setVisible(false);
        endTextField.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        initializeDetails();

        customerTableView.setItems(CustomerQuery.select());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        customerPostalColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionID"));

        allAppointmentTable.setItems(AppointmentQuery.select());
        allAppointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        allCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        allUserIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
        allTypeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        allTitleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        allLocationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        allDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        allContactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactID"));
        allStartColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
        allEndColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("end"));


    }
}
