package controller;

import DAO.CustomerDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static DAO.CustomerDAO.deleteCustomer;

public class Customers implements Initializable {

    public TableView CustTable;
    public TableColumn CustTableId;
    public TableColumn CustTableName;
    public TableColumn CustTableAddress;
    public TableColumn CustTablePostalCode;
    public TableColumn CustTablePhoneNumber;
    public TableColumn CustTableDivId;
    /*
    private ObservableList<ObservableList<String>> data;
    */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");


        // TESTS

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
        /*
        int rowsAffected = 0;
        try {
            rowsAffected = CustomerDAO.insertCustomer("Rory", "1234 Weblo Ave", "77445", "123-334-1234",29);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (rowsAffected > 0) {
            System.out.println("Insert successful");
        }
        else {
            System.out.println("Insert failed");
        }
         */


        // Fill customer table with customer data
        populateCustTable();

        // Lambda function for selecting table object
        /*
        CustTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println(newSelection);
            }
        });
        */

        // System print table data
        /*
        ObservableList<Customer> custList = CustomerDAO.getAllCustomers();
        for(Customer C: custList) {
            System.out.println("Customer ID: " + C.getId() + " Name: " + C.getName());
        }

         */

        // CustomerDAO.checkDateConversion();
    }

    private void populateCustTable() {
        // Populate Customer Table on Customers form
        CustTable.setItems(CustomerDAO.getCustomerData());
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

    public void ClearButtonAction(ActionEvent actionEvent) {
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
            // Get customer id to delete
            int custId = selected.getId();

            // Call delete and check if successful
            if (CustomerDAO.deleteCustomer(custId) > 0) {
                // Repopulate customer table
                populateCustTable();
            }
            else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setContentText("Delete unsuccessful.");
                alert.showAndWait();
            }
        }
    }

    // Dynamic populating of table
    /*
    // Populate CustTable
    public void setCustTable() {
        data = FXCollections.observableArrayList();
        try {
            // SQL statement to run
            String sql = "Select * from customers";
            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Insert titles into columns: for each rs, set column name to rs.columnName and cellValueFactory determines what to show in cell
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    final int j = i;
                    TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    });
                    CustTable.getColumns().addAll(col);
                    // Check
                    System.out.println("Column " + i + " successful.");
            }

            // Insert data into rows: for each rs, add each column at row value to row, then add to data
            while(rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
                // Check
                System.out.println("Row added: " + row);
            }

            // Add to CustTable
            CustTable.setItems(data);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Error building data");
        }
    }
     */

}
