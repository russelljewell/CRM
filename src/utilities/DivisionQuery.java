package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionQuery {

    public static ObservableList<Division> divisions(int countryID) {
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Country_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                Division division = new Division(divisionID, divisionName, countryID);
                allDivisions.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allDivisions;
    }

    public static int associatedCountry(int divisionID) throws SQLException {
            String sql = "SELECT Country_ID FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ps.setInt(1, divisionID);
            int countryID = rs.getInt("Country_ID");
            return countryID;
    }
}
