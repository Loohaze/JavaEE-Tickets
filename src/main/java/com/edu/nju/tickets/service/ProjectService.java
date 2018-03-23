package com.edu.nju.tickets.service;

import com.edu.nju.tickets.model.Project;
import com.edu.nju.tickets.vo.Project.ProjectInfoVO;

import java.util.List;

public interface ProjectService {

    void publishProject(ProjectInfoVO projectInfoVO);

    List<ProjectInfoVO> findProjectsByVenue(String venueId);

    List<ProjectInfoVO> findAllProjects();
}
