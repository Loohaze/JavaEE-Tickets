package com.edu.nju.tickets.vo.Statistic;

import com.edu.nju.tickets.model.Order;
import com.edu.nju.tickets.vo.Order.OrderInfoVO;

import java.util.List;

public class UserStatisticVO {

    private List<OrderInfoVO> completedOrders;

    private List<OrderInfoVO> overtimeOrders;

    private List<OrderInfoVO> bookedOrders;

    private List<OrderInfoVO> debookOrders;

    private int expense;



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

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }
}
