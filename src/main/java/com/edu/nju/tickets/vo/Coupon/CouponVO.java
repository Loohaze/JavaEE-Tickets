package com.edu.nju.tickets.vo.Coupon;

import com.edu.nju.tickets.model.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class CouponVO {

    private String couponName;

    private int amount;

    private String couponToken;

    private String username;

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCouponToken() {
        return couponToken;
    }

    public void setCouponToken(String couponToken) {
        this.couponToken = couponToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
