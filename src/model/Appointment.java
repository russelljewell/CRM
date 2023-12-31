/**
 * @author Russell Jewell
 * Customer Relationshop Manager
 * Appointment Model
 * */

package model;

import java.time.LocalDateTime;

/** This class creates an appointment object. */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerID;
    private int userID;
    private int contactID;

    /** Appointment constructor.
     * @param appointmentID The appointment ID.
     * @param title The appointment title.
     * @param description The appointment description.
     * @param location The appointment location.
     * @param type The appointment type.
     * @param start The appointment start date and time.
     * @param end The appointment end date and time.
     * @param customerID The appointment customer ID.
     * @param userID The appointment user ID.
     * @param contactID The appointment contactID.
     * */
    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * @return The appointment ID.
     * */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * @return The title.
     * */
    public String getTitle() {
        return title;
    }

    /**
     * @return The description.
     * */
    public String getDescription() {
        return description;
    }

    /**
     * @return The location.
     * */
    public String getLocation() {
        return location;
    }

    /**
     * @return The type.
     * */
    public String getType() {
        return type;
    }

    /**
     * @return The start date and time.
     * */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * @return The end date and time.
     * */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * @return The customer ID.
     * */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @return The user ID.
     * */
    public int getUserID() {
        return userID;
    }

    /**
     * @return The contact ID.
     * */
    public int getContactID() {
        return contactID;
    }
}
