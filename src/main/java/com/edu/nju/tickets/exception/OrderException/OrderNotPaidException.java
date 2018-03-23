package com.edu.nju.tickets.exception.OrderException;

public class OrderNotPaidException extends Exception {
    public OrderNotPaidException() {
        super("The order is not paid");
    }
}
