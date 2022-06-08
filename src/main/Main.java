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

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Set stage with Login scene and open
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 350, 370));
        stage.show();
        System.out.println();
    }

    public static void main(String[] args) {
        // DELETE ME: Sets the language to french right of the bat.
        // Locale.setDefault(new Locale("fr"));
        // System.out.println(Locale.getDefault());

        // Open DB connection method called
        JDBC.openConnection();

        // Launch application
        launch(args);

        // Close DB connection method called
        JDBC.closeConnection();
    }
}
