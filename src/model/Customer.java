/**
 * @author Russell Jewell
 * Customer Relationshop Manager
 * Customer Model
 * */

package model;

/** This class creates a Customer object. */
public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionID;
    private int countryID;
    private String divisionName;
    private String countryName;

    /** Customer constructor.
     * @param customerID The customer ID.
     * @param customerName The customer name.
     * @param address The customer address.
     * @param postalCode The customer postal code.
     * @param phoneNumber The customer phone number.
     * @param divisionID The customer division ID.
     * @param countryID The customer country ID.
     * @param divisionName The customer division name.
     * @param countryName The customer country name.
     * */
    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID, int countryID, String divisionName, String countryName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.divisionName = divisionName;
        this.countryName = countryName;
    }

    /**
     * @return The customer ID.
     * */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @return The customer name.
     * */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @return The address.
     * */
    public String getAddress() {
        return address;
    }

    /**
     * @return The postal code.
     * */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @return The phone number.
     * */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return The division ID.
     * */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @return The country ID.
     * */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @return The division name.
     * */
    public String getDivisionName() { return divisionName; }

    /**
     * @return The country name.
     * */
    public String getCountryName() {
        return countryName;
    }

}
