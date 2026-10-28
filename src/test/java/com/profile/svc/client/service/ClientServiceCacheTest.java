package com.profile.svc.client.service;

import com.profile.svc.admin.entities.ProjectEntity;
import com.profile.svc.shared.mapper.BookMapper;
import com.profile.svc.shared.mapper.ProjectMapper;
import com.profile.svc.shared.mapper.UserDetailsMapper;
import com.profile.svc.shared.model.Book;
import com.profile.svc.shared.model.ClientModel;
import com.profile.svc.shared.model.Project;
import com.profile.svc.shared.model.UserDetails;
import com.profile.svc.shared.repository.interfaces.BookDetailsRepository;
import com.profile.svc.shared.repository.interfaces.ProjectRepository;
import com.profile.svc.shared.repository.interfaces.UserDetailsRepository;
import com.profile.svc.shared.service.MultimediaService;
import com.profile.svc.shared.service.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(ClientServiceCacheTest.TestConfig.class)
class ClientServiceCacheTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private BookDetailsRepository bookDetailsRepository;

    @Autowired
    private Processor processor;

    @Autowired
    private ProjectMapper projectMapper;

    @BeforeEach
    void clearCacheAndResetRepositories() {
        cacheManager.getCache("client-details-v2").clear();
        reset(projectRepository, userDetailsRepository, bookDetailsRepository);
    }

    @Test
    void gatherClientDataUsesCachedResultOnSubsequentCalls() {
        Optional<UserDetails> userDetails = Optional.of(new UserDetails());
        List<Project> projects = List.of(new Project());
        List<Book> books = List.of(new Book());
        when(userDetailsRepository.obtainDetails()).thenReturn(userDetails);
        when(projectRepository.findAllProjects()).thenReturn(projects);
        when(bookDetailsRepository.getAllBooks()).thenReturn(books);

        ClientModel firstResult = clientService.gatherClientData();
        ClientModel secondResult = clientService.gatherClientData();

        assertSame(firstResult, secondResult);
        assertSame(userDetails, secondResult.getUserDetails());
        assertSame(projects, secondResult.getProjects());
        assertSame(books, secondResult.getBooks());
        verify(userDetailsRepository).obtainDetails();
        verify(projectRepository).findAllProjects();
        verify(bookDetailsRepository).getAllBooks();
    }

    @Test
    void savingProjectEvictsCachedClientData() throws Exception {
        List<Project> existingProjects = List.of(new Project());
        List<Project> updatedProjects = List.of(new Project(), new Project());
        when(projectRepository.findAllProjects()).thenReturn(existingProjects, updatedProjects);

        ClientModel cachedResult = clientService.gatherClientData();
        ProjectEntity entity = new ProjectEntity();
        when(projectMapper.toProject(entity)).thenReturn(new Project());

        processor.processProjectData(entity);

        ClientModel refreshedResult = clientService.gatherClientData();

        assertSame(existingProjects, cachedResult.getProjects());
        assertSame(updatedProjects, refreshedResult.getProjects());
        verify(projectRepository).saveProject(any(Project.class));
        verify(projectRepository, times(2)).findAllProjects();
    }

    @Configuration
    @EnableCaching
    @Import({ClientService.class, Processor.class})
    static class TestConfig {

        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("client-details-v2");
        }

        @Bean
        ProjectRepository projectRepository() {
            return mock(ProjectRepository.class);
        }

        @Bean
        UserDetailsRepository userDetailsRepository() {
            return mock(UserDetailsRepository.class);
        }

        @Bean
        BookDetailsRepository bookDetailsRepository() {
            return mock(BookDetailsRepository.class);
        }

        @Bean
        ProjectMapper projectMapper() {
            return mock(ProjectMapper.class);
        }

        @Bean
        BookMapper bookMapper() {
            return mock(BookMapper.class);
        }

        @Bean
        UserDetailsMapper userDetailsMapper() {
            return mock(UserDetailsMapper.class);
        }

        @Bean
        MultimediaService multimediaService() {
            return mock(MultimediaService.class);
        }
    }
}
