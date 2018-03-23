package com.edu.nju.tickets.service.impl;

import com.edu.nju.tickets.exception.venueException.VenueApprovedException;
import com.edu.nju.tickets.exception.venueException.VenueIdExistedException;
import com.edu.nju.tickets.model.Venue;
import com.edu.nju.tickets.model.VenueAccount;
import com.edu.nju.tickets.model.VenueRecord;
import com.edu.nju.tickets.repository.VenueAccountRepository;
import com.edu.nju.tickets.repository.VenueRecordRepository;
import com.edu.nju.tickets.repository.VenueRepository;
import com.edu.nju.tickets.service.VenueService;
import com.edu.nju.tickets.util.Config;
import com.edu.nju.tickets.vo.Venue.VenueInfoVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VenueImpl implements VenueService{

    private final VenueRepository venueRepository;
    private final VenueRecordRepository venueRecordRepository;
    private final VenueAccountRepository venueAccountRepository;

    @Autowired
    public VenueImpl(VenueRepository venueRepository, VenueRecordRepository venueRecordRepository, VenueAccountRepository venueAccountRepository) {
        this.venueRepository = venueRepository;
        this.venueRecordRepository = venueRecordRepository;
        this.venueAccountRepository = venueAccountRepository;
    }




    @Override
    public String register(VenueInfoVO vo) {
        String venueId = RandomStringUtils.randomAlphanumeric(7);
        while (venueRepository.findByVenueId(venueId) != null) {
            venueId = RandomStringUtils.randomAlphanumeric(7);
        }

        VenueRecord record = new VenueRecord();
        record.setVenueId(venueId);
        record.setLocation(vo.getLocation());
        record.setName(vo.getVenueName());
        record.setSeats(vo.getSeatsNum());
        record.setTime(vo.getTime());
        record.setState(Config.VENUE_STATE.PENDING);
        record.setComment(Config.VENUE_COMMENT.REGISTER);
        venueRecordRepository.saveAndFlush(record);


        Venue venue = new Venue();
        venue.setVenueId(venueId);
        venue.setPassword(vo.getPassword());
        venueRepository.saveAndFlush(venue);
        return venueId;
    }

    @Override
    public void approvedRegister(String venueId) {
        VenueRecord record = venueRecordRepository.findByVenueIdAndComment(venueId,Config.VENUE_COMMENT.REGISTER);
        if (record.getState().equals(Config.VENUE_STATE.APPROVED)) {
            try {
                throw new VenueApprovedException();
            } catch (VenueApprovedException e) {
                e.printStackTrace();
            }
        } else {
            record.setState(Config.VENUE_STATE.APPROVED);
            venueRecordRepository.saveAndFlush(record);

            Venue venue = venueRepository.findByVenueId(venueId);
            venue.setSeats(record.getSeats());
            venue.setName(record.getName());
            venue.setLocation(record.getLocation());
            venueRepository.saveAndFlush(venue);

            VenueAccount venueAccount = new VenueAccount();
            venueAccount.setVenue(venue);
            venueAccount.setBalance(0);
            venueAccountRepository.saveAndFlush(venueAccount);
        }

    }

    @Override
    public void modifyInfo(VenueInfoVO vo) {
        VenueRecord record = new VenueRecord();
        record.setVenueId(vo.getVenueId());
        record.setTime(vo.getTime());
        record.setComment(Config.VENUE_COMMENT.MODIFY);
        record.setState(Config.VENUE_STATE.PENDING);
        record.setSeats(vo.getSeatsNum());
        record.setLocation(vo.getLocation());
        record.setName(vo.getVenueName());
        venueRecordRepository.saveAndFlush(record);
    }

    @Override
    public void approvedModify(String venueId) {
        VenueRecord record = venueRecordRepository.findByVenueIdAndState(venueId,Config.VENUE_STATE.PENDING);

        record.setState(Config.VENUE_STATE.APPROVED);
        venueRecordRepository.saveAndFlush(record);

        Venue venue = venueRepository.findByVenueId(venueId);
        venue.setName(record.getName());
        venue.setLocation(record.getLocation());
        venue.setSeats(record.getSeats());

        venueRepository.saveAndFlush(venue);
    }

    @Override
    public void rejectModify(String venueId) {
        VenueRecord record = venueRecordRepository.findByVenueIdAndState(venueId,Config.VENUE_STATE.PENDING);

        record.setState(Config.VENUE_STATE.REJECTED);
        venueRecordRepository.saveAndFlush(record);
    }

    @Override
    public VenueInfoVO findByVenueId(String venueId) {
        VenueInfoVO vo = new VenueInfoVO();
        Venue venue = venueRepository.findByVenueId(venueId);
        vo.setVenueId(venue.getVenueId());
        vo.setLocation(venue.getLocation());
        vo.setSeatsNum(venue.getSeats());
        vo.setVenueName(venue.getName());
        return vo;
    }

    @Override
    public String login(String venueId, String password) {
        Venue venue = venueRepository.findByVenueId(venueId);
        if (venue.getPassword().equals(password)) {
            return Config.VENUE_VERIFY.PASSED;
        } else {
            return Config.VENUE_VERIFY.PWD_ERROR;
        }
    }
}
