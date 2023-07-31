package model;

public class Customer {
    private static int customerID;
    private static String customerName;
    private static String address;
    private static int postalCode;
    private static int phoneNumber;
    private static int divisionID;

    public Customer(int customerID, String customerName, String address, int postalCode, int phoneNumber, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
    }

    public static int getCustomerID() {
        return customerID;
    }

    public static void setCustomerID(int customerID) {
        Customer.customerID = customerID;
    }

    public static String getCustomerName() {
        return customerName;
    }

    public static void setCustomerName(String customerName) {
        Customer.customerName = customerName;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        Customer.address = address;
    }

    public static int getPostalCode() {
        return postalCode;
    }

    public static void setPostalCode(int postalCode) {
        Customer.postalCode = postalCode;
    }

    public static int getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(int phoneNumber) {
        Customer.phoneNumber = phoneNumber;
    }

    public static int getDivisionID() {
        return divisionID;
    }

    public static void setDivisionID(int divisionID) {
        Customer.divisionID = divisionID;
    }
}
