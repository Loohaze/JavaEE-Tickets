package com.edu.nju.tickets.vo.Statistic;

import com.edu.nju.tickets.vo.User.UserInfoVO;

public class UserStatisticInfo extends UserInfoVO {

    private int expense;

    private int points;

    private int level;

    private String lastOrderTime;

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLastOrderTime() {
        return lastOrderTime;
    }

    public void setLastOrderTime(String lastOrderTime) {
        this.lastOrderTime = lastOrderTime;
    }
}
