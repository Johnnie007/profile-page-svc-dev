package com.profile.svc.repository;

import com.datastax.astra.client.tables.Table;
import com.profile.svc.model.Project;
import com.profile.svc.repository.interfaces.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
@Slf4j
@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private final Table<Project> collection;

    public ProjectRepositoryImpl(@Qualifier("projectsTbc") Table<Project> projectTbc) {
            this.collection = projectTbc;
    }

    @Override
    public List<Project> findAllProjects() {
        try {
            return collection.findAll().toList();
        } catch (RuntimeException e) {
            log.error("Error retrieving projects list : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
