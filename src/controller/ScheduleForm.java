package controller;

import DAO.*;
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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

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
    public ComboBox UserComboBox;
    public ComboBox CustomerComboBox;
    public ComboBox StartTimeComboBox;
    public ComboBox EndTimeComboBox;
    public TextField LocationField;
    public javafx.scene.control.DatePicker DatePicker;
    public TextField DescriptionField;
    public TextField ApptTypeField;
    private Appointment appointment;
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
    ObservableList<Customer> customerList = observableArrayList();
    ObservableList<Contact> contactList = observableArrayList();
    ObservableList<User> userList = observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");

        // Fill All Appointments tab table
        populateAllApptsTable();

        // Fill combo boxes
        fillComboBoxes();





        // Lambda function for putting selected customer in Customer Details form
        AllApptsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                appointment = (Appointment)newSelection;

                System.out.println("DB TIME: " + appointment.getStartDateTime() + "\n Updated time: " +
                        appointment.updateDateTime(appointment.getStartDateTime()));
                // System.out.println("")

                apptId = appointment.getId();
                ApptIdField.setText(Integer.toString(apptId));
                title = appointment.getTitle();
                ApptTitleField.setText(title);
                custId = appointment.getCustId();
                Customer customer = CustomerDAO.getCustomerById(custId);
                CustomerComboBox.setValue(customer);
                contactId = appointment.getContactId();
                Contact contact = ContactDAO.getContactById(contactId);
                ContactComboBox.setValue(contact);
                userId = appointment.getUserId();
                User user = UserDAO.getUserById(userId);
                UserComboBox.setValue(user);
                type = appointment.getType();
                ApptTypeField.setText(type);
                description = appointment.getDescription();
                DescriptionField.setText(description);
                date = (appointment.getStartDateTime().toLocalDateTime()).toLocalDate();
                DatePicker.setValue(date);
                // update time
                // LocalDateTime updatedTime = appointment.updateDateTime(appointment.getStartDateTime());
                // startTime = updatedTime.toLocalTime();
                startTime = (appointment.getStartDateTime().toLocalDateTime()).toLocalTime();
                StartTimeComboBox.setValue(startTime);
                endTime = (appointment.getEndDateTime().toLocalDateTime()).toLocalTime();
                EndTimeComboBox.setValue(endTime);
                location = appointment.getLocation();
                LocationField.setText(location);
            }
        });

        // Print all appointment info
        /*
        ObservableList<Appointment> allApptList = AppointmentsDAO.getAllApptData();
        for(Appointment a : allApptList) {
            System.out.println("Appt ID: " + a.getId() + " EndDateTime: " + a.getEndDateTime());
        }

         */
    }

    //****ADD ALL TABLES IN HERE****//
    private void populateAllApptsTable() {
        // Populate Customer Table on Customers form
        AllApptsTable.setItems(AppointmentsDAO.getAllApptData());
        AllApptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
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

    private void fillComboBoxes() {
        // Fill customer combo box
        customerList = CustomerDAO.getCustomerData();
        CustomerComboBox.setVisibleRowCount(5);
        CustomerComboBox.setItems(customerList);

        // Fill contact combo box
        contactList = ContactDAO.getContactData();
        ContactComboBox.setVisibleRowCount(5);
        ContactComboBox.setItems(contactList);

        // Fill user combo box
        userList = UserDAO.getUserData();
        UserComboBox.setVisibleRowCount(5);
        UserComboBox.setItems(userList);

        // Fill start time combo box

        // Fill end time combo box
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
        // Add test
        /*
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        String st = "12-03-2022 12:30";
        LocalDateTime ldt = LocalDateTime.parse(st, dtf);
        Timestamp sts = Timestamp.valueOf(ldt);
        String et = "12-03-2022 12:30";
        ldt = LocalDateTime.parse(et, dtf);
        Timestamp ets = Timestamp.valueOf(ldt);
        AppointmentsDAO.addAppt(120, 2, 3, "Networking", "Coffee with coworker and friend", "Coffee Shop",
                "Coffee Date", sts, ets);
        populateAllApptsTable();
         */

        Alert alert;

        // Check that all fields/combo boxes have been filled out, add customer
        if (emptyFieldCheck()) {
            // Get new field values
            title = ApptTitleField.getText();
            custId = ((Customer)CustomerComboBox.getSelectionModel().getSelectedItem()).getId();
            contactId = ((Contact)ContactComboBox.getSelectionModel().getSelectedItem()).getId();
            userId = ((User)UserComboBox.getSelectionModel().getSelectedItem()).getId();
            type = ApptTypeField.getText();
            description = DescriptionField.getText();
            date = DatePicker.getValue();
            startTime = (LocalTime)StartTimeComboBox.getSelectionModel().getSelectedItem();
            endTime = (LocalTime)EndTimeComboBox.getSelectionModel().getSelectedItem();
            startTimestamp = Timestamp.valueOf(LocalDateTime.of(date, startTime));
            endTimestamp = Timestamp.valueOf(LocalDateTime.of(date, endTime));
            location = LocationField.getText();
            System.out.println("startTime: " + startTime);

            // If apptId > 0 returned, repopulate table and inform user of success
            int apptId = AppointmentsDAO.addAppt(custId, userId, contactId, title, description, location, type, startTimestamp, endTimestamp);
            if (apptId > 0) {
                populateAllApptsTable();
                // Confirm customer added
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Customer");
                alert.setContentText("Appointment #" + apptId + " has been added.");
                alert.showAndWait();

                // Clear form fields
                ClearButtonAction(null);
            }
            // Alert user: customer has not been added
            else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Appointment Error");
                alert.setContentText("Error: Appointment has NOT been added");
                alert.showAndWait();
            }
        }
    }

    public void UpdateApptButtonAction(ActionEvent actionEvent) {
    }

    public void DeleteApptButtonAction(ActionEvent actionEvent) {
    }

    public void ClearButtonAction(ActionEvent actionEvent) {
        ApptIdField.clear();
        ApptTitleField.clear();
        CustomerComboBox.getSelectionModel().clearSelection();
        ContactComboBox.getSelectionModel().clearSelection();
        UserComboBox.getSelectionModel().clearSelection();
        ApptTypeField.clear();
        DescriptionField.clear();
        DatePicker.getEditor().clear();
        StartTimeComboBox.getSelectionModel().clearSelection();
        EndTimeComboBox.getSelectionModel().clearSelection();
        LocationField.clear();
        // DELETE ME //
        // CustDivIdComboBox.getItems().clear(); // If you need the combo box emptied
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
        if ((ApptTitleField.getText().isBlank()) || (CustomerComboBox.getSelectionModel().isEmpty())
                || (ContactComboBox.getSelectionModel().isEmpty()) || (UserComboBox.getSelectionModel().isEmpty())
                || (ApptTypeField.getText().isBlank()) || (DescriptionField.getText().isBlank())
                || (DatePicker.getValue() == null) || (StartTimeComboBox.getSelectionModel().isEmpty())
                || (EndTimeComboBox.getSelectionModel().isEmpty())
                || (LocationField.getText().isBlank())) {
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
