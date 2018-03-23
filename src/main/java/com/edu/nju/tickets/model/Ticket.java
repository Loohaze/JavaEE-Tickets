package com.edu.nju.tickets.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 单张票
 * ticketId
 * project              对应的演出计划
 * seat                 票所对应的座位
 * price                单张票价
 * order                票所对应的订单
 */
@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue
    private Long ticketId;

    @OneToOne()
    @JoinColumn(name = "project_id",referencedColumnName = "project_id")
    private Project project;

    @OneToOne()
    @JoinColumn(name = "seat_id",referencedColumnName = "seat_id")
    private Seat seat;

    @Column(name = "ticket_price")
    private int price;

    @ManyToOne()
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @Column(name = "ticket_state")
    private String ticketState;

    @Column(name = "ticket_token")
    private String ticketToken;

    public String getTicketToken() {
        return ticketToken;
    }

    public void setTicketToken(String ticketToken) {
        this.ticketToken = ticketToken;
    }

    public String getTicketState() {
        return ticketState;
    }

    public void setTicketState(String ticketState) {
        this.ticketState = ticketState;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", project=" + project +
                ", seat=" + seat +
                ", price=" + price +
                ", ticketState='" + ticketState + '\'' +
                ", ticketToken='" + ticketToken + '\'' +
                '}';
    }
}
