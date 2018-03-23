package com.edu.nju.tickets.controller;

import com.edu.nju.tickets.model.Coupon;
import com.edu.nju.tickets.service.CouponService;
import com.edu.nju.tickets.vo.Coupon.CouponVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController {


    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping(value = "/one")
    @ResponseBody
    public ResponseEntity<List<CouponVO>> findAllCoupon(@RequestParam String username){
        List<CouponVO> coupons = couponService.findAllCoupon(username);
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @PostMapping(value = "")
    @ResponseBody
    public ResponseEntity<String> exchangeCoupon(@RequestBody CouponVO vo) {
        couponService.exchangeCoupon(vo.getUsername(),vo.getAmount(),vo.getCouponName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
