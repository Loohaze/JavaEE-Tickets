package com.edu.nju.tickets.exception.SeatException;

public class SeatAlreadyBookedException extends Exception {
    public SeatAlreadyBookedException() {
        super("The seats you chose have been booked");
    }
}
