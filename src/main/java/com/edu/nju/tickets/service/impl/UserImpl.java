package com.edu.nju.tickets.service.impl;

import com.edu.nju.tickets.exception.tokenException.TokenNotActiveException;
import com.edu.nju.tickets.exception.userException.UserExistedException;
import com.edu.nju.tickets.exception.userException.UserNotFoundException;
import com.edu.nju.tickets.exception.userException.UserWrittenOffException;
import com.edu.nju.tickets.exception.userException.UserWrongPwdException;
import com.edu.nju.tickets.model.Coupon;
import com.edu.nju.tickets.model.User;
import com.edu.nju.tickets.model.UserAccount;
import com.edu.nju.tickets.model.UserToken;
import com.edu.nju.tickets.repository.CouponRepository;
import com.edu.nju.tickets.repository.UserAccountRepository;
import com.edu.nju.tickets.repository.UserRepository;
import com.edu.nju.tickets.repository.UserTokenRepository;
import com.edu.nju.tickets.service.EmailService;
import com.edu.nju.tickets.service.UserService;
import com.edu.nju.tickets.util.Config;
import com.edu.nju.tickets.vo.User.UserAccountVO;
import com.edu.nju.tickets.vo.User.UserInfoVO;
import com.edu.nju.tickets.vo.User.UserLevelVO;
import com.edu.nju.tickets.vo.User.UserVerifyVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserTokenRepository userTokenRepository;
    private final UserAccountRepository userAccountRepository;



    public UserImpl(UserRepository userRepository, EmailService emailService, UserTokenRepository userTokenRepository, UserAccountRepository userAccountRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.userTokenRepository = userTokenRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public String verifyUser(UserVerifyVO vo) {
        UserToken token = userTokenRepository.findByUserName(vo.getUserName());
        if (token.getActiveState().equals(Config.ACTIVE_STATE.CANCELLED)) {
            try {
                throw new UserWrittenOffException(vo.getUserName());
            } catch (UserWrittenOffException e) {
                e.printStackTrace();
            }
            return Config.USER_VERIFY.CANCELLED;
        }

        if (token.getActiveState().equals(Config.ACTIVE_STATE.INACTIVE)) {
            try {
                throw new TokenNotActiveException(vo.getUserName());
            } catch (TokenNotActiveException e) {
                e.printStackTrace();
            }

            return Config.USER_VERIFY.NOT_ACTIVE;
        }

        User user0 = userRepository.findByName(vo.getUserName());
        if (user0 == null) {
            try {
                throw new UserNotFoundException(vo.getUserName());
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
            return Config.USER_VERIFY.ID_ERROR;
        } else {
            User user = userRepository.findByNameAndPassword(vo.getUserName(), vo.getPassword());
            if (user == null) {
                return Config.USER_VERIFY.PWD_ERROR;
            } else {
                return Config.USER_VERIFY.PASSED;
            }
        }

    }

    @Override
    public void register(String userName, String email, String password) throws UserExistedException {
        User user0 = userRepository.findByName(userName);
        if (user0 != null) {
            throw new UserExistedException(userName);
        } else {
            User nUser = new User();
            nUser.setName(userName);
            nUser.setMail(email);
            nUser.setPassword(password);
            nUser.setLevel(0);
            nUser.setPoints(0);
            userRepository.saveAndFlush(nUser);

            UserAccount userAccount = new UserAccount();
            userAccount.setUser(nUser);
            userAccount.setBalance(0);
            userAccountRepository.saveAndFlush(userAccount);
            emailService.sendEmail(userName,email);
        }

    }

    @Override
    public void cancel(String userName) {
        UserToken token = userTokenRepository.findByUserName(userName);
        token.setActiveState(Config.ACTIVE_STATE.CANCELLED);
        userTokenRepository.saveAndFlush(token);
    }

    @Override
    public void modifyInfo(UserInfoVO vo) {
        User user = userRepository.findByName(vo.getUserName());
        user.setBirthday(vo.getBirthday());
        user.setGender(vo.getGender());
        user.setPhone(vo.getPhoneNumber());
        userRepository.saveAndFlush(user);
    }

    @Override
    public void modifyPwd(String userName, String oldPassword, String newPassword) {
        User user = userRepository.findByName(userName);
        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userRepository.saveAndFlush(user);
        } else {
            try {
                throw new UserWrongPwdException();
            } catch (UserWrongPwdException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public UserInfoVO findInfoByUserName(String userName) {
        User user = userRepository.findByName(userName);
        UserInfoVO infoVO = new UserInfoVO();

        infoVO.setUserId(user.getUserId());
        infoVO.setUserName(user.getName());
        infoVO.setPhoneNumber(user.getPhone());
        infoVO.setGender(user.getGender());
        infoVO.setBirthday(user.getBirthday());
        infoVO.setUserEmail(user.getMail());
        infoVO.setPassword(user.getPassword());
        return infoVO;
    }

    @Override
    public UserAccountVO findAccountByUserName(String userName) {
        User user = userRepository.findByName(userName);
        UserAccount userAccount = userAccountRepository.findByUser(user);
        UserAccountVO accountVO = new UserAccountVO();

        accountVO.setAccountId(userAccount.getAccountId());
        accountVO.setUserName(userAccount.getUser().getName());
        accountVO.setBalance(userAccount.getBalance());
        return accountVO;
    }

    @Override
    public UserLevelVO findLevelByUserName(String userName) {
        User user = userRepository.findByName(userName);
        UserLevelVO levelVO = new UserLevelVO();

        levelVO.setUserId(user.getUserId());
        levelVO.setLevel(user.getLevel());
        levelVO.setPoints(user.getPoints());
        return levelVO;
    }

    @Override
    public void recharge(String userName, double amount) {
        UserAccount account = userAccountRepository.findByUser(userRepository.findByName(userName));
        double balance = account.getBalance();
        balance += amount;
        account.setBalance(balance);

        userAccountRepository.saveAndFlush(account);
    }

    @Override
    public String verifyAccountPassword(String userName, String payPassword) {
        UserAccount account = userAccountRepository.findByUser(userRepository.findByName(userName));
        if (payPassword.equals(account.getPayPassword())) {
            return Config.USER_VERIFY.PASSED;
        }else {
            return Config.USER_VERIFY.PWD_ERROR;
        }
    }


}
