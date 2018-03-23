package com.edu.nju.tickets.exception.OrderException;

public class OrderAlreadyCancelledException extends Exception {
    public OrderAlreadyCancelledException() {
        super("The order has been cancelled");
    }
}
