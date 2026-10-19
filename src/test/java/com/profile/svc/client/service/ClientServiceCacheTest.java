package com.profile.svc.client.service;

import com.profile.svc.model.Book;
import com.profile.svc.model.ClientModel;
import com.profile.svc.model.Project;
import com.profile.svc.model.UserDetails;
import com.profile.svc.repository.interfaces.BookDetailsRepository;
import com.profile.svc.repository.interfaces.ProjectRepository;
import com.profile.svc.repository.interfaces.UserDetailsRepository;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
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

    @Configuration
    @EnableCaching
    @Import(ClientService.class)
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
    }
}
