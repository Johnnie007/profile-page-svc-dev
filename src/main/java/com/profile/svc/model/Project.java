package com.profile.svc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Entity
@Data
public class Project {
    @Id
    String id;
    @Column(name = "first_name")
    String name;
    @Column(name = "category")
    String category;
    @Column(name = "year_deployed")
    Date deployedDate;
    @Column(name = "link")
    String externalLink;
    @Column(name = "stack")
    List<String> techStack;
    @Column(name = "image")
    String previewImage;
}
