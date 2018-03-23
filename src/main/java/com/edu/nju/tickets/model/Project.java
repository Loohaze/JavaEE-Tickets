package com.edu.nju.tickets.model;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

/**
 * 计划
 * projectId
 * startDate          开始时间
 * endDate            结束时间
 * type               类型
 * description        简单描述
 * projectPrices      票价
 * venue              场馆
 */
@Entity
@Table(name = "project")
public class Project implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "project_token")
    private String projectToken;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_start_date")
    private Date startDate;

    @Column(name = "project_end_date")
    private Date endDate;

    @Column(name = "project_type")
    private String type;

    @Column(name = "project_description")
    private String description;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("price asc")
    private Set<ProjectPrice> projectPrices;


    @ManyToOne
    @JoinColumn(name = "venue_id", referencedColumnName = "venue_id")
    private Venue venue;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectToken() {
        return projectToken;
    }

    public void setProjectToken(String projectToken) {
        this.projectToken = projectToken;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long id) {
        this.projectId = id;
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

    public Set getProjectPrices() {
        return projectPrices;
    }

    public void setProjectPrices(Set projectPrices) {
        this.projectPrices = projectPrices;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + projectId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", projectPrice=" + projectPrices +
                '}';
    }
}
