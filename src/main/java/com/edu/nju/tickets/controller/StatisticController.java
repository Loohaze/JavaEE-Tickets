package com.edu.nju.tickets.controller;

import com.edu.nju.tickets.service.StatisticService;
import com.edu.nju.tickets.vo.Statistic.ManagerStatisticVO;
import com.edu.nju.tickets.vo.Statistic.UserStatisticVO;
import com.edu.nju.tickets.vo.Statistic.VenueStatisticVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistic")
public class StatisticController {


    private final StatisticService statisticService;


    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping(value = "/user")
    @ResponseBody
    public ResponseEntity<UserStatisticVO> userStatisticInfo(@RequestParam String username){
        UserStatisticVO vo = statisticService.userStatistic(username);
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }

    @GetMapping(value = "/venue")
    @ResponseBody
    public ResponseEntity<VenueStatisticVO> venueStatisticInfo(@RequestParam String venueId){
        VenueStatisticVO vo = statisticService.venueStatistic(venueId);
        return new ResponseEntity<>(vo,HttpStatus.OK);
    }

    @GetMapping(value = "/manager")
    @ResponseBody
    public ResponseEntity<ManagerStatisticVO> managerStatisticInfo(){
        ManagerStatisticVO vo = statisticService.managerStatistic();
        return new ResponseEntity<>(vo,HttpStatus.OK);
    }
}
