package com.edu.nju.tickets.service;

import com.edu.nju.tickets.vo.Order.SeatInfoVO;
import com.edu.nju.tickets.vo.Seat.SeatChartVO;
import com.edu.nju.tickets.vo.Seat.SeatVO;

import java.util.List;

public interface SeatService {

    void setProjectSeat(List<SeatInfoVO> list);

    List<SeatVO> findAllSeatsByProjectToken(String projectToken);

    SeatVO findByRowAndNumAndProjectToken(int row, int number, String projectToken);

    SeatChartVO findChartByProjectToken(String projectToken);
}
