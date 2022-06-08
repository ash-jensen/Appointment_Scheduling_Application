package DAO;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Customer;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public abstract class CustomerDAO {
    private static ObservableList<Customer> customerList = observableArrayList();
    private static Customer customer;

    public static ObservableList<Customer> getCustomerData() {
        try {
            // SQL statement to get all customers from customer table
            String sql = "SELECT * FROM customers";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Clear customerList
            customerList.clear();

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

        // Return customerList from db
        return customerList;
    }

    public static int addCustomer(String name, String address, String postalCode, String phoneNumber, int divId) {
        int custId = 0;

        try {
            // SQL statement to insert customer in customers table
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";

            // Get connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Call prepared statement setter method to assign bind variables value
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setInt(5, divId);

            // Execute the insert, get returned customer id
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            custId = rs.getInt(1);

        }
        catch (SQLException throwables) {
            // Catch if errors with SQL
            throwables.printStackTrace();
        }

        // return rowsAffected;
        return custId;
    }

    public static int updateCustomer(int custId, String name, String address, String postalCode, String phoneNumber, int divId) {
        Alert alert;
        int rowsAffected = 0;

        alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit customer #" + custId + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // SQL statement to update customer with given customer id
                String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

                // Get connection to DB and send over the SQL
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

                // Call prepared statement setter method to assign bind variable values
                ps.setString(1, name);
                ps.setString(2, address);
                ps.setString(3, postalCode);
                ps.setString(4, phoneNumber);
                ps.setInt(5, divId);
                ps.setInt(6, custId);

                // Execute the update, assign num of rows affected to var to return
                rowsAffected = ps.executeUpdate();
                return rowsAffected;
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return rowsAffected;
    }

    public static int deleteCustomer(Customer customerToDelete) {
        Alert alert;
        int rowsAffected = 0;
        int custId = customerToDelete.getId();

        // Confirm user wants to delete customer & delete
        alert = new Alert(Alert.AlertType.CONFIRMATION, "If you delete this customer, any associated appointments will also be deleted." +
                " Are you sure you want to delete customer #" + custId + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // SQL statement to run
                String sqla = "DELETE FROM appointments WHERE Customer_ID = ?";

                // Get a connection to DB and send over the SQL
                PreparedStatement psa = JDBC.getConnection().prepareStatement(sqla);

                // Call prepared statement setter method to assign bind variables value
                psa.setInt(1, custId);

                // Var of updated rows to return
                rowsAffected = psa.executeUpdate();

                // SQL statement to run
                String sql = "DELETE FROM customers WHERE Customer_ID = ?";

                // Get a connection to DB and send over the SQL
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

                // Call prepared statement setter method to assign bind variables value
                ps.setInt(1, custId);

                // Var of updated rows to return
                rowsAffected = ps.executeUpdate();
                return rowsAffected;

            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return rowsAffected;
    }

    public static Customer getCustomerById(int custIdToFind) {
        try {
            // SQL statement to get all customers from customer table
            String sql = "SELECT * FROM customers WHERE Customer_ID = ?";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, custIdToFind);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Set bind variables to create customer object, add customer to list
            rs.next();
            String custName = rs.getString("Customer_Name");
            String custAddress = rs.getString("Address");
            String custPostalCode = rs.getString("Postal_Code");
            String custPhoneNumber = rs.getString("Phone");
            int custDivId = rs.getInt("Division_ID");
            customer = new Customer(custIdToFind, custName, custAddress, custPostalCode, custPhoneNumber, custDivId);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Return customer from db
        return customer;
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



