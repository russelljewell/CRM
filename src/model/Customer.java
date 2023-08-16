package model;

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
