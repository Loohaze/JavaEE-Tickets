package com.edu.nju.tickets.service;

import com.edu.nju.tickets.exception.userException.UserExistedException;
import com.edu.nju.tickets.model.Coupon;
import com.edu.nju.tickets.model.User;
import com.edu.nju.tickets.vo.User.UserAccountVO;
import com.edu.nju.tickets.vo.User.UserInfoVO;
import com.edu.nju.tickets.vo.User.UserLevelVO;
import com.edu.nju.tickets.vo.User.UserVerifyVO;

import java.util.List;

public interface UserService {

    String verifyUser(UserVerifyVO vo);

    void register(String userName, String email, String password) throws UserExistedException;

    void cancel(String userName);

    void modifyInfo(UserInfoVO vo);

    void modifyPwd(String userName, String oldPassword, String newPassword);

    UserInfoVO findInfoByUserName(String userName);

    UserAccountVO findAccountByUserName(String userName);

    UserLevelVO findLevelByUserName(String userName);

    void recharge(String userName, double amount);

    String verifyAccountPassword(String userName, String payPassword);


}
