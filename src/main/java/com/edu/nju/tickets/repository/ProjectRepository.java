package com.edu.nju.tickets.repository;

import com.edu.nju.tickets.model.Project;
import com.edu.nju.tickets.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    Project findByProjectId(Long projectId);

    Project findByProjectToken(String projectToken);

    List<Project> findByVenue(Venue venue);

    Project findByProjectName(String projectName);
}
