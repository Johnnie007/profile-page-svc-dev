package com.profile.svc.service;

import com.profile.svc.mapper.ProjectDetailsMapper;
import com.profile.svc.model.Project;
import com.profile.svc.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects(){
        List<Project> test = projectRepository.findAllProjects();
        System.out.println("This is the list: "+ test);
        return projectRepository.findAllProjects();
    }
}
