package com.edu.nju.tickets.controller;

import com.edu.nju.tickets.exception.tokenException.IncorrectTokenException;
import com.edu.nju.tickets.exception.tokenException.TokenAlreadyActiveException;
import com.edu.nju.tickets.exception.userException.UserExistedException;
import com.edu.nju.tickets.exception.userException.UserNotFoundException;
import com.edu.nju.tickets.service.EmailService;
import com.edu.nju.tickets.service.UserService;
import com.edu.nju.tickets.util.Config;
import com.edu.nju.tickets.vo.User.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }


    @GetMapping(value = "/info", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<UserInfoVO> findInfoByUseName(@RequestParam String username) {
        try {
            UserInfoVO vo = userService.findInfoByUserName(username);
            return new ResponseEntity<>(vo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> verifyUser(@RequestBody UserVerifyVO vo) {
        String result = userService.verifyUser(vo);
        if (!result.equals(Config.USER_VERIFY.PASSED)) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/register", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> register(@RequestBody UserInfoVO vo) {
        String username = vo.getUserName();
        String email = vo.getUserEmail();
        String password = vo.getPassword();

        try {
            userService.register(username, email, password);
        } catch (UserExistedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/verifyEmail", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> responseEmail(@RequestParam String username,
                                                @RequestParam String token) {
        try {
            emailService.responseEmail(username, token);
        } catch (IncorrectTokenException | TokenAlreadyActiveException | UserNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().cacheControl(CacheControl.noCache()).body("fail");
        }
        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).body("success");
    }

    @GetMapping(value = "/cancel", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> cancelUser(@RequestParam String username) {
        try {
            userService.cancel(username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/modify", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> modifyUserInfo(@RequestBody UserInfoVO vo) {
        try {
            userService.modifyInfo(vo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/modifyPwd", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> modifyPwd(@RequestParam String username,
                                            @RequestParam String oldPwd,
                                            @RequestParam String newPwd) {
        try {
            userService.modifyPwd(username, oldPwd, newPwd);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/account")
    @ResponseBody
    public ResponseEntity<UserAccountVO> findAccountByUsername(@RequestParam String username) {
        try {
            UserAccountVO vo = userService.findAccountByUserName(username);
            return new ResponseEntity<>(vo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/level")
    @ResponseBody
    public ResponseEntity<UserLevelVO> findLevelByUsername(@RequestParam String username) {
        try {
            UserLevelVO vo = userService.findLevelByUserName(username);
            return new ResponseEntity<>(vo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/recharge", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> recharge(@RequestParam String username,
                                           @RequestParam double amount) {
        try {
            userService.recharge(username, amount);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/paypwd", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> verifyPayPwd(@RequestBody UserPayVO vo) {
        try {
            String result = userService.verifyAccountPassword(vo.getUserName(), vo.getPayPwd());
            if (result.equals(Config.USER_VERIFY.PASSED)) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
