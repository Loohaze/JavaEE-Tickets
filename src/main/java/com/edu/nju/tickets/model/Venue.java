package com.edu.nju.tickets.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 场馆
 * venueId
 * name             场馆名称
 * location         场馆位置
 * seats            座位数量
 * projects         当前拥有的计划
 */
@Entity
@Table(name = "venue")
public class Venue implements Serializable{

    @Id
    @Column(name = "venue_id")
    private String venueId;

    @Column(name = "venue_name")
    private String name;

    @Column(name = "venue_location")
    private String location;

    @Column(name = "venue_seats")
    private int seats;

    @Column(name = "venue_password")
    private String password;


    @OneToMany(mappedBy = "venue",fetch = FetchType.EAGER)
    @OrderBy("id asc")
    private Set<Project> projects;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
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

    public Set getProjects() {
        return projects;
    }

    public void setProjects(Set projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "venueId='" + venueId + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", seats=" + seats +
                ", projects=" + projects +
                '}';
    }
}
