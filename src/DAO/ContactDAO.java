package DAO;

import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.collections.FXCollections.observableArrayList;

public abstract class ContactDAO {
    private static ObservableList<Contact> contactList = observableArrayList();

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
