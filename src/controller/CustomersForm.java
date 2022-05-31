package controller;

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
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static DAO.CountryDAO.getCountryData;
import static DAO.DivisionDAO.getDivData;
import static model.CustomerList.*;

public class CustomersForm implements Initializable {

    public TableView CustTable;
    public TableColumn CustTableId;
    public TableColumn CustTableName;
    public TableColumn CustTableAddress;
    public TableColumn CustTablePostalCode;
    public TableColumn CustTablePhoneNumber;
    public TableColumn CustTableDivId;
    public ComboBox CustCountryId;
    public TextField CustAddressField;
    public TextField CustPostalCodeField;
    public ComboBox CustDivIdComboBox;
    public TextField CustPhoneNumberField;
    public TextField CustIdField;
    public TextField CustNameField;
    private Customer customer;
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int countryId;
    private int divId;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");

        // Fill customer table with customer data
        populateCustTable();

        // Lambda function for putting selected customer in Customer Details form
        CustTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                customer = (Customer)newSelection;
                id = customer.getId();
                CustIdField.setText(Integer.toString(id));
                name = customer.getName();
                CustNameField.setText(name);
                address = customer.getAddress();
                CustAddressField.setText(address);
                postalCode = customer.getPostalCode();
                CustPostalCodeField.setText(postalCode);
                phoneNumber = customer.getPhoneNumber();
                CustPhoneNumberField.setText(phoneNumber);
                divId = customer.getDivId();
            }
        });

        // TESTS
        // Division list test
        /*
        ObservableList<Division> divList = getDivData();
        for (Division d: divList) {
            System.out.println("Division Name: " + d.getDivId());
            System.out.println("Country Name: " + d.getDivName());
        }

         */

        // Country list test
        /*
        ObservableList<Country> countryList = getCountryData();
        for (Country c: countryList) {
            System.out.println("Country name: " + c.getCountryName());
        }

         */

        // Update customer test
         /*
        int rowsAffected = 0;
        try {
            rowsAffected = CustomerDAO.updateCustomer(4, "Ashley", "5678 Init Road", "84041", "801-345-4567", 103);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (rowsAffected > 0) {
            System.out.println("Update successful");
        }
        else {
            System.out.println("Update failed");
        }
         */

        // Insert customer test
        int rowsAffected = 0;
        try {
            rowsAffected = CustomerDAO.insertCustomer("Ashley", "1234 Weblo Ave", "77445", "123-334-1234",29);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (rowsAffected > 0) {
            System.out.println("Insert successful");
        }
        else {
            System.out.println("Insert failed");
        }

        // System print table data
        /*ObservableList<Customer> custList = getCustomerList();
        for(Customer C: custList) {
            System.out.println("Customer ID: " + C.getId() + " Name: " + C.getName());
        }*/

        // CustomerDAO.checkDateConversion();
    }

    private void populateCustTable() {
        // Populate Customer Table on Customers form
        CustTable.setItems(getCustomerList());
        CustTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        CustTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        CustTableAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        CustTablePostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        CustTablePhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        CustTableDivId.setCellValueFactory(new PropertyValueFactory<>("divId"));
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

    public void ReportsButtonAction(ActionEvent actionEvent) throws IOException {
        // Load Schedule Page
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 653);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }

    public void AddButtonAction(ActionEvent actionEvent) {
        Alert alert;

        // Get new field values
        name = CustNameField.getText();
        address = CustAddressField.getText();
        postalCode = CustPostalCodeField.getText();
        phoneNumber = CustPhoneNumberField.getText();
        divId = customer.getDivId();

        if (addCustomer(name, address, postalCode, phoneNumber, divId)) {
            // Confirm customer added
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Customer");
            alert.setContentText("Customer has been added.");
            alert.showAndWait();
        }
    }

    public void UpdateButtonAction(ActionEvent actionEvent) throws SQLException {
        Alert alert;

        // Get new field values
        name = CustNameField.getText();
        address = CustAddressField.getText();
        postalCode = CustPostalCodeField.getText();
        phoneNumber = CustPhoneNumberField.getText();
        divId = customer.getDivId();

        if (updateCustomer(id, name, address, postalCode, phoneNumber, divId)) {
            // Repopulate table
            populateCustTable();

            // Confirm customer updated
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Customer");
            alert.setContentText("Customer " + id + " has been updated.");
            alert.showAndWait();
        }
    }

    public void DeleteButtonAction(ActionEvent actionEvent) throws SQLException {
        Alert alert;

        // Get selected customer from table
        Customer selected = (Customer)CustTable.getSelectionModel().getSelectedItem();

        // If nothing selected, alert user to select customer
        if (selected == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("Please select a customer to delete.");
            alert.showAndWait();
            return;
        }
        else {
            // If delete is unsuccessful,notify user, else repopulate table
            if (deleteCustomer(selected)) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setContentText("Delete unsuccessful.");
                alert.showAndWait();
            }
        }
    }

    public void ClearButtonAction(ActionEvent actionEvent) {
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
}
