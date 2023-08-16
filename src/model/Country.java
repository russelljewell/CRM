package model;

public class Country {
    private int countryID;
    private String countryName;

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


