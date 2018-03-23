package com.edu.nju.tickets.exception.userException;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String userName) {
        super("User " + userName + " is not found");
    }
}
