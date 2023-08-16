/**
 * @author Russell Jewell
 * Customer Relationshop Manager
 * Division Model
 * */

package model;

/** This class creates a First Level Division object. */
public class Division {
    private int divisionID;
    private String divisionName;
    private int countryID;

    /** Division constructor.
     * @param divisionID The division ID.
     * @param divisionName The division name.
     * @param countryID The country ID.
     * */
    public Division(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * @return The division ID.
     * */
    public int getDivisionID() {
        return divisionID;
    }

    /** Displays the division name in place of the division ID.
     * @return The division name.
     * */
    @Override
    public String toString() { return divisionName; }
}
