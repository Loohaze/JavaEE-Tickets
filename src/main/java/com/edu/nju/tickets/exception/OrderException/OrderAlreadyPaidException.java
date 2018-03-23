package com.edu.nju.tickets.exception.OrderException;

public class OrderAlreadyPaidException extends Exception {
    public OrderAlreadyPaidException() {
        super("The order has been paid");
    }
}
