package com.edu.nju.tickets.exception.userException;

public class UserWrittenOffException extends Exception {
    public UserWrittenOffException(String userName) {
        super("User " + userName + " has been written off");
    }
}
