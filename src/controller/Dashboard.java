package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Country;
import model.Customer;
import utilities.*;
import utilities.Alerts;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    public ComboBox countryComboBox;
    public Label divisionLabel;
    public ComboBox divisionComboBox;
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

    public void onActionCreateCustomer(ActionEvent actionEvent) {
        customerDetails();
        customerIdText.setText("(Auto)");
        clear();
        customerTable.getSelectionModel().clearSelection();
        appointmentTable.setItems(null);
    }

    public void onActionDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            Alerts.selectCustomer();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                AppointmentQuery.deleteAll(selectedCustomer.getCustomerID());
                CustomerQuery.delete(selectedCustomer.getCustomerID());
                initializeDetails();
            }
        }
    }

    public void onActionCreateAppointment(ActionEvent actionEvent) {
        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            Alerts.selectCustomer();
        } else {
            appointmentDetails();
            appointmentIdText.setText("(Auto)");
            customerNameTextField.clear();
            addressTextField.clear();
            postalTextField.clear();
            phoneTextField.clear();
            countryComboBox.setValue(null);
            countryComboBox.setItems(ContactQuery.contacts());
            divisionComboBox.setValue(null);
            divisionComboBox.setItems(UserQuery.users());
            dateDatePicker.setValue(null);
            startTextField.clear();
            endTextField.clear();
            appointmentTable.getSelectionModel().clearSelection();
        }
    }

    public void onActionDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            Alerts.selectCustomer();
        } else if (appointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alerts.selectAppointment();
        } else {
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            AppointmentQuery.delete(selectedAppointment.getAppointmentID());
            initializeDetails();
        }
    }

    public void onActionSave(ActionEvent actionEvent) throws SQLException {
        if (!customerIdLabel.isVisible()) {
            Alerts.selectCustomer();
        } else {
            if (!appointmentIdLabel.isVisible()) {
                int customerID = Integer.parseInt(customerIdText.getText());
                String customerName = customerNameTextField.getText();
                String address = addressTextField.getText();
                String postalCode = postalTextField.getText();
                String phoneNumber = phoneTextField.getText();
                int divisionID = Integer.parseInt(String.valueOf(divisionComboBox.getValue()));
                if (customerIdText.equals("(Auto)")) {
                    CustomerQuery.insert(customerName, address, postalCode, phoneNumber, divisionID);
                } else {
                    CustomerQuery.update(customerID, customerName, address, postalCode, phoneNumber, divisionID);
                }
            } else {
                int appointmentID = Integer.parseInt(appointmentIdText.getText());
                String title = customerNameTextField.getText();
                String description = addressTextField.getText();
                String location = postalTextField.getText();
                String type = phoneTextField.getText();
                Timestamp start = Timestamp.valueOf((startTextField.getText()));
                Timestamp end = Timestamp.valueOf((endTextField.getText()));
                int customerID = Integer.parseInt(customerIdText.getText());
                int userID = Integer.parseInt(String.valueOf(divisionComboBox.getValue()));
                int contactID = Integer.parseInt(String.valueOf(countryComboBox.getValue()));
                if (appointmentIdText.equals("(Auto)")) {
                    AppointmentQuery.insert(title, description, location, type, start, end, customerID, userID, contactID);
                } else {
                    AppointmentQuery.update(appointmentID, title, description, location, type, start, end, customerID, userID, contactID);
                }
            }
        }
    }

    public void onActionReset(ActionEvent actionEvent) throws SQLException {
        if (!customerIdLabel.isVisible()) {
            Alerts.selectCustomer();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Unsaved changes will be lost, are you sure?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                if (detailsLabel.equals("Modify Customer")) {
                    Customer selected = customerTable.getSelectionModel().getSelectedItem();
                    customerIdText.setText(String.valueOf(selected.getCustomerID()));
                    customerNameTextField.setText(selected.getCustomerName());
                    addressTextField.setText(selected.getAddress());
                    postalTextField.setText(selected.getPostalCode());
                    phoneTextField.setText(selected.getPhoneNumber());
                    countryComboBox.setValue(CountryQuery.associatedCountry(selected.getDivisionID()));
                    divisionComboBox.setValue(selected.getDivisionID());
                } else if (detailsLabel.equals("Modify Appointment")) {
                    Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
                    appointmentIdText.setText(String.valueOf(selected.getAppointmentID()));
                    customerNameTextField.setText(selected.getTitle());
                    addressTextField.setText(selected.getDescription());
                    postalTextField.setText(selected.getLocation());
                    phoneTextField.setText(selected.getType());


                }

            }
        }
    }

    public void onActionAll(ActionEvent actionEvent) {
        appointmentTable.setItems(AppointmentQuery.select());
    }

    public void onActionMonth(ActionEvent actionEvent) {
    }

    public void onActionWeek(ActionEvent actionEvent) {
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

        customerTable.getSelectionModel().selectedItemProperty().addListener((observableValue, priorSelection, newSelection) -> {
            if (newSelection != null) {
                appointmentToggle.selectToggle(associatedRadio);
                customerDetails();
                Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                detailsLabel.setText("Modify Customer");
                customerIdText.setText(String.valueOf(selectedCustomer.getCustomerID()));
                customerNameTextField.setText(selectedCustomer.getCustomerName());
                addressTextField.setText(selectedCustomer.getAddress());
                postalTextField.setText(selectedCustomer.getPostalCode());
                phoneTextField.setText(selectedCustomer.getPhoneNumber());
                countryComboBox.setItems(CountryQuery.countries());
                try {
                    countryComboBox.setValue(CountryQuery.associatedCountry(selectedCustomer.getDivisionID()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                divisionComboBox.setValue(selectedCustomer.getDivisionID());
                appointmentTable.setItems(AppointmentQuery.selectAssociated(selectedCustomer.getCustomerID()));
            }
        });

        appointmentTable.getSelectionModel().selectedItemProperty().addListener((observableValue, priorSelection, newSelection) -> {
            if (newSelection != null) {
                appointmentDetails();
                Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
                detailsLabel.setText("Modify Appointment");
                appointmentIdText.setText(String.valueOf(selectedAppointment.getAppointmentID()));
                customerIdText.setText(String.valueOf(selectedAppointment.getCustomerID()));
                customerNameTextField.setText(String.valueOf(selectedAppointment.getTitle()));
                addressTextField.setText(selectedAppointment.getDescription());
                postalTextField.setText(selectedAppointment.getLocation());
                phoneTextField.setText(selectedAppointment.getType());
                countryComboBox.setItems(ContactQuery.contacts());
                divisionComboBox.setItems(UserQuery.users());
                //dateDatePicker
                startTextField.setText(String.valueOf(selectedAppointment.getStart()));
                endTextField.setText(String.valueOf(selectedAppointment.getEnd()));
            }
        });

        customerTable.setItems(CustomerQuery.select());
        idCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionID"));

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
        countryComboBox.setItems(ContactQuery.contacts());
        divisionLabel.setVisible(true);
        divisionLabel.setText("User");
        divisionComboBox.setVisible(true);
        divisionComboBox.setItems(UserQuery.users());
        dateLabel.setVisible(true);
        dateDatePicker.setVisible(true);
        startLabel.setVisible(true);
        startTextField.setVisible(true);
        endLabel.setVisible(true);
        endTextField.setVisible(true);
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
        countryComboBox.setItems(CountryQuery.countries());
        divisionLabel.setVisible(true);
        divisionLabel.setText("Division");
        divisionComboBox.setVisible(true);
        countryComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, priorSelection, newSelection) -> {
            if (newSelection != null) {
                Country selectedCountry = (Country) countryComboBox.getSelectionModel().getSelectedItem();
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
        }
    }
}