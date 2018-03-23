package com.edu.nju.tickets.service.impl;

import com.edu.nju.tickets.model.*;
import com.edu.nju.tickets.repository.*;
import com.edu.nju.tickets.service.StatisticService;
import com.edu.nju.tickets.util.Config;
import com.edu.nju.tickets.vo.Order.OrderInfoVO;
import com.edu.nju.tickets.vo.Order.TicketInfoVO;
import com.edu.nju.tickets.vo.Statistic.ManagerStatisticVO;
import com.edu.nju.tickets.vo.Statistic.UserStatisticInfo;
import com.edu.nju.tickets.vo.Statistic.UserStatisticVO;
import com.edu.nju.tickets.vo.Statistic.VenueStatisticVO;
import com.edu.nju.tickets.vo.User.UserInfoVO;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.*;

@Service
public class StatisticImpl implements StatisticService {

    private final VenueRepository venueRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final ProjectRepository projectRepository;
    private final SeatRepository seatRepository;


    @Autowired
    public StatisticImpl(VenueRepository venueRepository, UserRepository userRepository, OrderRepository orderRepository, TicketRepository ticketRepository,ProjectRepository projectRepository,SeatRepository seatRepository) {
        this.venueRepository = venueRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.projectRepository = projectRepository;
        this.seatRepository = seatRepository;
    }


    @Override
    public VenueStatisticVO venueStatistic(String venueId) {
        VenueStatisticVO statisticVO = new VenueStatisticVO();
        statisticVO.setBookedOrders(new ArrayList<>());
        statisticVO.setCompletedOrders(new ArrayList<>());
        statisticVO.setDebookOrders(new ArrayList<>());
        statisticVO.setOvertimeOrders(new ArrayList<>());
        List<Order> orders = orderRepository.findByVenue(venueRepository.findByVenueId(venueId));
        int income = 0;
        for (Order order : orders) {
            switch (order.getOrderCondition()) {
                case Config.ORDER_CONDITION.COMPLETED:
                    statisticVO.getCompletedOrders().add(orderModel2Vo(order));
                    income += order.getSumPrice();
                    break;
                case Config.ORDER_CONDITION.OVERTIME:
                    statisticVO.getOvertimeOrders().add(orderModel2Vo(order));
                    break;
                case Config.ORDER_CONDITION.BOOKED:
                    statisticVO.getBookedOrders().add(orderModel2Vo(order));
                    break;
                case Config.ORDER_CONDITION.DEBOOKED:
                    statisticVO.getDebookOrders().add(orderModel2Vo(order));
                    break;
            }
        }

        Map<String, Integer> projectIncome = new HashMap<>();
        List<Ticket> tickets = ticketRepository.findAll();
        for (Ticket ticket : tickets) {
            if (ticket.getProject().getVenue().getVenueId().equals(venueId)) {
                if (!projectIncome.containsKey(ticket.getProject().getProjectName())) {
                    projectIncome.put(ticket.getProject().getProjectName(), ticket.getPrice());
                } else {
                    Integer temp = projectIncome.get(ticket.getProject().getProjectName());
                    Integer newtemp = temp + ticket.getPrice();
                    projectIncome.replace(ticket.getProject().getProjectName(),temp,newtemp);
                }
            }
        }
        statisticVO.setIncome(income);
        statisticVO.setProjectIncome(projectIncome);

        return statisticVO;
    }

