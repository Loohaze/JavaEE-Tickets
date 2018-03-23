package com.edu.nju.tickets.controller;

import com.edu.nju.tickets.service.ManagerService;
import com.edu.nju.tickets.util.Config;
import com.edu.nju.tickets.vo.Manager.ManagerVerifyVO;
import com.edu.nju.tickets.vo.Order.OrderInfoVO;
import com.edu.nju.tickets.vo.Venue.VenueInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }


    @PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> managerLogin(@RequestBody ManagerVerifyVO vo) {
        String managerId = vo.getManagerId();
        String password = vo.getPassword();
        String result = managerService.login(managerId, password);
        if (result.equals(Config.MANAGER_VERIFY.PASSED)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/num", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<Map<String, Integer>> getNum() {
        Map<String, Integer> result = managerService.findApprovalNum();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/info", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<Map<String, List<VenueInfoVO>>> findAllPendingInfo() {
        Map<String, List<VenueInfoVO>> result = managerService.findAllPendingInfo();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/settle", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> settleOrder(@RequestParam String venueName) {
        String result = managerService.settle(venueName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/unsettled", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<Map<String,List<OrderInfoVO>>> findAllUnsettledOrders() {
        Map<String,List<OrderInfoVO>> result = managerService.findAllUnsettledOrders();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}

