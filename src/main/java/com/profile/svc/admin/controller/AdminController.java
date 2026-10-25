package com.profile.svc.admin.controller;

import com.profile.svc.admin.entities.BookEntity;
import com.profile.svc.admin.entities.ProjectEntity;
import com.profile.svc.admin.entities.UserEntity;
import com.profile.svc.shared.service.Processor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final Processor processService;
    @PostMapping( value = "/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveBook(@ModelAttribute BookEntity request)
            throws IOException {
        processService.processBookData(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping( value = "/projects", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveProject(@ModelAttribute ProjectEntity request)
            throws IOException {
        processService.processProjectData(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping( value = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveProject(@ModelAttribute UserEntity request)
            throws IOException {
        processService.processUserData(request);
        return ResponseEntity.ok().build();
    }
}
