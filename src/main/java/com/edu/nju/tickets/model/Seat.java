package com.edu.nju.tickets.model;

import javax.persistence.*;

/**
 * 座位
 * seatId           座位Id
 * venue            对应场馆
 * row              排
 * number           座
 */
@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue
    @Column(name = "seat_id")
    private Long seatId;

    @ManyToOne
    @JoinColumn(name = "venue_id", referencedColumnName = "venue_id")
    private Venue venue;

    @Column(name = "row")
    private int row;

    @Column(name = "number")
    private int number;

    @Column(name = "seat_state")
    private String state;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;

    @Column(name = "seat_price")
    private int price;

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId=" + seatId +
                ", venue=" + venue +
                ", row=" + row +
                ", number=" + number +
                ", state='" + state + '\'' +
                ", project=" + project +
                ", price=" + price +
                '}';
    }
}
