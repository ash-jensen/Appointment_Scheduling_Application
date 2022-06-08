package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

    public Appointment(int id, int custId, int userId, int contactId, String title, String description, String location,
                       String type, Timestamp startTimestamp, Timestamp endTimestamp) {
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

    public LocalDateTime updateDateTime(Timestamp timeToChange) {
        LocalDateTime utcLocalDateTime = timeToChange.toLocalDateTime();
        ZonedDateTime utcZonedDateTime = utcLocalDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime sysDefZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York")); // Phoenix
        LocalDateTime updatedTime = sysDefZonedDateTime.toLocalDateTime();
        return updatedTime;
    }
}
