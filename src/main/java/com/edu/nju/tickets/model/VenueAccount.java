package com.edu.nju.tickets.model;

import javax.persistence.*;

@Entity
@Table(name = "venue_account")
public class VenueAccount {

    @Id
    @GeneratedValue
    @Column(name = "venue_account_id")
    private Long venueAccountId;

    @OneToOne
    @JoinColumn(name = "venue_id",referencedColumnName = "venue_id")
    private Venue venue;

    @Column(name = "venue_balance")
    private double balance;

    public Long getVenueAccountId() {
        return venueAccountId;
    }

    public void setVenueAccountId(Long venueAccountId) {
        this.venueAccountId = venueAccountId;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
