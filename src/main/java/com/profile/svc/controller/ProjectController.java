package com.profile.svc.controller;

import com.profile.svc.model.Project;
import com.profile.svc.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/project")
@AllArgsConstructor
public class ProjectController {

   private final ProjectService service;

    @GetMapping("/list")
    public ResponseEntity<List<Project>> getProjects(){
        return ResponseEntity.ok(service.getAllProjects());
    }
}
