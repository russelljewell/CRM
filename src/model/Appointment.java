package model;

import java.time.LocalDateTime;

public class Appointment {
    private static int appointmentID;
    private static String title;
    private static String description;
    private static String location;
    private static String type;
    private static LocalDateTime start;
    private static LocalDateTime end;
    private static int customerID;
    private static int userID;
    private static int contactID;

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

    public static int getAppointmentID() {
        return appointmentID;
    }

    public static void setAppointmentID(int appointmentID) {
        Appointment.appointmentID = appointmentID;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        Appointment.title = title;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        Appointment.description = description;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        Appointment.location = location;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        Appointment.type = type;
    }

    public static LocalDateTime getStart() {
        return start;
    }

    public static void setStart(LocalDateTime start) {
        Appointment.start = start;
    }

    public static LocalDateTime getEnd() {
        return end;
    }

    public static void setEnd(LocalDateTime end) {
        Appointment.end = end;
    }

    public static int getCustomerID() {
        return customerID;
    }

    public static void setCustomerID(int customerID) {
        Appointment.customerID = customerID;
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        Appointment.userID = userID;
    }

    public static int getContactID() {
        return contactID;
    }

    public static void setContactID(int contactID) {
        Appointment.contactID = contactID;
    }
}
