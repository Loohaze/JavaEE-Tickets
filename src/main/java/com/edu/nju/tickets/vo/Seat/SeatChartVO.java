package com.edu.nju.tickets.vo.Seat;

import java.util.List;
import java.util.Map;

public class SeatChartVO {

    private List<String> chart;

    private List<List<String>> items;

    private Map<String,Map<String,Object>> seats;

    public Map<String, Map<String, Object>> getSeats() {
        return seats;
    }

    public void setSeats(Map<String, Map<String, Object>> seats) {
        this.seats = seats;
    }

    public List<String> getChart() {
        return chart;
    }

    public void setChart(List<String> chart) {
        this.chart = chart;
    }

    public List<List<String>> getItems() {
        return items;
    }

    public void setItems(List<List<String>> items) {
        this.items = items;
    }
}
