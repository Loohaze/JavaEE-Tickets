package com.edu.nju.tickets.service;

import com.edu.nju.tickets.model.Coupon;
import com.edu.nju.tickets.vo.Coupon.CouponVO;

import java.util.List;

public interface CouponService {

    void exchangeCoupon(String userName, int amount,String couponName);

    List<CouponVO> findAllCoupon(String userName);
}
