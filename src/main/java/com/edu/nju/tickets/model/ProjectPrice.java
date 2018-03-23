package com.edu.nju.tickets.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 计划票价
 * priceId
 * project         计划
 * price           座位价格
 * number          座位数量
 */
@Entity
@Table(name = "project_price")
public class ProjectPrice implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "price_id")
    private Long priceId;

    @ManyToOne()
    @JoinColumn(name = "project_id",referencedColumnName = "project_id")
    private Project project;

    @Column(name = "project_price")
    private int price;

    @Column(name = "project_number")
    private int number;

    public ProjectPrice() {
    }

    public ProjectPrice(int price, int number) {
        this.price = price;
        this.number = number;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ProjectPrice{" +
                "priceId=" + priceId +
                ", price=" + price +
                ", number=" + number +
                '}';
    }
}
