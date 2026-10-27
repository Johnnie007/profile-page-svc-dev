package com.profile.svc.admin.entities;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class ProjectEntity {
    UUID id;
    String name;
    List<String> category;
    Instant deployedDate;
    String externalLink;
    List<String> techStack;
    private MultipartFile imageMetaData;
}
