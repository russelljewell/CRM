package utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DivisionQuery {

    public static int insert(String divisionName, int countryID) throws SQLException {
        String sql = "INSERT INTO FIRST_LEVEL_DIVISIONS (Division, Country_ID) VALUES (?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, divisionName);
        ps.setInt(2, countryID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int divisionID, String divisionName, int countryID) throws SQLException {
        String sql = "UPDATE FIRST_LEVEL_DIVISIONS SET Division, Country_ID = ? WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, divisionName);
        ps.setInt(2, countryID);
        ps.setInt(3, divisionID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int divisionID) throws SQLException {
        String sql = "DELETE FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
        }
    }

}
