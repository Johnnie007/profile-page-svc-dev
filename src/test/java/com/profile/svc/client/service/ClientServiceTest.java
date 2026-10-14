package com.profile.svc.client.service;

import com.profile.svc.model.Book;
import com.profile.svc.model.ClientModel;
import com.profile.svc.model.Project;
import com.profile.svc.model.UserDetails;
import com.profile.svc.repository.interfaces.BookDetailsRepository;
import com.profile.svc.repository.interfaces.ProjectRepository;
import com.profile.svc.repository.interfaces.UserDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private BookDetailsRepository bookDetailsRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    void gatherClientDataCombinesRepositoryResults() {
        Optional<UserDetails> userDetails = Optional.of(new UserDetails());
        List<Project> projects = List.of(new Project());
        List<Book> books = List.of(new Book());
        when(userDetailsRepository.obtainDetails()).thenReturn(userDetails);
        when(projectRepository.findAllProjects()).thenReturn(projects);
        when(bookDetailsRepository.getAllBooks()).thenReturn(books);

        ClientModel result = clientService.gatherClientData();

        assertSame(userDetails, result.getUserDetails());
        assertSame(projects, result.getProjects());
        assertSame(books, result.getBooks());
        verify(userDetailsRepository).obtainDetails();
        verify(projectRepository).findAllProjects();
        verify(bookDetailsRepository).getAllBooks();
    }
}
