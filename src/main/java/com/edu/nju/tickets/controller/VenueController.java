package com.edu.nju.tickets.controller;

import com.edu.nju.tickets.service.VenueService;
import com.edu.nju.tickets.util.Config;
import com.edu.nju.tickets.vo.Venue.VenueInfoVO;
import com.edu.nju.tickets.vo.Venue.VenueVerifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/venue")
public class VenueController {

    private final VenueService venueService;

    @Autowired
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping(value = "/register", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> register(@RequestBody VenueInfoVO vo) {
        try {
            String result = venueService.register(vo);
            return new ResponseEntity<>(result,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/approve/register", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> approvedRegister(@RequestParam String venueId) {
        try {
            venueService.approvedRegister(venueId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/modify", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> modifyInfo(@RequestBody VenueInfoVO vo) {
        try {
            venueService.modifyInfo(vo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/approve/modify", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> approvedModify(@RequestParam String venueId) {
        try {
            venueService.approvedModify(venueId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/reject/modify", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> rejectModify(@RequestParam String venueId) {
        try {
            venueService.rejectModify(venueId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<VenueInfoVO> findByVenueId(@RequestParam String venueId) {
        try {
            VenueInfoVO vo = venueService.findByVenueId(venueId);
            return new ResponseEntity<>(vo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> verify(@RequestBody VenueVerifyVO vo) {
        try {
            String id = vo.getVenueId();
            String password = vo.getPassword();
            String result = venueService.login(id, password);
            if (result.equals(Config.VENUE_VERIFY.PASSED)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
