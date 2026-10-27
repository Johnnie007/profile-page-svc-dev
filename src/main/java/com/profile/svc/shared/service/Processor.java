package com.profile.svc.shared.service;

import com.profile.svc.admin.entities.BookEntity;
import com.profile.svc.admin.entities.ProjectEntity;
import com.profile.svc.admin.entities.UserEntity;
import com.profile.svc.shared.mapper.BookMapper;
import com.profile.svc.shared.mapper.ProjectMapper;
import com.profile.svc.shared.mapper.UserDetailsMapper;
import com.profile.svc.shared.model.Book;
import com.profile.svc.shared.model.Metadata;
import com.profile.svc.shared.model.Project;
import com.profile.svc.shared.model.UserDetails;
import com.profile.svc.shared.repository.interfaces.BookDetailsRepository;
import com.profile.svc.shared.repository.interfaces.ProjectRepository;
import com.profile.svc.shared.repository.interfaces.UserDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class Processor {
    MultimediaService multimediaService;
    BookMapper bookMapper;
    ProjectMapper projectMapper;
    UserDetailsMapper userDetailsMapper;
    BookDetailsRepository bookDetailsRepository;
    ProjectRepository projectRepository;
    UserDetailsRepository userDetailsRepository;

    public void processBookData(BookEntity entity) throws IOException {
        if (entity.getId() == null) entity.setId(UUID.randomUUID());

        Book book = bookMapper.toBook(entity);
        book.setCoverImage(uploadImageIfPresent(entity.getCoverImage()));
        book.setPdf(uploadPdfIfPresent(entity.getPdf()));

        bookDetailsRepository.saveBook(book);
    }

    public void processProjectData(ProjectEntity entity) throws IOException {
        if (entity.getId() == null) entity.setId(UUID.randomUUID());

        Project project = projectMapper.toProject(entity);
        project.setImageMetaData(uploadImageIfPresent(entity.getImageMetaData()));

        projectRepository.saveProject(project);
    }

    public void processUserData(UserEntity entity) throws IOException {
        if (entity.getId() == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Id must be provided.");
        }

        UserDetails userDetails = userDetailsMapper.toUser(entity);
        userDetails.setProfileImage(uploadImageIfPresent(entity.getProfileImage()));

        userDetailsRepository.saveUserDetails(userDetails);
    }

    private Metadata uploadImageIfPresent(MultipartFile file) throws IOException {
        return file == null || file.isEmpty()
                ? null
                : Metadata.from(multimediaService.saveImage(file));
    }

    private Metadata uploadPdfIfPresent(MultipartFile file) throws IOException {
        return file == null || file.isEmpty()
                ? null
                : Metadata.from(multimediaService.savePdf(file));
    }

}
