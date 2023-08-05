package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Customer;
import utilities.*;
import utilities.Alert;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
        customerNameTextField.clear();
        addressTextField.clear();
        postalTextField.clear();
        phoneTextField.clear();
        countryComboBox.setItems(Query.countries());
        divisionComboBox.setItems(null);
    }

    public void onActionDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            Alert.selectCustomer();
        } else {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
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
            Alert.selectCustomer();
        } else {
            appointmentDetails();
            appointmentIdText.setText("(Auto)");
            customerNameTextField.clear();
            addressTextField.clear();
            postalTextField.clear();
            countryComboBox.setItems(Query.contacts());
            divisionComboBox.setItems(Query.users());
            dateDatePicker.setValue(LocalDate.now());
            startTextField.clear();
            endTextField.clear();
        }
    }

    public void onActionDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            Alert.selectCustomer();
        } else if (appointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alert.selectAppointment();
        } else {
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            AppointmentQuery.delete(selectedAppointment.getAppointmentID());
            initializeDetails();
        }
    }

    public void onActionSave(ActionEvent actionEvent) {
        if (!customerIdLabel.isVisible()) {
            Alert.selectCustomer();
        } else {
            if (!appointmentIdLabel.isVisible()) {

            } else {

            }
        }
    }

    public void onActionReset(ActionEvent actionEvent) {
        if (!customerIdLabel.isVisible()) {
            Alert.selectCustomer();
        } else {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION, "Unsaved changes will be lost, are you sure?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                Customer selected = customerTable.getSelectionModel().getSelectedItem();
                customerIdText.setText(String.valueOf(selected.getCustomerID()));
                customerNameTextField.setText(selected.getCustomerName());
                addressTextField.setText(selected.getAddress());
                postalTextField.setText(selected.getPostalCode());
                phoneTextField.setText(selected.getPhoneNumber());
                countryComboBox.setItems(Query.countries());
                //countryComboBox.setValue(selected.);
                //divisionComboBox.setItems(DivisionQuery.select());
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
        Alert.exit();
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
        countryComboBox.setItems(Query.countries());
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
        countryComboBox.setItems(Query.contacts());
        divisionLabel.setVisible(true);
        divisionLabel.setText("User ID");
        divisionComboBox.setVisible(true);
        divisionComboBox.setItems(Query.users());
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

        customerTable.getSelectionModel().selectedItemProperty().addListener((observableValue, priorSelection, newSelection) -> {
            if (newSelection != null) {
                appointmentToggle.selectToggle(associatedRadio);
                customerDetails();
                Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                customerIdText.setText(String.valueOf(selectedCustomer.getCustomerID()));
                customerNameTextField.setText(selectedCustomer.getCustomerName());
                addressTextField.setText(selectedCustomer.getAddress());
                postalTextField.setText(selectedCustomer.getPostalCode());
                phoneTextField.setText(selectedCustomer.getPhoneNumber());
                countryComboBox.setItems(Query.countries());

                //divisionComboBox.setItems(DivisionQuery.select());
                appointmentTable.setItems(AppointmentQuery.selectAssociated(selectedCustomer.getCustomerID()));
            }
        });

        appointmentTable.getSelectionModel().selectedItemProperty().addListener((observableValue, priorSelection, newSelection) -> {
            if (newSelection != null) {
                appointmentDetails();
                Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
                appointmentIdText.setText(String.valueOf(selectedAppointment.getAppointmentID()));
                customerIdText.setText(String.valueOf(selectedAppointment.getCustomerID()));
                customerNameTextField.setText(String.valueOf(selectedAppointment.getTitle()));
                addressTextField.setText(selectedAppointment.getDescription());
                postalTextField.setText(selectedAppointment.getLocation());
                phoneTextField.setText(selectedAppointment.getType());
                countryComboBox.setItems(Query.contacts());
                divisionComboBox.setItems(Query.users());
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
}