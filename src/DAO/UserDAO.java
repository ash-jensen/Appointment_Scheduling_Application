package DAO;

import javafx.collections.ObservableList;
import model.Country;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.collections.FXCollections.observableArrayList;

public abstract class UserDAO {
    public static ObservableList<User> userList = observableArrayList();

    public static ObservableList<User> getCountryData() {
        try {
            // SQL statement to get all users from user table
            String sql = "SELECT * FROM users";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            userList.clear();

            // Set bind variables to create user object, add user to list
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User user = new User(userId, userName, password);
                userList.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Return userList from db
        return userList;
    }

    public static void checkLoginInfo(String userName, String password) {
        User user = null;
        try {
            // SQL statement to get country by division ID
            String sql = "SELECT * FROM users WHERE Password = ?"

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, password);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Set bind variables to create country object
            rs.next();
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            user = new user(countryId, countryName);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Return customerList from db
        return country;
    }
}
