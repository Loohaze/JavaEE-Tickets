package com.edu.nju.tickets.service.impl;

import com.edu.nju.tickets.model.Manager;
import com.edu.nju.tickets.model.Order;
import com.edu.nju.tickets.model.VenueAccount;
import com.edu.nju.tickets.model.VenueRecord;
import com.edu.nju.tickets.repository.*;
import com.edu.nju.tickets.service.ManagerService;
import com.edu.nju.tickets.util.Config;
import com.edu.nju.tickets.vo.Order.OrderInfoVO;
import com.edu.nju.tickets.vo.Venue.VenueInfoVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final VenueRecordRepository venueRecordRepository;
    private final OrderRepository orderRepository;
    private final VenueAccountRepository venueAccountRepository;
    private final VenueRepository venueRepository;

    public ManagerImpl(ManagerRepository managerRepository, VenueRecordRepository venueRecordRepository, OrderRepository orderRepository, VenueAccountRepository venueAccountRepository, VenueRepository venueRepository) {
        this.managerRepository = managerRepository;
        this.venueRecordRepository = venueRecordRepository;
        this.orderRepository = orderRepository;
        this.venueAccountRepository = venueAccountRepository;
        this.venueRepository = venueRepository;
    }

    @Override
    public String login(String id, String password) {
        Manager manager = managerRepository.findByManagerId(id);
        if (manager.getPassword().equals(password)) {
            return Config.MANAGER_VERIFY.PASSED;
        } else {
            return Config.MANAGER_VERIFY.PWD_ERROR;
        }
    }

    @Override
    public Map<String, Integer> findApprovalNum() {
        Map<String, Integer> result = new HashMap<>();
        List<VenueRecord> records = venueRecordRepository.findAll();
        for (VenueRecord record : records) {
            if (record.getState().equals(Config.VENUE_STATE.PENDING)) {
                if (!result.containsKey(record.getComment())) {
                    result.put(record.getComment(), 1);
                } else {
                    Integer temp = result.get(record.getComment());
                    Integer newtemp = temp + result.get(record.getComment());
                    result.replace(record.getComment(), temp, newtemp);
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, List<VenueInfoVO>> findAllPendingInfo() {
        Map<String, List<VenueInfoVO>> result = new HashMap<>();
        List<VenueRecord> records = venueRecordRepository.findAll();
        for (VenueRecord record : records) {
            if (record.getState().equals(Config.VENUE_STATE.PENDING)) {
                if (!result.containsKey(record.getComment())) {
                    List<VenueInfoVO> list = new ArrayList<>();
                    list.add(record2InfoVO(record));
                    result.put(record.getComment(), list);
                } else {
                    List<VenueInfoVO> list = result.get(record.getComment());
                    list.add(record2InfoVO(record));
                }
            }
        }
        return result;
    }

    @Override
    public String settle(String venueName) {
        List<Order> orders = orderRepository.findBySettleCondition(Config.ORDER_SETTLE_CONDITION.UNSETTLED);
        double sumPrice = 0;
        for (Order order : orders) {
            if (order.getVenue().getName().equals(venueName)) {
                sumPrice += order.getSumPrice();
                order.setSettleCondition(Config.ORDER_SETTLE_CONDITION.SETTLED);
            }
        }

        VenueAccount account = venueAccountRepository.findByVenue(venueRepository.findByName(venueName));
        double balance = account.getBalance();
        balance += sumPrice * (0.85);
        account.setBalance(balance);
        venueAccountRepository.saveAndFlush(account);

        return "success";
    }

    @Override
    public Map<String, List<OrderInfoVO>> findAllUnsettledOrders() {
        Map<String, List<OrderInfoVO>> result = new HashMap<>();
        List<Order> orders = orderRepository.findBySettleCondition(Config.ORDER_SETTLE_CONDITION.UNSETTLED);
        for (Order order : orders) {
            if (!result.containsKey(order.getVenue().getName())) {
                List<OrderInfoVO> list = new ArrayList<>();
                list.add(orderModel2VO(order));
                result.put(order.getVenue().getName(), list);
            } else {
                List<OrderInfoVO> list = result.get(order.getVenue().getName());
                list.add(orderModel2VO(order));
            }
        }
        return result;
    }

    @Override
    public Map<String, Integer> findVenueAmount() {
        Map<String, Integer> result = new HashMap<>();

        return null;
    }

    private VenueInfoVO record2InfoVO(VenueRecord record) {
        VenueInfoVO vo = new VenueInfoVO();
        vo.setVenueId(record.getVenueId());
        vo.setVenueName(record.getName());
        vo.setSeatsNum(record.getSeats());
        vo.setLocation(record.getLocation());
        vo.setTime(record.getTime());
        switch (record.getState()) {
            case "pending":
                vo.setApproval("未批准");
                break;
            case "approved":
                vo.setApproval("已批准");
                break;
            case "rejected":
                vo.setApproval("已拒绝");
                break;
        }

        return vo;
    }

    private OrderInfoVO orderModel2VO(Order order) {
        OrderInfoVO vo = new OrderInfoVO();
        vo.setVenueName(order.getVenue().getName());
        vo.setUserName(order.getUser().getName());
        vo.setOrderType(order.getOrderType());
        vo.setOrderCondition(order.getOrderCondition());
        vo.setOrderToken(order.getOrderToken());
        vo.setPaymentCondition(order.getPaymentCondition());
        vo.setSumPrice(order.getSumPrice());
        vo.setTime(order.getTime());
        vo.setSettleCondition(order.getSettleCondition());

        return vo;
    }
}
