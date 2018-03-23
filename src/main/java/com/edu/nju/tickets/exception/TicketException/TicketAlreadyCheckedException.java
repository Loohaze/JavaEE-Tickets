package com.edu.nju.tickets.exception.TicketException;

public class TicketAlreadyCheckedException extends Exception{
    public TicketAlreadyCheckedException() {
        super("The ticket has been checked");
    }
}
