package com.profile.svc.shared.mapper;

import com.profile.svc.admin.entities.ProjectEntity;
import com.profile.svc.shared.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface ProjectMapper {
    @Mapping(target = "imageMetaData", ignore = true)
    Project toProject(ProjectEntity project);
}
