/**
 * @author Russell Jewell
 * Customer Relationshop Manager
 * User Model
 * */

package model;

/** This class creates a User object. */
public class User {
    private int userID;
    private String userName;
    private String password;

    /** User constructor.
     * @param userID The user ID.
     * @param userName The user name.
     * @param password The user password.
     * */
    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     * @return The user ID.
     * */
    public int getUserID() {
        return userID;
    }

    /**
     * @return The user name.
     * */
    public String getUserName() {
        return userName;
    }

    /**
     * @return The password.
     * */
    public String getPassword() {
        return password;
    }

    /** Displays user name in place of user ID.
     * @return The user name.
     * */
    @Override
    public String toString() { return userName; }
}
