package com.edu.nju.tickets.service;

import com.edu.nju.tickets.vo.Statistic.ManagerStatisticVO;
import com.edu.nju.tickets.vo.Statistic.UserStatisticVO;
import com.edu.nju.tickets.vo.Statistic.VenueStatisticVO;
import org.springframework.stereotype.Service;

@Service
public interface StatisticService {

    VenueStatisticVO venueStatistic(String venueId);

    UserStatisticVO userStatistic(String username);

    ManagerStatisticVO managerStatistic();
}
