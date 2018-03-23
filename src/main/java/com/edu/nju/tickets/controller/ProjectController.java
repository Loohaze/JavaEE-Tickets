package com.edu.nju.tickets.controller;

import com.edu.nju.tickets.service.ProjectService;
import com.edu.nju.tickets.vo.Project.ProjectInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping(value = "/publish", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> publishProject (@RequestBody ProjectInfoVO vo) {
        try {
            projectService.publishProject(vo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<List<ProjectInfoVO>> findProjectByVenue(@RequestParam String venueId) {
        try {
            List<ProjectInfoVO> list = projectService.findProjectsByVenue(venueId);
            return new ResponseEntity<>(list,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/all", produces = "application/json;charset=UTF-8" )
    @ResponseBody
    public ResponseEntity<List<ProjectInfoVO>> findAllProjects() {
        try{
            List<ProjectInfoVO> list = projectService.findAllProjects();
            return new ResponseEntity<>(list,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
