package com.edu.nju.tickets.exception.OrderException;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException() {
        super("not sufficient funds");
    }
}
