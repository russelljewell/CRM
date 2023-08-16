package model;

public class User {
    private int userID;
    private String userName;
    private String password;

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
