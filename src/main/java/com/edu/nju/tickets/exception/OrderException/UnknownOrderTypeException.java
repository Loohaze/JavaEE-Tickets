package com.edu.nju.tickets.exception.OrderException;

public class UnknownOrderTypeException extends Exception {
    public UnknownOrderTypeException() {
        super("Unknown Order Type");
    }
}
