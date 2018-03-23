package com.edu.nju.tickets.vo.Project;

import java.sql.Date;
import java.util.Set;

public class ProjectInfoVO {

    private Long projectId;

    private String venueName;

    private String projectName;

    private String projectToken;

    private String location;

    private Date startDate;

    private Date endDate;

    private String type;

    private String description;

    private Set<ProjectPriceInfoVO> projectPrices;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProjectPriceInfoVO> getProjectPrices() {
        return projectPrices;
    }

    public void setProjectPrices(Set<ProjectPriceInfoVO> projectPrices) {
        this.projectPrices = projectPrices;
    }

    public String getProjectToken() {
        return projectToken;
    }

    public void setProjectToken(String projectToken) {
        this.projectToken = projectToken;
    }

    @Override
    public String toString() {
        return "ProjectInfoVO{" +
                "projectId=" + projectId +
                ", venueName='" + venueName + '\'' +
                ", projectToken='" + projectToken + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", projectPrices=" + projectPrices +
                '}';
    }
}
