package com.edu.nju.tickets.vo.Order;

import java.util.Set;

public class OrderInfoVO {

    private Long orderId;

    private String userName;

    private String venueName;

    private String orderToken;

    private String orderType;

    private String paymentCondition;

    private String orderCondition;

    private int sumPrice;

    private String time;

    private String settleCondition;

    private Set<TicketInfoVO> ticketInfo;

    public String getSettleCondition() {
        return settleCondition;
    }

    public void setSettleCondition(String settleCondition) {
        this.settleCondition = settleCondition;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderToken() {
        return orderToken;
    }

    public void setOrderToken(String orderToken) {
        this.orderToken = orderToken;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaymentCondition() {
        return paymentCondition;
    }

    public void setPaymentCondition(String paymentCondition) {
        this.paymentCondition = paymentCondition;
    }

    public String getOrderCondition() {
        return orderCondition;
    }

    public void setOrderCondition(String orderCondition) {
        this.orderCondition = orderCondition;
    }

    public int getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(int sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Set<TicketInfoVO> getTicketInfo() {
        return ticketInfo;
    }

    public void setTicketInfo(Set<TicketInfoVO> ticketInfo) {
        this.ticketInfo = ticketInfo;
    }

    @Override
    public String toString() {
        return "OrderInfoVO{" +
                "orderId=" + orderId +
                ", userName='" + userName + '\'' +
                ", orderToken='" + orderToken + '\'' +
                ", orderType='" + orderType + '\'' +
                ", paymentCondition='" + paymentCondition + '\'' +
                ", orderCondition='" + orderCondition + '\'' +
                ", sumPrice=" + sumPrice +
                ", time='" + time + '\'' +
                ", ticketInfo=" + ticketInfo +
                '}';
    }
}
