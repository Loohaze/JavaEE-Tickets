package com.edu.nju.tickets.repository;

import com.edu.nju.tickets.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    Ticket findByTicketToken(String ticketToken);

}
