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
import model.Appointment;
import model.Contact;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class ReportsForm implements Initializable {
    public ComboBox ApptTypeComboBox;
    public ComboBox MonthComboBox;
    public Label NumberOfAppointmentsLabel;
    public static ObservableList<String> monthsOfYear = observableArrayList();
    public static ObservableList<String> apptTypeList = observableArrayList();
    public TableView ApptsTable;

    public TableColumn ApptIdCol;
    public TableColumn CustIdCol;
    public TableColumn UserIdCol;
    public TableColumn ContactIdCol;
    public TableColumn TitleCol;
    public TableColumn LocationCol;
    public TableColumn StartDateTimeCol;
    public TableColumn EndDateTimeCol;
    public TableColumn TypeCol;
    public TableColumn DescriptionCol;
    public ComboBox ContactComboBox;
    public TableView CustomerTable;
    public TableColumn IdCol;
    public TableColumn NameCol;
    public TableColumn AddressCol;
    public TableColumn PostalCodeCol;
    public TableColumn PhoneCol;
    public TableColumn DivisionIdCol;
    public Label CustomerNumbers;

    ObservableList<Contact> contactList = observableArrayList();
    ObservableList<Customer> customerList = observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");

        // Fill combo boxes
        fillComboBoxes();

        // Fill customer table
        populateCustomerTable();

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

        // Fill contact combo box
        contactList = ContactDAO.getContactData();
        ContactComboBox.setVisibleRowCount(5);
        ContactComboBox.setItems(contactList);
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

    public void NumApptsOkayButtonAction(ActionEvent actionEvent) {
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

    public void ContactSchedOkayButtonAction(ActionEvent actionEvent) {
         int contactId = ((Contact)ContactComboBox.getSelectionModel().getSelectedItem()).getId();

        // Populate Appointments table on schedule form
        ApptsTable.setItems(AppointmentsDAO.getContactApptData(contactId));
        ApptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        CustIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
        UserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        ContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        TitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        LocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        StartDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startString"));
        EndDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endString"));
        TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    public void populateCustomerTable() {
        customerList = CustomerDAO.getCustomerData();
        int numCustomers = customerList.size();
        CustomerNumbers.setText(String.valueOf(numCustomers));

        // Populate All Appointments table on schedule form
        CustomerTable.setItems(CustomerDAO.getCustomerData());
        IdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        PostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        PhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        DivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divId"));


    }
}
