package com.edu.nju.tickets.vo.Project;

public class ProjectPriceInfoVO {

    private Long priceId;

    private Long projectId;

    private int price;

    private int number;

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ProjectPriceInfoVO{" +
                "priceId=" + priceId +
                ", projectId=" + projectId +
                ", price=" + price +
                ", number=" + number +
                '}';
    }
}
