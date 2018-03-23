package com.edu.nju.tickets.serviceTest;

import com.edu.nju.tickets.exception.tokenException.IncorrectTokenException;
import com.edu.nju.tickets.exception.tokenException.TokenAlreadyActiveException;
import com.edu.nju.tickets.exception.userException.UserNotFoundException;
import com.edu.nju.tickets.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSend(){
        emailService.sendEmail("loohaze","809739692@qq.com");
    }

    @Test
    public void testResponse() throws TokenAlreadyActiveException, IncorrectTokenException, UserNotFoundException {
        String userName = "loohaze";
        String token = "4397f542-d569-4e1c-9f1d-8ff8bbfad881";

        emailService.responseEmail(userName,token);
    }

}
