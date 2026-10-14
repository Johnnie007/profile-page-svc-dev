package com.profile.svc.repository.interfaces;

import com.profile.svc.model.Project;
import java.util.List;

public interface ProjectRepository {
    List<Project> findAllProjects();
}
