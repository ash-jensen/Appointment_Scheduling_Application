package model;

import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;

public class Appointment {
    private int id;
    private int custId;
    private int userId;
    private int contactId;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp startTimestamp;
    private Timestamp endTimestamp;
    private String startString;
    private String endString;
    private static ObservableList<String> apptTypes = observableArrayList();
    private static ObservableList<String> monthsOfYear = observableArrayList();

    public Appointment(int id, int custId, int userId, int contactId, String title, String description, String location, String type, Timestamp startTimestamp, Timestamp endTimestamp) {
        this.id = id;
        this. custId = custId;
        this.userId = userId;
        this.contactId = contactId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getEndDateTime() {
        return endTimestamp;
    }

    public void setEndDateTime(Timestamp endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public String getEndString() {
        LocalDateTime endDateTime = endTimestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        endString = endDateTime.format(formatter);
        return endString;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getStartDateTime() {
        return startTimestamp;
    }

    public void setStartDateTime(Timestamp startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getStartString() {
        LocalDateTime startDateTime = startTimestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        startString = startDateTime.format(formatter);
        return startString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static LocalDateTime updateDateTime(LocalDateTime timeToChangeLDT) {
        ZonedDateTime sysDefZDT = timeToChangeLDT.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime estZDT = sysDefZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime updatedTime = estZDT.toLocalDateTime();
        return updatedTime;
    }

    public static ObservableList<String> getAllApptTypes() {
        apptTypes.clear();
        apptTypes.add("Planning Session");
        apptTypes.add("De-Briefing");
        apptTypes.add("Meeting");
        apptTypes.add("Code Review");
        apptTypes.add("Other");
        return apptTypes;
    }

    public static ObservableList<String> getMonthsOfYear() {
        monthsOfYear.clear();
        monthsOfYear.add("January");
        monthsOfYear.add("February");
        monthsOfYear.add("March");
        monthsOfYear.add("April");
        monthsOfYear.add("May");
        monthsOfYear.add("June");
        monthsOfYear.add("July");
        monthsOfYear.add("August");
        monthsOfYear.add("September");
        monthsOfYear.add("October");
        monthsOfYear.add("November");
        monthsOfYear.add("December");
        return monthsOfYear;
    }
}
