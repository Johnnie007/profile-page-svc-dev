package com.profile.svc.model;

import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class Project {
    UUID id;
    String name;
    String Category;
    Date deployedDate;
    String externalLink;
    List<String> techStack;
    String previewImage;
}
