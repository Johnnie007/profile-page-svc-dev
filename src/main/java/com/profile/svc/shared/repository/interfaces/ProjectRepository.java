package com.profile.svc.shared.repository.interfaces;

import com.profile.svc.shared.model.Project;
import java.util.List;

public interface ProjectRepository {
    List<Project> findAllProjects();
    void saveProject(Project project);
}
