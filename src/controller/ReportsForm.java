package controller;

import DAO.AppointmentsDAO;
import DAO.ContactDAO;
import DAO.GeneralQueriesDAO;
import DAO.UserDAO;
import com.sun.javafx.image.impl.General;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class ReportsForm implements Initializable {
    public ComboBox ApptTypeComboBox;
    public ComboBox MonthComboBox;
    public Label NumberOfAppointmentsLabel;
    public static ObservableList<String> monthsOfYear = observableArrayList();
    public static ObservableList<String> apptTypeList = observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");

        // Fill combo boxes
        fillComboBoxes();

    }

    private void fillComboBoxes() {
        // Fill month combo box
        monthsOfYear = Appointment.getMonthsOfYear();
        MonthComboBox.setVisibleRowCount(5);
        MonthComboBox.setItems(monthsOfYear);

        // Fill appointment type comob box
        apptTypeList = Appointment.getAllApptTypes();
        ApptTypeComboBox.setVisibleRowCount(5);
        ApptTypeComboBox.setItems(apptTypeList);

    }

    public void SchedButtonAction(ActionEvent actionEvent) throws IOException {
        // Load Schedule Page
        Parent root = FXMLLoader.load(getClass().getResource("/view/Schedule.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 653);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.show();
    }

    public void CustButtonAction(ActionEvent actionEvent) throws IOException {
        // Load Customers Page
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 585);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    public void ExitButtonAction(ActionEvent actionEvent) {
        Alert alert;

        // Confirm user wants to exit program
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            // Close Program
            Platform.exit();
        }
    }

    public void OkayButtonAction(ActionEvent actionEvent) {
        Alert alert;
        String month;
        String apptType;

        // Get field values
        month = (String)MonthComboBox.getSelectionModel().getSelectedItem();
        apptType = (String)ApptTypeComboBox.getSelectionModel().getSelectedItem();


        // If apptId > 0 returned, repopulate table and inform user of success
        int apptNum = GeneralQueriesDAO.getNumAppts(month, apptType);
        if (apptNum > 0) {
            NumberOfAppointmentsLabel.setText(String.valueOf(apptNum));
        }
        // Alert user: customer has not been added
        else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointments Found Error");
            alert.setContentText("No appointments found");
            alert.showAndWait();
        }
    }
}
