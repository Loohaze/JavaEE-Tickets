package com.edu.nju.tickets.exception.tokenException;

public class TokenNotActiveException extends Exception {
    public TokenNotActiveException(String userName) {
        super("User" + userName + "is not active");
    }
}
