package com.edu.nju.tickets.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "venue_record")
public class VenueRecord implements Serializable{

    @Id
    @Column(name = "venue_record_id")
    @GeneratedValue
    private Long recordId;

    @Column(name = "venue_id")
    private String venueId;

    @Column(name = "venue_name")
    private String name;

    @Column(name = "venue_location")
    private String location;

    @Column(name = "venue_seats")
    private int seats;

    @Column(name = "venue_comment")
    private String comment;

    @Column(name = "venue_state")
    private String state;

    @Column(name = "modify_time")
    private Date time;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "VenueRecord{" +
                "recordId=" + recordId +
                ", venueId='" + venueId + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", seats=" + seats +
                ", comment='" + comment + '\'' +
                ", state='" + state + '\'' +
                ", time=" + time +
                '}';
    }
}
