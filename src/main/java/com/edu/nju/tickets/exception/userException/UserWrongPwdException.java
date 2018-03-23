package com.edu.nju.tickets.exception.userException;

public class UserWrongPwdException extends Exception {
    public UserWrongPwdException() {
        super("Wrong password");
    }
}
