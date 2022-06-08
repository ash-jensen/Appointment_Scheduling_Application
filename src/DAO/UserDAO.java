package DAO;

import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.collections.FXCollections.observableArrayList;

public abstract class UserDAO {
    private static ObservableList<User> userList = observableArrayList();
    private static User user;

    public static ObservableList<User> getUserData() {
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

    public static User getUserById(int userIdToFind) {
        try {
            // SQL statement to get user from users table
            String sql = "SELECT * FROM users WHERE User_ID = ?";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, userIdToFind);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Set bind variables to create user object
            rs.next();
            String userName = rs.getString("User_Name");
            user = new User(userIdToFind, userName);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Return user from db
        return user;
    }

    public static boolean checkLoginInfo(String loginUserName, String loginPassword) {
        Boolean isMatch = false;

        // Connect to db, get password associated with username, check if it matches input password
        try {
            // SQL statement to get country by division ID
            String sql = "SELECT * FROM users WHERE User_Name = ?";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, loginUserName);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Set bind variables to create country object
            rs.next();
            String password = rs.getString("Password");

            // Check username and password match
            if (loginPassword.equals(password)) {
                isMatch = true;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Return result of isMatch;
        return isMatch;
    }

}
