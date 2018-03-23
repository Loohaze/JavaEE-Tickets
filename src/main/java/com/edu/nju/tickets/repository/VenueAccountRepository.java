package com.edu.nju.tickets.repository;

import com.edu.nju.tickets.model.Venue;
import com.edu.nju.tickets.model.VenueAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueAccountRepository extends JpaRepository<VenueAccount,Long> {

    VenueAccount findByVenue(Venue venue);
}
