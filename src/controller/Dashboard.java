package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;
import utilities.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** This class defines the functionality of the dashboard screen. */
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
    public Text hoursText;
    public Label hoursLabel;
    public TableColumn countryCol;
    int status;

    LocalDate date = LocalDate.now();
    LocalTime open = LocalTime.of(8,00);
    LocalTime close = LocalTime.of(22,00);
    ZoneId local = ZoneId.of(TimeZone.getDefault().getID());
    ZoneId EST = ZoneId.of("America/New_York");
    ZonedDateTime businessOpen = ZonedDateTime.of(date, open, EST);
    ZonedDateTime businessClose = ZonedDateTime.of(date, close, EST);
    ZonedDateTime localOpen = businessOpen.withZoneSameInstant(local);
    ZonedDateTime localClose = businessClose.withZoneSameInstant(local);

    /** This method prepares the details panel to accept user input to add a customer.
     * @param actionEvent The customer-table create button is pressed.
     * */
    public void onActionCreateCustomer(ActionEvent actionEvent) {
        customerDetails();
        customerIdText.setText("(Auto)");
        clear();
        customerTable.getSelectionModel().clearSelection();
        appointmentTable.setItems(null);
    }

    /** This method deletes a selected customer.
     * @param actionEvent The customer-table delete button is pressed.
     * */
    public void onActionDeleteCustomer(ActionEvent actionEvent) {
        if (status == 0 || customerTable.getSelectionModel().getSelectedItem() == null) {
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

    /** This method prepares the details panel to accept user input to add an appointment.
     * @param actionEvent The appointment-table create button is pressed.
     * */
    public void onActionCreateAppointment(ActionEvent actionEvent) {
        if (status == 0 || customerTable.getSelectionModel().getSelectedItem() == null) {
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

     /** This method deletes a selected appointment.
     * @param actionEvent The appointment-table delete button is pressed.
     * */
    public void onActionDeleteAppointment(ActionEvent actionEvent) {
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
                if (customerTable.getSelectionModel().getSelectedItem() == null) {
                    appointmentTable.setItems(null);
                } else {
                    appointmentTable.setItems(AppointmentQuery.selectAssociated(customerTable.getSelectionModel().getSelectedItem().getCustomerID()));
                }
                initializeDetails();
            }
        }
    }

    /** This method saves the values of all fields in the details panel. This method checks a "state" condition to determine which query method to call.
     * @param actionEvent The save button is pressed.
     * */
    public void onActionSave(ActionEvent actionEvent) {
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
                    if (customerName.isBlank() || address.isBlank() || postalCode.isBlank() || phoneNumber.isBlank() || divisionComboBox.getValue() == null) {
                        Alerts.invalid();
                        return;
                    } else {
                        CustomerQuery.insert(customerName, address, postalCode, phoneNumber, divisionID);
                        customerTable.setItems(CustomerQuery.select());
                        initializeDetails();
                    }
                } else if (status == 2) {
                    Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                    int customerID = selectedCustomer.getCustomerID();
                    if (customerName.isBlank() || address.isBlank() || postalCode.isBlank() || phoneNumber.isBlank() || divisionComboBox.getValue() == null) {
                        Alerts.invalid();
                        return;
                    } else {
                        CustomerQuery.update(customerID, customerName, address, postalCode, phoneNumber, divisionID);
                        customerTable.setItems(CustomerQuery.select());
                        initializeDetails();
                    }
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
                Timestamp start = Timestamp.from(Timestamp.valueOf(formatStart).toInstant());
                Timestamp end = Timestamp.from(Timestamp.valueOf(formatEnd).toInstant());
                Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                int customerID = selectedCustomer.getCustomerID();
                int userID = userComboBox.getValue().getUserID();
                int contactID = contactComboBox.getValue().getContactID();
                if (status == 3) {
                    if (title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank() || startTextField.getText().isBlank() || endTextField.getText().isBlank() || dateDatePicker.getValue() == null || userComboBox.getValue() == null || contactComboBox.getValue() == null) {
                        Alerts.invalid();
                    } else if (initEnd.isBefore(initStart)) {
                        Alerts.invalidTime();
                    } else if (initStart.isBefore(LocalTime.from(localOpen)) || initEnd.isAfter(LocalTime.from(localClose))) {
                        Alerts.businessHours();
                    } else if (conflictCheckerAdd(formatStart, formatEnd, customerID)) {
                       Alerts.overlap();
                    } else {
                        AppointmentQuery.insert(title, description, location, type, start, end, customerID, userID, contactID);
                        if (customerTable.getSelectionModel().getSelectedItem() == null) {
                            appointmentTable.setItems(null);
                        } else {
                            appointmentTable.setItems(AppointmentQuery.selectAssociated(customerID));
                        }
                        initializeDetails();
                    }
                } else if (status == 4) {
                    Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
                    int appointmentID = selectedAppointment.getAppointmentID();
                    if (title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank() || startTextField.getText().isBlank() || endTextField.getText().isBlank() || dateDatePicker.getValue() == null || userComboBox.getValue() == null || contactComboBox.getValue() == null) {
                        Alerts.invalid();
                    } else if (initEnd.isBefore(initStart)) {
                        Alerts.invalidTime();
                    } else if (initStart.isBefore(LocalTime.from(localOpen)) || initEnd.isAfter(LocalTime.from(localClose))) {
                        Alerts.businessHours();
                    } else if (conflictCheckerUpdate(formatStart, formatEnd, customerID, appointmentID)) {
                        Alerts.overlap();
                    } else {
                        AppointmentQuery.update(appointmentID, title, description, location, type, start, end, customerID, userID, contactID);
                        if (customerTable.getSelectionModel().getSelectedItem() == null) {
                            appointmentTable.setItems(null);
                        } else {
                            appointmentTable.setItems(AppointmentQuery.selectAssociated(customerID));
                        }
                        initializeDetails();
                    }
                }
            }
        }
    }

    /** This method resets the values of all fields in the details panel. This method checks a "state" condition to determine how to re-initialize the values.
     * @param actionEvent The reset button is pressed.
     * */
    public void onActionReset(ActionEvent actionEvent) {
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

    /** This method displays all appointments in the appointments table.
     * @param actionEvent The "All" radio button is pressed.
     * */
    public void onActionAll(ActionEvent actionEvent) {
        appointmentTable.setItems(AppointmentQuery.select());
    }

    /** This method displays all appointments within the next month in the appointments able.
     * @param actionEvent The "Monthly" radio button is pressed.
     * */
    public void onActionMonth(ActionEvent actionEvent) {
        ObservableList<Appointment> monthly = FXCollections.observableArrayList();
        for (Appointment appointment : AppointmentQuery.select()) {
            if (appointment.getStart().isBefore(LocalDateTime.now().plusMonths(1))) {
                monthly.add(appointment);
            }
        }
        appointmentTable.setItems(monthly);
    }

    /** This method displays all appointments within the next week in the appointments able.
     * @param actionEvent The "Weekly" radio button is pressed.
     * */
    public void onActionWeek(ActionEvent actionEvent) {
        ObservableList<Appointment> weekly = FXCollections.observableArrayList();
        for (Appointment appointment : AppointmentQuery.select()) {
            if (appointment.getStart().isBefore(LocalDateTime.now().plusWeeks(1))) {
                weekly.add(appointment);
            }
        }
        appointmentTable.setItems(weekly);
    }

    /** This method displays all appointments associated with the selected customer in the appointments table.
     * @param actionEvent The "Associated" radio button is pressed.
     * */
    public void onActionAssociated(ActionEvent actionEvent) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            appointmentTable.setItems(AppointmentQuery.selectAssociated(selectedCustomer.getCustomerID()));
        } else {
            appointmentTable.setItems(null);
        }
    }

    /** This method displays the reports screen.
     * @Param The generate reports button is pressed.
     * */
    public void onActionGenerateReport(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/reports.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException throwables) {
            throwables.printStackTrace();
        }
    }

    /** This method terminates the application.
     * @param actionEvent The exit button is pressed.
     * */
    public void onActionExit(ActionEvent actionEvent) {
        Alerts.exit();
    }

    /** This method initializes the dashboard screen.
     * @param url Location of root-object.
     * @param resourceBundle  Root-object localization resources.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        boolean upcoming = false;
        for (Appointment appointment : AppointmentQuery.select()) {
            if (appointment.getStart().isAfter(LocalDateTime.now()) && appointment.getStart().isBefore(LocalDateTime.now().plusMinutes(15))) {
                Alerts.upcoming(appointment.getCustomerID(), appointment.getStart().toLocalDate(), appointment.getStart().toLocalTime());
                upcoming = true;
                break;
            }
        }
        if (!upcoming) {
            Alerts.noUpcoming();
        }

        initializeDetails();
        status = 0;

        /** This lambda expression automatically populates the text fields of the details panel when a customer is selected from the customer table.
         * This allows the user to immediately view and edit customer details without having to press an extra button to display them.
         * */
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

        /** This lambda expression automatically populates the text fields of the details panel when an appointment is selected from the appointment table.
         * This allows the user to immediately view and edit appointment details without having to press an extra button to display them.
         * */
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
                dateDatePicker.setValue(selectedAppointment.getStart().atZone(TimeZone.getDefault().toZoneId()).toLocalDate());
                startTextField.setText(String.valueOf(selectedAppointment.getStart().atZone(TimeZone.getDefault().toZoneId()).toLocalTime()));
                endTextField.setText(String.valueOf(selectedAppointment.getEnd().atZone(TimeZone.getDefault().toZoneId()).toLocalTime()));
            }
        });

        /** Initialize customer table columns. */
        customerTable.setItems(CustomerQuery.select());
        idCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        countryCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("countryName"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("divisionName"));


        /** Initialize appointment table columns. */
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

    /** This method hides all labels and fields in the details panel until a customer or appointment is selected. */
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
        hoursLabel.setVisible(false);
        hoursText.setVisible(false);
        customerTable.getSelectionModel().clearSelection();
        appointmentTable.getSelectionModel().clearSelection();
    }

    /** This method displays only the fields and labels necessary within the details panel to view and edit appointment information. */
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
        hoursLabel.setVisible(true);
        hoursText.setVisible(true);
        hoursText.setText(localOpen.format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + localClose.format(DateTimeFormatter.ofPattern("HH:mm")) );
    }

    /** This method displays only the fields and labels necessary within the details panel to view and edit customer information. */
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
        /** This lambda expression automatically loads the relevant divisions into a combo box based on the selection of the country combo-box. */
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

    /** This method clears all fields in the details panel. */
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

    /** This method checks whether a user-created appointment will conflict with existing appointments.
     * @param start The start date and time input by the user.
     * @param end The end and date time input by the user.
     * @param customerID The customer ID selected by the user.
     * @return conflict A boolean condition indicating the presence of a schedule conflict.
     * */
    public boolean conflictCheckerAdd(LocalDateTime start, LocalDateTime end, int customerID) {
        boolean conflict = false;
        for (Appointment appointment : AppointmentQuery.selectAssociated(customerID)) {
            LocalDateTime apptStart = appointment.getStart();
            LocalDateTime apptEnd = appointment.getEnd();
                if ((start.isAfter(apptStart) || start.isEqual(apptStart)) && start.isBefore(apptEnd)) {
                    conflict = true;
                } else if ((end.isAfter(apptStart)) && (end.isBefore(apptEnd) || end.isEqual(apptEnd))) {
                    conflict = true;
                } else if ((start.isBefore(apptStart) || start.isEqual(apptStart)) && (end.isAfter(apptEnd) || end.isEqual(apptEnd))) {
                    conflict = true;
                }
        }
        return conflict;
    }

    /** This method checks whether a user-modified appointment will conflict with existing appointments.
     * This method excludes the existing appointment of the same appointment ID.
     * @param start The start date and time (un)modified by the user.
     * @param end The end and date time (un)modified by the user.
     * @param customerID The customer ID selected by the user.
     * @param appointmentID The appointment ID of the (un)modified appointment.
     * @return conflict A boolean condition indicating the presence of a schedule conflict.
     * */
    public boolean conflictCheckerUpdate (LocalDateTime start, LocalDateTime end, int customerID, int appointmentID) {
        boolean conflict = false;
        for (Appointment appointment : AppointmentQuery.selectAssociated(customerID)) {
            LocalDateTime apptStart = appointment.getStart();
            LocalDateTime apptEnd = appointment.getEnd();
            if (appointment.getAppointmentID() != appointmentID) {
                if ((start.isAfter(apptStart) || start.isEqual(apptStart)) && start.isBefore(apptEnd)) {
                    conflict = true;
                } else if ((end.isAfter(apptStart)) && (end.isBefore(apptEnd) || end.isEqual(apptEnd))) {
                    conflict = true;
                } else if ((start.isBefore(apptStart) || start.isEqual(apptStart)) && (end.isAfter(apptEnd) || end.isEqual(apptEnd))) {
                    conflict = true;
                }
            }
        }
        return conflict;
    }
}