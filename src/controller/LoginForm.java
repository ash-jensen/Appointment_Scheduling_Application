package controller;

import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class LoginForm implements Initializable {

    public TextField UserNameField;
    public TextField PasswordField;
    String userName;
    String password;
    ResourceBundle myBundle = ResourceBundle.getBundle("bundle/lang");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");

        System.out.println(myBundle.getString("Hello"));
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
