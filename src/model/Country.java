/**
 * @author Russell Jewell
 * Customer Relationshop Manager
 * Country Model
 * */

package model;

/** This class creates a Country object. */
public class Country {
    private int countryID;
    private String countryName;

    /** Country constructor.
     * @param countryID The country ID.
     * @param countryName The country name.
     * */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * @return The country ID.
     * */
    public int getCountryID() {
        return countryID;
    }

    /** Displays country name in place of country ID.
     * @return The country name.
     * */
    @Override
    public String toString() {
        return countryName;
    }
}