    @Override
    public UserStatisticVO userStatistic(String username) {
        UserStatisticVO statisticVO = new UserStatisticVO();
        statisticVO.setBookedOrders(new ArrayList<>());
        statisticVO.setCompletedOrders(new ArrayList<>());
        statisticVO.setDebookOrders(new ArrayList<>());
        statisticVO.setOvertimeOrders(new ArrayList<>());
        List<Order> orders = orderRepository.findByUser(userRepository.findByName(username));
        int expense = 0;
        for (Order order : orders) {
            switch (order.getOrderCondition()) {
                case Config.ORDER_CONDITION.COMPLETED:
                    statisticVO.getCompletedOrders().add(orderModel2Vo(order));
                    expense += order.getSumPrice();
                    break;
                case Config.ORDER_CONDITION.OVERTIME:
                    statisticVO.getOvertimeOrders().add(orderModel2Vo(order));
                    break;
                case Config.ORDER_CONDITION.BOOKED:
                    statisticVO.getBookedOrders().add(orderModel2Vo(order));
                    break;
                case Config.ORDER_CONDITION.DEBOOKED:
                    statisticVO.getDebookOrders().add(orderModel2Vo(order));
                    break;
            }
        }
        statisticVO.setExpense(expense);

        return statisticVO;
    }

    @Override
    public ManagerStatisticVO managerStatistic() {
        ManagerStatisticVO vo = new ManagerStatisticVO();
        Map<String,Integer> venueIncome = new HashMap<>();
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            if (!venueIncome.containsKey(order.getVenue().getName())) {
                venueIncome.put(order.getVenue().getName(),order.getSumPrice());
            } else {
                Integer temp = venueIncome.get(order.getVenue().getName());
                Integer newtemp = temp + order.getSumPrice();
                venueIncome.replace(order.getVenue().getName(),temp,newtemp);
            }
        }

        List<UserStatisticInfo> userInfoList = new ArrayList<>();
        List<User> allUser = userRepository.findAll();
        for (User user : allUser) {
            UserStatisticInfo userVo = new UserStatisticInfo();
            userVo.setUserName(user.getName());
            userVo.setGender(user.getGender());
            userVo.setPhoneNumber(user.getPhone());
            userVo.setBirthday(user.getBirthday());
            userVo.setUserEmail(user.getMail());
            userVo.setPoints(user.getPoints());
            userVo.setLevel(user.getLevel());

            List<Order> userOrder = orderRepository.findByUserOrderByTimeDesc(user);
            String lastOrderTime = "";
            int expense = 0;
            for (Order order : userOrder) {
                expense += order.getSumPrice();
            }

            if (!userOrder.isEmpty()){
                lastOrderTime = userOrder.get(0).getTime();
            }
            userVo.setLastOrderTime(lastOrderTime);
            userVo.setExpense(expense);
            userInfoList.add(userVo);
        }



        Map<String,Object> ticketInfo = new HashMap<>();
        List<Order> allOrders = orderRepository.findAll();
        double income = 0;
        double outcome = 0;
        for (Order order : allOrders) {
            income += order.getSumPrice();
            if (order.getSettleCondition().equals(Config.ORDER_SETTLE_CONDITION.SETTLED)) {
                outcome += order.getSumPrice() * 0.85;
            }
        }
        ticketInfo.put("income",income);
        ticketInfo.put("outcome",outcome);


        Map<String,Object> attendence = new HashMap<>();
        List<Venue> venues = venueRepository.findAll();
        for (Venue venue : venues) {
            double bookedNum = 0;
            double idleNum = 0;
            List<Project> projects = projectRepository.findByVenue(venue);
            for (Project project : projects) {
                List<Seat> seats = seatRepository.findByProject(project);
                for (Seat seat : seats) {
                    if (seat.getState().equals(Config.SEAT_STATE.BOOKED)) {
                        bookedNum++;
                    } else {
                        idleNum++;
                    }
                }
            }
            NumberFormat nt = NumberFormat.getPercentInstance();
            nt.setMinimumFractionDigits(2);
            String rate = nt.format(bookedNum / (bookedNum + idleNum));
            rate = rate.substring(0,rate.length()-1);
            attendence.put(venue.getName(),Double.valueOf(rate));
        }

        vo.setAttendence(attendence);
        vo.setUserInfo(userInfoList);
        vo.setVenueIncome(venueIncome);
        vo.setTicketInfo(ticketInfo);
        return vo;
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
