package com.edu.nju.tickets.service.impl;

import com.edu.nju.tickets.exception.tokenException.IncorrectTokenException;
import com.edu.nju.tickets.exception.tokenException.TokenAlreadyActiveException;
import com.edu.nju.tickets.exception.userException.UserNotFoundException;
import com.edu.nju.tickets.model.User;
import com.edu.nju.tickets.model.UserToken;
import com.edu.nju.tickets.repository.UserRepository;
import com.edu.nju.tickets.repository.UserTokenRepository;
import com.edu.nju.tickets.service.EmailService;
import com.edu.nju.tickets.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.IDN;
import java.util.UUID;

@Service
public class EmailImpl implements EmailService {

    private final UserTokenRepository userTokenRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String serverMail;

    @Autowired
    public EmailImpl(UserTokenRepository userTokenRepository, UserRepository userRepository, JavaMailSender javaMailSender) {
        this.userTokenRepository = userTokenRepository;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String userName, String targetEmail) {
        System.out.println(userName);
        String token = UUID.randomUUID().toString();

        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUserName(userName);
        userToken.setActiveState(Config.ACTIVE_STATE.INACTIVE);

        userTokenRepository.save(userToken);

        SimpleMailMessage message = new SimpleMailMessage();
//        System.out.println(serverMail);
        message.setFrom(IDN.toASCII(serverMail));
        message.setTo(IDN.toASCII(targetEmail));
        message.setSubject("欢迎注册Tickets");
        String text = "点击此链接: http://localhost:8080/api/user/verifyEmail/?username=" + userName + "&" + "token=" + token + " 激活账号";
        System.out.println(text);
        message.setText(text);
        javaMailSender.send(message);
    }


    @Override
    public void responseEmail(String userName, String token) throws IncorrectTokenException, TokenAlreadyActiveException, UserNotFoundException {
        UserToken userToken = userTokenRepository.findByUserName(userName);
        User user = userRepository.findByName(userName);

        if (user != null) {
            if (userToken.getActiveState().equals(Config.ACTIVE_STATE.INACTIVE)) {
                if (userToken.getToken().equals(token)) {
                    userToken.setActiveState(Config.ACTIVE_STATE.ACTIVE);
                    userTokenRepository.saveAndFlush(userToken);
                } else {
                    throw new IncorrectTokenException();
                }
            } else {
                throw new TokenAlreadyActiveException();
            }
        } else {
            throw new UserNotFoundException(userName);
        }
    }
}
