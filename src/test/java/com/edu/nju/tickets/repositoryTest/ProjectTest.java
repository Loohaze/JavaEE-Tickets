package com.edu.nju.tickets.repositoryTest;


import com.edu.nju.tickets.model.Project;
import com.edu.nju.tickets.model.ProjectPrice;
import com.edu.nju.tickets.repository.ProjectRepository;
import com.edu.nju.tickets.repository.VenueRepository;
import com.edu.nju.tickets.util.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectTest {


    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private VenueRepository venueRepository;

    @Test
    public void testAdd() {
        Project project = new Project();
        project.setDescription("test");
        project.setType(Config.PROJECT_TYPE.CONCERT);
        project.setStartDate(Date.valueOf("2017-10-10"));
        project.setEndDate(Date.valueOf("2018-01-01"));
        project.setVenue(venueRepository.findByVenueId("A91FSQ1"));

        Set<ProjectPrice> set = new HashSet<>();
        ProjectPrice price1 = new ProjectPrice(100, 200);
        price1.setProject(project);
        ProjectPrice price2 = new ProjectPrice(200, 200);
        price2.setProject(project);
        set.add(price1);
        set.add(price2);

        project.setProjectPrices(set);

        projectRepository.save(project);
    }
}
