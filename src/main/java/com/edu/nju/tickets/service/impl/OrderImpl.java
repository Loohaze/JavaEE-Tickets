package com.edu.nju.tickets.service.impl;

import com.edu.nju.tickets.exception.OrderException.*;
import com.edu.nju.tickets.exception.SeatException.SeatAllocationFailException;
import com.edu.nju.tickets.exception.SeatException.SeatAlreadyBookedException;
import com.edu.nju.tickets.exception.TicketException.TicketAlreadyCheckedException;
import com.edu.nju.tickets.model.*;
import com.edu.nju.tickets.repository.*;
import com.edu.nju.tickets.service.OrderService;
import com.edu.nju.tickets.util.Config;
import com.edu.nju.tickets.util.DateUtil;
import com.edu.nju.tickets.vo.Order.OrderInfoVO;
import com.edu.nju.tickets.vo.Order.TicketInfoVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final TransactionRepository transactionRepository;
    private final VenueRepository venueRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private final VenueAccountRepository venueAccountRepository;
    private final CouponRepository couponRepository;

    public OrderImpl(OrderRepository orderRepository, SeatRepository seatRepository, TicketRepository ticketRepository, TransactionRepository transactionRepository, VenueRepository venueRepository, ProjectRepository projectRepository, UserRepository userRepository, UserAccountRepository userAccountRepository, VenueAccountRepository venueAccountRepository, CouponRepository couponRepository) {
        this.orderRepository = orderRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.transactionRepository = transactionRepository;
        this.venueRepository = venueRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.userAccountRepository = userAccountRepository;
        this.venueAccountRepository = venueAccountRepository;
        this.couponRepository = couponRepository;
    }

    @Override
    public String reserve(OrderInfoVO orderInfoVO) {
        String orderType = orderInfoVO.getOrderType();
        switch (orderType) {
            case Config.ORDER_TYPE.SEATED:
                return seatedOrder(orderInfoVO);
            case Config.ORDER_TYPE.UNSEATED:
                return unseatedOrder(orderInfoVO);
            default:
                try {
                    throw new UnknownOrderTypeException();
                } catch (UnknownOrderTypeException e) {
                    e.printStackTrace();
                }
                break;
        }
        return "";
    }

    @Override
    public void payment(String orderToken,String couponToken) {
        Order order = orderRepository.findByOrderToken(orderToken);
        Transaction transaction = transactionRepository.findByOrder(order);
        switch (order.getOrderCondition()) {
            case Config.ORDER_CONDITION.BOOKED:
                double price = 0;
                UserAccount account = userAccountRepository.findByUser(order.getUser());
                VenueAccount venueAccount = venueAccountRepository.findByVenue(order.getVenue());
                double userBalance = account.getBalance();
                double venueBalance = venueAccount.getBalance();
                if (couponRepository.findByCouponToken(couponToken) != null) {
                    Coupon coupon = couponRepository.findByCouponToken(couponToken);
                    price = order.getSumPrice() - coupon.getAmount();
                }


                if (userBalance < price) {
                    try {
                        throw new InsufficientBalanceException();
                    } catch (InsufficientBalanceException e) {
                        e.printStackTrace();
                    }
                    break;
                } else {
                    userBalance -= price;
                    account.setBalance(userBalance);
                    userAccountRepository.saveAndFlush(account);

                    if (couponRepository.findByCouponToken(couponToken) != null) {
                        Coupon coupon = couponRepository.findByCouponToken(couponToken);
                        couponRepository.delete(coupon);
                    }

                    order.setOrderCondition(Config.ORDER_CONDITION.COMPLETED);
                    order.setPaymentCondition(Config.PAYMENT_CONDITION.PREPAID);
                    order.setSettleCondition(Config.ORDER_SETTLE_CONDITION.UNSETTLED);
                    orderRepository.saveAndFlush(order);

                    transaction.setCondition(Config.TRANSACTION_CONDITION.COMPLETED);
                    transactionRepository.saveAndFlush(transaction);

                    // 获取积分
                    changeUserPoints(orderToken, Config.CHANGE_POINTS_TYPE.ADD);
                    break;
                }
            case Config.ORDER_CONDITION.COMPLETED:
                try {
                    throw new OrderAlreadyPaidException();
                } catch (OrderAlreadyPaidException e) {
                    e.printStackTrace();
                }
                break;
            case Config.ORDER_CONDITION.CANCELED:
                try {
                    throw new OrderAlreadyCancelledException();
                } catch (OrderAlreadyCancelledException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void cancelOrder(String orderToken) {
        Order order = orderRepository.findByOrderToken(orderToken);
        Transaction transaction = transactionRepository.findByOrder(order);

        switch (order.getOrderCondition()) {
            case Config.ORDER_CONDITION.BOOKED:
                order.setOrderCondition(Config.ORDER_CONDITION.CANCELED);
                orderRepository.saveAndFlush(order);

                transaction.setCondition(Config.TRANSACTION_CONDITION.CANCELLED);
                transactionRepository.saveAndFlush(transaction);

                Set<Ticket> tickets = order.getTickets();
                for (Ticket ticket : tickets) {
                    ticket.getSeat().setState(Config.SEAT_STATE.IDLE);
                    seatRepository.saveAndFlush(ticket.getSeat());
                    ticketRepository.delete(ticket);
                }

                break;
            case Config.ORDER_CONDITION.COMPLETED:
                try {
                    throw new OrderAlreadyPaidException();
                } catch (OrderAlreadyPaidException e) {
                    e.printStackTrace();
                }
                break;
            case Config.ORDER_CONDITION.CANCELED:
                try {
                    throw new OrderAlreadyCancelledException();
                } catch (OrderAlreadyCancelledException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void debook(String orderToken) {
        Order order = orderRepository.findByOrderToken(orderToken);
        Transaction transaction = transactionRepository.findByOrder(order);

        switch (order.getOrderCondition()) {
            case Config.ORDER_CONDITION.BOOKED:
                try {
                    throw new OrderNotPaidException();
                } catch (OrderNotPaidException e) {
                    e.printStackTrace();
                }
                break;
            case Config.ORDER_CONDITION.COMPLETED:
                String currentTime = DateUtil.getCurrentDate();
                String endTime = "";
                Set<Ticket> tickets = order.getTickets();
                for (Ticket ticket : tickets) {
                    endTime = ticket.getProject().getEndDate().toString();
                }

                // 一周内退50%，两周内退80%，一个月内退90%
                double punitive = 0.0;
                int remainDays = DateUtil.getIntervalDays(currentTime, endTime);
                if (remainDays <= 7) {
                    punitive = 0.5;
                } else if (remainDays <= 14) {
                    punitive = 0.2;
                } else if (remainDays <= 30) {
                    punitive = 0.1;
                }

                double realAmount = Double.parseDouble(String.valueOf(order.getSumPrice())) * (1.0 - punitive);

                order.setOrderCondition(Config.ORDER_CONDITION.DEBOOKED);
                order.setSettleCondition(Config.ORDER_SETTLE_CONDITION.SETTLED);
                orderRepository.saveAndFlush(order);


                for (Ticket ticket : tickets) {
                    ticket.getSeat().setState(Config.SEAT_STATE.IDLE);
                    seatRepository.saveAndFlush(ticket.getSeat());
                    ticketRepository.delete(ticket);
                }

                VenueAccount venueAccount = venueAccountRepository.findByVenue(order.getVenue());
                double venueBalance = venueAccount.getBalance();
                venueBalance -= realAmount;
                venueAccount.setBalance(venueBalance);
                venueAccountRepository.saveAndFlush(venueAccount);

                UserAccount userAccount = userAccountRepository.findByUser(order.getUser());
                double userBalance = userAccount.getBalance();
                userBalance += realAmount;
                userAccount.setBalance(userBalance);
                userAccountRepository.save(userAccount);

                //扣除积分
                changeUserPoints(orderToken, Config.CHANGE_POINTS_TYPE.SUB);
                break;
            case Config.ORDER_CONDITION.CANCELED:
                try {
                    throw new OrderAlreadyCancelledException();
                } catch (OrderAlreadyCancelledException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void checkTicket(String ticketToken) throws TicketAlreadyCheckedException {
        Ticket ticket = ticketRepository.findByTicketToken(ticketToken);
        switch (ticket.getTicketState()) {
            case Config.TICKET_STATE.UNCHECKED:
                ticket.setTicketState(Config.TICKET_STATE.CHECKED);
                ticketRepository.saveAndFlush(ticket);
                break;
            case Config.TICKET_STATE.CHECKED:
                throw new TicketAlreadyCheckedException();
        }
    }


    @Override
    public OrderInfoVO findOrderByToken(String orderToken) {
        Order order = orderRepository.findByOrderToken(orderToken);
        return orderModel2Vo(order);
    }

    @Override
    public List<OrderInfoVO> findOrdersByUser(String userName) {
        List<OrderInfoVO> orderVos = new ArrayList<>();

        List<Order> orders = orderRepository.findByUser(userRepository.findByName(userName));
        for (Order order : orders) {
            orderVos.add(orderModel2Vo(order));
        }
        return orderVos;
    }

    @Override
    public List<OrderInfoVO> findBookedOrdersByUser(String userName) {
        List<Order> orders = orderRepository.findByUser(userRepository.findByName(userName));

        List<OrderInfoVO> bookedOrderVO = new ArrayList<>();
        for (Order order : orders) {
            if (order.getOrderCondition().equals(Config.ORDER_CONDITION.BOOKED)) {
                bookedOrderVO.add(orderModel2Vo(order));
            }
        }
        return bookedOrderVO;
    }

    @Override
    public List<OrderInfoVO> findCancelledOrdersByUser(String userName) {
        List<Order> orders = orderRepository.findByUser(userRepository.findByName(userName));

        List<OrderInfoVO> bookedOrderVO = new ArrayList<>();
        for (Order order : orders) {
            if (order.getOrderCondition().equals(Config.ORDER_CONDITION.CANCELED) || order.getOrderCondition().equals(Config.ORDER_CONDITION.OVERTIME)) {
                bookedOrderVO.add(orderModel2Vo(order));
            }
        }
        return bookedOrderVO;
    }

    @Override
    public List<OrderInfoVO> findCompletedOrdersByUser(String userName) {
        List<Order> orders = orderRepository.findByUser(userRepository.findByName(userName));

        List<OrderInfoVO> bookedOrderVO = new ArrayList<>();
        for (Order order : orders) {
            if (order.getOrderCondition().equals(Config.ORDER_CONDITION.COMPLETED)) {
                bookedOrderVO.add(orderModel2Vo(order));
            }
        }
        return bookedOrderVO;
    }

    @Override
    public double getDebookAmount(String orderToken) {
        Order order = orderRepository.findByOrderToken(orderToken);

        String currentTime = DateUtil.getCurrentDate();
        String endTime = "";
        Set<Ticket> tickets = order.getTickets();
        for (Ticket ticket : tickets) {
            endTime = ticket.getProject().getEndDate().toString();
        }

        // 一周内退50%，两周内退80%，一个月内退90%
        double punitive = 0.0;
        int remainDays = DateUtil.getIntervalDays(currentTime, endTime);
        if (remainDays <= 7) {
            punitive = 0.5;
        } else if (remainDays <= 14) {
            punitive = 0.2;
        } else if (remainDays <= 30) {
            punitive = 0.1;
        }

        double realAmount = Double.parseDouble(String.valueOf(order.getSumPrice())) * (1.0 - punitive);
        return realAmount;
    }

    @Override
    public void localeBook(String orderToken) {
        Order order = orderRepository.findByOrderToken(orderToken);
        order.setOrderCondition(Config.ORDER_CONDITION.COMPLETED);
        order.setPaymentCondition(Config.PAYMENT_CONDITION.PREPAID);
        order.setSettleCondition(Config.ORDER_SETTLE_CONDITION.UNSETTLED);

        Transaction transaction = transactionRepository.findByOrder(order);
        transaction.setCondition(Config.TRANSACTION_CONDITION.COMPLETED);

        orderRepository.saveAndFlush(order);
        transactionRepository.saveAndFlush(transaction);
    }


    private String seatedOrder(OrderInfoVO orderInfoVO) {
        Order order = new Order();
        String orderToken = RandomStringUtils.randomAlphanumeric(10);
        while (orderRepository.findByOrderToken(orderToken) != null) {
            orderToken = RandomStringUtils.randomAlphanumeric(10);
        }

        order.setVenue(venueRepository.findByName(orderInfoVO.getVenueName()));
        order.setUser(userRepository.findByName(orderInfoVO.getUserName()));
        order.setOrderToken(orderToken);
        order.setOrderType(Config.ORDER_TYPE.SEATED);
        order.setPaymentCondition(Config.PAYMENT_CONDITION.UNPAID);
        order.setOrderCondition(Config.ORDER_CONDITION.BOOKED);
        order.setTime(DateUtil.getCurrentDateTime());
        order.setSettleCondition(Config.ORDER_SETTLE_CONDITION.SETTLED);

        orderRepository.saveAndFlush(order);

        Set<TicketInfoVO> ticketInfoVOS = orderInfoVO.getTicketInfo();
        Set<Ticket> tickets = ticketVo2model(ticketInfoVOS, orderToken, Config.ORDER_TYPE.SEATED);

        for (Ticket ticket : tickets) {
            ticket.setTicketState(Config.TICKET_STATE.UNCHECKED);
            ticketRepository.saveAndFlush(ticket);
        }

        order.setTickets(tickets);
        int sumPrice = 0;
        for (Ticket ticket : tickets) {
            sumPrice += ticket.getPrice();
        }
        order.setSumPrice(sumPrice);

        for (Ticket ticket : tickets) {
            Seat seat = ticket.getSeat();
            if (seat.getState().equals(Config.SEAT_STATE.BOOKED)) {
                try {
                    throw new SeatAlreadyBookedException();
                } catch (SeatAlreadyBookedException e) {
                    e.printStackTrace();
                }
            }
            seat.setState(Config.SEAT_STATE.BOOKED);
            seatRepository.saveAndFlush(seat);
        }

        orderRepository.saveAndFlush(order);

        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        transaction.setUser(userRepository.findByName(orderInfoVO.getUserName()));
        transaction.setCondition(Config.TRANSACTION_CONDITION.UNDERWAY);
        transactionRepository.saveAndFlush(transaction);

        return orderToken;
    }

    private String unseatedOrder(OrderInfoVO orderInfoVO) {
        Order order = new Order();
        String orderToken = RandomStringUtils.randomAlphanumeric(10);
        while (orderRepository.findByOrderToken(orderToken) != null) {
            orderToken = RandomStringUtils.randomAlphanumeric(10);
        }

        order.setVenue(venueRepository.findByName(orderInfoVO.getVenueName()));
        order.setUser(userRepository.findByName(orderInfoVO.getUserName()));
        order.setOrderToken(orderToken);
        order.setOrderType(Config.ORDER_TYPE.UNSEATED);
        order.setPaymentCondition(Config.PAYMENT_CONDITION.UNPAID);
        order.setOrderCondition(Config.ORDER_CONDITION.BOOKED);
        order.setTime(DateUtil.getCurrentDateTime());
        order.setSettleCondition(Config.ORDER_SETTLE_CONDITION.SETTLED);

        orderRepository.saveAndFlush(order);

        Set<TicketInfoVO> ticketInfoVOS = orderInfoVO.getTicketInfo();
        Set<Ticket> tickets = ticketVo2model(ticketInfoVOS, orderToken, Config.ORDER_TYPE.UNSEATED);

        Long projectId = 0L;
        for (Ticket ticket : tickets) {
            projectId = ticket.getProject().getProjectId();
        }

        List<Seat> seatList = seatRepository.findByProject(projectRepository.findByProjectId(projectId));

        for (Ticket ticket : tickets) {
            for (Seat seat : seatList) {
                if (seat.getState().equals(Config.SEAT_STATE.IDLE) && (seat.getPrice() == ticket.getPrice())) {
                    seat.setState(Config.SEAT_STATE.BOOKED);
                    seatRepository.saveAndFlush(seat);

                    ticket.setSeat(seat);
                    ticket.setTicketState(Config.TICKET_STATE.UNCHECKED);
                    ticketRepository.saveAndFlush(ticket);
                    break;
                }
            }
        }

        //配票是否成功
        Boolean isAllSeatCompleted = true;
        for (Ticket ticket : tickets) {
            if (ticket.getSeat() == null) {
                isAllSeatCompleted = false;
                break;
            }
        }
        if (!isAllSeatCompleted) {
            order.setOrderCondition(Config.ORDER_CONDITION.CANCELED);
            orderRepository.save(order);

            for (Ticket ticket : tickets) {
                if (ticket.getSeat() != null) {
                    ticket.getSeat().setState(Config.SEAT_STATE.IDLE);
                    seatRepository.saveAndFlush(ticket.getSeat());
                    ticketRepository.delete(ticket);
                }
            }
            try {
                throw new SeatAllocationFailException();
            } catch (SeatAllocationFailException e) {
                e.printStackTrace();
            }
            return "";
        } else {
            order.setTickets(tickets);
            int sumPrice = 0;
            for (Ticket ticket : tickets) {
                sumPrice += ticket.getPrice();
            }
            order.setSumPrice(sumPrice);

            orderRepository.saveAndFlush(order);

            Transaction transaction = new Transaction();
            transaction.setOrder(order);
            transaction.setUser(userRepository.findByName(orderInfoVO.getUserName()));
            transaction.setCondition(Config.TRANSACTION_CONDITION.UNDERWAY);
            transactionRepository.saveAndFlush(transaction);
            return orderToken;
        }


    }

    private void changeUserPoints(String orderToken, String type) {
        Order order = orderRepository.findByOrderToken(orderToken);
        User user = order.getUser();

        int points = user.getPoints();
        int level = user.getLevel();

        if (type.equals(Config.CHANGE_POINTS_TYPE.ADD)) {
            points += order.getSumPrice();
        } else if (type.equals(Config.CHANGE_POINTS_TYPE.SUB)) {
            points -= order.getSumPrice();
        }

        user.setPoints(points);

        if (points < 500) {
            level = 0;
        } else if (points < 2000) {
            level = 1;
        } else if (points < 5000) {
            level = 2;
        } else if (points < 10000) {
            level = 3;
        } else if (points < 20000) {
            level = 4;
        } else if (points < 50000) {
            level = 5;
        }

        if (user.getLevel() < level) {
            user.setLevel(level);
        }
        userRepository.saveAndFlush(user);
    }


    private Set<Ticket> ticketVo2model(Set<TicketInfoVO> set, String orderToken, String orderType) {
        Set<Ticket> tickets = new HashSet<>();
        for (TicketInfoVO vo : set) {
            Ticket ticket = new Ticket();
            String ticketToken = RandomStringUtils.randomAlphanumeric(20);
            while (ticketRepository.findByTicketToken(ticketToken) != null) {
                ticketToken = RandomStringUtils.randomAlphanumeric(20);
            }
            ticket.setTicketToken(ticketToken);
            ticket.setPrice(vo.getPrice());
            ticket.setOrder(orderRepository.findByOrderToken(orderToken));
            ticket.setProject(projectRepository.findByProjectName(vo.getProjectName()));
            if (orderType.equals(Config.ORDER_TYPE.SEATED)) {
                ticket.setSeat(seatRepository.findBySeatId(vo.getSeatId()));
            }
            tickets.add(ticket);
        }
        return tickets;
    }


    private OrderInfoVO orderModel2Vo(Order order) {
        OrderInfoVO vo = new OrderInfoVO();

        vo.setOrderType(order.getOrderType());
        vo.setOrderCondition(order.getOrderCondition());
        vo.setPaymentCondition(order.getPaymentCondition());
        vo.setOrderId(order.getOrderId());
        vo.setUserName(order.getUser().getName());
        vo.setOrderToken(order.getOrderToken());
        vo.setSumPrice(order.getSumPrice());
        vo.setTime(order.getTime());
        vo.setTicketInfo(ticketModel2Vo(order.getTickets()));
        vo.setVenueName(order.getVenue().getName());

        return vo;
    }

    private Set<TicketInfoVO> ticketModel2Vo(Set<Ticket> set) {
        Set<TicketInfoVO> vos = new HashSet<>();
        for (Ticket ticket : set) {
            TicketInfoVO vo = new TicketInfoVO();
            vo.setTicketState(ticket.getTicketState());
            vo.setTicketToken(ticket.getTicketToken());
            vo.setPrice(ticket.getPrice());
            vo.setTicketId(ticket.getTicketId());
            vo.setSeatId(ticket.getSeat().getSeatId());
            vo.setSeatName(String.valueOf(ticket.getSeat().getRow()) + "排" + String.valueOf(ticket.getSeat().getNumber()) + "座");
            vo.setOrderId(ticket.getOrder().getOrderId());
            vo.setProjectId(ticket.getProject().getProjectId());
            vo.setProjectName(ticket.getProject().getProjectName());
            vos.add(vo);
        }
        return vos;
    }
}
