/**
 * @author Russell Jewell
 * Customer Relationshop Manager
 * Main
 * */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.JDBC;

import java.io.IOException;

/** This class creates a customer relationship management application. */
public class Main extends Application {

    /** This method displays the dashboard.
     * @param primaryStage The primary stage.
     * */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        primaryStage.setTitle("Customer Relationship Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    /**This is the Main method. This is the first method called which launches the program and establishes a connection to a MySQL database.*/
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
    }
}
