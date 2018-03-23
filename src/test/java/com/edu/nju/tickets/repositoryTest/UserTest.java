package com.edu.nju.tickets.repositoryTest;

import com.edu.nju.tickets.model.User;
import com.edu.nju.tickets.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByName(){
        User user = userRepository.findByName("loohaze");
        System.out.println(user.toString());
    }
}
