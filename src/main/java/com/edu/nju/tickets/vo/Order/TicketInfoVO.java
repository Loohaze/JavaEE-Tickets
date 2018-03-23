package com.edu.nju.tickets.vo.Order;

public class TicketInfoVO {

    private Long ticketId;

    private Long projectId;

    private String projectName;

    private String seatName;

    private Long seatId;

    private int price;

    private Long orderId;

    private String ticketState;

    private String ticketToken;

    public String getTicketToken() {
        return ticketToken;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "TicketInfoVO{" +
                "ticketId=" + ticketId +
                ", projectId=" + projectId +
                ", seatId=" + seatId +
                ", price=" + price +
                ", orderId=" + orderId +
                ", ticketState='" + ticketState + '\'' +
                '}';
    }
}
