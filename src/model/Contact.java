package model;

public class Contact {
    private static int contactID;
    private static String contactName;

    public Contact(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public static int getContactID() {
        return contactID;
    }

    public static void setContactID(int contactID) {
        Contact.contactID = contactID;
    }

    public static String getContactName() {
        return contactName;
    }

    public static void setContactName(String contactName) {
        Contact.contactName = contactName;
    }
}
