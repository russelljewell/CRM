/**
 * @author Russell Jewell
 * Customer Relationshop Manager
 * Contact DAO
 * */

package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class contains database interface methods. This class queries data pertaining to Contact objects.  */
public class ContactQuery {

    /** This method returns an Observable List of all contacts from the database.
     * @return allContacts The Observable List of all contacts.
     * */
    public static ObservableList<Contact> contacts() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CONTACTS";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Contact contact = new Contact(contactID, contactName);
                allContacts.add(contact);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allContacts;
    }
}
