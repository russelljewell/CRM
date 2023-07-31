package model;

public class Division {
    private static int divisionID;
    private static String divisionName;
    private static int countryID;

    public Division(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    public static int getDivisionID() {
        return divisionID;
    }

    public static void setDivisionID(int divisionID) {
        Division.divisionID = divisionID;
    }

    public static String getDivisionName() {
        return divisionName;
    }

    public static void setDivisionName(String divisionName) {
        Division.divisionName = divisionName;
    }

    public static int getCountryID() {
        return countryID;
    }

    public static void setCountryID(int countryID) {
        Division.countryID = countryID;
    }
}
