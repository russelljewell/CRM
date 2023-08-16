/**
 * @author Russell Jewell
 * Customer Relationshop Manager
 * First-Level Division DAO
 * */

package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class contains database interface methods. This class queries data pertaining to First Level Division objects.  */
public class DivisionQuery {

    /** This method returns an Observable List of all first-level divisions of a given country ID from the database.
     * @param countryID The ID of the selected country.
     * @return allDivisions The Observable List of all divisions.
     * */
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
}
