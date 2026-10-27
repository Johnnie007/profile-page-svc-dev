package com.profile.svc.shared.mapper;

import com.profile.svc.admin.entities.ProjectEntity;
import com.profile.svc.admin.entities.UserEntity;
import com.profile.svc.shared.model.Project;
import com.profile.svc.shared.model.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDetailsMapper {
    @Mapping(target = "profileImage", ignore = true)
    UserDetails toUser(UserEntity user);
}
