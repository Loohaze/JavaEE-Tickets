package com.edu.nju.tickets.service.impl;

import com.edu.nju.tickets.model.Seat;
import com.edu.nju.tickets.repository.ProjectRepository;
import com.edu.nju.tickets.repository.SeatRepository;
import com.edu.nju.tickets.repository.VenueRepository;
import com.edu.nju.tickets.service.SeatService;
import com.edu.nju.tickets.vo.Order.SeatInfoVO;
import com.edu.nju.tickets.vo.Seat.SeatChartVO;
import com.edu.nju.tickets.vo.Seat.SeatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeatImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final VenueRepository venueRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public SeatImpl(SeatRepository seatRepository, VenueRepository venueRepository, ProjectRepository projectRepository) {
        this.seatRepository = seatRepository;
        this.venueRepository = venueRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void setProjectSeat(List<SeatInfoVO> list) {
        List<Seat> seats = seatVO2model(list);
        for (Seat seat : seats) {
            seatRepository.saveAndFlush(seat);
        }
    }

    @Override
    public List<SeatVO> findAllSeatsByProjectToken(String projectToken) {
        List<Seat> seats = seatRepository.findByProject(projectRepository.findByProjectToken(projectToken));
        return seatModel2VO(seats);
    }

    @Override
    public SeatVO findByRowAndNumAndProjectToken(int row, int number, String projectToken) {
        Seat seat = seatRepository.findByRowAndNumberAndProject(row, number, projectRepository.findByProjectToken(projectToken));
        SeatVO vo = new SeatVO();
        vo.setNumber(seat.getNumber());
        vo.setRow(seat.getRow());
        vo.setSeatId(seat.getSeatId());
        vo.setProjectToken(seat.getProject().getProjectToken());
        vo.setPrice(seat.getPrice());
        vo.setState(seat.getState());
        return vo;
    }

    @Override
    public SeatChartVO findChartByProjectToken(String projectToken) {
        List<Seat> seatList = seatRepository.findByProject(projectRepository.findByProjectToken(projectToken));
        SeatChartVO chartVO = new SeatChartVO();
        List<String> chart = new ArrayList<>();
        List<List<String>> items = new ArrayList<>();
        Map<String,Map<String,Object>> seats = new HashMap<>();

        String[][] tempChart = new String[100][100];

        Map<String, String> price = new HashMap<>();

        String seatValue = "a";
        for (Seat seat : seatList) {
            if (!price.containsKey(String.valueOf(seat.getPrice()))) {
                price.put(String.valueOf(seat.getPrice()), seatValue);

                Map<String,Object> seatMap = new HashMap<>();
                seatMap.put("price",seat.getPrice());
                seatMap.put("classes",seatValue+"-class");
                seatMap.put("category","");
                seats.put(seatValue,seatMap);

                List<String> item = new ArrayList<>();
                item.add(seatValue);
                item.add("available");
                item.add(String.valueOf(seat.getPrice()));
                items.add(item);

                int charValue = seatValue.charAt(0);
                seatValue = String.valueOf((char) (charValue + 1));
            }
        }
        for (Seat seat : seatList) {
            tempChart[seat.getRow() - 1][seat.getNumber() - 1] = price.get(String.valueOf(seat.getPrice()));
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tempChart.length; i++) {
            sb = new StringBuffer();
            for (int j = 0; j < tempChart[0].length; j++) {
                if (tempChart[i][j] != null) {
                    sb.append(tempChart[i][j]);
                }
            }
            if (!sb.toString().equals("")){
                chart.add(sb.toString());
            }
        }

        for (List<String> item : items) {
            for (String s : item) {
                System.out.println(s);
            }
        }
        chartVO.setChart(chart);
        chartVO.setItems(items);
        chartVO.setSeats(seats);

        return chartVO;
    }

    private List<Seat> seatVO2model(List<SeatInfoVO> list) {
        List<Seat> seats = new ArrayList<>();

        for (SeatInfoVO vo : list) {
            Seat seat = new Seat();
            seat.setRow(vo.getRow());
            seat.setNumber(vo.getNumber());
            seat.setState(vo.getState());
            seat.setPrice(vo.getPrice());
            seat.setVenue(venueRepository.findByVenueId(vo.getVenueId()));
            seat.setProject(projectRepository.findByProjectName(vo.getProjectName()));
            seats.add(seat);
        }

        return seats;
    }

    private List<SeatVO> seatModel2VO(List<Seat> list) {
        List<SeatVO> vos = new ArrayList<>();

        for (Seat seat : list) {
            SeatVO vo = new SeatVO();
            vo.setNumber(seat.getNumber());
            vo.setRow(seat.getRow());
            vo.setSeatId(seat.getSeatId());
            vo.setProjectToken(seat.getProject().getProjectToken());
            vo.setPrice(seat.getPrice());
            vo.setState(seat.getState());
            vos.add(vo);
        }

        return vos;
    }
}
