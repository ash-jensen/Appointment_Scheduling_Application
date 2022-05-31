package model;

import DAO.CustomerDAO;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;

import static DAO.CustomerDAO.getCustomerData;
import static DAO.CustomerDAO.updateCustomerInDB;
import static javafx.collections.FXCollections.observableArrayList;

public class CustomerList {
    private static ObservableList<Customer> customerList = observableArrayList();

    public static ObservableList<Customer> getCustomerList() {
        customerList = getCustomerData();
        return customerList;
    }

    public static boolean addCustomer(String name, String address, String postalCode, String phoneNumber, int divId) {
        return false;
    }

    public static boolean updateCustomer(int custId, String name, String address, String postalCode, String phoneNumber, int divId) throws SQLException {
        // Return true if update to DB is complete, else false;
        if (updateCustomerInDB(custId, name, address, postalCode, phoneNumber, divId) > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean deleteCustomer(Customer selectedCustomer) throws SQLException {
        // Get customerID from selected customer
        int customerId = selectedCustomer.getId();

        // If customer is deleted from db, delete from customerList
        if (CustomerDAO.deleteCustomerFromDB(customerId) > 0) {
            return getCustomerList().remove(selectedCustomer);
        }
        else {
            return false;
        }
    }

}
