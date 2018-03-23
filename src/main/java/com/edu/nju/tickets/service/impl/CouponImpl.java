package com.edu.nju.tickets.service.impl;

import com.edu.nju.tickets.model.Coupon;
import com.edu.nju.tickets.model.User;
import com.edu.nju.tickets.repository.CouponRepository;
import com.edu.nju.tickets.repository.UserRepository;
import com.edu.nju.tickets.service.CouponService;
import com.edu.nju.tickets.vo.Coupon.CouponVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    public CouponImpl(CouponRepository couponRepository, UserRepository userRepository) {
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void exchangeCoupon(String userName, int amount,String couponName) {
        Coupon coupon = new Coupon();
        String couponToken = RandomStringUtils.randomAlphanumeric(10);
        while (couponRepository.findByCouponToken(couponToken) != null) {
            couponToken = RandomStringUtils.randomAlphanumeric(10);
        }

        coupon.setCouponToken(couponToken);
        coupon.setUser(userRepository.findByName(userName));
        coupon.setAmount(amount);
        coupon.setCouponName(couponName);

        couponRepository.saveAndFlush(coupon);

        User user = userRepository.findByName(userName);
        int points = user.getPoints();
        points -= amount * 100;
        user.setPoints(points);
        userRepository.saveAndFlush(user);
    }

    @Override
    public List<CouponVO> findAllCoupon(String userName) {
        List<Coupon> coupons = couponRepository.findByUser(userRepository.findByName(userName));
        List<CouponVO> result = new ArrayList<>();
        for (Coupon coupon : coupons) {
            result.add(couponModel2VO(coupon));
        }

        return result;
    }

    private CouponVO couponModel2VO(Coupon coupon) {
        CouponVO vo = new CouponVO();
        vo.setAmount(coupon.getAmount());
        vo.setCouponName(coupon.getCouponName());
        vo.setCouponToken(coupon.getCouponToken());
        vo.setUsername(coupon.getUser().getName());
        return vo;
    }

}
