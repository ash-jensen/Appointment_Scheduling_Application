package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.*;


public class CustomerDAO {

    // Create customer objects using data from database, insert into observable list and return
    public static ObservableList<Customer> getAllCustomers () {
        ObservableList<Customer> custList = FXCollections.observableArrayList();

        try {
            // SQL statement to run
            String sql = "Select * from customers";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Loop through result set to create object Customer, add customer to list
            while(rs.next()) {
                int custId = rs.getInt("Customer_ID");
                String custName = rs.getString("Customer_Name");
                String custAddress = rs.getString("Address");
                String custPostalCode = rs.getString("Postal_Code");
                String custPhoneNumber = rs.getString("Phone");
                int custDivId = rs.getInt("Division_ID");
                Customer cust = new Customer(custId, custName, custAddress, custPostalCode, custPhoneNumber, custDivId);
                custList.add(cust);
            }

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return custList;
    }

// check date conversion???
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

}
