package com.edu.nju.tickets.model;

import javax.persistence.*;

@Entity
@Table(name = "user_token")
public class UserToken {

    @Id
    @Column(name = "user_name")
    private String userName;

    @Column(name = "active_state")
    private String activeState;

    @Column(name = "token")
    private String token;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActiveState() {
        return activeState;
    }

    public void setActiveState(String activeState) {
        this.activeState = activeState;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "userId=" + userName +
                ", activeState='" + activeState + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
