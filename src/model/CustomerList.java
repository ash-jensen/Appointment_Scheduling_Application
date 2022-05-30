package model;

import DAO.CustomerDAO;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;

import static DAO.CustomerDAO.getCustomerData;
import static javafx.collections.FXCollections.observableArrayList;

public class CustomerList {
    private static ObservableList<Customer> customerList = observableArrayList();

    public static ObservableList<Customer> getCustomerList() {
        customerList = getCustomerData();
        return customerList;
    }

    public static boolean addCustomer(int custId, String name, String address, String postalCode, String phoneNumber, int divId) {

    }

    /*
    public static boolean updateCustomer(Customer selectedCustomer) {

    }

     */

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
