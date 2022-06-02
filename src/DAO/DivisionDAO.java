package DAO;

import javafx.collections.ObservableList;
import model.Customer;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.collections.FXCollections.observableArrayList;

public abstract class DivisionDAO {
    private int divId;
    private String divName;
    private int countryId;

    private static ObservableList<Division> divList = observableArrayList();
    private static ObservableList<Division> divsByCountry = observableArrayList();

    public static ObservableList<Division> getDivData() {
        try {
            // Clear divList
            divList.clear();

            // SQL statement to get all customers from customer table
            String sql = "SELECT * FROM first_level_divisions";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Set bind variables to create customer object, add customer to list
            while (rs.next()) {
                int divId = rs.getInt("Division_ID");
                String divName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                Division division = new Division(divId, divName, countryId);
                divList.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Return customerList from db
        return divList;
    }

    public static ObservableList<Division> getDivsByCountry (int countryIdToFind) {
        try {
            // SQL statement to get all customers from customer table
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Call prepared statement setter method to assign bind variables value
            ps.setInt(1, countryIdToFind);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Set bind variables to create division object, add division to list
            while (rs.next()) {
                int  divId= rs.getInt("Division_ID");
                String divName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                Division division = new Division(divId, divName, countryId);
                divsByCountry.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Return customerList from db
        return divsByCountry;
    }

    /*
    public static int getCountryByDivId(int divIdToFind) {
        int foundCountryId = 0;
        try {
            // SQL statement to get all customers from customer table
            String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Call prepared statement setter method to assign bind variables value
            ps.setInt(1, divIdToFind);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Var to hold country id
            foundCountryId = rs.getInt("Country_ID");

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Return customerList from db
        System.out.println("found country id " + foundCountryId);
        return foundCountryId;

    }
    */
}
