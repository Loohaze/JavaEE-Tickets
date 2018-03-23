package com.edu.nju.tickets.service;

import com.edu.nju.tickets.exception.TicketException.TicketAlreadyCheckedException;
import com.edu.nju.tickets.vo.Order.OrderInfoVO;

import java.util.List;

public interface OrderService {

    String reserve(OrderInfoVO orderInfoVO);

    void payment(String orderToken,String couponToken);

    void cancelOrder(String orderToken);

    void debook(String orderToken);

    void checkTicket(String ticketToken) throws TicketAlreadyCheckedException;

    OrderInfoVO findOrderByToken(String orderToken);

    List<OrderInfoVO> findOrdersByUser(String userName);

    List<OrderInfoVO> findBookedOrdersByUser(String userName);

    List<OrderInfoVO> findCancelledOrdersByUser(String userName);

    List<OrderInfoVO> findCompletedOrdersByUser(String userName);

    double getDebookAmount(String orderToken);

    void localeBook(String orderToken);
}
