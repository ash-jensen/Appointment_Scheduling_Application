package main;


import DAO.CustomerDAO;
import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Customer;

import java.sql.SQLException;
import java.util.Locale;

/**
 * This is the Main class for the Appointment Scheduler application
 * @author Ashley Jensen
 */
public class Main extends Application {
    /**
     * This method sets the stage with the Login form scene.
     * @param stage the stage to set
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Set stage with Login scene and open
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 350, 370));
        stage.show();
        System.out.println();
    }

    /**
     * This is the main method. This is the first method that gets called to run the application.
     * @param args the arguments passed to main
     */
    public static void main(String[] args) {
        // DELETE ME: Sets the language to french right of the bat.
        // Locale.setDefault(new Locale("fr"));

        // Open DB connection method called
        JDBC.openConnection();

        // Launch application
        launch(args);

        // Close DB connection method called
        JDBC.closeConnection();
    }
}
