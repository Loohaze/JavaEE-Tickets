package com.edu.nju.tickets.serviceTest;

import com.edu.nju.tickets.exception.TicketException.TicketAlreadyCheckedException;
import com.edu.nju.tickets.service.OrderService;
import com.edu.nju.tickets.util.Config;
import com.edu.nju.tickets.vo.Order.OrderInfoVO;
import com.edu.nju.tickets.vo.Order.TicketInfoVO;
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
    private OrderService orderService;

    @Test
    public void testReserveSeated() {
        OrderInfoVO vo = new OrderInfoVO();
        vo.setOrderType(Config.ORDER_TYPE.SEATED);
        vo.setUserName("loohaze");

        TicketInfoVO ticket1 = new TicketInfoVO();
        ticket1.setProjectId(1L);
        ticket1.setSeatId(1201L);
        ticket1.setPrice(600);

        TicketInfoVO ticket2 = new TicketInfoVO();
        ticket2.setProjectId(1L);
        ticket2.setSeatId(1202L);
        ticket2.setPrice(600);

        Set<TicketInfoVO> set = new HashSet<>();
        set.add(ticket1);
        set.add(ticket2);

        vo.setTicketInfo(set);

        orderService.reserve(vo);
    }

    @Test
    public void testReserveUnseated() {
        OrderInfoVO vo = new OrderInfoVO();
        vo.setOrderType(Config.ORDER_TYPE.UNSEATED);
        vo.setUserName("loohaze");

        TicketInfoVO ticket1 = new TicketInfoVO();
        ticket1.setProjectId(1L);
        ticket1.setPrice(400);

        TicketInfoVO ticket2 = new TicketInfoVO();
        ticket2.setProjectId(1L);
        ticket2.setPrice(400);

        Set<TicketInfoVO> set = new HashSet<>();
        set.add(ticket1);
        set.add(ticket2);

        vo.setTicketInfo(set);

        orderService.reserve(vo);
    }

    @Test
    public void testFindOrderByToken() {
        String orderToken = "bCC3R5dK0d";
        OrderInfoVO vo = orderService.findOrderByToken(orderToken);
        System.out.println(vo.toString());
    }

    @Test
    public void testFindOrdersByUser() {
        String userName = "loohaze";
        List<OrderInfoVO> list = orderService.findOrdersByUser(userName);
        for (OrderInfoVO vo : list) {
            System.out.println(vo.toString());
        }
    }

    @Test
    public void testPayment() {
        String orderToken = "OkqgQH3ek1";
//        orderService.payment(orderToken);
    }

    @Test
    public void testCancel() {
        String orderToken = "x8HUAnrr2W";
        orderService.cancelOrder(orderToken);
    }

    @Test
    public void testDebook() {
        String orderToken = "OkqgQH3ek1";
        orderService.debook(orderToken);
    }

    @Test
    public void testCheckTicket() throws TicketAlreadyCheckedException {
        String ticketToken = "bCC3R5dK0d5Psgw04Jed";
        orderService.checkTicket(ticketToken);
    }
}
