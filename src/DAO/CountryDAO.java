package DAO;

import javafx.collections.ObservableList;
import model.Country;
import model.Customer;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.collections.FXCollections.observableArrayList;

public class CountryDAO {
    private static ObservableList<Country> countryList = observableArrayList();

    public static ObservableList<Country> getCountryData() {
        try {
            // SQL statement to get all customers from customer table
            String sql = "SELECT * FROM countries";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            countryList.clear();

            // Set bind variables to create customer object, add customer to list
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country country = new Country(countryId, countryName);
                countryList.add(country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Return customerList from db
        return countryList;
    }

    public static Country getCountryByDiv(int divId) {
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
}
