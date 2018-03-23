package com.edu.nju.tickets.serviceTest;

import com.edu.nju.tickets.service.ProjectService;
import com.edu.nju.tickets.util.Config;
import com.edu.nju.tickets.vo.Project.ProjectInfoVO;
import com.edu.nju.tickets.vo.Project.ProjectPriceInfoVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectTest {

    @Autowired
    private ProjectService projectService;

    @Test
    public void testPublishProject() {
        ProjectInfoVO projectInfoVO = new ProjectInfoVO();
        projectInfoVO.setStartDate(Date.valueOf("2018-01-02"));
        projectInfoVO.setEndDate(Date.valueOf("2018-04-02"));
        projectInfoVO.setDescription("Test Project");
        projectInfoVO.setType(Config.PROJECT_TYPE.CONCERT);
        projectInfoVO.setVenueName("");

        Set<ProjectPriceInfoVO> set = new HashSet<>();
        ProjectPriceInfoVO info1 = new ProjectPriceInfoVO();
        info1.setNumber(500);
        info1.setPrice(200);

        ProjectPriceInfoVO info2 = new ProjectPriceInfoVO();
        info2.setNumber(500);
        info2.setPrice(400);

        ProjectPriceInfoVO info3 = new ProjectPriceInfoVO();
        info3.setNumber(200);
        info3.setPrice(600);

        set.add(info1);
        set.add(info2);
        set.add(info3);

        projectInfoVO.setProjectPrices(set);

        projectService.publishProject(projectInfoVO);
    }

    @Test
    public void testFindByVenue(){
        String venueId = "QUjci2U";
        List<ProjectInfoVO> list = projectService.findProjectsByVenue(venueId);
        for (ProjectInfoVO vo : list) {
            System.out.println(vo.toString());
        }
    }
}
