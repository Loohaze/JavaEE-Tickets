package com.edu.nju.tickets.serviceTest;

import com.edu.nju.tickets.service.SeatService;
import com.edu.nju.tickets.util.Config;
import com.edu.nju.tickets.vo.Order.SeatInfoVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeatTest {

    @Autowired
    private SeatService seatService;

    @Test
    public void testSetProjectSeats() {
        List<SeatInfoVO> list = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 20; j++) {
                SeatInfoVO seatInfoVO = new SeatInfoVO();
                seatInfoVO.setRow(i);
                seatInfoVO.setNumber(j);
                seatInfoVO.setProjectId(1L);
                seatInfoVO.setVenueId("QUjci2U");
                seatInfoVO.setState(Config.SEAT_STATE.IDLE);
                seatInfoVO.setPrice(600);
                list.add(seatInfoVO);
            }
        }

        for (int i = 11; i <= 20; i++) {
            for (int j = 1; j<= 50 ; j++) {
                SeatInfoVO seatInfoVO = new SeatInfoVO();
                seatInfoVO.setRow(i);
                seatInfoVO.setNumber(j);
                seatInfoVO.setProjectId(1L);
                seatInfoVO.setVenueId("QUjci2U");
                seatInfoVO.setState(Config.SEAT_STATE.IDLE);
                seatInfoVO.setPrice(400);
                list.add(seatInfoVO);
            }
        }

        for (int i = 21; i <= 30; i++) {
            for (int j = 1; j <= 50; j++) {
                SeatInfoVO seatInfoVO = new SeatInfoVO();
                seatInfoVO.setRow(i);
                seatInfoVO.setNumber(j);
                seatInfoVO.setProjectId(1L);
                seatInfoVO.setVenueId("QUjci2U");
                seatInfoVO.setState(Config.SEAT_STATE.IDLE);
                seatInfoVO.setPrice(200);
                list.add(seatInfoVO);
            }
        }

        seatService.setProjectSeat(list);
    }

    @Test
    public void testChart(){
        String projectToken = "unSPtEpYICQ";
        seatService.findChartByProjectToken(projectToken);
    }
}
