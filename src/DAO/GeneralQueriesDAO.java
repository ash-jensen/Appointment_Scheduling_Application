package DAO;

import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class GeneralQueriesDAO {
    public static int getNumAppts(String month, String apptType) {
        int numAppts = 0;
        try {
            // SQL statement to get appointments of month and apptType
            String sql = "SELECT * FROM appointments WHERE MONTHNAME(Start) = ? AND Type = ?";

            // Get a connection to DB and send over the SQL
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, month);
            ps.setString(2, apptType);

            // Get results of query
            ResultSet rs = ps.executeQuery();

            // Count number of rows
            while(rs.next()) {
                numAppts++;
            }
            return numAppts;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Return number of appointments
        return numAppts;
    }
}
