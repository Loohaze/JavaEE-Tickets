package com.edu.nju.tickets.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_account")
public class UserAccount implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "user_account_id")
    private Long accountId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true)
    private User user;

    @Column(name = "user_balance")
    private double balance;

    @Column(name = "user_paypassword")
    private String payPassword;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "accountId=" + accountId +
                ", user=" + user +
                ", balance=" + balance +
                ", payPassword='" + payPassword + '\'' +
                '}';
    }
}
