package DAO;

import javafx.collections.ObservableList;
import model.Contact;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * This abstract class is a data access object that gets Contact data from the database.
 *
 * @author Ashley Jensen
 */
public abstract class ContactDAO {
    private static ObservableList<Contact> contactList = observableArrayList();
    private static Contact contact;

    /**
     * This method makes an ObservableList of Contacts using data from the database. It gets Contact_Id, Contact_Name,
     * and Email, and makes a Contact object with the information. The Contact is then put into ObservableList contactList
     * which is then returned.
     * @return ObservableList contactList
     */
    public static ObservableList<Contact> getContactData() {
        // If contact list already filled, return contactList
        if (!contactList.isEmpty()) {
            return contactList;
        }
        try {
            // SQL statement to get all contacts from contacts table
            String sql = "SELECT * FROM contacts";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Set bind variables to create contact object, add contact to list
            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact contact = new Contact(contactId, contactName, email);
                contactList.add(contact);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Return contactList from db
        return contactList;
    }

    /**
     * This method makes an ObservableList of Contacts by contactId using data from the database. It gets Contact_Id,
     * Contact_Name, and Email, and makes a Contact object with the information. The Contact is then put into
     * ObservableList contactList which is then returned.
     * @return ObservableList contactList
     */
    public static Contact getContactById(int contactIdToFind) {
        try {
            // SQL statement to get contact from contacts table
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contactIdToFind);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Set bind variables to create contact object
            rs.next();
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            contact = new Contact(contactIdToFind, contactName, email);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Return contact from db
        return contact;
    }

    /*
    public static Contact getCountryByDiv(int divId) {
        Country country = null;
        try {
            // SQL statement to get country by division ID
            String sql = "SELECT * FROM countries INNER JOIN first_level_divisions " +
                    "ON countries.Country_ID = first_level_divisions.Country_ID " +
                    "WHERE first_level_divisions.Division_ID = ?";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, divId);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Set bind variables to create country object
            rs.next();
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            country = new Country(countryId, countryName);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Return customerList from db
        return country;
    }

     */
}
