package com.edu.nju.tickets.repository;

import com.edu.nju.tickets.model.Coupon;
import com.edu.nju.tickets.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon,Long> {

    Coupon findByCouponToken(String couponToken);

    List<Coupon> findByUser(User user);
}
