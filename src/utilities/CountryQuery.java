package utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CountryQuery {

    public static int insert(String countryName) throws SQLException {
        String sql = "INSERT INTO COUNTRIES (Country) VALUES (?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, countryName);
        int rowsAffected = ps.executeUpdate();
        return  rowsAffected;
    }

    public static int update(int countryID, String countryName) throws SQLException {
        String sql = "UPDATE COUNTRIES SET Country = ? WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, countryName);
        ps.setInt(2, countryID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int countryID) throws SQLException {
        String sql = "DELETE FROM COUNTRIES WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static void query() throws SQLException {
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
        }
    }
}
