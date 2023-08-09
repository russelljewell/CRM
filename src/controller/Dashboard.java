package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import utilities.*;
import utilities.Alerts;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

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
    public ComboBox<Country> countryComboBox;
    public Label divisionLabel;
    public ComboBox<Division> divisionComboBox;
    public Label dateLabel;
    public DatePicker dateDatePicker;
    public Label startLabel;
    public TextField startTextField;
    public Label endLabel;
    public TextField endTextField;
    public ToggleGroup appointmentToggle;
    public TableView<Customer> customerTable;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn addressCol;
    public TableColumn postalCol;
    public TableColumn phoneCol;
    public TableColumn divisionCol;
    public TableView<Appointment> appointmentTable;
    public TableColumn appointmentIdCol;
    public TableColumn customerIdCol;
    public TableColumn userIdCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public RadioButton allRadio;
    public RadioButton monthRadio;
    public RadioButton weekRadio;
    public RadioButton associatedRadio;
    public ComboBox<User> userComboBox;
    public ComboBox<Contact> contactComboBox;
    int status;

    public void onActionCreateCustomer(ActionEvent actionEvent) {
        customerDetails();
        customerIdText.setText("(Auto)");
        clear();
        customerTable.getSelectionModel().clearSelection();
        appointmentTable.setItems(null);
    }

    public void onActionDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        if (status == 0) {
            Alerts.selectCustomer();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                AppointmentQuery.deleteAssociated(selectedCustomer.getCustomerID());
                String customerName = selectedCustomer.getCustomerName();
                CustomerQuery.delete(selectedCustomer.getCustomerID());
                customerTable.setItems(CustomerQuery.select());
                Alert success = new Alert(Alert.AlertType.INFORMATION, customerName + " has been successfully deleted.");
                success.showAndWait();
                initializeDetails();
            }
            appointmentTable.setItems(null);
        }
    }

    public void onActionCreateAppointment(ActionEvent actionEvent) {
        if (status == 0) {
            Alerts.selectCustomer();
        } else {
            appointmentDetails();
            appointmentIdText.setText("(Auto)");
            customerNameTextField.clear();
            addressTextField.clear();
            postalTextField.clear();
            phoneTextField.clear();
            countryComboBox.setValue(null);
            contactComboBox.setItems(ContactQuery.contacts());
            divisionComboBox.setValue(null);
            userComboBox.setItems(UserQuery.users());
            dateDatePicker.setValue(null);
            startTextField.clear();
            endTextField.clear();
            appointmentTable.getSelectionModel().clearSelection();
        }
    }

    public void onActionDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        if (status == 0) {
            Alerts.selectCustomer();
        } else if (status != 4) {
            Alerts.selectAppointment();
        } else {
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            int appointmentID = selectedAppointment.getAppointmentID();
            String type = selectedAppointment.getType();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                AppointmentQuery.delete(selectedAppointment.getAppointmentID());
                Alert success = new Alert(Alert.AlertType.INFORMATION, "Appointment #" + appointmentID + " " + type + " has been successfully deleted.");
                success.showAndWait();
                initializeDetails();
            }
        }
    }

    public void onActionSave(ActionEvent actionEvent) throws SQLException {
        if (status == 0) {
            Alerts.selectCustomer();
        } else {
            if (status == 1 || status == 2) {
                String customerName = customerNameTextField.getText();
                String address = addressTextField.getText();
                String postalCode = postalTextField.getText();
                String phoneNumber = phoneTextField.getText();
                int divisionID = divisionComboBox.getValue().getDivisionID();
                if (status == 1) {
                    CustomerQuery.insert(customerName, address, postalCode, phoneNumber, divisionID);
                    customerTable.setItems(CustomerQuery.select());

                } else if (status == 2) {
                    Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                    int customerID = selectedCustomer.getCustomerID();
                    CustomerQuery.update(customerID, customerName, address, postalCode, phoneNumber, divisionID);
                    customerTable.setItems(CustomerQuery.select());
                }
            } else {
                String title = customerNameTextField.getText();
                String description = addressTextField.getText();
                String location = postalTextField.getText();
                String type = phoneTextField.getText();
                LocalDate datePicker = dateDatePicker.getValue();
                LocalTime initStart = LocalTime.parse(startTextField.getText());
                LocalTime initEnd = LocalTime.parse(endTextField.getText());
                LocalDateTime formatStart = LocalDateTime.of(datePicker, initStart);
                LocalDateTime formatEnd = LocalDateTime.of(datePicker, initEnd);
                Timestamp start = Timestamp.valueOf(formatStart);
                Timestamp end = Timestamp.valueOf(formatEnd);
                Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                int customerID = selectedCustomer.getCustomerID();
                int userID = userComboBox.getValue().getUserID();
                int contactID = contactComboBox.getValue().getContactID();
                if (status == 3) {
                    AppointmentQuery.insert(title, description, location, type, start, end, customerID, userID, contactID);
                } else if (status == 4) {
                    Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
                    int appointmentID = selectedAppointment.getAppointmentID();
                    AppointmentQuery.update(appointmentID, title, description, location, type, start, end, customerID, userID, contactID);

                }
                appointmentTable.setItems(AppointmentQuery.selectAssociated(customerID));
            }
        }
    }

    public void onActionReset(ActionEvent actionEvent) throws SQLException {
        if (status == 0) {
            Alerts.selectCustomer();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Unsaved changes will be lost, are you sure?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                if (status == 2) {
                    Customer selected = customerTable.getSelectionModel().getSelectedItem();
                    customerIdText.setText(String.valueOf(selected.getCustomerID()));
                    customerNameTextField.setText(selected.getCustomerName());
                    addressTextField.setText(selected.getAddress());
                    postalTextField.setText(selected.getPostalCode());
                    phoneTextField.setText(selected.getPhoneNumber());
                    for (Country country : countryComboBox.getItems()) {
                        if (country.getCountryID() == selected.getCountryID() ) {
                            countryComboBox.setValue(country);
                            break;
                        }
                    }
                    for (Division division : divisionComboBox.getItems()) {
                        if (division.getDivisionID() == selected.getDivisionID()) {
                            divisionComboBox.setValue(division);
                            break;
                        }
                    }
                } else if (status == 4) {
                    Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
                    appointmentIdText.setText(String.valueOf(selected.getAppointmentID()));
                    customerNameTextField.setText(selected.getTitle());
                    addressTextField.setText(selected.getDescription());
                    postalTextField.setText(selected.getLocation());
                    phoneTextField.setText(selected.getType());

                } else if (status == 1 || status == 3) {
                    clear();
                }

            }
        }
    }

    public void onActionAll(ActionEvent actionEvent) {
        appointmentTable.setItems(AppointmentQuery.select());
    }

    public void onActionMonth(ActionEvent actionEvent) {
        ObservableList<Appointment> monthly = FXCollections.observableArrayList();
        for (Appointment appointment : AppointmentQuery.select()) {
            if (appointment.getStart().isBefore(LocalDateTime.now().plusMonths(1))) {
                monthly.add(appointment);
            }
        }
        appointmentTable.setItems(monthly);
    }

    public void onActionWeek(ActionEvent actionEvent) {
        ObservableList<Appointment> weekly = FXCollections.observableArrayList();
        for (Appointment appointment : AppointmentQuery.select()) {
            if (appointment.getStart().isBefore(LocalDateTime.now().plusWeeks(1))) {
                weekly.add(appointment);
            }
        }
        appointmentTable.setItems(weekly);
    }

    public void onActionAssociated(ActionEvent actionEvent) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            appointmentTable.setItems(AppointmentQuery.selectAssociated(selectedCustomer.getCustomerID()));
        } else {
            appointmentTable.setItems(null);
        }
    }

    public void onActionGenerateReport(ActionEvent actionEvent) {
    }

    public void onActionExit(ActionEvent actionEvent) {
        Alerts.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        initializeDetails();
        status = 0;

        customerTable.getSelectionModel().selectedItemProperty().addListener((observableValue, priorSelection, newSelection) -> {
            if (newSelection != null) {
                appointmentToggle.selectToggle(associatedRadio);
                customerDetails();
                status = 2;
                Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                detailsLabel.setText("Modify Customer");
                customerIdText.setText(String.valueOf(selectedCustomer.getCustomerID()));
                customerNameTextField.setText(selectedCustomer.getCustomerName());
                addressTextField.setText(selectedCustomer.getAddress());
                postalTextField.setText(selectedCustomer.getPostalCode());
                phoneTextField.setText(selectedCustomer.getPhoneNumber());
                for (Country country : countryComboBox.getItems()) {
                    if (country.getCountryID() == selectedCustomer.getCountryID() ) {
                        countryComboBox.setValue(country);
                        break;
                    }
                }
                for (Division division : divisionComboBox.getItems()) {
                    if (division.getDivisionID() == selectedCustomer.getDivisionID()) {
                        divisionComboBox.setValue(division);
                        break;
                    }
                }

                appointmentTable.setItems(AppointmentQuery.selectAssociated(selectedCustomer.getCustomerID()));
            }
        });

        appointmentTable.getSelectionModel().selectedItemProperty().addListener((observableValue, priorSelection, newSelection) -> {
            if (newSelection != null) {
                appointmentDetails();
                status = 4;
                Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
                detailsLabel.setText("Modify Appointment");
                appointmentIdText.setText(String.valueOf(selectedAppointment.getAppointmentID()));
                customerIdText.setText(String.valueOf(selectedAppointment.getCustomerID()));
                customerNameTextField.setText(String.valueOf(selectedAppointment.getTitle()));
                addressTextField.setText(selectedAppointment.getDescription());
                postalTextField.setText(selectedAppointment.getLocation());
                phoneTextField.setText(selectedAppointment.getType());
                for (Contact contact : contactComboBox.getItems()) {
                    if (contact.getContactID() == selectedAppointment.getContactID()) {
                        contactComboBox.setValue(contact);
                    }
                }
                for (User user : userComboBox.getItems()) {
                    if (user.getUserID() == selectedAppointment.getUserID()) {
                        userComboBox.setValue(user);
                    }
                }
                dateDatePicker.setValue(selectedAppointment.getStart().toLocalDate());
                startTextField.setText(String.valueOf(selectedAppointment.getStart().toLocalTime()));
                endTextField.setText(String.valueOf(selectedAppointment.getEnd().toLocalTime()));
            }
        });

        customerTable.setItems(CustomerQuery.select());
        idCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("divisionName"));

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        locationCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        contactCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactID"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("end"));
    }

    public void initializeDetails() {
        status = 0;
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
        contactComboBox.setVisible(false);
        divisionLabel.setVisible(false);
        divisionComboBox.setVisible(false);
        userComboBox.setVisible(false);
        dateLabel.setVisible(false);
        dateDatePicker.setVisible(false);
        startLabel.setVisible(false);
        startTextField.setVisible(false);
        endLabel.setVisible(false);
        endTextField.setVisible(false);
    }

    public void appointmentDetails() {
        status = 3;
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
        countryComboBox.setVisible(false);
        contactComboBox.setVisible(true);
        contactComboBox.setItems(ContactQuery.contacts());
        divisionLabel.setVisible(true);
        divisionLabel.setText("User");
        divisionComboBox.setVisible(false);
        userComboBox.setVisible(true);
        userComboBox.setItems(UserQuery.users());
        dateLabel.setVisible(true);
        dateDatePicker.setVisible(true);
        startLabel.setVisible(true);
        startTextField.setVisible(true);
        endLabel.setVisible(true);
        endTextField.setVisible(true);
    }

    public void customerDetails() {
        status = 1;
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
        contactComboBox.setVisible(false);
        countryComboBox.setVisible(true);
        countryComboBox.setItems(CountryQuery.countries());
        divisionLabel.setVisible(true);
        divisionLabel.setText("Division");
        userComboBox.setVisible(false);
        divisionComboBox.setVisible(true);
        countryComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, priorSelection, newSelection) -> {
            if (newSelection != null) {
                Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
                divisionComboBox.setItems(DivisionQuery.divisions(selectedCountry.getCountryID()));
            }
        });
        appointmentIdLabel.setVisible(false);
        appointmentIdText.setVisible(false);
        dateLabel.setVisible(false);
        dateDatePicker.setVisible(false);
        startLabel.setVisible(false);
        startTextField.setVisible(false);
        endLabel.setVisible(false);
        endTextField.setVisible(false);
    }

    public void clear() {
        customerNameTextField.clear();
        addressTextField.clear();
        postalTextField.clear();
        phoneTextField.clear();
        countryComboBox.setValue(null);
        divisionComboBox.setValue(null);
        if (appointmentIdLabel.isVisible()) {
            dateDatePicker.setValue(null);
            startTextField.clear();
            endTextField.clear();
            contactComboBox.setValue(null);
            userComboBox.setValue(null);
        }
    }
}