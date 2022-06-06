package controller;

import DAO.AppointmentsDAO;
import DAO.CustomerDAO;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static DAO.AppointmentsDAO.getAllApptData;

public class ScheduleForm implements Initializable {
    // FXML Vars
    public Tab AllApptsTab;
    public TableView AllApptsTable;
    public TableColumn AllApptIdCol;
    public TableColumn AllCustIdCol;
    public TableColumn AllUserIdCol;
    public TableColumn AllContactIdCol;
    public TableColumn AllTitleCol;
    public TableColumn AllLocationCol;
    public TableColumn AllStartDateTimeCol;
    public TableColumn AllEndDateTimeCol;
    public TableColumn AllTypeCol;
    public TableColumn AllDescriptionCol;
    public Tab CurrMonthTab;
    public TableView CurrMonthTable;
    public TableColumn CurrMonthApptIdCol;
    public TableColumn CurrMonthCustIdCol;
    public TableColumn CurrMonthUserIdCol;
    public TableColumn CurrMonthContactIdCol;
    public TableColumn CurrMonthTitleCol;
    public TableColumn CurrMonthLocationCol;
    public TableColumn CurrMonthStartDateTimeCol;
    public TableColumn CurrMonthEndDateTimeCol;
    public TableColumn CurrMonthTypeCol;
    public TableColumn CurrMonthDescriptionCol;
    public Tab CurrWeek;
    public TableView CurrWeekTable;
    public TableColumn CurrWeekApptIdCol;
    public TableColumn CurrWeekCustIdCol;
    public TableColumn CurrWeekUserIdCol;
    public TableColumn CurrWeekContactIdCol;
    public TableColumn CurrWeekTitleCol;
    public TableColumn CurrWeekLocationCol;
    public TableColumn CurrWeekStartDateTimeCol;
    public TableColumn CurrWeekEndDateTimeCol;
    public TableColumn CurrWeekTypeCol;
    public TableColumn CurrWeekDescriptionCol;
    public TextField ApptIdField;
    public TextField ApptTitleField;
    public ComboBox ContactComboBox;
    public ComboBox UserIdComboBox;
    public ComboBox CustIdComboBox;
    public ComboBox StartTimeComboBox;
    public ComboBox EndTimeComboBox;
    public TextField LocationField;
    public javafx.scene.control.DatePicker DatePicker;
    public TextField DescriptionField;
    public TextField ApptTypeField;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");

        ObservableList<Appointment> allApptList = getAllApptData();
        for(Appointment a : allApptList) {
            System.out.println("Appt ID: " + a.getApptId() + "EndDateTime: " + a.getEndDateTime());

        }

        // Fill All Appointments tab table
        populateAllApptsTable();

    }

    private void populateAllApptsTable() {
        // Populate Customer Table on Customers form
        AllApptsTable.setItems(AppointmentsDAO.getAllApptData());
        AllApptIdCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        AllCustIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
        AllUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        AllContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        AllTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        AllLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        AllStartDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        AllEndDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        AllTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        AllDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
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

    public void AddApptButtonAction(ActionEvent actionEvent) {

    }

    public void UpdateApptButtonAction(ActionEvent actionEvent) {
    }

    public void DeleteApptButtonAction(ActionEvent actionEvent) {
    }

    public void ReportsButtonAction(ActionEvent actionEvent) throws IOException {
        // Load Schedule Page
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 653);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }
}
