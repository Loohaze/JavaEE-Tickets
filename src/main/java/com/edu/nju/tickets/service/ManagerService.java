package com.edu.nju.tickets.service;

import com.edu.nju.tickets.model.Order;
import com.edu.nju.tickets.vo.Order.OrderInfoVO;
import com.edu.nju.tickets.vo.Venue.VenueInfoVO;

import java.util.List;
import java.util.Map;

public interface ManagerService {

    String login(String id,String password);

    Map<String,Integer> findApprovalNum();

    Map<String, List<VenueInfoVO>> findAllPendingInfo();

    String settle(String venueName);

    Map<String,List<OrderInfoVO>> findAllUnsettledOrders();

    Map<String,Integer> findVenueAmount();
}
