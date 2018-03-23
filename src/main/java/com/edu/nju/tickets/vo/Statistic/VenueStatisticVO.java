package com.edu.nju.tickets.vo.Statistic;

import com.edu.nju.tickets.vo.Order.OrderInfoVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VenueStatisticVO {

    private List<OrderInfoVO> completedOrders;

    private List<OrderInfoVO> overtimeOrders;

    private List<OrderInfoVO> bookedOrders;

    private List<OrderInfoVO> debookOrders;

    private Map<String,Integer> projectIncome;

    private int income;


    public Map<String, Integer> getProjectIncome() {
        return projectIncome;
    }

    public void setProjectIncome(Map<String, Integer> projectIncome) {
        this.projectIncome = projectIncome;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public List<OrderInfoVO> getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(List<OrderInfoVO> completedOrders) {
        this.completedOrders = completedOrders;
    }

    public List<OrderInfoVO> getOvertimeOrders() {
        return overtimeOrders;
    }

    public void setOvertimeOrders(List<OrderInfoVO> overtimeOrders) {
        this.overtimeOrders = overtimeOrders;
    }

    public List<OrderInfoVO> getBookedOrders() {
        return bookedOrders;
    }

    public void setBookedOrders(List<OrderInfoVO> bookedOrders) {
        this.bookedOrders = bookedOrders;
    }

    public List<OrderInfoVO> getDebookOrders() {
        return debookOrders;
    }

    public void setDebookOrders(List<OrderInfoVO> debookOrders) {
        this.debookOrders = debookOrders;
    }
}
