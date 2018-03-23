package com.edu.nju.tickets.serviceTest;

import com.edu.nju.tickets.service.VenueService;
import com.edu.nju.tickets.vo.Venue.VenueInfoVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VenueTest {

    @Autowired
    private VenueService venueService;


    @Test
    public void testRegister(){
        VenueInfoVO vo = new VenueInfoVO();
        vo.setTime(Date.valueOf("2018-01-01"));
        vo.setLocation("Wuxi");
        vo.setSeatsNum(1000);
        vo.setVenueName("LiveHouse");

        venueService.register(vo);
    }

    @Test
    public void testApprovedRegister(){
        String venueId = "QUjci2U";
        venueService.approvedRegister(venueId);
    }

    @Test
    public void testModify(){
        VenueInfoVO vo = new VenueInfoVO();
        vo.setVenueId("QUjci2U");
        vo.setTime(Date.valueOf("2018-01-31"));
        vo.setLocation("Wuxi");
        vo.setSeatsNum(1500);
        vo.setVenueName("LiveHouse");

        venueService.modifyInfo(vo);
    }

    @Test
    public void testApprovedModify(){
        String venueId = "QUjci2U";
        venueService.approvedModify(venueId);
    }

    @Test
    public void testRejectModify(){
        String venueId = "QUjci2U";
        venueService.rejectModify(venueId);
    }
}
