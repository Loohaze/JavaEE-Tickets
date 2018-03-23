package com.edu.nju.tickets.repositoryTest;

import com.edu.nju.tickets.model.ProjectPrice;
import com.edu.nju.tickets.repository.ProjectPriceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectPriceTest {

    @Autowired
    private ProjectPriceRepository projectPriceRepository;

    @Test
    public void testAdd(){
        ProjectPrice price1 = new ProjectPrice(100,200);
        ProjectPrice price2 = new ProjectPrice(200,200);
        projectPriceRepository.save(price1);
        projectPriceRepository.save(price2);
    }
}
