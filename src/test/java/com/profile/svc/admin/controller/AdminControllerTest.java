package com.profile.svc.admin.controller;

import com.profile.svc.admin.entities.BookEntity;
import com.profile.svc.admin.entities.ProjectEntity;
import com.profile.svc.admin.entities.UserEntity;
import com.profile.svc.shared.service.Processor;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AdminControllerTest {
    @Test
    void saveBookDelegatesToProcessor() throws Exception {
        Processor processor = mock(Processor.class);
        AdminController controller = new AdminController(processor);
        BookEntity request = new BookEntity();

        var response = controller.saveBook(request);

        assertEquals(200, response.getStatusCode().value());
        verify(processor).processBookData(request);
    }

    @Test
    void saveProjectDelegatesToProcessor() throws Exception {
        Processor processor = mock(Processor.class);
        AdminController controller = new AdminController(processor);
        ProjectEntity request = new ProjectEntity();

        var response = controller.saveProject(request);

        assertEquals(200, response.getStatusCode().value());
        verify(processor).processProjectData(request);
    }

    @Test
    void saveUserDelegatesToProcessor() throws Exception {
        Processor processor = mock(Processor.class);
        AdminController controller = new AdminController(processor);
        UserEntity request = new UserEntity();

        var response = controller.saveProject(request);

        assertEquals(200, response.getStatusCode().value());
        verify(processor).processUserData(request);
    }

    @Test
    void endpointsDeclareMultipartFormData() throws Exception {
        assertEquals(MediaType.MULTIPART_FORM_DATA_VALUE,
                AdminController.class.getMethod("saveBook", BookEntity.class)
                        .getAnnotation(PostMapping.class)
                        .consumes()[0]);
        assertEquals(MediaType.MULTIPART_FORM_DATA_VALUE,
                AdminController.class.getMethod("saveProject", ProjectEntity.class)
                        .getAnnotation(PostMapping.class)
                        .consumes()[0]);
        assertEquals(MediaType.MULTIPART_FORM_DATA_VALUE,
                AdminController.class.getMethod("saveProject", UserEntity.class)
                        .getAnnotation(PostMapping.class)
                        .consumes()[0]);
    }
}
