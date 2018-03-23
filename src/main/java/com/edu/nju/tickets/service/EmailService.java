package com.edu.nju.tickets.service;

import com.edu.nju.tickets.exception.tokenException.IncorrectTokenException;
import com.edu.nju.tickets.exception.tokenException.TokenAlreadyActiveException;
import com.edu.nju.tickets.exception.userException.UserNotFoundException;

public interface EmailService {

    void sendEmail(String userName, String targetEmail);

    void responseEmail(String userName, String token) throws IncorrectTokenException, TokenAlreadyActiveException, UserNotFoundException;
}
