/**
 * @author Russell Jewell
 * Customer Relationshop Manager
 * User DAO
 * */

package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class contains database interface methods. This class queries data pertaining to User objects.  */
public class UserQuery {

    /** This method returns an Observable List of all users from the database This method is used to validate login credentials.
     * @return allUsers The Observable List of all users.
     * */
    public static ObservableList<User> users() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM USERS";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User user = new User(userID, userName, password);
                allUsers.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUsers;
    }

}


