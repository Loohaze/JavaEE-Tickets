package com.edu.nju.tickets.repositoryTest;

import com.edu.nju.tickets.model.UserAccount;
import com.edu.nju.tickets.repository.UserAccountRepository;
import com.edu.nju.tickets.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAccountTest {


    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUser() {
        UserAccount userAccount = userAccountRepository.findByUser(userRepository.findByName("loohaze"));
        System.out.println(userAccount.toString());
    }
}
