package com.edu.nju.tickets.exception.SeatException;

public class SeatAllocationFailException extends Exception {
    public SeatAllocationFailException() {
        super("Seat allocation fails, the order is cancelled");
    }
}
