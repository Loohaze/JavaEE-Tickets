package com.edu.nju.tickets.repository;

import com.edu.nju.tickets.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue,String> {

    Venue findByVenueId(String id);

    Venue findByName(String name);
}
