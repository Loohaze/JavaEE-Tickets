package com.edu.nju.tickets.controller;

import com.edu.nju.tickets.model.Seat;
import com.edu.nju.tickets.service.SeatService;
import com.edu.nju.tickets.vo.Order.SeatInfoVO;
import com.edu.nju.tickets.vo.Seat.SeatChartVO;
import com.edu.nju.tickets.vo.Seat.SeatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/seat")
public class SeatController {

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping(value = "/set", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> setProjectSeat(@RequestBody List<SeatInfoVO> list) {
        try {
            seatService.setProjectSeat(list);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "",produces = "application/json;charset=UTF-8" )
    @ResponseBody
    public ResponseEntity<List<SeatVO>> findAllSeatsByProject(@RequestParam String projectToken){
        try{
            List<SeatVO> list = seatService.findAllSeatsByProjectToken(projectToken);
            return new ResponseEntity<>(list,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/one",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<SeatVO> findOneSeatByRowAndNumAndProjectToken(@RequestParam int row,
                                                                        @RequestParam int num,
                                                                        @RequestParam String projectToken) {
        try {
            SeatVO seatVO = seatService.findByRowAndNumAndProjectToken(row, num, projectToken);
            return new ResponseEntity<>(seatVO,HttpStatus.OK);
        }  catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/chart",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<SeatChartVO> findSeatChart(@RequestParam String projectToken){
        try{
            SeatChartVO vo = seatService.findChartByProjectToken(projectToken);
            return new ResponseEntity<>(vo,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
