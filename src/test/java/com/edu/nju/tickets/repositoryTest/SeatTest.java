package com.edu.nju.tickets.repositoryTest;

import com.edu.nju.tickets.model.Seat;
import com.edu.nju.tickets.repository.SeatRepository;
import com.edu.nju.tickets.repository.VenueRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeatTest {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Test
    public void testAdd(){
        Seat seat = new Seat();
        seat.setVenue(venueRepository.findByVenueId("A91FSQ1"));
        seat.setRow(1);
        seat.setNumber(1);
        seatRepository.save(seat);
    }
}
