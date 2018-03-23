package com.edu.nju.tickets.repositoryTest;

import com.edu.nju.tickets.model.Order;
import com.edu.nju.tickets.model.Seat;
import com.edu.nju.tickets.model.Ticket;
import com.edu.nju.tickets.model.User;
import com.edu.nju.tickets.repository.*;
import com.edu.nju.tickets.util.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void testAdd() {

        Order order = new Order();
        order.setOrderType(Config.ORDER_TYPE.UNSEATED);
        Set<Ticket> seatSet = new HashSet<>();

        Ticket ticket1 = new Ticket();
        ticket1.setOrder(order);
        ticket1.setSeat(seatRepository.findBySeatId(1L));
        ticket1.setProject(projectRepository.findByProjectId(1L));
        ticket1.setPrice(1000);

        seatSet.add(ticket1);

        order.setTickets(seatSet);

        orderRepository.save(order);
        ticketRepository.save(ticket1);
    }


    @Test
    public void testFindByOrderConditionAndPaymentCondition(){
        String orderCondition = Config.ORDER_CONDITION.BOOKED;
        String paymentCondition = Config.PAYMENT_CONDITION.UNPAID;

        List<Order> list = orderRepository.findByOrderConditionAndPaymentCondition(orderCondition,paymentCondition);
        System.out.println(list.size());
    }

    @Test
    public void test(){
        User user = new User();
        user.setUserId(1L);
        List<Order> orders = orderRepository.findByUserOrderByTimeDesc(user);
        for (Order order : orders) {
            System.out.println(order.toString());
        }
    }
}
