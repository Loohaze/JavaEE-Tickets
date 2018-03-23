package com.edu.nju.tickets.repositoryTest;


import com.edu.nju.tickets.model.Venue;
import com.edu.nju.tickets.repository.VenueRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VenueTest {

    @Autowired
    private VenueRepository venueRepository;

    @Test
    public void testAdd(){
        Venue venue = new Venue();
        venue.setVenueId("A91FSQ1");
        venue.setLocation("Shanghai");
        venue.setName("Livehouse");
        venue.setSeats(1000);
        venueRepository.save(venue);
    }

    @Test
    public void testFind(){
        Venue venue = venueRepository.findByVenueId("A91FSQ1");
        System.out.println(venue.toString());
    }


}
