package com.edu.nju.tickets.exception.tokenException;

public class IncorrectTokenException extends Exception {
    public IncorrectTokenException() {
        super("Token is incorrect");
    }
}
