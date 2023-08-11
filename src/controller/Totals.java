package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.AppointmentQuery;

import java.io.IOException;

public class Totals {
    public BarChart barChart;
    public TextField yearTF;

    public void onActionSubmit(ActionEvent actionEvent) {

        barChart.getData().clear();
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

    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/reports.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
