package com.edu.nju.tickets.exception.userException;

public class UserExistedException extends Exception{
    public UserExistedException(String userName) {
        super("User " + userName + " is existed");
    }
}
