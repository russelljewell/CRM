package model;

public class Country {
    private static int countryID;
    private static String countryName;

    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    public static int getCountryID() {
        return countryID;
    }

    public static void setCountryID(int countryID) {
        Country.countryID = countryID;
    }

    public static String getCountryName() {
        return countryName;
    }

    public static void setCountryName(String countryName) {
        Country.countryName = countryName;
    }
}
