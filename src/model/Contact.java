/**
 * @author Russell Jewell
 * Customer Relationshop Manager
 * Contact Model
 * */

package model;

/** This class creates a Contact object. */
public class Contact {
    private int contactID;
    private String contactName;

    /** Contact constructor.
     * @param contactID The contact ID.
     * @param contactName The contact name.
     * */
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
