package DAO;

import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.collections.FXCollections.observableArrayList;

public class DivisionDAO {
    private static ObservableList<Division> divList = observableArrayList();

    public static ObservableList<Division> getDivData() {
        try {
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
}
