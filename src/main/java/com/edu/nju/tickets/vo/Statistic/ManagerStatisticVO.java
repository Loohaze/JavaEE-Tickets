package com.edu.nju.tickets.vo.Statistic;

import com.edu.nju.tickets.vo.User.UserInfoVO;

import java.util.List;
import java.util.Map;

public class ManagerStatisticVO {

    private Map<String,Integer> venueIncome;

    private List<UserStatisticInfo> userInfo;

    private Map<String,Object> ticketInfo;

    private Map<String,Object> attendence;

    public Map<String, Object> getAttendence() {
        return attendence;
    }

    public void setAttendence(Map<String, Object> attendence) {
        this.attendence = attendence;
    }

    public Map<String, Object> getTicketInfo() {
        return ticketInfo;
    }

    public void setTicketInfo(Map<String, Object> ticketInfo) {
        this.ticketInfo = ticketInfo;
    }

    public List<UserStatisticInfo> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserStatisticInfo> userInfo) {
        this.userInfo = userInfo;
    }

    public Map<String, Integer> getVenueIncome() {
        return venueIncome;
    }

    public void setVenueIncome(Map<String, Integer> venueIncome) {
        this.venueIncome = venueIncome;
    }
}
