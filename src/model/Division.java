package model;

public class Division {
    private int divisionID;
    private String divisionName;
    private int countryID;

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
