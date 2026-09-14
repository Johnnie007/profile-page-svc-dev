package com.profile.svc.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.profile.svc.model.Project;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ServiceRepositoryImpl implements ProjectRepository{

    private final List<Project> mockProjectList = new ArrayList<>();
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public ServiceRepositoryImpl(ResourceLoader resourceLoader1, ObjectMapper objectMapper){
        this.resourceLoader = resourceLoader1;
        this.objectMapper = objectMapper;
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @PostConstruct
    public void initMockData(){
        try{
            Resource resource = resourceLoader.getResource("classpath:Data.json");

            JsonNode rootNode = objectMapper.readTree(resource.getInputStream());
            JsonNode projectsNode = rootNode.get("projects");

            List<Project> loadedProjects = objectMapper.readValue(
                    projectsNode.traverse(objectMapper),
                    new TypeReference<List<Project>>() {}
            );
            this.mockProjectList.addAll(loadedProjects);
        }
        catch (Exception ex){
            throw new RuntimeException("Failed to load: "+ ex.getMessage());
        }
    }
    @Override
    public List<Project> findAllProjects() {
        return new ArrayList<>(mockProjectList);
    }
}
