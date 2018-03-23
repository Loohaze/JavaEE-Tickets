package com.edu.nju.tickets.repository;

import com.edu.nju.tickets.model.Order;
import com.edu.nju.tickets.model.User;
import com.edu.nju.tickets.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderId(Long orderId);

    Order findByOrderToken(String orderToken);

    List<Order> findByUser(User user);

    List<Order> findByOrderConditionAndPaymentCondition(String orderCondition, String paymentCondition);

    List<Order> findByVenue(Venue venue);

    List<Order> findBySettleCondition(String condition);

    List<Order> findByUserOrderByTimeDesc(User user);
}
