package com.edu.nju.tickets.service.impl;

import com.edu.nju.tickets.model.Project;
import com.edu.nju.tickets.model.ProjectPrice;
import com.edu.nju.tickets.repository.ProjectPriceRepository;
import com.edu.nju.tickets.repository.ProjectRepository;
import com.edu.nju.tickets.repository.VenueRepository;
import com.edu.nju.tickets.service.ProjectService;
import com.edu.nju.tickets.vo.Project.ProjectInfoVO;
import com.edu.nju.tickets.vo.Project.ProjectPriceInfoVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjectImpl implements ProjectService {


    private final ProjectRepository projectRepository;
    private final ProjectPriceRepository projectPriceRepository;
    private final VenueRepository venueRepository;

    public ProjectImpl(ProjectRepository projectRepository, ProjectPriceRepository projectPriceRepository, VenueRepository venueRepository) {
        this.projectRepository = projectRepository;
        this.projectPriceRepository = projectPriceRepository;
        this.venueRepository = venueRepository;
    }

    @Override
    public void publishProject(ProjectInfoVO projectInfoVO) {
        Project project = new Project();

        String projectToken = RandomStringUtils.randomAlphanumeric(11);
        while (projectRepository.findByProjectToken(projectToken) != null) {
            projectToken = RandomStringUtils.randomAlphanumeric(11);
        }

        project.setProjectName(projectInfoVO.getProjectName());
        project.setProjectToken(projectToken);
        project.setStartDate(projectInfoVO.getStartDate());
        project.setEndDate(projectInfoVO.getEndDate());
        project.setDescription(projectInfoVO.getDescription());
        project.setType(projectInfoVO.getType());
        project.setVenue(venueRepository.findByName(projectInfoVO.getVenueName()));

        projectRepository.saveAndFlush(project);

        Set<ProjectPrice> projectPriceSet = new HashSet<>();
        Set<ProjectPriceInfoVO> priceInfoSet = projectInfoVO.getProjectPrices();

        for (ProjectPriceInfoVO priceInfoVO : priceInfoSet) {
            ProjectPrice projectPrice = new ProjectPrice();
            projectPrice.setProject(project);
            projectPrice.setNumber(priceInfoVO.getNumber());
            projectPrice.setPrice(priceInfoVO.getPrice());

            projectPriceRepository.saveAndFlush(projectPrice);
            projectPriceSet.add(projectPrice);
        }

        project.setProjectPrices(projectPriceSet);
        projectRepository.saveAndFlush(project);
    }

    @Override
    public List<ProjectInfoVO> findProjectsByVenue(String venueId) {
        List<ProjectInfoVO> list = new ArrayList<>();
        List<Project> projects = projectRepository.findByVenue(venueRepository.findByVenueId(venueId));

        for (Project project : projects) {
            list.add(project2vo(project));
        }

        return list;
    }

    @Override
    public List<ProjectInfoVO> findAllProjects() {
        List<ProjectInfoVO> list = new ArrayList<>();
        List<Project> projects = projectRepository.findAll();

        for (Project project : projects){
            list.add(project2vo(project));
        }
        return list;
    }

    private ProjectInfoVO project2vo(Project project) {
        ProjectInfoVO vo = new ProjectInfoVO();
        vo.setProjectId(project.getProjectId());
        vo.setProjectName(project.getProjectName());
        vo.setVenueName(project.getVenue().getName());
        vo.setType(project.getType());
        vo.setDescription(project.getDescription());
        vo.setStartDate(project.getStartDate());
        vo.setEndDate(project.getEndDate());
        vo.setProjectToken(project.getProjectToken());
        vo.setLocation(project.getVenue().getLocation());
        vo.setProjectToken(project.getProjectToken());
        vo.setProjectPrices(projectPrices2vo(project.getProjectPrices()));
        return vo;
    }

    private Set<ProjectPriceInfoVO> projectPrices2vo(Set<ProjectPrice> projectPrices) {
        Set<ProjectPriceInfoVO> voList = new HashSet<>();
        for (ProjectPrice price : projectPrices) {
            ProjectPriceInfoVO vo = new ProjectPriceInfoVO();
            vo.setPrice(price.getPrice());
            vo.setNumber(price.getNumber());
            vo.setPriceId(price.getPriceId());
            vo.setProjectId(price.getProject().getProjectId());
            voList.add(vo);
        }
        return voList;
    }
}
