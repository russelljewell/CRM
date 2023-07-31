package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import model.Appointment;

import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    public TableView allAppointmentTable;
    public TableColumn allAppointmentIdColumn;
    public TableColumn allCustomerIdColumn;
    public TableColumn allUserIdColumn;
    public TableColumn allTitleColumn;
    public TableColumn allDescriptionColumn;
    public TableColumn allLocationColumn;
    public TableColumn allContactColumn;
    public TableColumn allTypeColumn;
    public TableColumn allDateColumn;
    public TableColumn allStartColumn;
    public TableColumn allEndColumn;
    public TableView monthAppointmentTable;
    public TableColumn monthAppointmentIdColumn;
    public TableColumn monthCustomerIdColumn;
    public TableColumn monthUserIdColumn;
    public TableColumn monthTitleColumn;
    public TableColumn monthDescriptionColumn;
    public TableColumn monthLocationColumn;
    public TableColumn monthContactColumn;
    public TableColumn monthTypeColumn;
    public TableColumn monthDateColumn;
    public TableColumn monthStartColumn;
    public TableColumn monthEndColumn;
    public TableView weekAppointmentTable;
    public TableColumn weekAppointmentIdColumn;
    public TableColumn weekCustomerIdColumn;
    public TableColumn weekUserIdColumn;
    public TableColumn weekTitleColumn;
    public TableColumn weekDescriptionColumn;
    public TableColumn weekLocationColumn;
    public TableColumn weekContactColumn;
    public TableColumn weekTypeColumn;
    public TableColumn weekDateColumn;
    public TableColumn weekStartColumn;
    public TableColumn weekEndColumn;
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

    public void onActionDeleteCustomer(ActionEvent actionEvent) {
        initializeDetails();
    }

    public void onActionCreateAppointment(ActionEvent actionEvent) {
        appointmentDetails();
    }

    public void onActionDeleteAppointment(ActionEvent actionEvent) {
        initializeDetails();
    }

    public void onActionSave(ActionEvent actionEvent) {
        if (!customerIdLabel.isVisible()) {

        }
    }

    public void onActionClear(ActionEvent actionEvent) {
        if (!customerIdLabel.isVisible()) {

        }
    }

    public void onActionGenerateReport(ActionEvent actionEvent) {
    }

    public void onActionExit(ActionEvent actionEvent) {
        System.exit(0);
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
       initializeDetails();
    }
}
