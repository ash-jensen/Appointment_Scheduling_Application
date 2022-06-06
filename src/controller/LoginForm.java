package controller;

import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;



public class LoginForm implements Initializable {
    public TextField UserNameField;
    public TextField PasswordField;
    public Label Location;
    String userName;
    String password;
    String zone = ZoneId.systemDefault().toString();
    ResourceBundle langBundle = ResourceBundle.getBundle("bundle/lang", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");

        // Language test with malcolm
        /*
        // Show location
        Location.setText(ZonedDateTime.toString());


        Locale france = new Locale("fr");
        Locale english = new Locale("en", "EN");

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            System.out.println(langBundle.getString("Login" ) + " " + langBundle.getString("UserName"));
        }

        Locale locale = Locale.getDefault();
        if (locale.equals(france)) {
            Location.setText(zone);
        }
         */

        // Print Login in french
        System.out.println(langBundle.getString("Login"));

    }


    public static ZoneId systemDefault() {
        return null;
    }

    public void SIButtonAction(ActionEvent actionEvent) throws IOException {
        userName = UserNameField.getText();
        password = PasswordField.getText();
        if ((!userName.isEmpty()) && (!password.isEmpty())) {
            if (UserDAO.checkLoginInfo(userName, password)) {
                // Load Schedule Page
                Parent root = FXMLLoader.load(getClass().getResource("/view/Schedule.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 900, 653);
                stage.setTitle("Schedule");
                stage.setScene(scene);
                stage.show();
            }
            else {
                Alert alert;

                // Alert user that login information is invalid
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setContentText("Invalid user name and password.");
                alert.showAndWait();
            }
        }
        else {
            Alert alert;

            // Alert user that login information is invalid
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setContentText("Please enter a user name and password.");
            alert.showAndWait();
        }
    }


}
