package com.edu.nju.tickets.repository;

import com.edu.nju.tickets.model.VenueRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRecordRepository extends JpaRepository<VenueRecord, Long> {

    List<VenueRecord> findByVenueId(String venueId);

    VenueRecord findByVenueIdAndState(String venueId,String state);

    VenueRecord findByVenueIdAndComment(String venueId,String comment);
}
