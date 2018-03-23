package com.edu.nju.tickets.service;

import com.edu.nju.tickets.vo.Venue.VenueInfoVO;

public interface VenueService {

    String register(VenueInfoVO vo);

    void approvedRegister(String venueId);

    void modifyInfo(VenueInfoVO vo);

    void approvedModify(String venueId);

    void rejectModify(String venueId);

    VenueInfoVO findByVenueId(String venueId);

    String login(String venueId,String password);
}
