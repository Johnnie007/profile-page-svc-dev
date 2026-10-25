package com.profile.svc.shared;

import com.profile.svc.admin.entities.BookEntity;
import com.profile.svc.admin.entities.ProjectEntity;
import com.profile.svc.admin.entities.UserEntity;
import com.profile.svc.shared.mapper.BookMapper;
import com.profile.svc.shared.mapper.ProjectMapper;
import com.profile.svc.shared.mapper.UserDetailsMapper;
import com.profile.svc.shared.model.Book;
import com.profile.svc.shared.model.Metadata;
import com.profile.svc.shared.model.MultimediaUploadResponse;
import com.profile.svc.shared.model.Project;
import com.profile.svc.shared.model.UserDetails;
import com.profile.svc.shared.repository.interfaces.BookDetailsRepository;
import com.profile.svc.shared.repository.interfaces.ProjectRepository;
import com.profile.svc.shared.repository.interfaces.UserDetailsRepository;
import com.profile.svc.shared.service.MultimediaService;
import com.profile.svc.shared.service.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProcessorTest {
    private MultimediaService multimediaService;
    private BookMapper bookMapper;
    private ProjectMapper projectMapper;
    private UserDetailsMapper userDetailsMapper;
    private BookDetailsRepository bookRepository;
    private ProjectRepository projectRepository;
    private UserDetailsRepository userRepository;
    private Processor processor;

    @BeforeEach
    void setUp() {
        multimediaService = mock(MultimediaService.class);
        bookMapper = mock(BookMapper.class);
        projectMapper = mock(ProjectMapper.class);
        userDetailsMapper = mock(UserDetailsMapper.class);
        bookRepository = mock(BookDetailsRepository.class);
        projectRepository = mock(ProjectRepository.class);
        userRepository = mock(UserDetailsRepository.class);
        processor = new Processor(
                multimediaService, bookMapper, projectMapper, userDetailsMapper,
                bookRepository, projectRepository, userRepository);
    }

    @Test
    void processBookUploadsAttachmentsMapsMetadataAndSaves() throws IOException {
        BookEntity entity = new BookEntity();
        MockMultipartFile cover = file("cover.png", "image/png");
        MockMultipartFile pdf = file("book.pdf", "application/pdf");
        entity.setCoverImage(cover);
        entity.setPdf(pdf);
        Book book = new Book();
        when(bookMapper.toBook(entity)).thenAnswer(invocation -> {
            book.setId(entity.getId());
            return book;
        });
        when(multimediaService.saveImage(cover)).thenReturn(upload("cover-id"));
        when(multimediaService.savePdf(pdf)).thenReturn(upload("pdf-id"));

        processor.processBookData(entity);

        assertNotNull(entity.getId());
        assertEquals(entity.getId(), book.getId());
        assertEquals("cover-id", book.getCoverImage().getId());
        assertEquals("pdf-id", book.getPdf().getId());
        verify(bookRepository).saveBook(book);
    }

    @Test
    void processBookWithoutAttachmentsSavesNullMetadata() throws IOException {
        BookEntity entity = new BookEntity();
        Book book = new Book();
        when(bookMapper.toBook(entity)).thenAnswer(invocation -> {
            book.setId(entity.getId());
            return book;
        });

        processor.processBookData(entity);

        assertNotNull(entity.getId());
        assertEquals(entity.getId(), book.getId());
        assertNull(book.getCoverImage());
        assertNull(book.getPdf());
        verify(multimediaService, never()).saveImage(org.mockito.ArgumentMatchers.any());
        verify(multimediaService, never()).savePdf(org.mockito.ArgumentMatchers.any());
        verify(bookRepository).saveBook(book);
    }

    @Test
    void processProjectUploadsOptionalImageAndSaves() throws IOException {
        ProjectEntity entity = new ProjectEntity();
        MockMultipartFile image = file("project.png", "image/png");
        entity.setImageMetaData(image);
        Project project = new Project();
        when(projectMapper.toProject(entity)).thenAnswer(invocation -> {
            project.setId(entity.getId());
            return project;
        });
        when(multimediaService.saveImage(image)).thenReturn(upload("project-image"));

        processor.processProjectData(entity);

        assertNotNull(entity.getId());
        assertEquals(entity.getId(), project.getId());
        assertEquals("project-image", project.getImageMetaData().getId());
        verify(projectRepository).saveProject(project);
    }

    @Test
    void processUserRequiresExistingId() {
        UserEntity entity = new UserEntity();

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class, () -> processor.processUserData(entity));

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        verify(userDetailsMapper, never()).toUser(entity);
        verify(userRepository, never()).saveUserDetails(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void processUserUploadsImageAndSavesForProvidedId() throws IOException {
        UUID id = UUID.randomUUID();
        UserEntity entity = new UserEntity();
        entity.setId(id);
        MockMultipartFile image = file("profile.png", "image/png");
        entity.setProfileImage(image);
        UserDetails userDetails = new UserDetails();
        when(userDetailsMapper.toUser(entity)).thenAnswer(invocation -> {
            userDetails.setId(entity.getId());
            return userDetails;
        });
        when(multimediaService.saveImage(image)).thenReturn(upload("user-image"));

        processor.processUserData(entity);

        assertEquals(id, userDetails.getId());
        assertEquals("user-image", userDetails.getProfileImage().getId());
        verify(userRepository).saveUserDetails(userDetails);
    }

    private MockMultipartFile file(String name, String contentType) {
        return new MockMultipartFile("file", name, contentType, new byte[]{1, 2});
    }

    private MultimediaUploadResponse upload(String id) {
        return new MultimediaUploadResponse(
                id, "https://cdn.example/" + id, "png", 2L);
    }
}
