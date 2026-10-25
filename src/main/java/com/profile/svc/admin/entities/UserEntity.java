package com.profile.svc.admin.entities;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class UserEntity {
    UUID id;
    String firstName;
    String lastName;
    String mainText;
    String subText;
    MultipartFile profileImage;
}
