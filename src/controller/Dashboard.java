package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class Dashboard {
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
        detailsLabel.setText("Create Customer");
    }

    public void onActionDeleteCustomer(ActionEvent actionEvent) {
    }

    public void onActionCreateAppointment(ActionEvent actionEvent) {
    }

    public void onActionDeleteAppointment(ActionEvent actionEvent) {
    }

    public void onActionSave(ActionEvent actionEvent) {
    }

    public void onActionClear(ActionEvent actionEvent) {
    }

    public void onActionGenerateReport(ActionEvent actionEvent) {
    }

    public void onActionExit(ActionEvent actionEvent) {
    }


}
