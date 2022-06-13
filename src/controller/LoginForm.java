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
    public Button SignInButton;
    public Label SignInLabel;
    public Label UserNameLabel;
    public Label PasswordLabel;
    private String userName;
    private String password;
    private String zone = ZoneId.systemDefault().toString();
    ResourceBundle langBundle = ResourceBundle.getBundle("bundle/lang", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");

        // Show default location
        Location.setText(zone);

        if (Locale.getDefault().getLanguage().equals("fr") ) {
            SignInLabel.setText(langBundle.getString("Login"));
            UserNameLabel.setText(langBundle.getString("UserName"));
            UserNameField.setText(langBundle.getString("UserName"));
            PasswordLabel.setText(langBundle.getString("Password"));
            PasswordField.setText(langBundle.getString("Password"));
            SignInButton.setText(langBundle.getString("Login"));
        }
    }

    public void SIButtonAction(ActionEvent actionEvent) throws IOException {
        if ((!UserNameField.getText().isEmpty()) && (!PasswordField.getText().isEmpty())) {
            userName = UserNameField.getText();
            System.out.println("username" + userName);
            password = PasswordField.getText();
            System.out.println("password" + password);
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
                alert.setContentText("Invalid user name and password combo.");
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
