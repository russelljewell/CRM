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
        int year = 2020;

        int jan = AppointmentQuery.monthlyTotal(1, year);
        int feb = AppointmentQuery.monthlyTotal(2, year);
        int mar = AppointmentQuery.monthlyTotal(3, year);
        int apr = AppointmentQuery.monthlyTotal(4, year);
        int may = AppointmentQuery.monthlyTotal(5, year);
        int jun = AppointmentQuery.monthlyTotal(6, year);
        int jul = AppointmentQuery.monthlyTotal(7, year);
        int aug = AppointmentQuery.monthlyTotal(8, year);
        int sep = AppointmentQuery.monthlyTotal(9, year);
        int oct = AppointmentQuery.monthlyTotal(10, year);
        int nov = AppointmentQuery.monthlyTotal(11, year);
        int dec = AppointmentQuery.monthlyTotal(12, year);
        System.out.println(jan + " " + feb + " " + mar);

        XYChart.Series data = new XYChart.Series();
        data.getData().add(new XYChart.Data("January", jan));
        data.getData().add(new XYChart.Data("February", feb));
        data.getData().add(new XYChart.Data("March", mar));
        data.getData().add(new XYChart.Data("April", apr));
        data.getData().add(new XYChart.Data("May", may));
        data.getData().add(new XYChart.Data("June", jun));
        data.getData().add(new XYChart.Data("July", jul));
        data.getData().add(new XYChart.Data("August", aug));
        data.getData().add(new XYChart.Data("September", sep));
        data.getData().add(new XYChart.Data("October", oct));
        data.getData().add(new XYChart.Data("November", nov));
        data.getData().add(new XYChart.Data("December", dec));
        barChart.getData().add(data);
    }

    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/reports.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
