package com.profile.service.controller;

import com.profile.service.model.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/project")
public class ProjectController {

    @GetMapping("/list")
    public ResponseEntity<Project> getProjects(){
        return null;
    }
}
