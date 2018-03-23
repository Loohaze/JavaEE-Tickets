package com.edu.nju.tickets.vo.User;

public class UserLevelVO {

    private Long userId;

    private int level;

    private int points;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "UserLevelVO{" +
                "userId=" + userId +
                ", level=" + level +
                ", points=" + points +
                '}';
    }
}
