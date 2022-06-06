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
import model.Contact;
import model.Division;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public ComboBox ContactIdComboBox;
    public ComboBox UserIdComboBox;
    public ComboBox CustIdComboBox;
    public ComboBox StartTimeComboBox;
    public ComboBox EndTimeComboBox;
    public TextField LocationField;
    public javafx.scene.control.DatePicker DatePicker;
    public TextField DescriptionField;
    public TextField ApptTypeField;
    private int apptId;
    private int custId;
    private int userId;
    private int contactId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Timestamp startTimestamp;
    private Timestamp endTimestamp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");

        // Fill All Appointments tab table
        populateAllApptsTable();

        // Print all appointment info
        /*
        ObservableList<Appointment> allApptList = getAllApptData();
        for(Appointment a : allApptList) {
            System.out.println("Appt ID: " + a.getApptId() + " EndDateTime: " + a.getEndDateTime());

        }
         */

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
        AllStartDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startString"));
        AllEndDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endString"));
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
        Alert alert;

        // Check that all fields/combo boxes have been filled out, add customer
        if (emptyFieldCheck()) {
            // Get new field values
            title = ApptTitleField.getText();
            custId = ((Appointment)CustIdComboBox.getSelectionModel().getSelectedItem()).getCustId();
            // contactId = ((Contact)ContactIdComboBox.getSelectionModel().getSelectedItem()).getContactId();
            userId = ((User)UserIdComboBox.getSelectionModel().getSelectedItem()).getUserId();
            type = ApptTypeField.getText();
            description = DescriptionField.getText();
            date = DatePicker.getValue();
            startTime = (LocalTime) StartTimeComboBox.getSelectionModel().getSelectedItem();
            endTime = (LocalTime) EndTimeComboBox.getSelectionModel().getSelectedItem();
            startTimestamp = Timestamp.valueOf(LocalDateTime.of(date, startTime));
            endTimestamp = Timestamp.valueOf(LocalDateTime.of(date, endTime));
            location = LocationField.getText();

            // If apptId > 0 returned, repopulate table and inform user of success
            int apptId = AppointmentsDAO.addAppt(custId, userId, contactId, title, description, location, type, startTimestamp, endTimestamp);
            if (custId > 0) {
                populateCustTable();
                // Confirm customer added
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Customer");
                alert.setContentText("Customer #" + custId + " has been added.");
                alert.showAndWait();

                // Clear form fields
                ClearButtonAction(null);
            }
            // Alert user: customer has not been added
            else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Customer Error");
                alert.setContentText("Error: Customer has NOT been added");
                alert.showAndWait();
            }
        }
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

    public boolean emptyFieldCheck() {
        boolean hasText = true;
        if ((ApptTitleField.getText().isBlank()) || (CustIdComboBox.getSelectionModel().isEmpty())
                || (ContactIdComboBox.getSelectionModel().isEmpty()) || (UserIdComboBox.getSelectionModel().isEmpty())
                || (ApptTypeField.getText().isBlank()) || (DescriptionField.getText().isBlank())
                || (DatePicker.getValue() == null) || (StartTimeComboBox.getSelectionModel().isEmpty())
                || (EndTimeComboBox.getSelectionModel().isEmpty()) || (LocationField.getText().isBlank())) {
            Alert alert;

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Fields");
            alert.setContentText("Please make sure all fields are complete.");
            alert.show();
            hasText = false;
        }
        return hasText;
    }
}
