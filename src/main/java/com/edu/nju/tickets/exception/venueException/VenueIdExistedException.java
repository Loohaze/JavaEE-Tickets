package com.edu.nju.tickets.exception.venueException;

public class VenueIdExistedException extends Exception {
    public VenueIdExistedException(String venueId) {
        super("Venue " + venueId + " is existed");
    }
}
