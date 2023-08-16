package model;

public class Contact {
    private int contactID;
    private String contactName;

    public Contact(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /**
     * @return The contact ID.
     * */
    public int getContactID() {
        return contactID;
    }

    /** Displays contact name in place of contact ID.
     * @return The contact name.
     * */
    @Override
    public String toString() {
        return contactName;
    }
}
