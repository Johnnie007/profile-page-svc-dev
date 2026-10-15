package com.profile.svc.client.repository;

import com.datastax.astra.client.tables.Table;
import com.datastax.astra.client.tables.cursor.TableFindCursor;
import com.profile.svc.model.Book;
import com.profile.svc.model.Project;
import com.profile.svc.model.UserDetails;
import com.profile.svc.repository.BookDetailsRepositoryImpl;
import com.profile.svc.repository.ProjectRepositoryImpl;
import com.profile.svc.repository.UserDetailsRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepositoryTest {

    @Test
    void projectRepositoryReturnsAllProjects() {
        Table<Project> table = mock(Table.class);
        TableFindCursor<Project, Project> cursor = mock(TableFindCursor.class);
        List<Project> projects = List.of(new Project(), new Project());
        when(table.findAll()).thenReturn(cursor);
        when(cursor.toList()).thenReturn(projects);
        ProjectRepositoryImpl repository = new ProjectRepositoryImpl(table);

        assertSame(projects, repository.findAllProjects());
        verify(table).findAll();
        verify(cursor).toList();
    }

    @Test
    void bookRepositoryReturnsAllBooks() {
        Table<Book> table = mock(Table.class);
        TableFindCursor<Book, Book> cursor = mock(TableFindCursor.class);
        List<Book> books = List.of(new Book());
        when(table.findAll()).thenReturn(cursor);
        when(cursor.toList()).thenReturn(books);
        BookDetailsRepositoryImpl repository = new BookDetailsRepositoryImpl(table);

        assertSame(books, repository.getAllBooks());
        verify(table).findAll();
        verify(cursor).toList();
    }

    @Test
    void userDetailsRepositoryReturnsFirstRecord() {
        Table<UserDetails> table = mock(Table.class);
        TableFindCursor<UserDetails, UserDetails> cursor = mock(TableFindCursor.class);
        UserDetails userDetails = new UserDetails();
        when(table.findAll()).thenReturn(cursor);
        when(cursor.stream()).thenReturn(Stream.of(userDetails));
        UserDetailsRepositoryImpl repository = new UserDetailsRepositoryImpl(table);

        assertEquals(Optional.of(userDetails), repository.obtainDetails());
        verify(table).findAll();
        verify(cursor).stream();
    }

    @Test
    void userDetailsRepositoryReturnsEmptyWhenNoRecordExists() {
        Table<UserDetails> table = mock(Table.class);
        TableFindCursor<UserDetails, UserDetails> cursor = mock(TableFindCursor.class);
        when(table.findAll()).thenReturn(cursor);
        when(cursor.stream()).thenReturn(Stream.empty());
        UserDetailsRepositoryImpl repository = new UserDetailsRepositoryImpl(table);

        assertEquals(Optional.empty(), repository.obtainDetails());
        verify(table).findAll();
        verify(cursor).stream();
    }
}
