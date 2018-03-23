package com.edu.nju.tickets.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 订单
 * orderId
 * orderType                订单类型
 * Set tickets             票
 * paymentCondition         支付状态
 * orderCondition           订单状态
 * sumPrice                 总金额
 */
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long orderId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "venue_id", referencedColumnName = "venue_id")
    private Venue venue;

    @Column(name = "order_token")
    private String orderToken;

    @Column(name = "order_type")
    private String orderType;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @OrderBy("id asc")
    private Set<Ticket> tickets;

    @Column(name = "payment_condition")
    private String paymentCondition;

    @Column(name = "order_condition")
    private String orderCondition;

    @Column(name = "sum_price")
    private int sumPrice;

    @Column(name = "order_time")
    private String time;

    @Column(name = "settle_condition")
    private String settleCondition;

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getSettleCondition() {
        return settleCondition;
    }

    public void setSettleCondition(String settleCondition) {
        this.settleCondition = settleCondition;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrderToken() {
        return orderToken;
    }

    public void setOrderToken(String orderToken) {
        this.orderToken = orderToken;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getPaymentCondition() {
        return paymentCondition;
    }

    public void setPaymentCondition(String paymentCondition) {
        this.paymentCondition = paymentCondition;
    }

    public int getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(int sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getOrderCondition() {
        return orderCondition;
    }

    public void setOrderCondition(String orderCondition) {
        this.orderCondition = orderCondition;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderToken='" + orderToken + '\'' +
                ", orderType='" + orderType + '\'' +
                ", tickets=" + tickets +
                ", paymentCondition='" + paymentCondition + '\'' +
                ", orderCondition='" + orderCondition + '\'' +
                ", sumPrice=" + sumPrice +
                ", time='" + time + '\'' +
                '}';
    }
}
