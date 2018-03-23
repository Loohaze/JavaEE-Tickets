package com.edu.nju.tickets.vo.Order;

public class SeatInfoVO {

    private Long seatId;

    private String venueId;

    private Long projectId;

    private String projectName;

    private int row;

    private int number;

    private String state;

    private int price;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "SeatInfoVO{" +
                "seatId=" + seatId +
                ", venueId='" + venueId + '\'' +
                ", projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", row=" + row +
                ", number=" + number +
                ", state='" + state + '\'' +
                ", price=" + price +
                '}';
    }
}
