package DAO;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointment;
import model.Customer;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

import static javafx.collections.FXCollections.observableArrayList;

public abstract class AppointmentsDAO {
    private static ObservableList<Appointment> allApptsList = observableArrayList();

    public static ObservableList<Appointment> getAllApptData() {
        try {
            // SQL statement to get all customers from customer table
            String sql = "SELECT * FROM appointments";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Clear apptList
            allApptsList.clear();

            // Set bind variables to create appt object, add appt to list
            while(rs.next()) {
                int apptId = rs.getInt("Appointment_ID");
                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String title = rs.getString("Title");
                String location = rs.getString("Location");
                String description = rs.getString("Description");
                Timestamp startTimestamp = rs.getTimestamp("Start");
                // LocalDateTime startDateTime = startTimestamp.toLocalDateTime();
                Timestamp endTimestamp = rs.getTimestamp("End");
                //LocalDateTime endDateTime = endTimestamp.toLocalDateTime();
                String type = rs.getString("Type");
                Appointment appt = new Appointment (apptId, custId, userId, contactId, title, description, location, type, startTimestamp, endTimestamp);
                allApptsList.add(appt);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Return apptList from db
        return allApptsList;
    }

    public static int addAppt(int custId, int userId, int contactId, String title, String description, String location,
                              String type, Timestamp startTimestamp, Timestamp endTimestamp) {
        int apptId = 0;

        try {
            // SQL statement to insert customer in customers table
            String sql = "INSERT INTO appointments (Customer_ID, User_ID, Contact_ID, Title, Description, Location, Type, Start, End) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Get connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Call prepared statement setter method to assign bind variables value
            ps.setInt(1, custId);
            ps.setInt(2, userId);
            ps.setInt(3, contactId);
            ps.setString(4, title);
            ps.setString(5, description);
            ps.setString(6, location);
            ps.setString(7, type);
            ps.setTimestamp(8, startTimestamp);
            ps.setTimestamp(9, endTimestamp);

            // Execute the insert, get returned customer id
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            apptId = rs.getInt(1);

        }
        catch (SQLException throwables) {
            // Catch if errors with SQL
            throwables.printStackTrace();
        }

        // return rowsAffected;
        return apptId;
    }

    public static int updateAppt(int apptId, int custId, int userId, int contactId, String title, String description, String location,
                                 String type, Timestamp startTimestamp, Timestamp endTimestamp ) {
        Alert alert;
        int rowsAffected = 0;

        alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit appointment #" + apptId + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // SQL statement to insert customer in customers table
                String sql = "Update appointments SET Customer_ID = ?, User_ID =?, Contact_ID =?, Title =?, Description =?, Location =?, Type =?, Start =?, End =? WHERE Appointment_ID = ?";

                // Get connection to DB and send over the SQL
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

                // Call prepared statement setter method to assign bind variables value
                ps.setInt(1, custId);
                ps.setInt(2, userId);
                ps.setInt(3, contactId);
                ps.setString(4, title);
                ps.setString(5, description);
                ps.setString(6, location);
                ps.setString(7, type);
                ps.setTimestamp(8, startTimestamp);
                ps.setTimestamp(9, endTimestamp);
                ps.setInt(10, apptId);

                // Execute the update, assign number of rows affected and return
                rowsAffected = ps.executeUpdate();
                return rowsAffected;
            } catch (SQLException throwables) {
                // Catch if errors with SQL
                throwables.printStackTrace();
            }
        }
        // return number of rows affected
        return rowsAffected;
    }


    public static int deleteAppt(Appointment apptToDelete) {
        Alert alert;
        int rowsAffected = 0;
        int apptId = apptToDelete.getId();

        // Confirm user wants to delete customer & delete
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete appointment #" + apptId + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // SQL statement to run
                String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

                // Get a connection to DB and send over the SQL
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

                // Call prepared statement setter method to assign bind variables value
                ps.setInt(1, apptId);

                // Var of updated rows to return
                rowsAffected = ps.executeUpdate();
                return rowsAffected;

            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return rowsAffected;
    }

}
