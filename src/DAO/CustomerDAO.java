package DAO;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public abstract class CustomerDAO {
    private static ObservableList<Customer> customerList = observableArrayList();

    // Create customer objects using data from database, insert into observable list and return
    public static ObservableList<Customer> getCustomerData() {
        try {
            // SQL statement to get all customers from customer table
            String sql = "SELECT * FROM customers";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Set bind variables to create customer object, add customer to list
            while(rs.next()) {
                int custId = rs.getInt("Customer_ID");
                String custName = rs.getString("Customer_Name");
                String custAddress = rs.getString("Address");
                String custPostalCode = rs.getString("Postal_Code");
                String custPhoneNumber = rs.getString("Phone");
                int custDivId = rs.getInt("Division_ID");
                Customer cust = new Customer(custId, custName, custAddress, custPostalCode, custPhoneNumber, custDivId);
                customerList.add(cust);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    public static int insertCustomer(String name, String address, String postalCode, String phoneNumber, int divId) throws SQLException {
        // SQL statement to insert customer in customers table
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";

        // Get connection to DB and send over the SQL
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        // Call prepared statement setter method to assign bind variables value
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, divId);

        // Execute the insert, assign num of rows affected to var to return
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int updateCustomer(int custId, String name, String address, String postalCode, String phoneNumber, int divId) throws SQLException {
        // SQL statement to update customer with given customer id
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

        // Get connection to DB and send over the SQL
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        // Call prepared statement setter method to assign bind variables value
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, divId);
        ps.setInt(6, custId);

        // Execute the update, assign num of rows affected to var to return
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    // Delete Customer
    public static int deleteCustomer(int custId) throws SQLException {
        Alert alert;
        int rowsAffected = 0;

        // Confirm user wants to delete customer & delete
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            // SQL statement to run
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Call prepared statement setter method to assign bind variables value
            ps.setInt(1, custId);

            // Var of updated rows
            rowsAffected = ps.executeUpdate();
            return rowsAffected;
        }
        else {
            return rowsAffected;
        }
    }


    // check date conversion
    /*
    public static void checkDateConversion() {
        System.out.println("CREATE DATE TEST");
        String sql = "select Create_Date from countries";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    */

}



