package com.edu.nju.tickets.scheduler;


import com.edu.nju.tickets.model.Order;
import com.edu.nju.tickets.model.Ticket;
import com.edu.nju.tickets.model.Transaction;
import com.edu.nju.tickets.repository.OrderRepository;
import com.edu.nju.tickets.repository.SeatRepository;
import com.edu.nju.tickets.repository.TicketRepository;
import com.edu.nju.tickets.repository.TransactionRepository;
import com.edu.nju.tickets.util.Config;
import com.edu.nju.tickets.util.DateUtil;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


@Component
@EnableScheduling
public class CheckUnpaidOrders {


    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public CheckUnpaidOrders(OrderRepository orderRepository, TransactionRepository transactionRepository, SeatRepository seatRepository, TicketRepository ticketRepository) {
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void cancelUnpaidOrders() {
        String orderCondition = Config.ORDER_CONDITION.BOOKED;
        String paymentCondition = Config.PAYMENT_CONDITION.UNPAID;

        List<Order> unpaidOrders = orderRepository.findByOrderConditionAndPaymentCondition(orderCondition, paymentCondition);

        for (Order order : unpaidOrders) {
            DateTime currentTime = DateTime.now();
            DateTime orderTime = DateUtil.timeFormat(order.getTime());

            Interval interval = new Interval(orderTime, currentTime);
            long intervalMinutes = interval.toDuration().getStandardMinutes();

            if (Integer.parseInt(Long.toString(intervalMinutes)) > 15) {
                order.setOrderCondition(Config.ORDER_CONDITION.OVERTIME);
                order.setSettleCondition(Config.ORDER_SETTLE_CONDITION.SETTLED);
                orderRepository.saveAndFlush(order);

                Transaction transaction = transactionRepository.findByOrder(order);
                transaction.setCondition(Config.TRANSACTION_CONDITION.CANCELLED);
                transactionRepository.saveAndFlush(transaction);

                Set<Ticket> tickets = order.getTickets();
                for (Ticket ticket : tickets) {
                    ticket.getSeat().setState(Config.SEAT_STATE.IDLE);
                    seatRepository.saveAndFlush(ticket.getSeat());
                    ticketRepository.delete(ticket);
                }
            }
        }

    }
}
