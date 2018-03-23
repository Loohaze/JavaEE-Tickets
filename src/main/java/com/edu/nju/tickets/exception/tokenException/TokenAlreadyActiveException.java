package com.edu.nju.tickets.exception.tokenException;

public class TokenAlreadyActiveException extends Exception {
    public TokenAlreadyActiveException() {
        super("Token is already active");
    }
}
