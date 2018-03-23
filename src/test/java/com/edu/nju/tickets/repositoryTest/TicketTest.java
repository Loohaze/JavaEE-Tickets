package com.edu.nju.tickets.repositoryTest;

import com.edu.nju.tickets.model.Order;
import com.edu.nju.tickets.model.Ticket;
import com.edu.nju.tickets.repository.OrderRepository;
import com.edu.nju.tickets.repository.ProjectRepository;
import com.edu.nju.tickets.repository.SeatRepository;
import com.edu.nju.tickets.repository.TicketRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketTest {


    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void testAdd(){

        Order order = orderRepository.findByOrderId(5L);
        Ticket ticket1 = new Ticket();
        ticket1.setOrder(order);
        ticket1.setSeat(seatRepository.findBySeatId(1L));
        ticket1.setProject(projectRepository.findByProjectId(1L));
        ticket1.setPrice(1000);

        ticketRepository.save(ticket1);
    }
}
